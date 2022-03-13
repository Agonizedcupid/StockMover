package com.aariyan.stockmover;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aariyan.stockmover.Activity.HomeActivity;
import com.aariyan.stockmover.Common.Constant;
import com.aariyan.stockmover.Dialog.ProgressDialog;
import com.aariyan.stockmover.Networking.ApiCalling;
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

    //CompositeDisposable compositeDisposable = new CompositeDisposable();

    private ProgressBar progressBar;

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

        progressBar = findViewById(R.id.progressbar);
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
        progressBar.setVisibility(View.VISIBLE);
        ApiCalling apiCalling = new ApiCalling(MainActivity.this);
        apiCalling.postLogIn(name,pin,progressBar);

    }


}