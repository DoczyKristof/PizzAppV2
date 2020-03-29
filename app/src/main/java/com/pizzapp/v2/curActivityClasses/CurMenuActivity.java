package com.pizzapp.v2.curActivityClasses;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.pizzapp.v2.R;
import com.pizzapp.v2.misc.fakeLoad;

public class CurMenuActivity extends AppCompatActivity {
    //---------
    private TextView txt_profname;
    private CardView btn_curProf, btn_logout, btn_exitApp, btn_manageDeliveries;
    private FirebaseAuth fauth;
    private FirebaseFirestore firestore;
    private String userId;

    //---------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cur_menu);
        //---------
        inito();
        //---------------
        final DocumentReference dFerenc = firestore.collection("couriers").document(userId);
        dFerenc.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                txt_profname.setText(documentSnapshot.getString("UserName"));
            }
        });
        //---------------
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder exitAlert = new AlertDialog.Builder(CurMenuActivity.this);
                exitAlert.setMessage("Biztosan ki szeretnél jelentkezni?");
                exitAlert.setCancelable(true);
                exitAlert.setPositiveButton("Igen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(CurMenuActivity.this, fakeLoad.class));
                        finish();
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
        //---------------
        btn_curProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CurMenuActivity.this, CurProfActivity.class));
            }
        });
        //---------------
        btn_exitApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder exitAlert = new AlertDialog.Builder(CurMenuActivity.this);
                exitAlert.setMessage("Biztosan be szeretnéd zárni az appot?");
                exitAlert.setCancelable(true);
                exitAlert.setPositiveButton("Igen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);
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
        //---------------
        btn_manageDeliveries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CurMenuActivity.this, CurDeliveriesActivity.class));
            }
        });
    }


    //---------
    private void inito() {
        txt_profname = findViewById(R.id.txt_menuName);
        btn_exitApp = findViewById(R.id.btn_exitCur);
        btn_logout = findViewById(R.id.btn_logoutCur);
        btn_manageDeliveries = findViewById(R.id.btn_mngDel);
        btn_curProf = findViewById(R.id.btn_curprof);
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