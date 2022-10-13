package com.example.calatour.calatour.model;

import com.example.calatour.calatour.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rici on 26-Nov-18.
 */

public class OfferList
{
    // static variable single_instance of type Singleton
    private static OfferList single_instance = null;

    // variable of type String
    public List<Offer> offers;

    // private constructor restricted to this class itself
    public OfferList()
    {
        offers = new ArrayList<>();
        offers.add(new Offer("Barcelona, 3 nights", "blalbla", 300, R.drawable.offer_1, false, 0));
        offers.add(new Offer("Maldive, 7 nights", "blalbla", 1050, R.drawable.offer_2, false, 0));
        offers.add(new Offer("Thailand, 10 nights", "blalbla", 1250, R.drawable.offer_3, false, 0));
    }

    // static method to create instance of Singleton class
    public static OfferList getInstance()
    {
        if (single_instance == null)
            single_instance = new OfferList();

        return single_instance;
    }

    public void resetList() {
        offers.clear();
        offers.add(new Offer("Barcelona, 3 nights", "blalbla", 300, R.drawable.offer_1, false, 0));
        offers.add(new Offer("Maldive, 7 nights", "blalbla", 1050, R.drawable.offer_2, false, 0));
        offers.add(new Offer("Thailand, 10 nights", "blalbla", 1250, R.drawable.offer_3, false, 0));

    }

    public void resetFavorites()
    {
        for(Offer o: offers)
        {
            o.setFavorite(false);
        }
    }
}