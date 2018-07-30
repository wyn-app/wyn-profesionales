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
import com.santiagonoailles.matsprofesionales.entity.Informe;
import com.santiagonoailles.matsprofesionales.helper.FirebaseHelper;
import com.santiagonoailles.matsprofesionales.helper.MatsSettings;
import com.santiagonoailles.matsprofesionales.helper.OnLoadInformes;

import java.util.ArrayList;


/**
 * Created by nahuelul on 12/6/17.
 */

public class InformesFragment extends Fragment implements OnLoadInformes {

    private static final String INFORME_TYPE = "informeParameter";
    public static final int INFORME_TYPE_SOLICITADO = 0;
    public static final int INFORME_TYPE_HISTORIAL = 1;

    private int informeType;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar progressBar;
    private TextView warningTextView;

    public InformesFragment() {
        // Required empty public constructor
    }

    public static InformesFragment newInstance(int informeType) {
        InformesFragment fragment = new InformesFragment();
        Bundle args = new Bundle();
        args.putInt(INFORME_TYPE, informeType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            informeType = getArguments().getInt(INFORME_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mainView = inflater.inflate(R.layout.fragment_informes, container, false);
        mRecyclerView = (RecyclerView) mainView.findViewById(R.id.home_recicleView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        progressBar = (ProgressBar) mainView.findViewById(R.id.progressBar);
        warningTextView = (TextView) mainView.findViewById(R.id.informes_warning_textview);

//        FirebaseHelper.loadInformes(MatsSettings.getProfessionalId(), informeType, this);

        if (this.informeType == INFORME_TYPE_SOLICITADO) {
            warningTextView.setText(getString(R.string.informes_solicitados_warning_message));
        } else {
            warningTextView.setText(getString(R.string.informes_historial_warning_message));
        }

        return mainView;
    }

    @Override
    public void onResume() {
        super.onResume();
        FirebaseHelper.loadInformes(MatsSettings.getProfessionalId(getContext()), informeType, this);
    }

    @Override
    public void loadInformes(ArrayList<Informe> informes) {
        progressBar.setVisibility(View.GONE);
        if (informes.size() > 0) {
            mAdapter = new InformesAdapter(informes, getActivity());
            mRecyclerView.setAdapter(mAdapter);
        } else {
            warningTextView.setVisibility(View.VISIBLE);
        }
    }
}
