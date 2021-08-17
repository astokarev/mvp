package myapplications.libraries.mvp.mvp.presenter;

import io.reactivex.subjects.PublishSubject;
import myapplications.libraries.mvp.mvp.view.RepositoryItemView;

public interface IRepositoryListPresenter {
    void bind(RepositoryItemView view);
    int getCount();
    PublishSubject<RepositoryItemView> getClickSubject();
}
