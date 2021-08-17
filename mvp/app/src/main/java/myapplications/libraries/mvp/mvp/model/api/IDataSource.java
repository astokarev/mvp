package myapplications.libraries.mvp.mvp.model.api;

import java.util.List;

import io.reactivex.Single;
import myapplications.libraries.mvp.mvp.model.entity.Repository;
import myapplications.libraries.mvp.mvp.model.entity.User;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface IDataSource {
    @GET("/users/{user}")
    Single<User> getUser(@Path("user") String username);

    @GET
    Single<List<Repository>> getUserRepos(@Url String url);
}
