package com.aariyan.stockmover.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aariyan.stockmover.Common.Constant;
import com.aariyan.stockmover.Database.DatabaseAdapter;
import com.aariyan.stockmover.R;

import java.util.Calendar;

public class ActionActivity extends AppCompatActivity {

    private TextView topTitle;
    private EditText enterQuantity;
    private TextView enterDate, saveBtn;
    private ImageView backBtn;
    boolean checkClick = false;

    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    int day, month, year;
    String date = "";

    DatabaseAdapter databaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseAdapter = new DatabaseAdapter(this);

        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        setContentView(R.layout.activity_action);
        initUI();
        if (!Constant.PRODUCT_CODE.equals("")) {
            topTitle.setText(Constant.PRODUCT_CODE + " Product Id");
        }

        if (Constant.STOCK_TYPE.equals("out")) {
            enterDate.setVisibility(View.VISIBLE);
        } else {
            enterDate.setVisibility(View.GONE);
        }
    }

    private void initUI() {
        topTitle = findViewById(R.id.topTitle);
        enterQuantity = findViewById(R.id.enterQuantity);
        enterDate = findViewById(R.id.enterDate);
        saveBtn = findViewById(R.id.saveBtn);
        backBtn = findViewById(R.id.backBtn);

        enterDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTheDate();
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkClick = true;
                onBackPressed();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Constant.STOCK_TYPE.equals("out")) {
                    if (date.equals("")) {
                        enterDate.setError("Select date!");
                        enterDate.requestFocus();
                        return;
                    }
                }

                if (TextUtils.isEmpty(enterQuantity.getText().toString())) {
                    enterQuantity.setError("Enter quantity!");
                    enterQuantity.requestFocus();
                    return;
                }

                if (Constant.STOCK_TYPE.equals("out")) {
                    saveOnLocalDatabase(enterQuantity.getText().toString(), date, "OUT");
                } else {
                    saveOnLocalDatabase(enterQuantity.getText().toString(), "in", "IN");
                }

            }
        });
    }

    private void saveOnLocalDatabase(String quantity, String date, String type) {
        //long id = databaseAdapter.insertStocks(Constant.PRODUCT_CODE, date, quantity, Constant.STOCK_TYPE);
        long id = databaseAdapter.insertStocks("" + Constant.location, "" + quantity, "", "" + date,
                "" + Constant.PRODUCT_CODE, ""+type);
        if (id > 0) {
            Toast.makeText(this, "Inserted!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ActionActivity.this, HomeActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Failed to insert!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (!checkClick) {
            Toast.makeText(this, "Click Top Left Corner Icon!", Toast.LENGTH_LONG).show();
        } else {
            super.onBackPressed();
        }

    }

    private void getTheDate() {
        datePickerDialog = new DatePickerDialog(ActionActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                int j = i1 + 1;
                date = i + " - " + j + " - " + i2;
                enterDate.setText(date);
            }
        }, day, month, year);

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        datePickerDialog.show();
    }
}