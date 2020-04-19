package com.gamechange.issues.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Issues {
    @SerializedName("title")
    @Expose
    public String title;

    @SerializedName("updated_at")
    @Expose
    public String updatedAt;

    @SerializedName("body")
    @Expose
    public String body;

    @SerializedName("comments_url")
    @Expose
    public String commentsUrl;

    @SerializedName("comments")
    @Expose
    public int comments;

    @SerializedName("updated_in_epoch")
    @Expose
    public long updated_in_epoch;
}
