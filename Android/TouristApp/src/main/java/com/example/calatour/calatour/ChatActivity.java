package com.example.calatour.calatour;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.calatour.calatour.adapter.ChatAdapter;
import com.example.calatour.calatour.model.Chat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Chat> chatMessages = new ArrayList<>();
    String username;
    EditText message;
    TextView textUsername;
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        textUsername = findViewById(R.id.textUsername);
        textUsername.setText("Username: "+username);
        message = findViewById(R.id.inputMessage);

        ChatAdapter chatAdapter = new ChatAdapter(this, chatMessages);
        recyclerView = findViewById(R.id.messagesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(chatAdapter);
    }

    public void SendMessage(View view)
    {
        String currentDateandTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        chatMessages.add(0, new Chat(username, message.getText().toString(), currentDateandTime));
        ((ChatAdapter) recyclerView.getAdapter()).notifyDataSetChanged();
        if(username.equals("admin")) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    // Actions to do after wait
                    String currentDateandTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    chatMessages.add(0, new Chat("Computer", "Automated message:" + (i++), currentDateandTime));
                    ((ChatAdapter) recyclerView.getAdapter()).notifyDataSetChanged();
                }
            }, 3000);
        }
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }

    public	boolean	onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_menu, menu);
        return true;
    }

    public	boolean	onOptionsItemSelected(MenuItem item)
    {
        //	Handle	action	bar	item	clicks	here.	The	action	bar	will
        //	automatically	handle	clicks	on	the	Home/Up	button,	so	long
        //	as	you	specify	a	parent	activity	in	AndroidManifest.xml.

        int	id = item.getItemId();

        if (id == R.id.design1)
        {
            ((ChatAdapter) recyclerView.getAdapter()).getItemViewType(-1);
            ((ChatAdapter) recyclerView.getAdapter()).notifyDataSetChanged();
        }
        else if (id == R.id.design2)
        {
            ((ChatAdapter) recyclerView.getAdapter()).getItemViewType(-2);
            ((ChatAdapter) recyclerView.getAdapter()).notifyDataSetChanged();
        }
        else if (id == R.id.designBoth)
        {
            ((ChatAdapter) recyclerView.getAdapter()).notifyDataSetChanged();
        }

        return	super.onOptionsItemSelected(item);
    }

}
