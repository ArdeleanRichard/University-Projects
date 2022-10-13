package com.example.calatour.calatour;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;

import com.example.calatour.calatour.adapter.OfferAdapter;
import com.example.calatour.calatour.model.Offer;
import com.example.calatour.calatour.model.OfferList;

import java.util.ArrayList;
import java.util.List;

public class OfferActivity extends AppCompatActivity {

    static List<Offer> offers;
    List<Offer> startOffers;

    ListView listView;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_list);
        setTitle("Offer list");

        Intent intent = getIntent();
        username = intent.getStringExtra("username");


        startOffers = new ArrayList<>();
        startOffers.addAll((OfferList.getInstance()).offers);

        listView = findViewById(R.id.booksList);
        OfferAdapter listAdapter = new OfferAdapter(this, (OfferList.getInstance()).offers);
        listView.setAdapter(listAdapter);
        registerForContextMenu(listView);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(OfferActivity.this, OfferDetailsActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        // verificăm dacă meniul este creat pentru lista vizată
        if (v.getId()==R.id.booksList)
        {
            // identificăm elementul selectat din listă
            AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
            OfferAdapter offerAdapter = (OfferAdapter) ((ListView) v).getAdapter();
            menu.setHeaderTitle(offerAdapter.getItem(info.position).getTitle());
            // încărcăm structura vizuală a meniului
            getMenuInflater().inflate(R.menu.contextual_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        // accesarea informației atașate meniului contextual
        AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();

        // identificarea elementului selectat din meniu, folosind ID-urile predefinite
        if(item.getItemId() == R.id.add)
        {
            (OfferList.getInstance()).offers.add(info.position, new Offer("Title added", "Description", 0, R.drawable.offer_1, false, 0));
            ((OfferAdapter) listView.getAdapter()).notifyDataSetChanged();
        }
        else if(item.getItemId() == R.id.remove)
        {
            (OfferList.getInstance()).offers.remove(info.position);
            ((OfferAdapter) listView.getAdapter()).notifyDataSetChanged();
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        final AlertDialog.Builder logoutConfirmation = new AlertDialog.Builder(this);
        logoutConfirmation
                .setTitle("Confirmation")
                .setMessage("Please confirm log out intention")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        OfferActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(OfferActivity.this, "You have cancelled",Toast.LENGTH_SHORT).show();
                    }
                })
                .show();

    }

    public	boolean	onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return	true;
    }

    public	boolean	onOptionsItemSelected(MenuItem	item)
    {
        //	Handle	action	bar	item	clicks	here.	The	action	bar	will
        //	automatically	handle	clicks	on	the	Home/Up	button,	so	long
        //	as	you	specify	a	parent	activity	in	AndroidManifest.xml.
        int	id = item.getItemId();
        if (id == R.id.btnSignOut)
        {
            //	code for Option1
            onBackPressed();
            return	true;
        } else if (id == R.id.btnResetList) {
            (OfferList.getInstance()).resetList();
            ((OfferAdapter) listView.getAdapter()).notifyDataSetChanged();
            Toast.makeText(OfferActivity.this, "List has been reset",Toast.LENGTH_SHORT).show();

        } else if (id == R.id.btnClearFavorites) {
            (OfferList.getInstance()).resetFavorites();
        } else if (id == R.id.btnChat) {
            Intent intent =	new	Intent(this, ChatActivity.class);
            intent.putExtra("username",username);
            startActivity(intent);
        }

        return	super.onOptionsItemSelected(item);
    }
}
