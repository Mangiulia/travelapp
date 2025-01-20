package com.example.myapplication;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Activează persistența pentru FirebaseDatabase
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}

/*Funcționare Offline: Aplicația poate funcționa offline, oferind o experiență mai bună utilizatorilor chiar și atunci când nu auconexiune la internet.
Performanță îmbunătățită: Datele sunt stocate local, ceea ce poate îmbunătăți performanța aplicației deoarece accesul la date se face din cache-ul local în loc de o cerere de rețea.
Sincronizare Automată: Când aplicația revine online, datele sunt sincronizate automat cu baza de date Firebase.
 */
