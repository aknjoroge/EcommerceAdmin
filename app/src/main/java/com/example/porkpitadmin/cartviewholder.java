package com.example.porkpitadmin;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class cartviewholder extends  RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtusrname,txtphone,txttime,txtdate,txtproductname,txtpprice,txtpamount,txtproductid;
    itemclicklistener listener;

    public cartviewholder(@NonNull View itemView) {
        super(itemView);

        txtusrname = itemView.findViewById(R.id.cartusername);
        txtphone = itemView.findViewById(R.id.cartuserphone);
        txttime = itemView.findViewById(R.id.cartproducttime);
        txtdate = itemView.findViewById(R.id.cartproductdate);
        txtproductname = itemView.findViewById(R.id.cartproductname);
        txtpprice = itemView.findViewById(R.id.cartproductprice);
        txtpamount = itemView.findViewById(R.id.cartproductquantity);
        txtproductid = itemView.findViewById(R.id.cartproductid);


    }
    public void setItemClickListener(itemclicklistener listener){
        this.listener =listener;
    }

    @Override
    public void onClick(View v) {
        listener.onclick(v,getAdapterPosition(),false);

    }
}
