package com.example.porkpitadmin;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class usersviewholder  extends  RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtusruname,txtuphone,txtumail,txtuloc;
    itemclicklistener listener;

    public usersviewholder(@NonNull View itemView) {
        super(itemView);

        txtusruname = itemView.findViewById(R.id.userusername);
        txtuphone = itemView.findViewById(R.id.useruserphone);
        txtumail = itemView.findViewById(R.id.useruseremail);
        txtuloc = itemView.findViewById(R.id.useruserlocation);


    }
    public void setItemClickListener(itemclicklistener listener){
        this.listener =listener;
    }
    @Override
    public void onClick(View v) {
        listener.onclick(v,getAdapterPosition(),false);

    }





}
