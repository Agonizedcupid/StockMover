package com.aariyan.stockmover.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.aariyan.stockmover.R;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView stockOutBtn,stockInBtn,uploadDocumentBtn,syncProductBtn,syncLocationBtn;

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
                Toast.makeText(this, "Product Synced", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}