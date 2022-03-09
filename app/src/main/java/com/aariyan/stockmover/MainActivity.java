package com.aariyan.stockmover;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aariyan.stockmover.Activity.HomeActivity;
import com.aariyan.stockmover.Common.Constant;
import com.aariyan.stockmover.Dialog.ProgressDialog;
import com.aariyan.stockmover.Networking.ApiInterface;
import com.aariyan.stockmover.Networking.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONObject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText userName, pinCode;
    private TextView logInBtn;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Log-In");

        //Instantiate UI:
        initUI();

    }

    private void initUI() {
        userName = findViewById(R.id.logInUserNameEdtText);
        pinCode = findViewById(R.id.logInPinCodeEdtText);
        logInBtn = findViewById(R.id.logInBtn);
        logInBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.logInBtn:
                logInValidationCheck();
                break;
        }
    }

    private void logInValidationCheck() {
        String name = userName.getText().toString();
        String pin = pinCode.getText().toString();

        if (TextUtils.isEmpty(name)) {
            userName.setError("Please enter name!");
            userName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(pin)) {
            pinCode.setError("Please enter pin!");
            pinCode.requestFocus();
            return;
        }

        ApiInterface apis = RetrofitClient.getClient().create(ApiInterface.class);
        compositeDisposable.add(apis.loggedIn(name, Integer.parseInt(pin))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Throwable {
                        JSONArray root = new JSONArray(responseBody.string());
                        if (root.length() > 0) {
                            for (int i = 0; i < root.length(); i++) {
                                JSONObject single = root.getJSONObject(i);
                                Constant.userID = single.getString("UserID");
                                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Invalid User!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Toast.makeText(MainActivity.this, "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }));

    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}