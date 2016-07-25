package com.android.aximeye.fjson;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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


public class MainActivity extends ListActivity {
    RedditPosts redditPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayAdapter<Post> arrayAdapter =
                new ArrayAdapter<Post>(this,
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        new ArrayList<Post>());
        setListAdapter(arrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.reddit.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RedditEndpointInterface redditEndpointInterface = retrofit.create(RedditEndpointInterface.class);
        Call<ResponseBody> call = redditEndpointInterface.loadPosts(25);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "response.isSuccessful!", Toast.LENGTH_LONG).show();
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
                                redditPosts.posts.add(post);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        ArrayAdapter<Post> adapter = (ArrayAdapter<Post>) getListAdapter();
                        adapter.clear();
                        adapter.addAll(redditPosts.getPosts());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "response.isSuccessful failed!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, "onFailure() called...", Toast.LENGTH_LONG).show();
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
        return true;
    }

    public void loadPosts() {

    }
}