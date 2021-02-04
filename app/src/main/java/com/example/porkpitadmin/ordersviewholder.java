package com.example.porkpitadmin;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ordersviewholder extends  RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView txtousrname,txtomailzshh,txtophone,txtotime,txtodate,txtotransacts,txtorallpaid,txrsolocation;
    itemclicklistener listener;
    public ordersviewholder(@NonNull View itemView) {
        super(itemView);

        txtousrname = itemView.findViewById(R.id.orderusername);
        txtophone = itemView.findViewById(R.id.orderuserphone);
        txtotime = itemView.findViewById(R.id.orderproducttime);
        txtodate = itemView.findViewById(R.id.orderproductdate);
        txtotransacts = itemView.findViewById(R.id.ordertransact);
        txrsolocation=itemView.findViewById(R.id.orderproductloacs);
        txtorallpaid=itemView.findViewById(R.id.ordertotalprice);
        txtomailzshh=itemView.findViewById(R.id.orderusermail);


    }
    public void setItemClickListener(itemclicklistener listener){
        this.listener =listener;
    }

    @Override
    public void onClick(View v) {
        listener.onclick(v,getAdapterPosition(),false);

    }
}
