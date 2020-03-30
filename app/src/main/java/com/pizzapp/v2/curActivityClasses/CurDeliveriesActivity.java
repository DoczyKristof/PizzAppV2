package com.pizzapp.v2.curActivityClasses;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.pizzapp.v2.R;
import com.pizzapp.v2.segedClassok.FireStoreAdapter;
import com.pizzapp.v2.segedClassok.Globals;
import com.pizzapp.v2.segedClassok.Order;

public class CurDeliveriesActivity extends AppCompatActivity {

    private TextView txtVw_empty;
    private RecyclerView rcclVw;
    private FirebaseFirestore firestore;
    private CollectionReference cferenc, cferi;
    private FireStoreAdapter adapter;
    private FirebaseAuth fauth;
    private String UID;
    private Query query;
    private Globals global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cur_deliveries);
        inito();
        //---------------
        setUpRecyclerView();
    }

    //---------------
    private void inito() {
        txtVw_empty = findViewById(R.id.txtVw_empty);
        rcclVw = findViewById(R.id.rcclVw);
        rcclVw.setHasFixedSize(true);
        rcclVw.setLayoutManager(new LinearLayoutManager(this));
        firestore = FirebaseFirestore.getInstance();
        fauth = FirebaseAuth.getInstance();
        cferenc = firestore.collection("Orders");
        cferi = firestore.collection("couriers");
        global = Globals.getInstance();
        //---------------
    }

    private void setUpRecyclerView() {
        UID = fauth.getCurrentUser().getUid();
        query = cferenc.whereEqualTo("Courier", UID);
        FirestoreRecyclerOptions<Order> options = new FirestoreRecyclerOptions.Builder<Order>()
                .setQuery(query, Order.class).build();

        adapter = new FireStoreAdapter(options);
        adapter.setClickListener(new FireStoreAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                AlertDialog.Builder exitAlert = new AlertDialog.Builder(CurDeliveriesActivity.this);
                exitAlert.setMessage("Biztosan el szertnéd indítani a navigációt?");
                exitAlert.setCancelable(true);
                exitAlert.setPositiveButton("Igen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String str_location = global.getValue();
                        final String map = "http://maps.google.co.in/maps?q=" + str_location;
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(map)));
                    }
                });
                exitAlert.setNegativeButton("Nem", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog exitAlertDlg = exitAlert.create();
                exitAlertDlg.show();
            }
        });
        rcclVw.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}