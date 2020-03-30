package com.pizzapp.v2.curActivityClasses;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.pizzapp.v2.R;
import com.pizzapp.v2.adminActivityClasses.UpdateCurActivity;
import com.pizzapp.v2.segedClassok.Globals;

public class CurProfActivity extends AppCompatActivity {
    //---------
    private TextView txtVw_name, txtVw_email, txtVw_phone, txtVw_nickName;
    private String userId;
    private FirebaseAuth fauth;
    private FirebaseFirestore firestore;
    private Button btn_mod;
    private Globals global;

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
                txtVw_nickName.setText(documentSnapshot.getString("UserName"));
                txtVw_email.setText(documentSnapshot.getString("Email"));
                txtVw_phone.setText(documentSnapshot.getString("Phone"));
            }
        });
        btn_mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UID = fauth.getCurrentUser().getUid();
                global.setValue(UID);
                startActivity(new Intent(CurProfActivity.this, UpdateCurActivity.class));
            }
        });
        //---------
    }

    //---------
    private void inito() {
        global = Globals.getInstance();
        txtVw_nickName = findViewById(R.id.cur_prof_nickName);
        txtVw_name = findViewById(R.id.cur_prof_name);
        txtVw_email = findViewById(R.id.cur_prof_email);
        txtVw_phone = findViewById(R.id.cur_prof_phone);
        btn_mod = findViewById(R.id.cur_mdfy);
        fauth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        userId = fauth.getCurrentUser().getUid();
    }
    //---------
}
//-----------------------
//    || __   ||
//    ||=\_`\=||
//    || (__/ ||
//    ||  | | :-"""-.
//    ||==| \/-=-.   \
//    ||  |(_|o o/   |_
//    ||   \/ "  \   ,_)
//    ||====\ ^  /__/
//    ||     ;--'  `-.
//    ||    /      .  \
//    ||===;        \  \
//    ||   |         | |
//    || .-\ '     _/_/
//    |:'  _;.    (_  \
//    /  .'  `;\   \\_/
//   |_ /     |||  |\\
//  /  _)=====|||  | ||
// /  /|      ||/  / //
// \_/||      ( `-/ ||
//    ||======/  /  \\ .-.
//    ||      \_/    \'-'/
//    ||      ||      `"`
//    ||======||
//    ||      ||
//-----------------------