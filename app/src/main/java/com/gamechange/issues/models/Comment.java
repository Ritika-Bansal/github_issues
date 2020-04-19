package com.gamechange.issues.models;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gamechange.issues.models.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.databinding.BindingAdapter;

public class Comment {
    @SerializedName("user")
    @Expose
    public User user;

    @SerializedName("body")
    @Expose
    public String body;


}
