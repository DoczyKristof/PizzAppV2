package com.pizzapp.v2;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class CurProfActivity extends AppCompatActivity {
    //---------
    private TextView txtVw_name, txtVw_email, txtVw_phone;
    private String userId;
    private FirebaseAuth fauth;
    private FirebaseFirestore firestore;

    //---------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cur_prof);
        //---------
        inito();
        //---------
        final DocumentReference dFerenc = firestore.collection("couriers").document(userId);
        dFerenc.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                txtVw_name.setText(documentSnapshot.getString("Name"));
                txtVw_email.setText(documentSnapshot.getString("Email"));
                txtVw_phone.setText(documentSnapshot.getString("Phone"));
            }
        });
        //---------
    }

    //---------
    private void inito() {
        txtVw_name = findViewById(R.id.cur_prof_name);
        txtVw_email = findViewById(R.id.cur_prof_email);
        txtVw_phone = findViewById(R.id.cur_prof_phone);
        fauth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        userId = fauth.getCurrentUser().getUid();
    }
    //---------
}