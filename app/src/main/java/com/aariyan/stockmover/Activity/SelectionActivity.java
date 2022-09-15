package com.aariyan.stockmover.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aariyan.stockmover.Adapter.ProductAdapter;
import com.aariyan.stockmover.Common.Constant;
import com.aariyan.stockmover.Database.DatabaseAdapter;
import com.aariyan.stockmover.Interface.ItemClickListener;
import com.aariyan.stockmover.Model.ProductsSyncModel;
import com.aariyan.stockmover.Model.QueueModel;
import com.aariyan.stockmover.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.List;

public class SelectionActivity extends AppCompatActivity implements View.OnClickListener, ItemClickListener {

    private SwitchMaterial switching;
    private CardView barcodeCard;
    private RecyclerView productRecyclerView;
    DatabaseAdapter databaseAdapter;
    private ProductAdapter adapter;

    private EditText searchProduct;

    private TextView nextBtn;

    private ImageView backBtn;

    private EditText enterBarcode;

    boolean checkClick = false;
    private boolean checkSelection = false;


    private List<ProductsSyncModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        databaseAdapter = new DatabaseAdapter(this);

        initUI();

        Constant.STOCK_TYPE = getIntent().getStringExtra("type");
        Constant.location = getIntent().getStringExtra("loc");

    }

    private void initUI() {

        switching = findViewById(R.id.switching);
        switching.setOnClickListener(this);

        searchProduct = findViewById(R.id.searchProduct);

        barcodeCard = findViewById(R.id.barcodeLayout);
        productRecyclerView = findViewById(R.id.productRecyclerview);
        productRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(this);

        nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(this);

        enterBarcode = findViewById(R.id.enterBarcode);

        searchProduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        //loadProduct();

    }

    private void loadProduct() {
        list.clear();
        list = databaseAdapter.getProduct();
        if (list.size() > 0) {
            adapter = new ProductAdapter(SelectionActivity.this, list, SelectionActivity.this);
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
                    Constant.selectionType = Constant.typeProduct;
                    productRecyclerView.setVisibility(View.VISIBLE);
                    barcodeCard.setVisibility(View.GONE);
                    searchProduct.setVisibility(View.VISIBLE);
                    checkSelection = false;
                } else {
                    productRecyclerView.setVisibility(View.GONE);
                    barcodeCard.setVisibility(View.VISIBLE);
                    Constant.selectionType = Constant.typeQRCode;
                    searchProduct.setVisibility(View.GONE);
                    checkSelection = true;
                }
                break;

            case R.id.backBtn:
                checkClick = true;
                onBackPressed();
                break;

            case R.id.nextBtn:
                //moveToAction();
                saveProduct();
                break;
        }
    }

    private void saveProduct() {
        String barcode = enterBarcode.getText().toString().trim();
        String location = Constant.location;

        Toast.makeText(this, ""+Constant.STOCK_TYPE, Toast.LENGTH_SHORT).show();
        String moveFrom = "", moveIn = "";
        if (Constant.STOCK_TYPE.equals("MOVE_FROM")) {
            moveFrom = "1";
            moveIn = "0";
        } else {
            moveFrom = "0";
            moveIn = "1";
        }

        QueueModel model = new QueueModel("" + moveIn, "" + moveFrom, location, barcode);

        //Insert Operations:
        long id = databaseAdapter.insertQueue(model);

        startActivity(new Intent(this, HomeActivity.class));
        finish();
        //List<QueueModel> list = databaseAdapter.getQueueByBarcode(barcode);
//        if (list.size() == 1) {
//            //Update Operations:
//            QueueModel mod = list.get(0);
//            if (mod.getMoveFrom().equals("0") && Constant.STOCK_TYPE.equals("MOVE_FROM")) {
//                long id = databaseAdapter.updateQueueByBarcode("MOVE_FROM",barcode);
//            }
//            if (mod.getMoveFrom().equals("1") && Constant.STOCK_TYPE.equals("MOVE_FROM")) {
//                Toast.makeText(this, "Item already moved from", Toast.LENGTH_SHORT).show();
//            }
//
//            if (mod.getMoveIn().equals("0") && Constant.STOCK_TYPE.equals("MOVE_IN")) {
//                long id = databaseAdapter.updateQueueByBarcode("MOVE_IN",barcode);
//            }
//            if (mod.getMoveIn().equals("1") && Constant.STOCK_TYPE.equals("MOVE_IN")) {
//                Toast.makeText(this, "Item already moved in", Toast.LENGTH_SHORT).show();
//            }
//
//        } else {
//            //Insert Operations:
//            long id = databaseAdapter.insertQueue(model);
//            if (id > 0) {
//                Toast.makeText(this, "Saved On Queue", Toast.LENGTH_SHORT).show();
//            }
//        }


    }

    private boolean isBarcodeValidated() {
        List<ProductsSyncModel> productList = databaseAdapter.getProductByBarcode(enterBarcode.getText().toString().trim());
        if (productList.size() > 0) {
            Constant.PRODUCT_CODE = productList.get(0).getPastelCode();
            return true;
        } else {
            loadProduct();
            Toast.makeText(this, "No Product Found with " + enterBarcode.getText().toString(), Toast.LENGTH_SHORT).show();
            return false;
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
    public void onItemClick(String productId, String barcode) {
        Constant.PRODUCT_CODE = productId;
        Constant.BARCODE = barcode;
        startActivity(new Intent(SelectionActivity.this, ActionActivity.class));
    }

    private void moveToAction() {
        if (isBarcodeValidated()) {
            Constant.BARCODE = enterBarcode.getText().toString();
            startActivity(new Intent(SelectionActivity.this, ActionActivity.class));
        }
    }
}