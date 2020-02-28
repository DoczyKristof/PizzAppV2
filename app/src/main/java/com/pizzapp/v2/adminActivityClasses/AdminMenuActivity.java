package com.pizzapp.v2.adminActivityClasses;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

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
                Intent bye = new Intent(AdminMenuActivity.this, RegCurActivity.class);
                startActivity(bye);
            }
        });
        //---------
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMenuActivity.this, fakeLoad.class));
                finish();
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
                finish();
                System.exit(0);
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
