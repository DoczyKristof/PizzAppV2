package com.pizzapp.v2;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
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
                AlertDialog.Builder byeCur = new AlertDialog.Builder(ManageCourierActivity.this);
                byeCur.setMessage("Biztosan törölni szeretnéd ezt a futárt?");
                byeCur.setCancelable(true);
                byeCur.setPositiveButton("aha", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setCurInactive(getSelectedName());
                        Toast.makeText(ManageCourierActivity.this,
                                "A "+ getSelectedName() +" nevű futár byebye"
                                , Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                byeCur.setNegativeButton("no soz", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog byeCurDlg = byeCur.create();
                byeCurDlg.show();
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
        CFerenc.whereEqualTo("activity",1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
    //---------------
    private String getSelectedName(){
        return spinner.getSelectedItem().toString();
    }
    //---------------
    private void setCurInactive(String name){
        CFerenc.document(name).update("activity",0);
    }
}
