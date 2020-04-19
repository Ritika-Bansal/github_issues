package com.gamechange.issues.models;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.databinding.BindingAdapter;

public class User {
    @SerializedName("login")
    @Expose
    public String name;

    @SerializedName("avatar_url")
    @Expose
    public String userImage;

    @BindingAdapter("avatar_url")
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .into(view);
    }
}
