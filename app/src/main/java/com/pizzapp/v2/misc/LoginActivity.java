package com.pizzapp.v2.misc;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pizzapp.v2.R;
import com.pizzapp.v2.adminActivityClasses.AdminMenuActivity;
import com.pizzapp.v2.curActivityClasses.CurMenuActivity;

public class LoginActivity extends AppCompatActivity {
    //---------
    private Button btn_login;
    private EditText input_email, input_pw;
    private TextView txt_frgtnPw;
    private FirebaseAuth fauth;
    private FirebaseFirestore firestore;
    private CollectionReference CFerenc;

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
                    if (input_email.getText().toString().toLowerCase().equals("admin")) {
                        if (input_pw.getText().toString().equals("admin")) {
                            startActivity(new Intent(LoginActivity.this, AdminMenuActivity.class));
                            finish();
                        } else {
                            alertLoginError();
                        }
                    } else {
                        //---------
                        final String userName = input_email.getText().toString();
                        final String pw = input_pw.getText().toString();
                        //---------
                        if (Patterns.EMAIL_ADDRESS.matcher(userName).matches()) {
                            performLogin(userName, pw);
                        } else {
                            CFerenc.whereEqualTo("UserName", userName)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                                    String email = (String) doc.get("Email");
                                                    performLogin(email, pw);
                                                }
                                            } else {
                                                errorAlert(task.getException().toString());
                                            }

                                        }
                                    });
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
        firestore = FirebaseFirestore.getInstance();
        CFerenc = firestore.collection("couriers");
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
    private void performLogin(String email, String pw) {
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

    //---------
    private void alertLoginError() {
        AlertDialog.Builder nvldLgn = new AlertDialog.Builder(LoginActivity.this);
        nvldLgn.setMessage("Hibás bejelentkezési adatok!");
        nvldLgn.setCancelable(true);
        nvldLgn.setPositiveButton("X", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog nvldLgnDlg = nvldLgn.create();
        nvldLgnDlg.show();
    }
    private void errorAlert(String msg){
        AlertDialog.Builder nvldLgn = new AlertDialog.Builder(LoginActivity.this);
        nvldLgn.setMessage(msg);
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