package com.example.haripriya.rss_reader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CategoryActivity extends AppCompatActivity {

    Toolbar newtoolbar;
    RadioGroup group;
    RadioButton category;
    Button submit;
    String link=null;
    String title=null;
    public FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        newtoolbar=findViewById(R.id.new_toolbar);
        newtoolbar.setTitle("Choose Category");
        setSupportActionBar(newtoolbar);
        mAuth = FirebaseAuth.getInstance();
        group=findViewById(R.id.radioGroup);
        submit= findViewById(R.id.submitbtn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int x=group.getCheckedRadioButtonId();
                category=findViewById(x);
                    if(category.getText().equals("Top News")){
                        //link="https://www.indiatoday.in/rss/1206578";
                        link = "http://feeds.bbci.co.uk/news/world/asia/india/rss.xml";
                        title=category.getText().toString();
                Toast.makeText(CategoryActivity.this,category.getText(),Toast.LENGTH_SHORT).show();}
                else if(category.getText().equals("World News"))
                {
                    link="http://feeds.bbci.co.uk/news/world/rss.xml";
                    title=category.getText().toString();
                    Toast.makeText(CategoryActivity.this,category.getText(),Toast.LENGTH_SHORT).show();
                }
                else if(category.getText().equals("Business News"))
                {
                    link="https://www.news18.com/rss/business.xml";
                    title=category.getText().toString();
                    //link="http://feeds.bbci.co.uk/news/business/rss.xml";
                    Toast.makeText(CategoryActivity.this,category.getText(),Toast.LENGTH_SHORT).show();
                }
                else if(category.getText().equals("Political News"))
                {
                    link="https://www.news18.com/rss/politics.xml";
                    title=category.getText().toString();
                    //link="http://feeds.bbci.co.uk/news/politics/rss.xml";
                    Toast.makeText(CategoryActivity.this,category.getText(),Toast.LENGTH_SHORT).show();
                }
                    else if(category.getText().equals("Entertainment News"))
                    {
                        //link="https://economictimes.indiatimes.com/rssfeeds/13357410.cms";
                        link="http://www.bollywoodhungama.com/rss/news.xml";
                        title=category.getText().toString();
                        Toast.makeText(CategoryActivity.this,category.getText(),Toast.LENGTH_SHORT).show();
                    }
                else if(category.getText().equals("Technology News"))
                {
                    link="http://feeds.bbci.co.uk/news/technology/rss.xml";
                    title=category.getText().toString();
                    Toast.makeText(CategoryActivity.this,category.getText(),Toast.LENGTH_SHORT).show();

                }
                    else if(category.getText().equals("Lifestyle News"))
                    {
                        link="https://www.news18.com/rss/lifestyle.xml";
                        title=category.getText().toString();
                        Toast.makeText(CategoryActivity.this,category.getText(),Toast.LENGTH_SHORT).show();

                    }
                else if(category.getText().equals("Science and Environment News"))
                {
                    link="http://feeds.bbci.co.uk/news/science_and_environment/rss.xml";
                    title=category.getText().toString();
                    Toast.makeText(CategoryActivity.this,category.getText(),Toast.LENGTH_SHORT).show();
                }
                else if(category.getText().equals("Sports News"))
                    {
                        link="https://www.news18.com/rss/sports.xml";
                        title=category.getText().toString();
                        Toast.makeText(CategoryActivity.this,category.getText(),Toast.LENGTH_SHORT).show();
                    }
                Intent i=new Intent(CategoryActivity.this,MainActivity.class);
                Bundle bun = new Bundle();
                bun.putString("link",link);
                bun.putString("tit",title);
                i.putExtras(bun);
                startActivity(i);
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser==null)
        {
            sendtostart();
            finish();
        }
    }
    private void sendtostart() {
        Intent i=new Intent(CategoryActivity.this,StartActivity.class);
        startActivity(i);
    }

   /* @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.auth_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.auth_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.logout)
        {
            FirebaseAuth.getInstance().signOut();
            sendtostart();
        }
        return true;
    }

}
