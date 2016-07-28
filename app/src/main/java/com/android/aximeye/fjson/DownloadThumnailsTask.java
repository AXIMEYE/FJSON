package com.android.aximeye.fjson;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

class DownloadThumbnailsTask extends AsyncTask<String, Void, Bitmap> {
    Bitmap bm = null;

    @Override
    protected Bitmap doInBackground(String... url) {

        try {
            URL aURL = new URL(url[0]);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bm;
    }
}