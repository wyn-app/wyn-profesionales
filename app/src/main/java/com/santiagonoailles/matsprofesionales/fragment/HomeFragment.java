package com.santiagonoailles.matsprofesionales.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.santiagonoailles.matsprofesionales.NuevaMainActivity;
import com.santiagonoailles.matsprofesionales.R;
import com.santiagonoailles.matsprofesionales.adapter.InformesAdapter;
import com.santiagonoailles.matsprofesionales.entity.Informe;
import com.santiagonoailles.matsprofesionales.helper.FirebaseHelper;
import com.santiagonoailles.matsprofesionales.helper.MatsSettings;
import com.santiagonoailles.matsprofesionales.helper.NotificacionesListener;
import com.santiagonoailles.matsprofesionales.helper.OnDataHomeLoaded;
import com.santiagonoailles.matsprofesionales.helper.OnLoadInformes;

import java.util.ArrayList;


/**
 * Created by nahuelul on 12/6/17.
 */

public class HomeFragment extends Fragment implements OnDataHomeLoaded {


    private TextView notificationsTextView;
    private TextView worksTextView;
    private TextView sinConfirmarTextView;
    private NotificacionesListener listener;

    private CardView pendientesCardView;
    private CardView todayCardView;
    private CardView presupuestosCardView;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mainView = inflater.inflate(R.layout.fragment_main, container, false);

        notificationsTextView = (TextView) mainView.findViewById(R.id.home_notificacionsmessage);
        notificationsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onPresupuestosNotificacionesClick();
            }
        });
        sinConfirmarTextView = (TextView) mainView.findViewById(R.id.home_sinconfirmarsmessage);
        sinConfirmarTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onTurnosNotificacionesClick();
            }
        });
        worksTextView = (TextView) mainView.findViewById(R.id.home_todaymessage);
        worksTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onTurnosNotificacionesClick();
            }
        });

        pendientesCardView = (CardView) mainView.findViewById(R.id.cardpendientes);
        todayCardView = (CardView) mainView.findViewById(R.id.cardtoday);
        presupuestosCardView = (CardView) mainView.findViewById(R.id.cardpresupuestos);

        return mainView;
    }

    @Override
    public void onResume() {
        super.onResume();
        FirebaseHelper.findHomeData(MatsSettings.getProfessionalId(getContext()), this);
    }

    @Override
    public void onWorksLoaded(int worksToday) {
        if (isAdded() && worksToday > 0) {
            Resources res = getResources();
            String workText = res.getQuantityString(R.plurals.works_home, worksToday, worksToday);
            worksTextView.setText(workText);
            todayCardView.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    @Override
    public void onPresupuestosLoaded(int presupuestos) {
        if (isAdded() && presupuestos > 0) {
            Resources res = getResources();
            String presupuestosText = res.getQuantityString(R.plurals.presupuestos_home, presupuestos, presupuestos);
            notificationsTextView.setText(presupuestosText);
            presupuestosCardView.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    @Override
    public void onSinConfrimarLoadeded(int sinConfirmar) {
        if (isAdded() && sinConfirmar > 0) {
            Resources res = getResources();
            String presupuestosText = res.getQuantityString(R.plurals.pendientes_home, sinConfirmar, sinConfirmar);
            sinConfirmarTextView.setText(presupuestosText);
            pendientesCardView.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    public void loadListener(NotificacionesListener listener) {
        this.listener = listener;
    }
}
