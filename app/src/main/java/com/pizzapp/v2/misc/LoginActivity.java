package com.pizzapp.v2.misc;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.pizzapp.v2.curActivityClasses.CurMenuActivity;
import com.pizzapp.v2.R;
import com.pizzapp.v2.adminActivityClasses.AdminMenuActivity;

public class LoginActivity extends AppCompatActivity {
    //---------
    private Button btn_login;
    private EditText input_email, input_pw;
    private TextView txt_frgtnPw;
    private FirebaseAuth fauth;

    //---------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //---------
        inito();
        //---------
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vrfInput()) {
                    if (input_email.getText().toString().toLowerCase().equals("admin")
                            && input_pw.getText().toString().equals("admin")) {
                        Intent bye = new Intent(LoginActivity.this, AdminMenuActivity.class);
                        startActivity(bye);
                        finish();
                    } else {
                        if (input_pw.getText().toString().length() < 6) {
                            Toast.makeText(LoginActivity.this, "pw több mint 6 char plz", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            //---------
                            String email = input_email.getText().toString();
                            String pw = input_pw.getText().toString();
                            //---------
                            fauth.signInWithEmailAndPassword(email, pw).addOnCompleteListener(
                                    new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                startActivity(new Intent(LoginActivity.this, CurMenuActivity.class));
                                                finish();
                                            } else {
                                                AlertDialog.Builder nvldLgn = new AlertDialog.Builder(LoginActivity.this);
                                                nvldLgn.setMessage("Hiba! " + task.getException());
                                                nvldLgn.setCancelable(true);
                                                nvldLgn.setPositiveButton("K", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.cancel();
                                                    }
                                                });
                                                AlertDialog nvldLgnDlg = nvldLgn.create();
                                                nvldLgnDlg.show();
                                            }
                                        }
                                    }
                            );
                        }
                    }
                }
            }
        });
    }

    //---------
    private void inito() {
        btn_login = findViewById(R.id.btn_login);
        input_email = findViewById(R.id.input_email);
        input_pw = findViewById(R.id.input_password);
        txt_frgtnPw = findViewById(R.id.txt_frgtnPw);
        fauth = FirebaseAuth.getInstance();
    }

    //---------
    private boolean vrfInput() {
        if (input_pw.getText().toString().trim().equals("")
                || input_email.getText().toString().trim().equals("")) {
            AlertDialog.Builder nvldLgn = new AlertDialog.Builder(LoginActivity.this);
            nvldLgn.setMessage("A mezők kitöltése kötelező!");
            nvldLgn.setCancelable(true);
            nvldLgn.setPositiveButton("K", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog nvldLgnDlg = nvldLgn.create();
            nvldLgnDlg.show();
            return false;
        } else {
            return true;
        }
    }
    //---------
    //---------
}