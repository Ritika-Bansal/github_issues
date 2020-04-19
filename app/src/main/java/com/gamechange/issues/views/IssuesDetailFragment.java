package com.gamechange.issues.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gamechange.issues.R;
import com.gamechange.issues.adapters.CommentAdapter;
import com.gamechange.issues.databinding.FragmentIssuesDetailBinding;
import com.gamechange.issues.viewmodels.IssueDetailViewModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class IssuesDetailFragment extends Fragment {
    static final String TAG = "IssuesDetailFragment";
    private FragmentIssuesDetailBinding fragmentIssuesDetailBinding;
    private CommentAdapter commentAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentIssuesDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_issues_detail, container, false);
        return fragmentIssuesDetailBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentIssuesDetailBinding.pbLoader.setVisibility(View.VISIBLE);
        fragmentIssuesDetailBinding.rvComments.setVisibility(View.GONE);

        ((IssuesActivity) getActivity()).setToolbarTitle("Comments");

        commentAdapter = new CommentAdapter(new ArrayList<>());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        fragmentIssuesDetailBinding.rvComments.setLayoutManager(linearLayoutManager);
        fragmentIssuesDetailBinding.rvComments.setAdapter(commentAdapter);

        if (getArguments() != null) {
            String endPoint = getArguments().getString("ENDPOINT_URL");

            IssueDetailViewModel.Factory factory = new IssueDetailViewModel.Factory(
                    getActivity().getApplication(), endPoint);

            IssueDetailViewModel issueDetailViewModel = ViewModelProviders.of(this, factory).get(IssueDetailViewModel.class);
            issueDetailViewModel.getCommentLiveData().observe(this, commentList -> {
                fragmentIssuesDetailBinding.pbLoader.setVisibility(View.GONE);
                fragmentIssuesDetailBinding.rvComments.setVisibility(View.VISIBLE);
                commentAdapter.refreshData(commentList);
            });
        }
    }
}
