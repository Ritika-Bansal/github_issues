package com.gamechange.issues.views;

import android.os.Bundle;

import com.gamechange.issues.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class IssuesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issues_main);

        if (savedInstanceState == null) {
            IssuesListFragment issuesListFragment = new IssuesListFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fl_container, issuesListFragment, IssuesListFragment.TAG)
                    .addToBackStack(null)
                    .commit();
        }
    }

    public void openIssueDetails(String detailEndpoint) {
        IssuesDetailFragment issuesDetailFragment = new IssuesDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("ENDPOINT_URL", detailEndpoint);
        issuesDetailFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().
                replace(R.id.fl_container, issuesDetailFragment, IssuesDetailFragment.TAG)
                .addToBackStack(null)
                .commit();
    }

    public void setToolbarTitle(String title){
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = this.getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 1) {
            fm.popBackStack();
        } else {
            this.finish();
        }
    }
}
