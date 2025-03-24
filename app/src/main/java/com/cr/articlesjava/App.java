package com.cr.articlesjava;

import android.app.Application;
import com.cr.articlesjava.di.AppModule;
import com.cr.articlesjava.di.NetworkModule;
import com.cr.articlesjava.di.RepositoryModule;
import com.cr.articlesjava.utils.AppComponent;
import com.cr.articlesjava.utils.DaggerAppComponent;

public class App extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .repositoryModule(new RepositoryModule())
                .networkModule(new NetworkModule())
                .build();
    }

    /**
     * Returns the initialized Dagger AppComponent instance.
     */
    public static AppComponent getAppComponent() {
        return appComponent;
    }

}
