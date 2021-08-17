package myapplications.libraries.mvp.di;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import myapplications.libraries.mvp.mvp.model.api.IDataSource;
import myapplications.libraries.mvp.mvp.model.api.INetworkStatus;
import myapplications.libraries.mvp.ui.NetworkStatus;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    @Named("baseUrl")
    @Provides
    public String baseUrl() {
        return "https://api.github.com/";
    }

    @Singleton
    @Provides
    public IDataSource apiService(@Named("clientLogging") OkHttpClient okHttpClient, Gson gson, @Named("baseUrl") String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(IDataSource.class);
    }

    @Singleton
    @Provides
    public HttpLoggingInterceptor loggingInterceptor() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @Named("clientLogging")
    @Singleton
    @Provides
    public OkHttpClient okHttpClientLogging(HttpLoggingInterceptor loggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
    }

    @Named("client")
    @Singleton
    @Provides
    public OkHttpClient okHttpClient(HttpLoggingInterceptor loggingInterceptor) {
        return new OkHttpClient.Builder()
                .build();
    }

    @Singleton
    @Provides
    public Gson gson() {
        return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    }


    @Singleton
    @Provides
    public INetworkStatus networkStatus(){
        return new NetworkStatus();
    }
}
