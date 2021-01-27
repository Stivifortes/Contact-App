package com.disfarse.contactapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.LinkedList;

import static com.disfarse.contactapp.MainActivity.favoriteContact;
//import static com.example.android.recycleview.MainActivity.favoriteContact;

public class Favorite extends AppCompatActivity {
    //public final LinkedList<Contact> favoriteContact = new LinkedList<>();
    private RecyclerView mRecyclerViewF;
    private FavoriteListAdapter mAdapterF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        mRecyclerViewF = findViewById(R.id.recyclerview_favorite);


        //criar contacto
        //Contact newContact = new Contact(nome, email, telefone);
        //Adicionar contacto
        // Get a handle to the RecyclerView.
        // Create an adapter and supply the data to be displayed.
        /*if (newContact.isFavorite()==true)
            favoriteContact.add(newContact);
        */
        mAdapterF = new FavoriteListAdapter(this, favoriteContact);
        // Connect the adapter with the RecyclerView.

        mRecyclerViewF.setAdapter(mAdapterF);
        // Give the RecyclerView a default layout manager.
        mRecyclerViewF.setLayoutManager(new LinearLayoutManager(this));
    }
}