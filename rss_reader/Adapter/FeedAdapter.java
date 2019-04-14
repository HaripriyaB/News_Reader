package com.example.haripriya.rss_reader.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.haripriya.rss_reader.ArticlePageActivity;
import com.example.haripriya.rss_reader.Interface.ItemClickListener;
import com.example.haripriya.rss_reader.MainActivity;
import com.example.haripriya.rss_reader.Model.Item;
import com.example.haripriya.rss_reader.Model.RSSObject;
import com.example.haripriya.rss_reader.R;
import com.example.haripriya.rss_reader.StartActivity;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Haripriya on 25-03-2018.
 */

class FeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener
{

    public TextView txtTitle,txtPubdate,txtContent;
    public ImageView imgageview;
    CardView cd;
    private ItemClickListener itemClickListener;


    public FeedViewHolder(View itemView) {
        super(itemView);

        txtTitle=(TextView)itemView.findViewById(R.id.txttitle);
        txtPubdate=(TextView)itemView.findViewById(R.id.txtpubdate);
        txtContent=(TextView)itemView.findViewById(R.id.txtcontent);
        imgageview=(ImageView) itemView.findViewById(R.id.img);
        cd= itemView.findViewById(R.id.cardview);

        //set event

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }

    @Override
    public boolean onLongClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),true);
        return true;
    }
}

public class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder> {

    private RSSObject rssObject;
    private Context mcontext;
    private LayoutInflater inflater;
    private String searchtext;
    int i=0;

    public FeedAdapter(RSSObject rssObject, Context mcontext,String searchtext) {
        this.rssObject = rssObject;
        this.mcontext = mcontext;
        this.searchtext=searchtext;
        inflater=LayoutInflater.from(mcontext);
    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview=inflater.inflate(R.layout.row,parent,false);
        return new FeedViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(FeedViewHolder holder, int position) {

        if(searchtext!=null) {
                    holder.txtTitle.setText(rssObject.getItems().get(position).getTitle());
                    holder.txtPubdate.setText(rssObject.getItems().get(position).getPubDate());
                    holder.txtContent.setText(rssObject.getItems().get(position).getContent().replaceAll("<(.*)>", ""));

                    Picasso.get().load(rssObject.getItems().get(position).getThumbnail()).into(holder.imgageview);

                    holder.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {
                            if (!isLongClick) {
                                Intent i=new Intent(mcontext, ArticlePageActivity.class);
                                Bundle bundle=new Bundle();
                                bundle.putString("imgview",rssObject.getItems().get(position).getThumbnail());
                                bundle.putString("title",rssObject.getItems().get(position).getTitle());
                                bundle.putString("date",rssObject.getItems().get(position).getPubDate());
                                bundle.putString("source",rssObject.getItems().get(position).getContent().replaceAll("<(.*)>", ""));
                                bundle.putString("articlelink",rssObject.getItems().get(position).getLink());
                                //Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(rssObject.getItems().get(position).getLink()));
                                i.putExtras(bundle);
                                mcontext.startActivity(i);
                            }
                        }
                    });
        }
        else
        {

            holder.txtTitle.setText(rssObject.getItems().get(position).getTitle());
            holder.txtPubdate.setText(rssObject.getItems().get(position).getPubDate());
            holder.txtContent.setText(rssObject.getItems().get(position).getContent().replaceAll("<(.*)>", ""));

            Picasso.get().load(rssObject.getItems().get(position).getThumbnail()).into(holder.imgageview);


            holder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    if (!isLongClick) {
                        Intent i=new Intent(mcontext, ArticlePageActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putString("imgview",rssObject.getItems().get(position).getThumbnail());
                        bundle.putString("title",rssObject.getItems().get(position).getTitle());
                        bundle.putString("date",rssObject.getItems().get(position).getPubDate());
                        bundle.putString("source",rssObject.getItems().get(position).getContent().replaceAll("<(.*)>", ""));
                        bundle.putString("articlelink",rssObject.getItems().get(position).getLink());
                        //Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(rssObject.getItems().get(position).getLink()));
                        i.putExtras(bundle);
                        mcontext.startActivity(i);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return rssObject.items.size();
    }

}
