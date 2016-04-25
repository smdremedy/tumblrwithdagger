package com.soldiersofmobile.tumblrviewer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.soldiersofmobile.tumblrviewer.di.DaggerTumblrComponent;
import com.soldiersofmobile.tumblrviewer.di.TumblrComponent;
import com.soldiersofmobile.tumblrviewer.di.TumblrModule;
import com.squareup.otto.Bus;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;


public class PostsListFragment extends ListFragment {

    public static final String ARG_NAME = "name";

    @Inject
    TumblrApi tumblrApi;

    @Inject
    DbHelper dbHelper;

    @Inject
    Bus bus;

    private FragmentCallback callback;
    private ArrayAdapter<Post> arrayAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        callback = (FragmentCallback) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        callback = null;
    }

    public static PostsListFragment newInstance(String name) {

        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        PostsListFragment fragment = new PostsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);



        App.getComponent().inject(this);
    }

    @Override
    public void onViewCreated(View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        arrayAdapter = new ArrayAdapter<Post>(getContext(), R.layout.post_item, R.id.itemTextView) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View view = convertView;
                if(view == null) {
                    view = getLayoutInflater(savedInstanceState).inflate(R.layout.post_item, parent, false);
                }

                ViewHolder viewHolder = (ViewHolder) view.getTag();
                if(viewHolder == null) {
                    viewHolder = new ViewHolder(view);
                    view.setTag(viewHolder);
                }


                Post post = getItem(position);

                viewHolder.textView.setText(Html.fromHtml(
                        post.getCaption()));

                Glide.with(PostsListFragment.this).load(post.getPhotos().get(0).getAltSizes().get(0).getUrl())
                        .into(viewHolder.imageView);

                return view;

            };


        };

        Call<PostsResponse> call = tumblrApi.getPosts(getArguments().getString(ARG_NAME), 10, 0);

        call.enqueue(new Callback<PostsResponse>() {
            @Override
            public void onResponse(Call<PostsResponse> call, retrofit2.Response<PostsResponse> response) {

                arrayAdapter.addAll(response.body().getResponse().getPosts());
                setListAdapter(arrayAdapter);

            }

            @Override
            public void onFailure(Call<PostsResponse> call, Throwable t) {

            }
        });


    }



    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        //callback.openUrl();

        bus.post(new OpenUrlEvent(arrayAdapter.getItem(position).getPostUrl()));
    }

    static class ViewHolder {
        ImageView imageView;
        TextView textView;

        ViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.itemImageView);
            textView = (TextView) view.findViewById(R.id.itemTextView);

        }

    }

    public interface FragmentCallback {
        void openUrl(String url);
    }
}
