package myapplications.libraries.mvp.mvp.model.repo;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import myapplications.libraries.mvp.mvp.model.api.IDataSource;
import myapplications.libraries.mvp.mvp.model.api.INetworkStatus;
import myapplications.libraries.mvp.mvp.model.cache.ICache;
import myapplications.libraries.mvp.mvp.model.entity.Repository;
import myapplications.libraries.mvp.mvp.model.entity.User;

public class UsersRepo implements IUsersRepo {

    private INetworkStatus networkStatus;
    private IDataSource api;
    private ICache cache;

    public UsersRepo(INetworkStatus networkStatus, IDataSource api, ICache cache) {
        this.networkStatus = networkStatus;
        this.api = api;
        this.cache = cache;
    }

    public Single<User> getUser(String username) {
        if (networkStatus.isOnline()) {
            return api.getUser(username)
                    .subscribeOn(Schedulers.io())
                    .map(user -> {
                        cache.putUser(username, user).subscribe();
                        return user;
                    });
        } else {
            return cache.getUser(username);
        }
    }

    public Single<List<Repository>> getUserRepos(User user) {
        if (networkStatus.isOnline()) {
            return api.getUserRepos(user.getReposUrl()).subscribeOn(Schedulers.io())
                    .subscribeOn(Schedulers.io())
                    .map(repos -> {
                        cache.putUserRepos(user, repos).subscribe();
                        return repos;
                    });
        } else {
            return cache.getUserRepos(user);
        }
    }
}
