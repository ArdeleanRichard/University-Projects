package com.example.calatour.calatour.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.calatour.calatour.R;
import com.example.calatour.calatour.model.Chat;

/**
 * Created by Rici on 05-Dec-18.
 */

class MyViewHolder extends RecyclerView.ViewHolder {

    private TextView messageText;
    private TextView usernameText;
    private TextView timeText;
    private ImageView starImage;


    public MyViewHolder(View itemView) {
        super(itemView);
        messageText = itemView.findViewById(R.id.textMessage);
        usernameText = itemView.findViewById(R.id.textUsernameChat);
        timeText = itemView.findViewById(R.id.textTime);
        starImage = itemView.findViewById(R.id.starImage);
    }

    public void bindViewHolder(Chat chat) {
        messageText.setText(chat.getMessage());
        usernameText.setText(chat.getUsername());
        timeText.setText(chat.getTime().toString());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // tratarea evenimentului de click pentru acest element (în cazul nostru setarea stării de favorite pe obiectul curent și afișarea sau ascunderea iconiței de favorite)
                starImage.setImageResource(R.drawable.star);
            }
        });
    }


}
