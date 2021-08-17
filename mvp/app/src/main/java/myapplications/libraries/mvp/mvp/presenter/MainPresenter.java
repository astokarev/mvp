package myapplications.libraries.mvp.mvp.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.subjects.PublishSubject;
import myapplications.libraries.mvp.mvp.model.entity.Repository;
import myapplications.libraries.mvp.mvp.model.repo.IUsersRepo;
import myapplications.libraries.mvp.mvp.view.MainView;
import myapplications.libraries.mvp.mvp.view.RepositoryItemView;
import timber.log.Timber;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {
    class RepositoryListPresenter implements IRepositoryListPresenter {

        PublishSubject<RepositoryItemView> clickSubject = PublishSubject.create();
        List<Repository> repositories = new ArrayList<>();

        @Override
        public void bind(RepositoryItemView view) {
            view.setTitle(repositories.get(view.getPos()).getName());
        }

        @Override
        public int getCount() {
            return repositories.size();
        }

        @Override
        public PublishSubject<RepositoryItemView> getClickSubject() {
            return clickSubject;
        }
    }

    @Inject
    IUsersRepo usersRepo;

    private Scheduler mainThreadScheduler;
    private RepositoryListPresenter repositoryListPresenter;

    public MainPresenter(Scheduler mainThreadScheduler) {
        this.mainThreadScheduler = mainThreadScheduler;
        repositoryListPresenter = new RepositoryListPresenter();
    }

    public IRepositoryListPresenter getCountriesListPresenter() {
        return repositoryListPresenter;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().init();
        loadData("googlesamples");
        repositoryListPresenter.getClickSubject().subscribe(countryRowView -> {
            getViewState().showMessage(repositoryListPresenter.repositories.get(countryRowView.getPos()).getName());
        });
    }

    @SuppressLint("CheckResult")
    public void loadData(String username) {
        getViewState().showLoading();
        usersRepo.getUser(username)
                .observeOn(mainThreadScheduler)
                .flatMap(user -> {
                    getViewState().setUsername(user.getLogin());
                    getViewState().loadImage(user.getAvatarUrl());
                    return usersRepo.getUserRepos(user)
                            .observeOn(mainThreadScheduler);
                })
                .subscribe(repositories -> {
                    repositoryListPresenter.repositories.clear();
                    repositoryListPresenter.repositories.addAll(repositories);
                    getViewState().updateList();
                    getViewState().hideLoading();
                }, t -> {
                    getViewState().hideLoading();
                    getViewState().showMessage(t.getMessage());
                    Timber.e(t);
                });
    }
}