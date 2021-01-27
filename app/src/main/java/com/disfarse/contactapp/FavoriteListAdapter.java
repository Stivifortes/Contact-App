package com.disfarse.contactapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class FavoriteListAdapter extends RecyclerView.Adapter<FavoriteListAdapter.FavoriteViewHolder>{

    Context context;
    private final LinkedList<Contact> favoriteList_A;

    private LayoutInflater mInflater;

    public FavoriteListAdapter(Context context, LinkedList<Contact> favoriteList) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.favoriteList_A = favoriteList;
    }

    @NonNull
    @Override
    public FavoriteListAdapter.FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.favorite_item, parent, false);

        return new FavoriteListAdapter.FavoriteViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteListAdapter.FavoriteViewHolder holder, int position) {
        Contact contact=favoriteList_A.get(position);
        holder.nameTV.setText(contact.getName());
        holder.phoneTV.setText(contact.getTelefone());
        holder.emailTV.setText(contact.getEmail());
    }

    @Override
    public int getItemCount() {

        return favoriteList_A.size();
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        public TextView nameTV, phoneTV,emailTV;
        final FavoriteListAdapter mAdapter;

        public FavoriteViewHolder(@NonNull View itemView, FavoriteListAdapter adapter) {
            super(itemView);

            nameTV = itemView.findViewById(R.id.tv_nomeF);
            phoneTV = itemView.findViewById(R.id.tv_telefoneF);
            emailTV = itemView.findViewById(R.id.tv_mailF);

            this.mAdapter=adapter;
            // itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            MenuItem call = menu.add(Menu.NONE,1,1,"Call");
            MenuItem sms = menu.add(Menu.NONE,2,2,"SMS");
            MenuItem email = menu.add(Menu.NONE,3,3,"Email");

            call.setOnMenuItemClickListener(onChange);
            sms.setOnMenuItemClickListener(onChange);
            email.setOnMenuItemClickListener(onChange);
        }
        private final MenuItem.OnMenuItemClickListener onChange = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String telefone=phoneTV.getText().toString().trim();
                String email=emailTV.getText().toString().trim();
                String nome=nameTV.getText().toString().trim();

                switch (item.getItemId()){
                    case 1:
                        Intent c = new Intent(Intent.ACTION_DIAL);
                        c.setData(Uri.parse("tel:" + telefone));
                        if (c.resolveActivity(context.getPackageManager()) != null)
                            context.startActivity(c);
                        else
                            Toast.makeText(context, "Não existe nenhuma aplicação para executar a ação.", Toast.LENGTH_LONG).show();
                        return true;

                    case 2:
                        Intent s = new Intent(Intent.ACTION_SEND);
                        s.setData(Uri.parse("smsto:"));
                        s.putExtra("sms_body", "Hey "+nome);
                        s.putExtra("address", new String(telefone));
                        if (s.resolveActivity(context.getPackageManager()) != null)
                            context.startActivity(s);
                        else
                            Toast.makeText(context, "Não existe nenhuma aplicação para executar a ação.", Toast.LENGTH_LONG).show();
                        return true;

                    case 3:
                        Intent e = new Intent(Intent.ACTION_SEND);
                        e.setType("*/*");
                        //intent.setData(Uri.parse("mailto:"));
                        e.putExtra(Intent.EXTRA_EMAIL, email);
                        e.putExtra(Intent.EXTRA_SUBJECT, "RecycleView");
                        e.putExtra(Intent.EXTRA_TEXT, "Olá "+nome);
                        if (e.resolveActivity(context.getPackageManager()) != null)
                            context.startActivity(e);
                        else
                            Toast.makeText(context, "Não existe nenhuma aplicação para executar a ação.", Toast.LENGTH_LONG).show();
                        return true;
                }
                return false;
            }
        };
    }


}