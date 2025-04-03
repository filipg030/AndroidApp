package com.example.jwtapp.model;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jwtapp.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private List<Post> postList;
    private int[] colors = {0xFFFFA69E, 0xFFB8F2E6, 0xFFAED9E0}; // Color array

    public Drawable convertFileToDrawable(File file, Context context) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
            fileInputStream.close();
            return new BitmapDrawable(context.getResources(), bitmap);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public PostAdapter(List<Post> postList) {
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Log.d("xxx", String.valueOf(position));
        Post post = postList.get(position);
        holder.titleTextView.setText(post.getEmail());
        holder.description.setText(post.getTitle());

        //zmienic na zdjecie!!
        byte[] decodedString = Base64.decode(post.getImageBase64(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.imageContent.setImageBitmap(decodedByte);
//        holder.imageContent.setBackground(post.getImageBase64()).;

        // Set alternating background colors
        holder.itemView.setBackgroundColor(colors[position % colors.length]);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, description;
        ImageView imageContent;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            description = itemView.findViewById(R.id.description);
            imageContent = itemView.findViewById(R.id.imageContent);
        }
    }
}
