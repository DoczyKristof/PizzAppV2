package com.pizzapp.v2.misc;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pizzapp.v2.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

public class orderplz extends AppCompatActivity {
    Button btn_assign;
    SearchableSpinner spinner_Rendeles, spinner_Futar;
    List<String> orderList, curList;
    DatabaseReference DFerenc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderplz);
        inito();
        //---------------
    }

    private void inito() {
        btn_assign = findViewById(R.id.btn_assign);
        spinner_Rendeles = findViewById(R.id.spinnerOrder);
        spinner_Futar = findViewById(R.id.spinnerCur);
        orderList = new ArrayList<>();
        DFerenc = FirebaseDatabase.getInstance().getReference("Orders");
        //---------------
        DFerenc.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapchat : dataSnapshot.getChildren()) {
                    String test = snapchat.child("deli_name").getValue(String.class);
                    orderList.add(test);
                }
                ArrayAdapter adapterOrder = new ArrayAdapter<>(
                        orderplz.this, android.R.layout.simple_spinner_item, orderList);
                adapterOrder.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_Rendeles.setAdapter(adapterOrder);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}
