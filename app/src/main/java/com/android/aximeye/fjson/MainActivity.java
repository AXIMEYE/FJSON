package com.android.aximeye.fjson;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadPosts();
    }

    public void loadPosts() {
        ArrayList<Post> arrayOfPosts = new ArrayList<Post>();
        PostsAdapter adapter = new PostsAdapter(this, arrayOfPosts);
        setListAdapter(adapter);

        RedditCallback redditCallback = new RedditCallback(this, adapter);
        redditCallback.fetchPosts();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_view_pager:
                enableSliderView();
                break;
        }
        return true;
    }

    public void enableSliderView() {
        Toast.makeText(MainActivity.this, "Nothing to do here!", Toast.LENGTH_LONG).show();
    }
}