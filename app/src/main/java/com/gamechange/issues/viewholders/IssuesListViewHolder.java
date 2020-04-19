package com.gamechange.issues.viewholders;

import android.view.View;

import com.gamechange.issues.databinding.IssueItemLayoutBinding;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class IssuesListViewHolder extends RecyclerView.ViewHolder {
    public IssueItemLayoutBinding issueItemLayoutBinding;

    public IssuesListViewHolder(IssueItemLayoutBinding issueItemLayoutBinding) {
        super(issueItemLayoutBinding.getRoot());
        this.issueItemLayoutBinding = issueItemLayoutBinding;
    }
}
