package com.santiagonoailles.matsprofesionales.helper;

import com.santiagonoailles.matsprofesionales.entity.Profesional;

/**
 * Created by nahuelul on 16/8/17.
 */

public interface OnDataHomeLoaded {

    void onWorksLoaded(int worksToday);
    void onPresupuestosLoaded(int presupuestos);

    void onSinConfrimarLoadeded(int sinConfirmar);
}
