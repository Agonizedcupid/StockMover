package com.aariyan.stockmover.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.aariyan.stockmover.Adapter.ProductAdapter;
import com.aariyan.stockmover.Database.DatabaseAdapter;
import com.aariyan.stockmover.Model.ProductsSyncModel;
import com.aariyan.stockmover.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.List;

public class SelectionActivity extends AppCompatActivity implements View.OnClickListener {

    private SwitchMaterial switching;
    private CardView barcodeCard;
    private RecyclerView productRecyclerView;
    DatabaseAdapter databaseAdapter;
    private ProductAdapter adapter;

    private ImageView backBtn;

    boolean checkClick = false;


    private List<ProductsSyncModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        databaseAdapter = new DatabaseAdapter(this);


        initUI();
    }

    private void initUI() {

        switching = findViewById(R.id.switching);
        switching.setOnClickListener(this);

        barcodeCard = findViewById(R.id.barcodeLayout);
        productRecyclerView = findViewById(R.id.productRecyclerview);
        productRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(this);


        loadProduct();

    }

    private void loadProduct() {
        list.clear();
        list = databaseAdapter.getProduct();
        if (list.size() > 0) {
            adapter = new ProductAdapter(SelectionActivity.this, list);
            productRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "No product found!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.switching:
                if (switching.isChecked()) {
                    productRecyclerView.setVisibility(View.VISIBLE);
                    barcodeCard.setVisibility(View.GONE);
                } else {
                    productRecyclerView.setVisibility(View.GONE);
                    barcodeCard.setVisibility(View.VISIBLE);
                }
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
}