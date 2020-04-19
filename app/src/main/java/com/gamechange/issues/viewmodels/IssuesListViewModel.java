package com.gamechange.issues.viewmodels;

import android.app.Application;

import com.gamechange.issues.service.ServiceRepository;
import com.gamechange.issues.models.Issues;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class IssuesListViewModel extends AndroidViewModel {
    private LiveData<List<Issues>> issuesLiveData;

    public IssuesListViewModel(Application application) {
        super(application);

        issuesLiveData = ServiceRepository.getInstance(getApplication().getApplicationContext()).fetchIssues();
    }

    public LiveData<List<Issues>> getIssuesList() {
        return issuesLiveData;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        public Factory(@NonNull Application application) {
            this.application = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new IssuesListViewModel(application);
        }
    }
}
