package com.aariyan.stockmover.Validation;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aariyan.stockmover.Database.DatabaseAdapter;
import com.aariyan.stockmover.Model.LocationSyncModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class InputValidation {

    private Context context;
    DatabaseAdapter databaseAdapter;
    List<LocationSyncModel>foundLocation = new ArrayList<>();
    private Activity activity;
    public InputValidation(Context context) {
        this.context = context;
        activity = (Activity) context;
        databaseAdapter = new DatabaseAdapter(context);
    }

    public void locationValidation(String character, EditText enterLocation, TextView nextBtn) {
        foundLocation.clear();
        Observable<String> observable = Observable.just(character).observeOn(Schedulers.io());
        Observer observer = new Observer() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                String inputCharacter = (String) o;
                foundLocation = databaseAdapter.getLocation(inputCharacter);
                if (foundLocation.size() > 0) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            enterLocation.setError("Not found!");
                            enterLocation.requestFocus();
                            return;
                        }
                    });
                } else {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "Location Found", Toast.LENGTH_SHORT).show();
                            nextBtn.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        observable.subscribe(observer);
    }
}
