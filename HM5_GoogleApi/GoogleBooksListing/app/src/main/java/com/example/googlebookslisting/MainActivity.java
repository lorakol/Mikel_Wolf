package com.example.googlebookslisting;



import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import org.apache.commons.io.IOUtils;

//import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpGet;
//import org.apache.http.conn. client.methods. HttpGet;
import com.squareup.picasso.Picasso;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.net.*;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ModelAdapter adapter;
    private ArrayList<Model> modelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        // Set up empty adapter with ArrayList
        modelList = new ArrayList<>();
        adapter = new ModelAdapter(modelList);
        recyclerView.setAdapter( adapter);

        // Check network state and load data
        //https://www.googleapis.com/books/v1/volumes?q=flowers
        //String url = "https://www.googleapis.com/books/v1/volumes";
        //String parms = "?q=flowers";
        if (isNetworkAvailable()) {
            new FetchBooksTask().execute();
        } else {
            Toast.makeText(this, "No network connection available.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private class FetchBooksTask extends AsyncTask<Void, Void, ArrayList<Model>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //recyclerView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Model> doInBackground(Void... params) {
            ArrayList<Model> modelList = new ArrayList<>();
            // TODO: Fetch books data here
            try {
                URL u = new URL("https://www.googleapis.com/books/v1/volumes?q=animal&maxResults=40");
                URLConnection urlconnect = u.openConnection();
                InputStream stream = urlconnect.getInputStream();
                String json = IOUtils.toString(u, Charset.forName("UTF-8"));
                JSONObject jsonObj = new JSONObject(json);
                JSONArray jsonarr = jsonObj.getJSONArray("items");
                for(int i = 0 ; i < jsonarr.length() ; i++) {
                    try{
                        JSONObject obj = jsonarr.getJSONObject(i);
                        JSONObject volume = obj.getJSONObject("volumeInfo");
                        String title = volume.getString("title");
                        String author = volume.getJSONArray("authors").getString(0);
                        JSONObject imageObj = volume.getJSONObject("imageLinks");
                        String thumblink = imageObj.getString("thumbnail");
                        //thumblink = "https://i.imgur.com/tGbaZCY.jpg";
                        thumblink = thumblink.replace("http","https");
                        Bitmap thumbnail = null;//Picasso.get().load(thumblink).get();
                        Model model = new Model(title, author, thumbnail);
                        model.setThumbnailUrl(thumblink);
                        modelList.add(model);
                    }catch (Exception er){

                    }

                }

            }catch (Exception er){
                    int n = 0;
            }
            return modelList;
        }

        @Override
        protected void onPostExecute(ArrayList<Model> result) {
            super.onPostExecute(result);
            progressBar.setVisibility(View.GONE);
            //recyclerView.setVisibility(View.VISIBLE);
            if (result != null) {
                modelList.clear();
                modelList.addAll(result);
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(MainActivity.this, "Failed to fetch books data.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}


