package com.pizzapp.v2.adminActivityClasses;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pizzapp.v2.R;

import java.util.HashMap;
import java.util.Map;

public class RegCurActivity extends AppCompatActivity {
    //---------
    private ProgressBar pb;
    private EditText input_regCur_nev, input_regCur_felhNev, input_regCur_email, input_regCur_phone,
            input_regCur_pw, input_regCur_pw2;
    private Button btn_regCur;
    private FirebaseAuth fauth;
    private FirebaseFirestore firestore;
    private String userId;

    //---------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_cur);
        //---------
        inito();

        input_regCur_pw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                input_regCur_pw2.setAlpha(1);
                input_regCur_pw2.setEnabled(true);
                btn_regCur.setEnabled(true);
                btn_regCur.setAlpha(1);
            }
        });
        //---------
        btn_regCur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                if (vrfInput()) {
                    final String email = input_regCur_email.getText().toString().trim();
                    final String pw = input_regCur_pw.getText().toString();
                    final String userName = input_regCur_felhNev.getText().toString();
                    final String name = input_regCur_nev.getText().toString();
                    final String phone = input_regCur_phone.getText().toString();
                    //---------
                    fauth.createUserWithEmailAndPassword(email, pw).addOnCompleteListener(
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull final Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        //---------
                                        userId = fauth.getCurrentUser().getUid();
                                        DocumentReference dFerenc = firestore.collection("couriers").document(userId);
                                        Map<String, Object> courier = new HashMap<>();
                                        courier.put("Name", name);
                                        courier.put("UserName", userName);
                                        courier.put("Email", email);
                                        courier.put("Phone", phone);
                                        courier.put("UID", userId);
                                        courier.put("activity", true);
                                        dFerenc.set(courier).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                //---------
                                                Toast.makeText(RegCurActivity.this, "Sikeres regisztráció.", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(RegCurActivity.this, AdminMenuActivity.class));
                                                pb.setVisibility(View.GONE);
                                                finish();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        Toast.makeText(RegCurActivity.this, "Sikertelen regisztráció.", Toast.LENGTH_SHORT).show();
                                                                        pb.setVisibility(View.GONE);
                                                                        errorAlert(task.getException().toString());
                                                                    }
                                                                }
                                        );

                                    } else {
                                        pb.setVisibility(View.GONE);
                                        errorAlert(task.getException().toString());
                                    }
                                }
                            }
                    );
                    //---------
                }
            }
        });
        //---------
    }

    //---------
    private void inito() {
        pb = findViewById(R.id.pb_reg);
        input_regCur_nev = findViewById(R.id.cur_name);
        input_regCur_felhNev = findViewById(R.id.cur_userName);
        input_regCur_email = findViewById(R.id.cur_email);
        input_regCur_phone = findViewById(R.id.cur_phone);
        input_regCur_pw = findViewById(R.id.input_password);
        input_regCur_pw2 = findViewById(R.id.input_password2);
        btn_regCur = findViewById(R.id.btn_regCur);
        fauth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    //---------
    private boolean vrfInput() {
        if (input_regCur_nev.getText().toString().trim().equals("")
                || input_regCur_email.getText().toString().trim().equals("")
                || input_regCur_phone.getText().toString().trim().equals("")
                || input_regCur_felhNev.getText().toString().trim().equals("")
                || input_regCur_pw.getText().toString().equals("")
                || input_regCur_pw2.getText().toString().equals("")) {
            AlertDialog.Builder nvldLgn = new AlertDialog.Builder(RegCurActivity.this);
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
            pb.setVisibility(View.GONE);
            return false;
        } else if (input_regCur_pw.getText().toString().length() < 6) {
            AlertDialog.Builder nvldLgn = new AlertDialog.Builder(RegCurActivity.this);
            nvldLgn.setMessage("Jelszónak hosszabbnak kell lennie, mint 6 karakter!");
            nvldLgn.setCancelable(true);
            nvldLgn.setPositiveButton("K", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog nvldLgnDlg = nvldLgn.create();
            nvldLgnDlg.show();
            pb.setVisibility(View.GONE);
            return false;
        } else if (!input_regCur_pw.getText().toString().equals(
                input_regCur_pw2.getText().toString())) {
            AlertDialog.Builder nvldLgn = new AlertDialog.Builder(RegCurActivity.this);
            nvldLgn.setMessage("Nem egyeznek a jelszavak.");
            nvldLgn.setCancelable(true);
            nvldLgn.setPositiveButton("K", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog nvldLgnDlg = nvldLgn.create();
            nvldLgnDlg.show();
            pb.setVisibility(View.GONE);
            return false;
        } else {
            return true;
        }
    }

    //---------
    private void errorAlert(String msg) {
        AlertDialog.Builder nvldLgn = new AlertDialog.Builder(RegCurActivity.this);
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