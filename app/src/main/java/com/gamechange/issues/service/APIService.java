package com.gamechange.issues.service;

import com.gamechange.issues.models.Comment;
import com.gamechange.issues.models.Issues;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface APIService {
    @GET("repos/firebase/firebase-ios-sdk/issues")
    Observable<List<Issues>> fetchOpenIssues();

    @GET
    Observable<List<Comment>> fetchIssueDetails(@Url String url);
}
