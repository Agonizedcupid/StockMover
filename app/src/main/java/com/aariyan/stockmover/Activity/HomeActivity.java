package com.aariyan.stockmover.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aariyan.stockmover.Interface.ProductSyncInterface;
import com.aariyan.stockmover.Model.ProductsSyncModel;
import com.aariyan.stockmover.Networking.ApiCalling;
import com.aariyan.stockmover.R;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView stockOutBtn, stockInBtn, uploadDocumentBtn, syncProductBtn, syncLocationBtn;

    private ImageView backBtn;

    boolean checkClick = false;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initUI();
    }

    private void initUI() {
        stockOutBtn = findViewById(R.id.stockOutBtn);
        stockOutBtn.setOnClickListener(this);

        stockInBtn = findViewById(R.id.stockInBtn);
        stockInBtn.setOnClickListener(this);

        uploadDocumentBtn = findViewById(R.id.uploadDocumentBtn);
        uploadDocumentBtn.setOnClickListener(this);

        syncLocationBtn = findViewById(R.id.syncLocationBtn);
        syncLocationBtn.setOnClickListener(this);

        syncProductBtn = findViewById(R.id.syncProductBtn);
        syncProductBtn.setOnClickListener(this);

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(this);

        progressBar = findViewById(R.id.pBar);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.stockOutBtn:
                Toast.makeText(this, "Stock Out", Toast.LENGTH_SHORT).show();
                break;
            case R.id.stockInBtn:
                Toast.makeText(this, "Stock In", Toast.LENGTH_SHORT).show();
                break;

            case R.id.uploadDocumentBtn:
                Toast.makeText(this, "Upload Document", Toast.LENGTH_SHORT).show();
                break;

            case R.id.syncLocationBtn:
                Toast.makeText(this, "Location Synced", Toast.LENGTH_SHORT).show();
                break;

            case R.id.syncProductBtn:
                ApiCalling apiCalling = new ApiCalling(HomeActivity.this);
                apiCalling.productSync(progressBar, new ProductSyncInterface() {
                    @Override
                    public void productList(List<ProductsSyncModel> list) {
                        //apiCalling.insertProductIntoSQLite(list);
                    }

                    @Override
                    public void error(String message) {
                        Toast.makeText(HomeActivity.this, "" + message,Toast.LENGTH_SHORT).show();
                    }
                });
                break;

            case R.id.backBtn:
                checkClick = true;
                onBackPressed();
                break;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}