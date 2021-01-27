package com.disfarse.contactapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    private static final int USER_PASS_REQUEST = 1;
    private final LinkedList<Contact> listContact = new LinkedList<>();
    public static final LinkedList<Contact> favoriteContact = new LinkedList<>();

    private RecyclerView mRecyclerView;
    private ContactListAdapter mAdapter;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddContact();
            }
        });

        mRecyclerView = findViewById(R.id.recyclerview);
        //mRecyclerViewF = findViewById(R.id.recyclerview_favorite);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings_item:
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Não existe nenhuma aplicação para executar a ação.", Toast.LENGTH_LONG).show();
                }
                return true;

            case R.id.favorites_item:
                Intent i = new Intent(getApplicationContext(), Favorite.class);
                startActivity(i);

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void AddContact() {
        Intent addContact = new Intent(this, AddContact.class);
        startActivityForResult(addContact,USER_PASS_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == USER_PASS_REQUEST){
            if (resultCode == RESULT_OK){

                String nome = data.getStringExtra("nome1");
                String telefone = data.getStringExtra("telefone1");
                String email= data.getStringExtra("email1");
                // String dataNasc = data.getStringExtra("apelido1");

                //criar contacto
                Contact newContact = new Contact(nome, email, telefone,false);
                //Adicionar contacto
                listContact.add(newContact);
                // Get a handle to the RecyclerView.
                // Create an adapter and supply the data to be displayed.
                 /*if (newContact.isFavorite()==true)
                     favoriteContact.add(newContact);*/

                mAdapter = new ContactListAdapter(this, listContact);
                //   mAdapterF = new FavoriteListAdapter(this, favoriteContact);
                // Connect the adapter with the RecyclerView.
                mRecyclerView.setAdapter(mAdapter);
                // mRecyclerViewF.setAdapter(mAdapterF);
                // Give the RecyclerView a default layout manager.
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                //    mRecyclerViewF.setLayoutManager(new LinearLayoutManager(this));

            }
        }
    }
}