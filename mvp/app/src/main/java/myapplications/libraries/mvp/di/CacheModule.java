package myapplications.libraries.mvp.di;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import myapplications.libraries.mvp.mvp.model.cache.ICache;
import myapplications.libraries.mvp.mvp.model.cache.PaperCache;
import myapplications.libraries.mvp.mvp.model.cache.RealmCache;
import myapplications.libraries.mvp.mvp.model.cache.RoomCache;

@Module
public class CacheModule {

    @Named("room")
    @Provides
    public ICache roomCache() {
        return new RoomCache();
    }

    @Named("realm")
    @Provides
    public ICache realmCache() {
        return new RealmCache();
    }

    @Named("paper")
    @Provides
    public ICache paperCache() {
        return new PaperCache();
    }
}