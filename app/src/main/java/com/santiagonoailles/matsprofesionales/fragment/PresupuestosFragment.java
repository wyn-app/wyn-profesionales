package com.santiagonoailles.matsprofesionales.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.santiagonoailles.matsprofesionales.R;
import com.santiagonoailles.matsprofesionales.adapter.InformesAdapter;
import com.santiagonoailles.matsprofesionales.adapter.PresupuestosAdapter;
import com.santiagonoailles.matsprofesionales.entity.Informe;
import com.santiagonoailles.matsprofesionales.entity.Presupuesto;
import com.santiagonoailles.matsprofesionales.helper.FirebaseHelper;
import com.santiagonoailles.matsprofesionales.helper.MatsSettings;
import com.santiagonoailles.matsprofesionales.helper.OnLoadPresupuestos;

import java.util.ArrayList;


/**
 * Created by nahuelul on 12/6/17.
 */

public class PresupuestosFragment extends Fragment implements OnLoadPresupuestos {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar progressBar;
    private TextView warningTextView;

    public PresupuestosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mainView = inflater.inflate(R.layout.fragment_presupuestos, container, false);
        mRecyclerView = (RecyclerView) mainView.findViewById(R.id.home_recicleView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        progressBar = (ProgressBar) mainView.findViewById(R.id.progressBar);
        warningTextView = (TextView) mainView.findViewById(R.id.informes_warning_textview);

        FirebaseHelper.loadPresupuestos(MatsSettings.getProfessionalId(getContext()), this);

        return mainView;
    }

    @Override
    public void loadPresupuestos(Presupuesto presupuesto) {
        progressBar.setVisibility(View.GONE);
        if (mAdapter == null) {
            ArrayList<Presupuesto> presupuestos = new ArrayList<Presupuesto>();
            presupuestos.add(presupuesto);
            mAdapter = new PresupuestosAdapter(presupuestos, getActivity());
            mRecyclerView.setAdapter(mAdapter);
        } else {
            ((PresupuestosAdapter)mRecyclerView.getAdapter()).addPresupuesto(presupuesto);
        }
    }
}
