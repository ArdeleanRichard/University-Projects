package com.example.calatour.calatour;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calatour.calatour.model.OfferList;

public class OfferDetailsActivity extends AppCompatActivity {

    Integer position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_details);
        setTitle("Offer details");

        Intent intent = getIntent();
        position = intent.getIntExtra("position", -1);

        TextView title = findViewById(R.id.textTitle);
        TextView description = findViewById(R.id.textDescription);
        TextView price = findViewById(R.id.textPrice);
        ImageView image = findViewById(R.id.image);
        TextView timesDisplay = findViewById(R.id.textTimesDisplay);

        title.setText((OfferList.getInstance()).offers.get(position).getTitle());
        price.setText((OfferList.getInstance()).offers.get(position).getPrice().toString());
        description.setText((OfferList.getInstance()).offers.get(position).getDescription());
        image.setImageResource((OfferList.getInstance()).offers.get(position).getImage());
        (OfferList.getInstance()).offers.get(position).setTimesDisplay((OfferList.getInstance()).offers.get(position).getTimesDisplay()+1);
        timesDisplay.setText("Details page displayed: "+(OfferList.getInstance()).offers.get(position).getTimesDisplay().toString()+" times");
    }

    public	boolean	onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.description_option_menu, menu);
        MenuItem item = menu.getItem(0);
        if((OfferList.getInstance()).offers.get(position).getFavorite()==true)
        {
            item.setTitle("Remove from favorites");
        }
        else {
            item.setTitle("Add to favorites");
        }
        return	true;
    }

    public	boolean	onOptionsItemSelected(MenuItem	item)
    {
        //	Handle	action	bar	item	clicks	here.	The	action	bar	will
        //	automatically	handle	clicks	on	the	Home/Up	button,	so	long
        //	as	you	specify	a	parent	activity	in	AndroidManifest.xml.

        int	id = item.getItemId();

        if (id == R.id.btnToggleFavorite)
        {
            if((OfferList.getInstance()).offers.get(position).getFavorite()==true)
            {
                Toast.makeText(OfferDetailsActivity.this, "Removed from favorites",Toast.LENGTH_SHORT).show();
                (OfferList.getInstance()).offers.get(position).setFavorite(false);
                item.setTitle("Add to favorites");
            }
            else
            {
                Toast.makeText(OfferDetailsActivity.this, "Added to favorites",Toast.LENGTH_SHORT).show();
                (OfferList.getInstance()).offers.get(position).setFavorite(true);
                item.setTitle("Remove from favorites");
            }
            return	true;
        }

        return	super.onOptionsItemSelected(item);
    }
}
