package com.gamechange.issues.service;

import android.content.Context;
import android.content.SharedPreferences;

import com.gamechange.issues.DateDeserializer;
import com.gamechange.issues.models.Comment;
import com.gamechange.issues.models.Issues;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceRepository {
    private APIService apiService;
    private static ServiceRepository serviceRepository;
    private SharedPreferences.Editor editor;
    public static String MY_PREFS_NAME= "nameOfSharedPreferences";

    private ServiceRepository(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create(initGSONSerializers()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        apiService = retrofit.create(APIService.class);
         editor = context.getSharedPreferences(MY_PREFS_NAME, 0).edit();
    }

    public synchronized static ServiceRepository getInstance(Context context) {
        if (serviceRepository == null) {
            if (serviceRepository == null) {
                serviceRepository = new ServiceRepository(context);
            }
        }
        return serviceRepository;
    }

    private Gson initGSONSerializers() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Issues.class, new DateDeserializer());
        return gsonBuilder.create();
    }

    public LiveData<List<Issues>> fetchIssues() {
        MutableLiveData<List<Issues>> issuesMutableLiveData = new MutableLiveData<>();
        apiService.fetchOpenIssues()
                .map(issuesList -> {
                    List<Issues> issuesSortedList = new ArrayList<>(issuesList);
                    Collections.sort(issuesSortedList, (o1, o2) -> Long.compare(o1.updated_in_epoch, o2.updated_in_epoch));
                    return issuesSortedList;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<List<Issues>>() {
                    @Override
                    public void onNext(List<Issues> issues) {
                        issuesMutableLiveData.setValue(issues);
                        saveDataInPreference(issues);
                    }

                    @Override
                    public void onError(Throwable e) {
                        issuesMutableLiveData.setValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return issuesMutableLiveData;
    }

    private void saveDataInPreference(List<Issues> issues) {
        Gson gson = new Gson();
        String json = gson.toJson(issues);
        editor.putString("Issues", json);
        editor.putLong("ExpiredDate", System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(1440));
        editor.commit();
    }

    public LiveData<List<Comment>> fetchDetails(String endPoint) {
        MutableLiveData<List<Comment>> commentMutableLiveData = new MutableLiveData<>();
        apiService.fetchIssueDetails(endPoint)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<List<Comment>>() {
                    @Override
                    public void onNext(List<Comment> commentList) {
                        commentMutableLiveData.setValue(commentList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        commentMutableLiveData.setValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return commentMutableLiveData;
    }
}