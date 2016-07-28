package com.android.aximeye.fjson;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RedditCallback implements Callback<ResponseBody> {
    private RedditPosts redditPosts;
    private Context context;
    private PostsAdapter adapter;

    public RedditCallback(Context context, PostsAdapter adapter) {
        this.context = context;
        this.adapter = adapter;
    }

    public RedditPosts getRedditPosts() {
        return redditPosts;
    }

    public void setRedditPosts(RedditPosts redditPosts) {
        this.redditPosts = redditPosts;
    }

    public void fetchPosts() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.reddit.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RedditEndpointInterface redditEndpointInterface = retrofit.create(RedditEndpointInterface.class);
        Call<ResponseBody> call = redditEndpointInterface.loadPosts(25);

        RedditCallback rc = new RedditCallback(context, adapter);
        call.enqueue(rc);
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if(response.isSuccessful()) {
            try {
                Toast.makeText(context, "response successful!", Toast.LENGTH_LONG).show();
                String json = response.body().string();
                redditPosts = new RedditPosts();

                try {
                    JSONObject jsonObject = new JSONObject(json);
                    JSONObject data  = jsonObject.getJSONObject("data");
                    JSONArray jsonArray = data.getJSONArray("children");

                    for (int i = 0; i<jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        JSONObject jsonObject2 = jsonObject1.getJSONObject("data");

                        Post post = new Post();
                        post.setTitle(jsonObject2.getString("title"));
                        post.setCreator(jsonObject2.getString("author"));
                        post.setLink(jsonObject2.getString("url"));

                        Bitmap bm = null;
                        try {
                            bm = new DownloadThumbnailsTask()
                                    .execute(jsonObject2.getString("thumbnail"))
                                    .get();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        post.setThumbnail(bm);

                        redditPosts.posts.add(post);
                    }
                } catch (JSONException e) { e.printStackTrace();}

                ArrayAdapter<Post> adapter = this.adapter;
                adapter.clear();
                adapter.addAll(redditPosts.getPosts());

            } catch (IOException e) { e.printStackTrace();}
        } else {
            Toast.makeText(context, "response unSuccessful!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        Toast.makeText(context, "onFailure() called...", Toast.LENGTH_LONG).show();
        Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        t.printStackTrace();
    }
}
