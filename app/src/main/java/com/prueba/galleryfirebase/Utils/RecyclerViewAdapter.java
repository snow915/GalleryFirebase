package com.prueba.galleryfirebase.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prueba.galleryfirebase.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    public ArrayList<String> url;
    private Context context;

    public RecyclerViewAdapter(ArrayList<String> url, Context context){
        this.url = url;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_layout, null);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Glide.with(context)
                .load(url.get(position))
                .fitCenter()
                .centerCrop()
                .into(holder.imgRetrieved);

    }

    @Override
    public int getItemCount() {
        return url.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{

        public ImageView imgRetrieved;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            imgRetrieved = (ImageView) itemView.findViewById(R.id.img_retrieved);


        }
    }

}
