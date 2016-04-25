package com.soldiersofmobile.tumblrviewer.di;

import com.soldiersofmobile.tumblrviewer.PostDetailsFragment;
import com.soldiersofmobile.tumblrviewer.PostsListFragment;
import com.soldiersofmobile.tumblrviewer.TumblrApi;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = TumblrModule.class)
public interface TumblrComponent {

    TumblrApi getTumblrApi();

    void inject(PostsListFragment fragment);
    void inject(PostDetailsFragment fragment);



}
