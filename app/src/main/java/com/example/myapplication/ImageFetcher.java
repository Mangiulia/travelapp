package com.example.myapplication;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ImageFetcher {

    private static final String UNSPLASH_API_URL = "https://api.unsplash.com/search/photos?query=%s&client_id=qUlE60n4T75-6QalvFg_l8Xh-0daRUHhDDQo7JvF__A";

    public static void fetchImage(String query, ImageFetchCallback callback) {
        new FetchImageTask(query, callback).execute();
    }

    public interface ImageFetchCallback {
        void onImageFetched(String imageUrl);
        void onError();
    }

    private static class FetchImageTask extends AsyncTask<Void, Void, String> {
        private String query;
        private ImageFetchCallback callback;

        public FetchImageTask(String query, ImageFetchCallback callback) {
            this.query = query;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(Void... voids) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(String.format(UNSPLASH_API_URL, query))
                    .build();

            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    Gson gson = new Gson();
                    UnsplashResponse response = gson.fromJson(result, UnsplashResponse.class);
                    if (response != null && response.results != null && !response.results.isEmpty()) {
                        String imageUrl = response.results.get(0).urls.regular;
                        callback.onImageFetched(imageUrl);
                    } else {
                        callback.onError();
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    callback.onError();
                }
            } else {
                callback.onError();
            }
        }
    }

    private static class UnsplashResponse {
        public List<UnsplashImage> results;

        private static class UnsplashImage {
            public UnsplashUrls urls;
        }

        private static class UnsplashUrls {
            public String regular;
        }
    }
}
