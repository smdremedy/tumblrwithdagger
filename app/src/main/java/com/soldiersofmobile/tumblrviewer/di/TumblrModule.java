package com.soldiersofmobile.tumblrviewer.di;

import android.content.Context;

import com.soldiersofmobile.tumblrviewer.TumblrApi;
import com.squareup.otto.Bus;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class TumblrModule {

    private Context context;

    public TumblrModule(Context context) {

        this.context = context;
    }

    @Singleton
    @Provides
    public Bus provideBus() {
        return new Bus();
    }

    @Provides
    public Converter.Factory provideConverterFactory() {
        return GsonConverterFactory.create();
    }


    @Named("url")
    @Provides String url() {
        return
                "http://api.tumblr.com/";
    }

    @Singleton
    @Provides
    public TumblrApi provideTumblrApi(Converter.Factory factory, @Named("url") String url) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(url);


        builder.addConverterFactory(factory);

        Retrofit retrofit = builder.build();


        return retrofit.create(TumblrApi.class);
    }


    @Provides
    public Context provideContext() {
        return context;
    }


}
