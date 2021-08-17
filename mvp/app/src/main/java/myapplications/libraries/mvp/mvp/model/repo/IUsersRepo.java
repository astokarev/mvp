package myapplications.libraries.mvp.mvp.model.repo;

import java.util.List;

import io.reactivex.Single;
import myapplications.libraries.mvp.mvp.model.entity.Repository;
import myapplications.libraries.mvp.mvp.model.entity.User;

public interface IUsersRepo {
    Single<User> getUser(String username);
    Single<List<Repository>> getUserRepos(User user);
}
