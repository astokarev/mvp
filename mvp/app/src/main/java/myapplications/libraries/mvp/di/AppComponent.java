package myapplications.libraries.mvp.di;

import javax.inject.Singleton;

import dagger.Component;
import myapplications.libraries.mvp.mvp.presenter.MainPresenter;

@Singleton
@Component(modules = {
        AppModule.class,
        RepoModule.class
})

public interface AppComponent {
    void inject(MainPresenter presenter);
}
