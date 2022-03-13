package com.aariyan.stockmover.Networking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aariyan.stockmover.Activity.HomeActivity;
import com.aariyan.stockmover.Common.Constant;
import com.aariyan.stockmover.Database.DatabaseAdapter;
import com.aariyan.stockmover.Dialog.ProgressDialog;
import com.aariyan.stockmover.Interface.ProductSyncInterface;
import com.aariyan.stockmover.MainActivity;
import com.aariyan.stockmover.Model.ProductsSyncModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class ApiCalling {

    private Context context;
    ApiInterface apis = RetrofitClient.getClient().create(ApiInterface.class);
    CompositeDisposable logInDisposable = new CompositeDisposable();
    CompositeDisposable productSyncDisposable = new CompositeDisposable();

    private List<ProductsSyncModel> listOfProduct = new ArrayList<>();
    DatabaseAdapter adapter;
    Activity activity;

    public ApiCalling(Context context) {
        this.context = context;
        adapter = new DatabaseAdapter(context);
        activity = (Activity) context;
    }

    public void postLogIn(String userName, String pinCode, ProgressBar progressBar) {
        logInDisposable.add(apis.loggedIn(userName, Integer.parseInt(pinCode))
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
                                context.startActivity(new Intent(context, HomeActivity.class));
                                progressBar.setVisibility(View.GONE);
                            }
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(context, "Invalid User!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(context, "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    public void productSync(ProgressBar progressBar, ProductSyncInterface productSyncInterface) {
        progressBar.setVisibility(View.VISIBLE);
        productSyncDisposable.add(apis.syncProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Throwable {
                        JSONArray root = new JSONArray(responseBody.string());
                        listOfProduct.clear();
                        if (root.length() > 0) {
                            for (int i = 0; i < root.length(); i++) {
                                JSONObject single = root.getJSONObject(i);
                                String PastelCode = single.getString("PastelCode");
                                String Barcode = single.getString("Barcode");
                                ProductsSyncModel model = new ProductsSyncModel(
                                        PastelCode, Barcode
                                );
                                listOfProduct.add(model);
                            }
                            insertProductIntoSQLite(listOfProduct);
                            productSyncInterface.productList(listOfProduct);
                        } else {
                            Toast.makeText(context, "No data found!", Toast.LENGTH_SHORT).show();
                            productSyncInterface.error("No data found!");
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        productSyncInterface.error("Error: " + throwable.getMessage());
                        Toast.makeText(context, "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }));
    }

    public void insertProductIntoSQLite(List<ProductsSyncModel> list) {
        Observable observable = Observable.fromIterable(list).observeOn(Schedulers.newThread());
        Toast.makeText(context, "" + list.size(), Toast.LENGTH_SHORT).show();
        Observer observer = new Observer() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                ProductsSyncModel model = (ProductsSyncModel) o;
                adapter.insertProducts(model.getBarcode(), model.getPastelCode());
               // Log.d("THREAD_CHECKING", Thread.currentThread().getName().toString());
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Sync Completed!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        observable.subscribe(observer);
    }
}
