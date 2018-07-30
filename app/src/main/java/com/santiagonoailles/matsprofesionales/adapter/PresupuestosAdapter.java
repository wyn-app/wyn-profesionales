package com.santiagonoailles.matsprofesionales.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.santiagonoailles.matsprofesionales.PresupuestoActivity;
import com.santiagonoailles.matsprofesionales.R;
import com.santiagonoailles.matsprofesionales.entity.Presupuesto;
import com.santiagonoailles.matsprofesionales.helper.FirebaseHelper;
import com.santiagonoailles.matsprofesionales.helper.MatsSettings;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by krustofski on 06/06/17.
 */

public class PresupuestosAdapter extends RecyclerView.Adapter<PresupuestosAdapter.ViewHolder> {
    private ArrayList<Presupuesto> mDataset;
    private Context mContext;

    public void addPresupuesto(Presupuesto presupuesto) {
        mDataset.add(presupuesto);
        notifyDataSetChanged();
        notifyItemRangeChanged(0, mDataset.size());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private Presupuesto presupuesto;
        private Context mContext;

        public TextView fechaSolicitudTextView;
        public TextView detalleTextView;
        public Button cancelButton;
        public Button confirmButton;

        public ViewHolder(View itemView) {
            super(itemView);

            fechaSolicitudTextView = (TextView) itemView.findViewById(R.id.informeItem_informe_pedido_textview);
            detalleTextView = (TextView) itemView.findViewById(R.id.informeItem_detalleTextView);
            cancelButton = (Button) itemView.findViewById(R.id.informeItem_cancel_button);
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseHelper.cancelPresupuesto(MatsSettings.getProfessionalId(mContext), presupuesto.getId());
                }
            });
            confirmButton = (Button) itemView.findViewById(R.id.informeItem_confirm_button);
            confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PresupuestoActivity.currentPresupuesto = presupuesto;
                    mContext.startActivity(new Intent(mContext, PresupuestoActivity.class));
                }
            });
        }

        public void fillData(Presupuesto presupuesto, Context context){
            this.presupuesto = presupuesto;
            mContext = context;
            detalleTextView.setText(presupuesto.getDetalleUsuario());
            SimpleDateFormat pedidoDate = new SimpleDateFormat("dd/MM/yy");
            fechaSolicitudTextView.setText(pedidoDate.format(presupuesto.getSolicitado()));
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PresupuestosAdapter(ArrayList<Presupuesto> myDataset, Context context) {
        mDataset = myDataset;
        mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.presupuesto_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Presupuesto presupuesto = mDataset.get(position);
        holder.fillData(presupuesto, mContext);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

