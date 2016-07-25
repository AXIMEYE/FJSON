package com.android.aximeye.fjson;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RedditEndpointInterface {
    @GET("/r/funny.json")
    Call<ResponseBody> loadPosts(@Query("count") int count);
}
