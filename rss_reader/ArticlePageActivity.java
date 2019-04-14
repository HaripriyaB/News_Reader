package com.example.haripriya.rss_reader;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haripriya.rss_reader.Model.RSSObject;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Locale;

public class ArticlePageActivity extends AppCompatActivity {

    TextToSpeech textToSpeech;
    int result;
    Toolbar toolbar;
    String publishtit,date,source,articlelink,imgtext;
    ImageView img;
    String referencelink;
    TextView titleset,dateset,contentset,articlepage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_page);
        Toast.makeText(ArticlePageActivity.this,FirebaseAuth.getInstance().getCurrentUser().getUid(),Toast.LENGTH_LONG).show();
        toolbar = findViewById(R.id.new_toolbar);
        toolbar.setTitle("News");
        setSupportActionBar(toolbar);
        getDelegate().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bun = getIntent().getExtras();
        if (bun != null) {
            imgtext = bun.getString("imgview");
            publishtit = bun.getString("title");
            date = bun.getString("date");
            source = bun.getString("source");
            articlelink = bun.getString("articlelink");
        }
        img = findViewById(R.id.imageView);
        titleset = findViewById(R.id.article_title);
        dateset = findViewById(R.id.publishdate);
        contentset = findViewById(R.id.article_content);
        articlepage = findViewById(R.id.redirect);
        referencelink = articlelink;

        Picasso.get().load(imgtext).fit().into(img);
        titleset.setText(publishtit);
        dateset.setText(date);
        contentset.setText(source);
        articlepage.setText(articlelink);
        articlepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(articlelink));
                startActivity(i);
            }
        });
        textToSpeech = new TextToSpeech(ArticlePageActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS) {
                    result = textToSpeech.setLanguage(Locale.ENGLISH);
                } else {
                    Toast.makeText(getApplicationContext(), "Feature not supported in your device", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.article_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
            onBackPressed();
            return true;
        }
        switch (item.getItemId())
        {
            case R.id.speak:
                if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
                {
                    Toast.makeText(getApplicationContext(),"Feature not supported in your device",Toast.LENGTH_LONG).show();
                }
                else
                {
                    textToSpeech.speak(contentset.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);
                }
                break;
            case R.id.pause:
                if(textToSpeech!=null)
                {
                    textToSpeech.stop();
                }
                break;
        }

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(textToSpeech!=null)
        {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}
