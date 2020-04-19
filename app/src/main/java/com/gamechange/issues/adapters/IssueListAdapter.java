package com.gamechange.issues.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.gamechange.issues.R;
import com.gamechange.issues.callback.ListItemClickCallback;
import com.gamechange.issues.databinding.IssueItemLayoutBinding;
import com.gamechange.issues.models.Issues;
import com.gamechange.issues.viewholders.IssuesListViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class IssueListAdapter extends RecyclerView.Adapter<IssuesListViewHolder> {
    private List<Issues> issuesList;
    private ListItemClickCallback listItemClickCallback;

    public IssueListAdapter(List<Issues> issuesList, ListItemClickCallback listItemClickCallback) {
        this.issuesList = issuesList;
        this.listItemClickCallback = listItemClickCallback;
    }

    @NonNull
    @Override
    public IssuesListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        IssueItemLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.issue_item_layout, parent, false);
        return new IssuesListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull IssuesListViewHolder holder, int position) {
        holder.issueItemLayoutBinding.setIssues(issuesList.get(position));
        holder.issueItemLayoutBinding.setCallback(listItemClickCallback);
    }

    @Override
    public int getItemCount() {
        return issuesList != null ? issuesList.size() : 0;
    }

    public void refreshData(List<Issues> issuesList) {
        this.issuesList = issuesList;
        notifyDataSetChanged();
    }
}
