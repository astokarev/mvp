package myapplications.libraries.mvp.di;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import myapplications.libraries.mvp.mvp.model.api.IDataSource;
import myapplications.libraries.mvp.mvp.model.api.INetworkStatus;
import myapplications.libraries.mvp.mvp.model.cache.ICache;
import myapplications.libraries.mvp.mvp.model.repo.IUsersRepo;
import myapplications.libraries.mvp.mvp.model.repo.UsersRepo;

@Module(includes = {ApiModule.class, CacheModule.class})
public class RepoModule {

    @Provides
    public IUsersRepo usersRepo(@Named("room") ICache cache, INetworkStatus networkStatus, IDataSource api) {
        return new UsersRepo(networkStatus, api, cache);
    }
}
