package com.aariyan.stockmover.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aariyan.stockmover.Common.Constant;
import com.aariyan.stockmover.Database.DatabaseAdapter;
import com.aariyan.stockmover.Interface.ProductSyncInterface;
import com.aariyan.stockmover.Model.LocationSyncModel;
import com.aariyan.stockmover.Model.ProductsSyncModel;
import com.aariyan.stockmover.R;
import com.aariyan.stockmover.Validation.InputValidation;

import java.util.ArrayList;
import java.util.List;

public class ScanShelfActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText enterLocation;
    private TextView nextBtn;
    private TextView topTitle;


    boolean checkClick = false;
    private ProgressBar progressBar;
    private ImageView backBtn;

    DatabaseAdapter databaseAdapter;
    private List<LocationSyncModel> listOfLocation = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_shelf);
        databaseAdapter = new DatabaseAdapter(this);

        initUI();

        if (getIntent() != null) {
            Constant.STOCK_TYPE = getIntent().getStringExtra("type");
            if (Constant.STOCK_TYPE.equals("out")) {
                topTitle.setText("Stock Out");
            } else {
                topTitle.setText("Stock-In");
            }
        }
    }

    private void initUI() {

        topTitle = findViewById(R.id.topTitle);

        enterLocation = findViewById(R.id.enterLocation);

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(this);

        nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(this);

        progressBar = findViewById(R.id.pBar);

        //validation();
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        int id = view.getId();
        switch (id) {
            case R.id.nextBtn:
                listOfLocation.clear();
                listOfLocation = databaseAdapter.getLocation(enterLocation.getText().toString());
                if (listOfLocation.size() > 0) {
                    intent = new Intent(ScanShelfActivity.this, SelectionActivity.class);
                    intent.putExtra("type", Constant.STOCK_TYPE);
                    intent.putExtra("loc", enterLocation.getText().toString());
                    Constant.location = enterLocation.getText().toString();
                    startActivity(intent);
                } else {
                    enterLocation.setError("Invalid Location");
                    enterLocation.requestFocus();
                    Toast.makeText(this, "Invalid Location", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.backBtn:
                checkClick = true;
                onBackPressed();
                break;
        }
    }

    // Validation of location input:
    private void validation() {
        InputValidation validation = new InputValidation(ScanShelfActivity.this);
        enterLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = charSequence.toString();
                validation.locationValidation(input,enterLocation, nextBtn);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (!checkClick) {
            Toast.makeText(this, "Click Top Left Corner Icon!", Toast.LENGTH_LONG).show();
        } else {
            super.onBackPressed();
        }

    }
}