package com.pizzapp.v2.misc;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pizzapp.v2.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

public class orderplz extends AppCompatActivity {
    Button btn_assign;
    SearchableSpinner spinner_Rendeles, spinner_Futar;
    List<String> orderList, cursList;
    CollectionReference cFerenc, cFeri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderplz);
        inito();
        //---------------
        btn_assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cFeri.document(getSelectedID()).update(
                        "Courier", getSelectedName()
                ).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(orderplz.this, "Sikeres módosítás.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(orderplz.this, "Sikertelen módosítás.", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    private void inito() {
        btn_assign = findViewById(R.id.btn_assign);
        spinner_Rendeles = findViewById(R.id.spinnerOrder);
        spinner_Futar = findViewById(R.id.spinnerCur);
        orderList = new ArrayList<>();
        cursList = new ArrayList<>();
        cFerenc = FirebaseFirestore.getInstance().collection("couriers");
        cFeri = FirebaseFirestore.getInstance().collection("Orders");
        //---------------
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item, cursList);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cFerenc.whereEqualTo("activity", true).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        String cur = doc.getString("UID");
                        cursList.add(cur);
                    }
                    adapter1.notifyDataSetChanged();
                }
            }
        });
        spinner_Futar.setAdapter(adapter1);
        //---------------
        final ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item, orderList);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cFeri.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        String order = (String) doc.getId();
                        orderList.add(order);
                    }
                    adapter2.notifyDataSetChanged();
                }
            }
        });
        spinner_Rendeles.setAdapter(adapter2);
        //---------------
    }

    private String getSelectedName() {
        return spinner_Futar.getSelectedItem().toString();
    }

    private String getSelectedID() {
        return spinner_Rendeles.getSelectedItem().toString();
    }
}
