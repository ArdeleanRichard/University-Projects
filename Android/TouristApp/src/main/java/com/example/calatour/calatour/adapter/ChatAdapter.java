package com.example.calatour.calatour.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.calatour.calatour.R;
import com.example.calatour.calatour.model.Chat;

import java.util.List;

/**
 * Created by Rici on 05-Dec-18.
 */

public class ChatAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private List<Chat> chatMessages;
    private Context context;

    private static final int DESIGN1 = 1;
    private static final int DESIGN2 = 2;
    private int CURRENT_DESIGN = -1;
    private boolean flag = false;

    public ChatAdapter(Context context, List<Chat> chatMessages) {
        this.context = context;
        this.chatMessages = chatMessages;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        if(getItemViewType(CURRENT_DESIGN) == DESIGN1)
        {
            return new MyViewHolder(layoutInflater.inflate(R.layout.chat_design1, viewGroup, false));
        } else {
            return new MyViewHolder(layoutInflater.inflate(R.layout.chat_design2, viewGroup, false));
        }
         // returns the view holder which is created from the view
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        viewHolder.bindViewHolder(chatMessages.get(position));
        // binds the view holder with the data. In that method, everything necessary for displaying the correct object should be set

    }
    @Override
    public int getItemCount() {
        return chatMessages.size(); // returns the number of objects from the list
    }

    // override this method if you want different types of view for the elements of the RecyclerView depending on their position
    @Override
    public int getItemViewType(int position) {
        // returns a code that will be used in method onCreateViewHolder to determine which layout to inflate for this item
        if(position==-1) {
            CURRENT_DESIGN = -1;
            return DESIGN1;
        }
        else if(position == -2) {
            CURRENT_DESIGN = -2;
            return DESIGN2;
        }
        return CURRENT_DESIGN;
    }
}
