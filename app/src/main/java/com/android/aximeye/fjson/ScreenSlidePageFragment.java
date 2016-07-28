package com.android.aximeye.fjson;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScreenSlidePageFragment extends Fragment implements Callback<RedditPosts> {
    private TextView mPostTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mPostTextView = (TextView) getView().findViewById(R.id.post_text_view);
        mPostTextView.setText("Post #: ");
    }

    @Override
    public void onResponse(Call<RedditPosts> call, Response<RedditPosts> response) {

    }

    @Override
    public void onFailure(Call<RedditPosts> call, Throwable t) {
        Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onDestroy() {
        mPostTextView = null;
    }
}
