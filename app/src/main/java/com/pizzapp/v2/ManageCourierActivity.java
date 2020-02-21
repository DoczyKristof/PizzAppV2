package com.pizzapp.v2;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

public class ManageCourierActivity extends AppCompatActivity {
    //---------
    SearchableSpinner spinner;
    CollectionReference CFerenc;
    FirebaseFirestore firestore;
    List<String> cursList;
    Button btn_delCur, btn_modCur;

    //---------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_courier);
        //---------
        inito();
        //---------------
        btn_delCur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //---------------
                //---------------
                //---------------
                //---------------
            }
        });
    }

    //---------
    private void inito() {
        btn_delCur = findViewById(R.id.btn_delCur);
        btn_modCur = findViewById(R.id.btn_modCur);
        spinner = findViewById(R.id.searchableSpinner);
        firestore = FirebaseFirestore.getInstance();
        CFerenc = firestore.collection("couriers");
        cursList = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item, cursList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //---------------
        CFerenc.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        String cur = doc.getString("Name");
                        cursList.add(cur);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}
