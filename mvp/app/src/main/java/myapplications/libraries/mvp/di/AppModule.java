package myapplications.libraries.mvp.di;

import dagger.Module;
import dagger.Provides;
import myapplications.libraries.mvp.App;

@Module
public class AppModule {
    private App app;

    public AppModule(App app){
        this.app = app;
    }

    @Provides
    public App app(){
        return app;
    }
}
