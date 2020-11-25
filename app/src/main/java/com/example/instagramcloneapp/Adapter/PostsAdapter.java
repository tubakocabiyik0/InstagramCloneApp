package com.example.instagramcloneapp.Adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramcloneapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.MyHolder> {


    private ArrayList<String> arrayListComment = new ArrayList<>();
    private ArrayList<String> arrayListUserM = new ArrayList<>();
    private ArrayList<Bitmap> arrayListImage = new ArrayList<>();

    public PostsAdapter(ArrayList arrayListComment, ArrayList arrayListImage, ArrayList arrayListUserM) {
        this.arrayListImage = arrayListImage;
        this.arrayListComment = arrayListComment;
        this.arrayListUserM = arrayListUserM;

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.posts_recycle, parent, false);

        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.userMail.setText(arrayListUserM.get(position).toString());
        holder.comment.setText(arrayListComment.get(position).toString());
        Picasso.get().load(String.valueOf(arrayListImage.get(position))).into(holder.image);
    }

    @Override
    public int getItemCount() {

        return arrayListUserM.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        ImageView image, likeI, commentI;
        TextView comment, userMail;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView2);
            commentI = itemView.findViewById(R.id.followerComment);
            likeI = itemView.findViewById(R.id.like);
            comment = itemView.findViewById(R.id.textView4);
            userMail = itemView.findViewById(R.id.textView3);


        }

    }


}
