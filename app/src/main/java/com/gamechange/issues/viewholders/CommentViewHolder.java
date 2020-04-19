package com.gamechange.issues.viewholders;

import android.view.View;

import com.gamechange.issues.databinding.CommentItemLayoutBinding;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CommentViewHolder extends RecyclerView.ViewHolder {
    public CommentItemLayoutBinding commentItemLayoutBinding;

    public CommentViewHolder(CommentItemLayoutBinding commentItemLayoutBinding) {
        super(commentItemLayoutBinding.getRoot());
        this.commentItemLayoutBinding=commentItemLayoutBinding;
    }
}
