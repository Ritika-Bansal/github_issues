package com.gamechange.issues.views;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gamechange.issues.R;
import com.gamechange.issues.adapters.IssueListAdapter;
import com.gamechange.issues.callback.ListItemClickCallback;
import com.gamechange.issues.databinding.FragmentIssuesListBinding;
import com.gamechange.issues.models.Issues;
import com.gamechange.issues.service.ServiceRepository;
import com.gamechange.issues.viewmodels.IssuesListViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

public class IssuesListFragment extends Fragment {
    static final String TAG = "IssuesListFragment";
    private IssueListAdapter issueListAdapter;
    private FragmentIssuesListBinding fragmentIssuesListBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentIssuesListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_issues_list, container, false);
        issueListAdapter = new IssueListAdapter(new ArrayList<>(), listItemClickCallback);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        fragmentIssuesListBinding.rvIssues.setLayoutManager(linearLayoutManager);
        fragmentIssuesListBinding.rvIssues.setAdapter(issueListAdapter);
        return fragmentIssuesListBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentIssuesListBinding.pbLoader.setVisibility(View.VISIBLE);
        fragmentIssuesListBinding.rvIssues.setVisibility(View.GONE);
        ((IssuesActivity) getActivity()).setToolbarTitle("Issues");

        if (getIssuesFromSharedPreferences() == null) {
            IssuesListViewModel.Factory factory = new IssuesListViewModel.Factory(
                    getActivity().getApplication());

            IssuesListViewModel issuesListViewModel = ViewModelProviders.of(this, factory).get(IssuesListViewModel.class);
            issuesListViewModel.getIssuesList().observe(this, this::showIssueList);
        }
    }

    //fetching data from shared preferences
    private List<Issues> getIssuesFromSharedPreferences() {
        List<Issues> issuesList = null;
        SharedPreferences sharedPref = getActivity().getSharedPreferences(ServiceRepository.MY_PREFS_NAME, 0);
        if (sharedPref.getLong("ExpiredDate", -1) > System.currentTimeMillis()) {
            Gson gson = new Gson();
            String jsonPreferences = sharedPref.getString("Issues", "");

            Type type = new TypeToken<List<Issues>>() {
            }.getType();
            issuesList = gson.fromJson(jsonPreferences, type);
            showIssueList(issuesList);
        } else {
            //deleting old data from prefernces after every 24 hours
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.clear();
            editor.apply();
        }
        return issuesList;
    }

    //setting up data on to the view
    private void showIssueList(List<Issues> issues) {
        fragmentIssuesListBinding.pbLoader.setVisibility(View.GONE);
        fragmentIssuesListBinding.rvIssues.setVisibility(View.VISIBLE);
        issueListAdapter.refreshData(issues);
    }

    private final ListItemClickCallback listItemClickCallback = issues -> {
        if (issues.comments > 0) {
            ((IssuesActivity) getActivity()).openIssueDetails(issues.commentsUrl);
        } else {
            showPopup();
        }
    };

    private void showPopup() {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());
        builder.setTitle("Hey");
        builder.setMessage("Comments are not yet available for this issue");
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setAllCaps(false);
    }

}
