package com.santiagonoailles.matsprofesionales.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import com.santiagonoailles.matsprofesionales.DarTurnoActivity;
import com.santiagonoailles.matsprofesionales.R;
import com.santiagonoailles.matsprofesionales.entity.Informe;
import com.santiagonoailles.matsprofesionales.helper.FirebaseHelper;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by krustofski on 06/06/17.
 */

public class InformesAdapter extends RecyclerView.Adapter<InformesAdapter.ViewHolder> {
    private ArrayList<Informe> mDataset;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private Informe mInforme;
        private Context mContext;

        public TextView nameTextView;
        public TextView addressTextView;
        public TextView phoneTextView;
        public TextView localidadTextView;

        public TextView fechaSolicitudTextView;
        public TextView diaTextView;
        public TextView fechaTextView;
        public TextView horaTextView;
        public ImageView photoImageView;

        public TextView detalleTextView;
        public Button cancelButton;
        public Button confirmButton;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.informeItem_name_textview);
            addressTextView = (TextView) itemView.findViewById(R.id.informeItem_address_textview);
            phoneTextView = (TextView) itemView.findViewById(R.id.informeItem_phone_textview);
            localidadTextView = (TextView) itemView.findViewById(R.id.informeItem_localidad_textview);
            fechaSolicitudTextView = (TextView) itemView.findViewById(R.id.informeItem_informe_pedido_textview);
            diaTextView = (TextView) itemView.findViewById(R.id.turnoItem_dia);
            fechaTextView = (TextView) itemView.findViewById(R.id.turnoItem_fecha);
            horaTextView = (TextView) itemView.findViewById(R.id.turnoItem_hora);
            detalleTextView = (TextView) itemView.findViewById(R.id.informeItem_detalleTextView);
            photoImageView = (ImageView) itemView.findViewById(R.id.presupuesto_image);
            cancelButton = (Button) itemView.findViewById(R.id.informeItem_cancel_button);
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseHelper.cancelInforme(mInforme.getId());
                }
            });
            confirmButton = (Button) itemView.findViewById(R.id.informeItem_confirm_button);
            confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DarTurnoActivity.currentInforme = mInforme;
                    mContext.startActivity(new Intent(mContext, DarTurnoActivity.class));
                }
            });
        }

        public void fillData(Informe informe, Context context){
            mInforme = informe;
            mContext = context;
            nameTextView.setText(informe.getUserName());
            SimpleDateFormat pedidoDate = new SimpleDateFormat("dd/MM/yy");
            fechaSolicitudTextView.setText(pedidoDate.format(informe.getPedidoDate()));
            localidadTextView.setText(informe.getLocalidad());
            addressTextView.setText(informe.getDireccion());
            phoneTextView.setText(informe.getPhone());

            if (informe.getUrlImage() != null && !informe.getUrlImage().equals("")){
                photoImageView.setVisibility(View.VISIBLE);
                Picasso.with(context).load(informe.getUrlImage()).into(photoImageView);
            }

            Date confirmadoDate = informe.getConfirmadoDate();
            if (confirmadoDate != null){
                SimpleDateFormat diaFormat = new SimpleDateFormat("E");
                diaTextView.setText(diaFormat.format(confirmadoDate));

                SimpleDateFormat fechaFormat = new SimpleDateFormat("dd/MM");
                fechaTextView.setText(fechaFormat.format(confirmadoDate));

                SimpleDateFormat horaFormat = new SimpleDateFormat("HH:mm");
                horaTextView.setText(horaFormat.format(confirmadoDate));

                confirmButton.setVisibility(View.GONE);
            }
            detalleTextView.setText(informe.getDetalle());
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public InformesAdapter(ArrayList<Informe> myDataset, Context context) {
        mDataset = myDataset;
        mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trabajos_solicitados_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Informe agenda = mDataset.get(position);
        holder.fillData(agenda, mContext);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

