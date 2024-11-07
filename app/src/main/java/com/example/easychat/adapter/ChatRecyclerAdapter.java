package com.example.easychat.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.easychat.R;
import com.example.easychat.model.ChatMessageModel;
import com.example.easychat.utils.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ChatRecyclerAdapter extends FirestoreRecyclerAdapter<ChatMessageModel, ChatRecyclerAdapter.ChatModelViewHolder> {

    private final Context context;

    public ChatRecyclerAdapter(@NonNull FirestoreRecyclerOptions<ChatMessageModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatModelViewHolder holder, int position, @NonNull ChatMessageModel model) {
        if (model.getSenderId().equals(FirebaseUtil.currentUserId())) {
            holder.leftChatLayout.setVisibility(View.GONE);
            holder.rightChatLayout.setVisibility(View.VISIBLE);
            if (model.isImage()) {
                holder.rightChatTextview.setVisibility(View.GONE);
                holder.rightChatImageView.setVisibility(View.VISIBLE);
                Bitmap imageBitmap = decodeBase64Image(model.getMessage());
                holder.rightChatImageView.setImageBitmap(imageBitmap);
            } else {
                holder.rightChatTextview.setVisibility(View.VISIBLE);
                holder.rightChatImageView.setVisibility(View.GONE);
                holder.rightChatTextview.setText(model.getMessage());
            }
        } else {
            holder.rightChatLayout.setVisibility(View.GONE);
            holder.leftChatLayout.setVisibility(View.VISIBLE);
            if (model.isImage()) {
                holder.leftChatTextview.setVisibility(View.GONE);
                holder.leftChatImageView.setVisibility(View.VISIBLE);
                Bitmap imageBitmap = decodeBase64Image(model.getMessage());
                holder.leftChatImageView.setImageBitmap(imageBitmap);
            } else {
                holder.leftChatTextview.setVisibility(View.VISIBLE);
                holder.leftChatImageView.setVisibility(View.GONE);
                holder.leftChatTextview.setText(model.getMessage());
            }
        }
    }
    private Bitmap decodeBase64Image(String encodedImage) {
        byte[] decodedBytes = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }


    @NonNull
    @Override
    public ChatModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_message_recycler_row, parent, false);
        return new ChatModelViewHolder(view);
    }

    static class ChatModelViewHolder extends RecyclerView.ViewHolder {

        LinearLayout leftChatLayout, rightChatLayout;
        TextView leftChatTextview, rightChatTextview;
        ImageView leftChatImageView, rightChatImageView;

        public ChatModelViewHolder(@NonNull View itemView) {
            super(itemView);
            leftChatLayout = itemView.findViewById(R.id.left_chat_layout);
            rightChatLayout = itemView.findViewById(R.id.right_chat_layout);
            leftChatTextview = itemView.findViewById(R.id.left_chat_textview);
            rightChatTextview = itemView.findViewById(R.id.right_chat_textview);
            leftChatImageView = itemView.findViewById(R.id.left_chat_imageview);
            rightChatImageView = itemView.findViewById(R.id.right_chat_imageview);
        }
    }
}
