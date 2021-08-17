package myapplications.libraries.mvp.mvp.model.cache;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import myapplications.libraries.mvp.mvp.model.entity.Repository;
import myapplications.libraries.mvp.mvp.model.entity.User;

public interface ICache {
    Single<User> getUser(String username);
    Completable putUser(String username, User user);

    Single<List<Repository>> getUserRepos(User user);
    Completable putUserRepos(User user, List<Repository> repositories);
}
