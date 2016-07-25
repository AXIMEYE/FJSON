package com.android.aximeye.fjson;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends ListActivity implements Callback<ResponseBody> {
    RedditPosts redditPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<Post> arrayOfPosts = new ArrayList<Post>();
        PostsAdapter adapter = new PostsAdapter(this, arrayOfPosts);
        setListAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_load:
                loadPosts();
                break;
            case R.id.menu_view_pager:
                enableSliderView();
                break;
        }
        return true;
    }

    public void loadPosts() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.reddit.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RedditEndpointInterface redditEndpointInterface = retrofit.create(RedditEndpointInterface.class);
        Call<ResponseBody> call = redditEndpointInterface.loadPosts(25);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if(response.isSuccessful()) {
            try {
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
                        redditPosts.posts.add(post);
                    }
                } catch (JSONException e) { e.printStackTrace();}

                ArrayAdapter<Post> adapter = (ArrayAdapter<Post>) getListAdapter();
                adapter.clear();
                adapter.addAll(redditPosts.getPosts());
            } catch (IOException e) { e.printStackTrace();}
        } else {
            Toast.makeText(MainActivity.this, "response unSuccessful!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        Toast.makeText(MainActivity.this, "onFailure() called...", Toast.LENGTH_LONG).show();
        Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        t.printStackTrace();
    }

    public void enableSliderView() {
        Toast.makeText(MainActivity.this, "Nothing to do here!", Toast.LENGTH_LONG).show();
    }
}