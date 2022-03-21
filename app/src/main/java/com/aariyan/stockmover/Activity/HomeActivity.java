package com.aariyan.stockmover.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aariyan.stockmover.Database.DatabaseAdapter;
import com.aariyan.stockmover.Interface.DeletePostingData;
import com.aariyan.stockmover.Interface.ProductSyncInterface;
import com.aariyan.stockmover.Model.PostLines;
import com.aariyan.stockmover.Model.ProductsSyncModel;
import com.aariyan.stockmover.Model.StockModel;
import com.aariyan.stockmover.Networking.ApiCalling;
import com.aariyan.stockmover.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView stockOutBtn, stockInBtn, uploadDocumentBtn, syncProductBtn, syncLocationBtn;

    private ImageView backBtn;

    boolean checkClick = false;
    private ProgressBar progressBar;

    private List<PostLines> posting = new ArrayList<>();
    private List<StockModel> list = new ArrayList<>();

    DatabaseAdapter databaseAdapter;

    DeletePostingData deletePostingData;

    List<StockModel> getStock = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        databaseAdapter = new DatabaseAdapter(this);


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

        getStock = databaseAdapter.getStock(deviceId());
    }

    @Override
    public void onClick(View view) {
        ApiCalling apiCalling = new ApiCalling(HomeActivity.this);
        Intent intent;
        int id = view.getId();
        switch (id) {
            case R.id.stockOutBtn:
                intent = new Intent(HomeActivity.this, ScanShelfActivity.class);
                intent.putExtra("type", "out");
                startActivity(intent);
                break;
            case R.id.stockInBtn:
                intent = new Intent(HomeActivity.this, ScanShelfActivity.class);
                intent.putExtra("type", "in");
                startActivity(intent);
                break;

            case R.id.uploadDocumentBtn:
                uploadDocument();
//                intent = new Intent(HomeActivity.this, UploadDocument.class);
//                startActivity(intent);
                break;

            case R.id.syncLocationBtn:
                Toast.makeText(this, "Sync Started, Please wait!", Toast.LENGTH_SHORT).show();
                apiCalling.locationSync(progressBar);
                break;

            case R.id.syncProductBtn:
                Toast.makeText(this, "Sync Started, Please wait!", Toast.LENGTH_SHORT).show();
                apiCalling.productSync(progressBar, new ProductSyncInterface() {
                    @Override
                    public void productList(List<ProductsSyncModel> list) {
                        //apiCalling.insertProductIntoSQLite(list);
                    }

                    @Override
                    public void error(String message) {
                        Toast.makeText(HomeActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                    }
                });
                break;

            case R.id.backBtn:
                checkClick = true;
                onBackPressed();
                break;
        }
    }

    public String deviceId() {
        Long tsLong = System.currentTimeMillis() / 1000;
        String ts = tsLong.toString();
        String subscriberId = ts + "-" + Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return subscriberId;
    }

    private void uploadDocument() {
        progressBar.setVisibility(View.VISIBLE);
        if (getStock.size() > 0) {
            StringRequest mStringRequest = new StringRequest(
                    Request.Method.POST,
                    "http://102.37.0.48/StockMover/postLines.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Log.d("FEEDBACK", response);
                            Toast.makeText(HomeActivity.this, response.toString() + " Posted successfully!", Toast.LENGTH_SHORT).show();
                            //Now Removing the data from SQLite:
                            //deleteUploadedJobs();
                            progressBar.setVisibility(View.GONE);
                            databaseAdapter.dropStockTable();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressBar.setVisibility(View.GONE);
                }
            }
            ) {
                @Override
                public byte[] getBody() throws AuthFailureError {
                    String jsonString = new Gson().toJson(getStock).toString();
                    return jsonString.getBytes();
                }
            };
            Volley.newRequestQueue(HomeActivity.this).add(mStringRequest);
        } else {
            Toast.makeText(HomeActivity.this, "Not enough data!", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
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