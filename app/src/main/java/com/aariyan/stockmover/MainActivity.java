package com.aariyan.stockmover;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aariyan.stockmover.Activity.HomeActivity;
import com.aariyan.stockmover.Common.Constant;
import com.aariyan.stockmover.Database.S_Preferences;
import com.aariyan.stockmover.Dialog.ProgressDialog;
import com.aariyan.stockmover.Networking.ApiCalling;
import com.aariyan.stockmover.Networking.ApiInterface;
import com.aariyan.stockmover.Networking.RetrofitClient;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

    private FloatingActionButton closeApps;

    //CompositeDisposable compositeDisposable = new CompositeDisposable();

    private ProgressBar progressBar;

    private EditText ipField;
    private Button saveBtn, exitBtn;

    private View ipBottomSheet;
    BottomSheetBehavior ipBehavior;
    private static SharedPreferences sharedPreferences;
    public static String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Log-In");
        sharedPreferences = getSharedPreferences("ip", Context.MODE_PRIVATE);

        //Instantiate UI:
        initUI();

    }

    private void initUI() {
        userName = findViewById(R.id.logInUserNameEdtText);
        pinCode = findViewById(R.id.logInPinCodeEdtText);
        logInBtn = findViewById(R.id.logInBtn);
        logInBtn.setOnClickListener(this);

        closeApps = findViewById(R.id.closeApps);
        closeApps.setOnClickListener(this);

        saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(this);
        exitBtn = findViewById(R.id.exitBtn);
        exitBtn.setOnClickListener(this);

        ipField = findViewById(R.id.ipField);

        ipBottomSheet = findViewById(R.id.bottomSheetForIp);
        ipBehavior = BottomSheetBehavior.from(ipBottomSheet);


        progressBar = findViewById(R.id.progressbar);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.logInBtn:
                logInValidationCheck();
                break;

            case R.id.closeApps:
                setUps();
                break;

            case R.id.saveBtn:
                saveIp();
                break;

            case R.id.exitBtn:
                exitApp();
                break;
        }
    }

    private void exitApp() {
        ipBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    private void saveIp() {
        if (ipField.getText().toString().endsWith("/")) {
            ipBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            saveIpOnSharedPreferences();
        } else {
            Toast.makeText(MainActivity.this, "Ip should end with a / (Forward slash)", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveIpOnSharedPreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("BASE_URL", ipField.getText().toString());
        editor.commit();
    }

    private void setUps() {
        showAlertDialog();
    }

    private void showAlertDialog() {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.app_close_dialog, null);
        TextView setUps = view.findViewById(R.id.setUps);
        TextView yes = view.findViewById(R.id.yes);
        TextView no = view.findViewById(R.id.no);

        setUps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                //saving the value on shared preference:
                ipBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                //getURL();
                ipField.setText(getURL(), TextView.BufferType.EDITABLE);
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                System.exit(0);
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.setContentView(view);
        dialog.show();
    }

    public static String getURL() {
        return sharedPreferences.getString("BASE_URL", "http://102.37.0.48/");
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
        apiCalling.postLogIn(name, pin, progressBar);

    }


}