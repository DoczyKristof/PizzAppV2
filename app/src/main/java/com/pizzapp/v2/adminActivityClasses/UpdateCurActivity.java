package com.pizzapp.v2.adminActivityClasses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pizzapp.v2.R;
import com.pizzapp.v2.segedClassok.Globals;

public class UpdateCurActivity extends AppCompatActivity {
    //---------------
    private EditText modCur_name, modCur_userName, modCur_email, modCur_phone,
            modCur_pw, modCur_pw2;
    private Button btn_modCur;
    private FirebaseFirestore firestore;
    private CollectionReference CFerenc;
    private String UID;
    //---------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_cur);
        //---------------
        inito();
        //---------------
        loadData();
        //---------------
        btn_modCur.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //validate
                        updateData();
                        finish();
                    }
                }
        );

    }
    //---------------
    private void inito() {
        modCur_name = findViewById(R.id.modCur_name);
        modCur_userName = findViewById(R.id.modCur_userName);
        modCur_email = findViewById(R.id.modCur_email);
        modCur_phone = findViewById(R.id.modCur_phone);
        modCur_pw = findViewById(R.id.modCur_pw);
        modCur_pw2 = findViewById(R.id.modCur_pw2);
        btn_modCur = findViewById(R.id.btn_modCur);
        firestore = FirebaseFirestore.getInstance();
        CFerenc = firestore.collection("couriers");
        Globals glbl = Globals.getInstance();
        UID = glbl.getValue();
    }
    //---------------
    private void loadData(){
        //---------------
        Toast.makeText(this, UID, Toast.LENGTH_SHORT).show();
        //---------------
        CFerenc.whereEqualTo("UID", UID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                modCur_name.setText(doc.get("Name").toString());
                                modCur_userName.setText(doc.get("UserName").toString());
                                modCur_email.setText(doc.get("Email").toString());
                                modCur_phone.setText(doc.get("Phone").toString());
                            }
                        } else {
                            Toast.makeText(UpdateCurActivity.this, "Hiba!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        //---------------
    }
    //---------------
    private void updateData(){
        String updated_name = modCur_name.getText().toString();
        String updated_userName = modCur_userName.getText().toString();
        String updated_email = modCur_email.getText().toString();
        String updated_phone = modCur_phone.getText().toString();
        //---------------
        CFerenc.document(UID).update("Name", updated_name,
                "Email", updated_email,
                "Phone", updated_phone,
                "UserName", updated_userName
        );
        //---------------
    }
}
