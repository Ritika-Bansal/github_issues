package com.gamechange.issues.viewmodels;

import android.app.Application;

import com.gamechange.issues.service.ServiceRepository;
import com.gamechange.issues.models.Comment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class IssueDetailViewModel extends AndroidViewModel {
    private LiveData<List<Comment>> commentLiveData;
    private final String issueDetailsUrl;

    public IssueDetailViewModel(@NonNull Application application, String url) {
        super(application);
        this.issueDetailsUrl = url;
        commentLiveData = ServiceRepository.getInstance(application.getApplicationContext()).fetchDetails(url);
    }

    public LiveData<List<Comment>> getCommentLiveData() {
        return commentLiveData;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String issueDetailsUrl;

        public Factory(@NonNull Application application, String issueDetailsUrl) {
            this.application = application;
            this.issueDetailsUrl = issueDetailsUrl;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new IssueDetailViewModel(application, issueDetailsUrl);
        }
    }
}
