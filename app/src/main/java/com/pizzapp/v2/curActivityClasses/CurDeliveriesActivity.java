package com.pizzapp.v2.curActivityClasses;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pizzapp.v2.R;
import com.pizzapp.v2.segedClassok.Order;

public class CurDeliveriesActivity extends AppCompatActivity {

    private RecyclerView rcclVw;
    private DatabaseReference DFerenc;
    private ImageButton imgBtn_go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cur_deliveries);
        inito();
        //---------------
    }

    //---------------
    private void inito() {
        imgBtn_go = findViewById(R.id.btn_letsGo);
        rcclVw = findViewById(R.id.rcclVw);
        rcclVw.setHasFixedSize(true);
        rcclVw.setLayoutManager(new LinearLayoutManager(this));
        //---------------
        DFerenc = FirebaseDatabase.getInstance().getReference().child("Orders");
        DFerenc.keepSynced(true);
    }

    //---------------
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Order, OrderViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Order, OrderViewHolder>
                        (Order.class, R.layout.order_row, OrderViewHolder.class, DFerenc) {
                    @Override
                    protected void populateViewHolder(OrderViewHolder orderViewHolder, Order order, int i) {
                        orderViewHolder.setName(order.getDeliName());
                    }
                };

        rcclVw.setAdapter(firebaseRecyclerAdapter);
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        View kilatas;

        public OrderViewHolder(View xDview) {
            super(xDview);
            kilatas = xDview;
        }

        public void setName(String name) {
            TextView txtVw_name = kilatas.findViewById(R.id.txtVw_deliName);
            txtVw_name.setText(name);
        }
    }
}