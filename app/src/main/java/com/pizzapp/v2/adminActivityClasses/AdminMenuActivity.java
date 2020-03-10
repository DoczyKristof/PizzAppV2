package com.pizzapp.v2.adminActivityClasses;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.pizzapp.v2.R;
import com.pizzapp.v2.misc.fakeLoad;

public class AdminMenuActivity extends AppCompatActivity {
    //---------
    private ImageButton btn_regCur, btn_logout, btn_manageCur, btn_exit;

    //---------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);
        //---------
        inito();
        //---------
        btn_regCur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMenuActivity.this, RegCurActivity.class));
            }
        });
        //---------
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder exitAlert = new AlertDialog.Builder(AdminMenuActivity.this);
                exitAlert.setMessage("Biztosan ki szeretnél lépni?");
                exitAlert.setCancelable(true);
                exitAlert.setPositiveButton("aha", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(AdminMenuActivity.this, fakeLoad.class));
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
        //---------
        btn_manageCur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminMenuActivity.this, ManageCourierActivity.class));
            }
        });
        //---------
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder exitAlert = new AlertDialog.Builder(AdminMenuActivity.this);
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
    }

    //---------
    private void inito() {
        btn_regCur = findViewById(R.id.btn_add_courier);
        btn_logout = findViewById(R.id.btn_logoutAdmin);
        btn_manageCur = findViewById(R.id.btn_manage_courier);
        btn_exit = findViewById(R.id.btn_exitAdmin);
    }
    //---------
}
