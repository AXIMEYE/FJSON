package com.android.aximeye.fjson;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PostsAdapter extends ArrayAdapter<Post> {
    public PostsAdapter(Context context, ArrayList<Post> posts) {
        super(context, 0, posts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Post post = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.simple_list, parent, false);
        }

        TextView tvTitle = (TextView) convertView.findViewById(R.id.post_title_text_view);
        TextView tvCreator = (TextView) convertView.findViewById(R.id.post_creator);
        TextView tvLink = (TextView) convertView.findViewById(R.id.post_link);
        ImageView ivThumbnail = (ImageView) convertView.findViewById(R.id.post_thumbnail);

        tvTitle.setText(post.getTitle());
        tvCreator.setText(post.getCreator());
        tvLink.setText(post.getLink());
        ivThumbnail.setImageBitmap(post.getThumbnail());

        return convertView;
    }
}
