package com.eiadmreh.listviewwithimg;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomListview extends ArrayAdapter<String> {
    private String[] fruitname;
    private String[] disc;
    private Bitmap[] bitmaps;
    private Activity context;
    private ArrayList<Frouit> frouits;
    public CustomListview(Activity context,  String[] fruitname, String[] disc,Bitmap[] imgid) {
        super(context,R.layout.listview_layout,fruitname);

        this.context=context;
        this.fruitname=fruitname;
        this.disc=disc;
        this.bitmaps=imgid;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view=convertView;
        VieHolder viewHolder=null;
        if(view==null){
            LayoutInflater layoutInflater=context.getLayoutInflater();
            view=layoutInflater.inflate(R.layout.listview_layout,null,true);
            viewHolder =new VieHolder(view);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (VieHolder) view.getTag();
        }
        /*Glide
                .with(context)
                .load(bitmaps[position])
                .into(viewHolder.ivw);*/
        //Bitmap bitmap = BitmapFactory.decodeFile(bitmaps[position]);
        viewHolder.ivw.setImageBitmap(bitmaps[position]);
        viewHolder.tvw1.setText(fruitname[position]);
        viewHolder.tvw2.setText(disc[position]);

        return view;
    }

    class VieHolder{
        TextView tvw1;
        TextView tvw2;
        ImageView ivw;
        VieHolder(View v){
                tvw1=(TextView) v.findViewById(R.id.tvFroutname);
                tvw2=(TextView) v.findViewById(R.id.tvDescreption);
                ivw=(ImageView)v.findViewById(R.id.ImageView);
        }

    }
}
