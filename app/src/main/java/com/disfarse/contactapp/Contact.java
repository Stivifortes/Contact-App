package com.disfarse.contactapp;

import android.content.Intent;

import java.util.Date;

public class Contact {

    private String name;
    private String email;
    private String telefone;
    private boolean isFavorite = false;

    public Contact(String name, String email, String telefone, boolean isFavorite) {
        this.name = name;
        this.email = email;
        this.telefone = telefone;
        this.isFavorite = isFavorite;
    }

    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getTelefone() {
        return telefone;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public boolean isFavorite() {
        return isFavorite;
    }
}
