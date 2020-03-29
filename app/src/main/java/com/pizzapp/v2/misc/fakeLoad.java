package com.pizzapp.v2.misc;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pizzapp.v2.R;

import maes.tech.intentanim.CustomIntent;

public class fakeLoad extends AppCompatActivity {
    //---------
    private ImageView logo;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fakeload);
        //---------
        inito();
    }

    //---------
    @Override
    protected void onStart() {
        super.onStart();
        fakeLoading();
    }

    //---------
    private void fakeLoading() {
        Animation blink_rotate = AnimationUtils.loadAnimation(fakeLoad.this, R.anim.rotate);
        logo.startAnimation(blink_rotate);
        blink_rotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent goLogin = new Intent(fakeLoad.this, LoginActivity.class);
                startActivity(goLogin);

                CustomIntent.customType(fakeLoad.this, "fadein-to-fadeout");
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    //---------
    private void inito() {
        logo = findViewById(R.id.logo_main);
        firestore = FirebaseFirestore.getInstance();
        randoPing();
    }

    //---------------
    private void randoPing() {
        CollectionReference CFerenc = firestore.collection("couriers");
        CFerenc.get();
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