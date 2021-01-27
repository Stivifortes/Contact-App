package com.disfarse.contactapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.IconCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.annotation.Retention;
import java.util.LinkedList;

import static com.disfarse.contactapp.MainActivity.favoriteContact;
//import static com.example.android.recycleview.MainActivity.favoriteContact;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder> {

    Context context;
    private final LinkedList<Contact> contactList_A;

    private LayoutInflater mInflater;

    public ContactListAdapter(Context context, LinkedList<Contact> contactList) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.contactList_A = contactList;
    }

    @Override
    public ContactListAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.contact_item, parent, false);

        return new ContactViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        /*Contact mCurrent = contactList_A.get(position);
        ImageView userIcon= holder.userIV;
        ImageView favoriteIcon=holder.favoriteIV;
        TextView userName=holder.nameTV;
        TextView userPhone=holder.phoneTV;*/

        Contact contact=contactList_A.get(position);
        holder.nameTV.setText(contact.getName());
        holder.phoneTV.setText(contact.getTelefone());
        holder.emailTV.setText(contact.getEmail());

        holder.favoriteIV.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                if (contact.isFavorite()==false) {
                    contact.setFavorite(true);
                    favoriteContact.add(contact);
                    Toast.makeText(context,"Contact Added To Favorite",Toast.LENGTH_LONG).show();
                    holder.favoriteIV.setImageResource(R.drawable.ic_favorite_true);

                }
                else {
                    contact.setFavorite(false);
                    favoriteContact.remove(contact);
                    Toast.makeText(context,"Contact Removed From Favorite",Toast.LENGTH_LONG).show();
                    holder.favoriteIV.setImageResource(R.drawable.ic_favorite);
                }

            }
        });
        // holder.contactItemView.set(mCurrent);
    }

    @Override
    public int getItemCount() {
        return contactList_A.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        //public final LinearLayout contactItemView;
        public TextView nameTV, phoneTV,emailTV;
        public ImageButton favoriteIV;

        final ContactListAdapter mAdapter;

        public ContactViewHolder(@NonNull View itemView, ContactListAdapter adapter) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.tv_nomeC);
            phoneTV = itemView.findViewById(R.id.tv_telefoneC);
            emailTV = itemView.findViewById(R.id.tv_mailC);
            favoriteIV = itemView.findViewById(R.id.iv_favoriteC);

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
