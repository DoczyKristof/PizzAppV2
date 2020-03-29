package com.pizzapp.v2.adminActivityClasses;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pizzapp.v2.R;
import com.pizzapp.v2.segedClassok.Globals;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

public class ManageCourierActivity extends AppCompatActivity {
    //---------
    ProgressBar pb;
    SearchableSpinner spinner;
    CollectionReference CFerenc;
    FirebaseFirestore firestore;
    List<String> cursList;
    Button btn_delCur, btn_modCur;
    FirebaseAuth fauth;

    //---------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_courier);
        //---------
        inito();
        pb.setVisibility(View.VISIBLE);
        //---------------
        if (spinner.getCount() > 0) {
            pb.setVisibility(View.GONE);
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                btn_delCur.setAlpha(1);
                btn_delCur.setEnabled(true);
                btn_modCur.setAlpha(1);
                btn_modCur.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //---------------
        btn_delCur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder byeCur = new AlertDialog.Builder(ManageCourierActivity.this);
                byeCur.setMessage("Biztosan törölni szeretnéd ezt a futárt?");
                byeCur.setCancelable(true);
                byeCur.setPositiveButton("Igen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setCurInactive(getSelectedName());
                        Toast.makeText(ManageCourierActivity.this,
                                 getSelectedName() + " sikeres törlése."
                                , Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                byeCur.setNegativeButton("Nem", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog byeCurDlg = byeCur.create();
                byeCurDlg.show();
            }
        });
        //---------------
        btn_modCur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //---------------
                CFerenc.whereEqualTo("Name", getSelectedName())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot doc : task.getResult()) {
                                        String uid = (String) doc.get("UID");
                                        addToGlobal(uid);
                                        startActivity(new Intent(ManageCourierActivity.this, UpdateCurActivity.class));
                                        finish();
                                    }
                                }
                            }
                        });
            }
        });
        //---------------
    }

    //---------
    private void inito() {
        pb = findViewById(R.id.pb_manage);
        btn_delCur = findViewById(R.id.btn_delCur);
        btn_modCur = findViewById(R.id.btn_modCur);
        spinner = findViewById(R.id.searchableSpinner);
        firestore = FirebaseFirestore.getInstance();
        fauth = FirebaseAuth.getInstance();
        CFerenc = firestore.collection("couriers");
        cursList = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item, cursList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //---------------
        CFerenc.whereEqualTo("activity", true).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        String cur = doc.getString("Name");
                        cursList.add(cur);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
        spinner.setAdapter(adapter);
    }
    //---------------
    private String getSelectedName() {
        return spinner.getSelectedItem().toString();
    }

    //---------------
    private void setCurInactive(String name) {
        CFerenc.whereEqualTo("Name", name)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                String uid = (String) doc.get("UID");
                                CFerenc.document(uid).update("activity", false);
                            }
                        } else {
                            errorAlert(task.getException().toString());
                        }
                    }
                });
    }

    //---------------
    private void addToGlobal(String string) {
        Globals glbl = Globals.getInstance();
        glbl.setValue(string);
    }

    //---------------
    private void errorAlert(String e) {
        AlertDialog.Builder nvldLgn = new AlertDialog.Builder(ManageCourierActivity.this);
        nvldLgn.setMessage(e);
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