package com.gamechange.issues.adapters;

import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.gamechange.issues.R;
import com.gamechange.issues.databinding.CommentItemLayoutBinding;
import com.gamechange.issues.models.Comment;
import com.gamechange.issues.viewholders.CommentViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {
    private List<Comment> commentList;

    public CommentAdapter(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CommentItemLayoutBinding commentItemLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.comment_item_layout, parent, false);
        return new CommentViewHolder(commentItemLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        holder.commentItemLayoutBinding.setComment(commentList.get(position));
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public void refreshData(List<Comment> commentList) {
        this.commentList = commentList;
        notifyDataSetChanged();
    }
}
