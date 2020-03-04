package com.pizzapp.v2.curActivityClasses;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.pizzapp.v2.R;
import com.pizzapp.v2.misc.fakeLoad;

public class CurMenuActivity extends AppCompatActivity {
    //---------
    private ImageButton btn_curProf, btn_logout, btn_exitApp, btn_manageDeliveries;
    private FirebaseAuth fauth;

    //---------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cur_menu);
        //---------
        inito();
        //---------
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder exitAlert = new AlertDialog.Builder(CurMenuActivity.this);
                exitAlert.setMessage("Biztosan ki szeretnél jelentkezni?");
                exitAlert.setCancelable(true);
                exitAlert.setPositiveButton("aha", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(CurMenuActivity.this, fakeLoad.class));
                        finish();
                    }
                });
                exitAlert.setNegativeButton("no", new DialogInterface.OnClickListener() {
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
                exitAlert.setPositiveButton("aha", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);
                    }
                });
                exitAlert.setNegativeButton("no", new DialogInterface.OnClickListener() {
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
    }


    //---------
    private void inito() {
        btn_exitApp = findViewById(R.id.btn_exitCur);
        btn_logout = findViewById(R.id.btn_logoutCur);
        btn_manageDeliveries = findViewById(R.id.btn_mngDel);
        btn_curProf = findViewById(R.id.btn_curprof);
    }
    //---------
}