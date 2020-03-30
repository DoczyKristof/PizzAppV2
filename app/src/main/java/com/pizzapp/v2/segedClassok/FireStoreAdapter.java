package com.pizzapp.v2.segedClassok;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.pizzapp.v2.R;

public class FireStoreAdapter extends FirestoreRecyclerAdapter<Order, FireStoreAdapter.OrderHolder> {


    Globals global = Globals.getInstance();
    private OnItemClickListener clickListener;

    public FireStoreAdapter(@NonNull FirestoreRecyclerOptions<Order> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull OrderHolder holder, int i, @NonNull Order order) {
        holder.txtVw_address.setText(order.getAddress());
        holder.txtVw_name.setText(order.getName());
        holder.txtVw_numOfPizzas.setText(order.getNumOfPizzas() + " db");
        holder.txtVw_price.setText(order.getFullPrice() + " Ft");
        global.setValue(order.getAddress());
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_row,
                parent, false);
        return new OrderHolder(v, clickListener);
    }

    public void setClickListener(OnItemClickListener listener) {
        clickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    class OrderHolder extends RecyclerView.ViewHolder {
        TextView txtVw_address,
                txtVw_name,
                txtVw_numOfPizzas,
                txtVw_price;
        ImageView imgBtn;

        public OrderHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            txtVw_address = itemView.findViewById(R.id.txtVw_address);
            txtVw_name = itemView.findViewById(R.id.txtVw_deliName);
            txtVw_numOfPizzas = itemView.findViewById(R.id.txtVw_numOfPizzas);
            txtVw_price = itemView.findViewById(R.id.txtVw_cash);
            imgBtn = itemView.findViewById(R.id.img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

}
