package com.example.haripriya.rss_reader;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.haripriya.rss_reader.Adapter.FeedAdapter;
import com.example.haripriya.rss_reader.Common.HTTPDataHandler;
import com.example.haripriya.rss_reader.Model.Feed;
import com.example.haripriya.rss_reader.Model.Item;
import com.example.haripriya.rss_reader.Model.RSSObject;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    android.support.v7.widget.Toolbar toolbar;
    RecyclerView recyclerView;
    RSSObject rssObject,searchobject;

    //rss link
    public String RSS_Link=null,rlink,toolbartitle;

    private final String RSS_to_json_API="https://api.rss2json.com/v1/api.json?rss_url=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bun = getIntent().getExtras();
        if (bun != null) {
            rlink= bun.getString("link");
            toolbartitle=bun.getString("tit");
        }
        RSS_Link=rlink;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle(toolbartitle);
        setSupportActionBar(toolbar);
        recyclerView=findViewById(R.id.recyclerview);

        //RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        loadrss();
    }

    private void loadrss() {

        @SuppressLint("StaticFieldLeak") AsyncTask<String,String,String> loadRssSync=new AsyncTask<String, String, String>() {
            ProgressDialog mDialog=new ProgressDialog(MainActivity.this);

            @Override
            protected void onPreExecute() {
                mDialog.setMessage("Please Wait!");
                mDialog.show();
                mDialog.setCanceledOnTouchOutside(false);
            }

            @Override
            protected String doInBackground(String... strings) {
                String result;
                HTTPDataHandler http=new HTTPDataHandler();
                result=http.GetHTTPData(strings[0]);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                mDialog.dismiss();
                rssObject =new Gson().fromJson(s,RSSObject.class);
                    FeedAdapter adapter = new FeedAdapter(rssObject, getBaseContext(),null);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

            }
        };

        StringBuilder url_get_data=new StringBuilder(RSS_to_json_API);
        url_get_data.append(RSS_Link);
        url_get_data.append("&api_key=81lgzemsa7yyr6cxjxed9vvmraebua8g3bcdveg4&order_by=pubDate&order_dir=desc&count=500");
        loadRssSync.execute(url_get_data.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        MenuItem menuItem=menu.findItem(R.id.search);
        SearchView searchView= (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.refreshmenu)
            loadrss();
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(final String newText) {

        AsyncTask<String,String,String> loadRssSync=new AsyncTask<String, String, String>() {
            ProgressDialog mDialog=new ProgressDialog(MainActivity.this);

            @Override
            protected void onPreExecute() {
                //mDialog.setMessage("Please Wait! ");
                //mDialog.show();
            }

            @Override
            protected String doInBackground(String... strings) {
                String result;
                HTTPDataHandler http=new HTTPDataHandler();
                result=http.GetHTTPData(strings[0]);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                mDialog.dismiss();
                searchobject =new Gson().fromJson(s,RSSObject.class);
                searchobject.items.clear();
                for(int i=0;i<rssObject.items.size();i++)
                {
                    if (rssObject.items.get(i).getTitle().toLowerCase().contains(newText.toLowerCase()) ||
                            rssObject.items.get(i).getContent().toLowerCase().contains(newText.toLowerCase()) ||
                            rssObject.items.get(i).getPubDate().toLowerCase().contains(newText.toLowerCase()))
                    {
                        searchobject.items.add(rssObject.items.get(i));
                    }
                }
                FeedAdapter adapter = new FeedAdapter(searchobject, getBaseContext(),newText.toLowerCase());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
        };

        StringBuilder url_get_data=new StringBuilder(RSS_to_json_API);
        url_get_data.append(RSS_Link);
        url_get_data.append("&api_key=81lgzemsa7yyr6cxjxed9vvmraebua8g3bcdveg4&order_by=pubDate&order_dir=desc&count=500");
        loadRssSync.execute(url_get_data.toString());
        return true;
    }
}
