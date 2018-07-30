package com.santiagonoailles.matsprofesionales.helper;

import com.santiagonoailles.matsprofesionales.R;

/**
 * Created by nahuelul on 10/3/18.
 */

public class ProfessionsHelper {


    private static final int HOGAR = 1;
    private static final int EMPRESA = 2;
    private static final int EDUCACION = 3;
    private static final int DEPORTE = 4;
    private static final int AUTOS = 5;
    private static final int ESTETICA = 6;
    private static final int MEDICOS = 7;

    private static final String CARPINTERIA = "100";
    private static final String CERRAJERIA = "101";
    private static final String CLIMATIZACION = "120";
    private static final String CONSTRUCCION = "102";
    private static final String ELECTRICIDAD = "103";
    private static final String GAS = "104";
    private static final String HERRERIA = "105";
    private static final String JARDINERIA = "106";
    private static final String LIMPIEZA = "121";
    private static final String MUDANZA = "107";
    private static final String MULTITAREAS = "108";
    private static final String NIÑERA = "122";
    private static final String PILETAS = "123";
    private static final String PINTURA = "109";
    private static final String PLOMERIA = "111";
    private static final String MAYORES = "124";

    private static final String LIMPIEZA_E = "200";
    private static final String SEGURIDAD = "201";
    private static final String INGLES_E = "202";
    private static final String ORGANIZACION = "203";
    private static final String PROVEEDORES = "204";
    private static final String MARKETING = "205";

    private static final String CS_EXACTAS = "300";
    private static final String INGLES = "301";


    private static final String ARTES_MARCIALES = "400";
    private static final String PERSONAL = "401";
    private static final String FUTBOL = "402";
    private static final String CROSSFIT = "403";

    private static final String LAVADERO = "500";
    private static final String MECANICO = "501";
    private static final String REPUESTOS = "502";
    private static final String POLARIZADO = "503";

    private static final String ESTETICA_DOMICILIO = "601";
    private static final String PELUQUERIA = "602";
    private static final String SALON = "603";

    private static final String ENFERMERO = "701";
    private static final String KINESIOLOGIA = "702";
    private static final String NUTRICIONISTA = "703";

    private static final int CARPINTERIA_STRING_ID = R.string.carpinteria;
    private static final int CERRAJERIA_STRING_ID = R.string.cerrajeria;
    private static final int CONSTRUCCION_STRING_ID = R.string.construccion;
    private static final int ELECTRICIDAD_STRING_ID = R.string.electicidad;
    private static final int GAS_STRING_ID = R.string.gas;
    private static final int HERRERIA_STRING_ID = R.string.herreria;
    private static final int JARDINERIA_STRING_ID = R.string.jardineria;
    private static final int MUDANZAS_STRING_ID = R.string.mudanza;
    private static final int MULTITAREAS_STRING_ID = R.string.multitarea;
    private static final int CLIMATIZACION_STRING_ID = R.string.climatizacion;
    private static final int NIÑERA_STRING_ID = R.string.niniera;
    private static final int PINTURA_STRING_ID = R.string.pintura;
    private static final int PLOMERIA_STRING_ID = R.string.plomeria;
    private static final int PILETAS_STRING_ID = R.string.piletas;

    private static final int LIMPIEZA_STRING_ID = R.string.limpieza;
    private static final int SEGURIDAD_STRING_ID = R.string.seguridad;
    private static final int INGLES_STRING_ID = R.string.inglesemp;
    private static final int ORGANIZACION_STRING_ID = R.string.organizacion;
    private static final int PROVEEDORES_STRING_ID = R.string.proveedores;
    private static final int MARKETING_STRING_ID = R.string.marketing;

    private static final int INGLESEDU_STRING_ID = R.string.inglesedu;
    private static final int EXACTAS_STRING_ID = R.string.exactas;

    private static final int ARTESMARCIALES_STRING_ID = R.string.artesmarciales;
    private static final int PERSONALTRAINER_STRING_ID = R.string.personal;
    private static final int FUTBOL_STRING_ID = R.string.futbol;
    private static final int CROSSFIT_STRING_ID = R.string.crossfit;

    private static final int LAVADERO_STRING_ID = R.string.lavadero;
    private static final int MECANICO_STRING_ID = R.string.mecanico;
    private static final int REPUESTOS_STRING_ID = R.string.repuestos;
    private static final int POLARIZADO_STRING_ID = R.string.polarizado;


    private static final int ESTETICA_DOMICILIO_STRING_ID = R.string.estitcadomiciolio;
    private static final int PELUQUERIA_STRING_ID = R.string.peluqueria;
    private static final int SALON_STRING_ID = R.string.salon;

    private static final int ENFERMERO_DOMICILIO_STRING_ID = R.string.enfermero;
    private static final int KINESIOLOGIA_STRING_ID = R.string.kinesio;
    private static final int NUTRICIONISTA_STRING_ID = R.string.nutrisionista;


    public static String convertPositionToProfession(int superCategory, int subCategoriesSelected) {
        switch (superCategory) {
            case HOGAR:
                return getCategoryHome(subCategoriesSelected);
            case EMPRESA:
                return getCategoryEmpresa(subCategoriesSelected);
            case EDUCACION:
                return getCategoryEducacion(subCategoriesSelected);
            case DEPORTE:
                return getCategoryDeporte(subCategoriesSelected);
            case AUTOS:
                return getCategoryAutos(subCategoriesSelected);
            case ESTETICA:
                return getCategoryEstetica(subCategoriesSelected);
            case MEDICOS:
                return getCategoryMedicos(subCategoriesSelected);

        }
        return "";
    }


    private static String getCategoryAutos(int subCategoriesSelected) {
        switch (subCategoriesSelected) {
            case 1:
                return LAVADERO;
            case 2:
                return MECANICO;
            case 3:
                return REPUESTOS;
            case 4:
                return POLARIZADO;
        }
        return "";

    }

    private static String getCategoryDeporte(int subCategoriesSelected) {
        switch (subCategoriesSelected) {
            case 1:
                return ARTES_MARCIALES;
            case 2:
                return PERSONAL;
            case 3:
                return FUTBOL;
            case 4:
                return CROSSFIT;
        }
        return "";
    }

    private static String getCategoryMedicos(int subCategoriesSelected) {
        switch (subCategoriesSelected) {
            case 1:
                return ENFERMERO;
            case 2:
                return KINESIOLOGIA;
            case 3:
                return NUTRICIONISTA;
        }
        return "";
    }

    private static String getCategoryEstetica(int subCategoriesSelected) {
        switch (subCategoriesSelected) {
            case 1:
                return ESTETICA_DOMICILIO;
            case 2:
                return PELUQUERIA;
            case 3:
                return SALON;
        }
        return "";
    }

    private static String getCategoryEducacion(int subCategoriesSelected) {
        switch (subCategoriesSelected) {
            case 1:
                return CS_EXACTAS;
            case 2:
                return INGLES;
        }
        return "";
    }

    private static String getCategoryEmpresa(int subCategoriesSelected) {
        switch (subCategoriesSelected) {
            case 1:
                return LIMPIEZA_E;
            case 2:
                return SEGURIDAD;
            case 3:
                return INGLES_E;
            case 4:
                return ORGANIZACION;
            case 5:
                return PROVEEDORES;
            case 6:
                return MARKETING;
        }
        return "";
    }

    private static String getCategoryHome(int subCategoriesSelected) {
        switch (subCategoriesSelected) {
            case 1:
                return CARPINTERIA;
            case 2:
                return CERRAJERIA;
            case 3:
                return CLIMATIZACION;
            case 4:
                return MAYORES;
            case 5:
                return CONSTRUCCION;
            case 6:
                return ELECTRICIDAD;
            case 7:
                return GAS;
            case 8:
                return HERRERIA;
            case 9:
                return JARDINERIA;
            case 10:
                return LIMPIEZA;
            case 11:
                return MUDANZA;
            case 12:
                return MULTITAREAS;
            case 13:
                return NIÑERA;
            case 14:
                return PILETAS;
            case 15:
                return PINTURA;
            case 16:
                return PLOMERIA;
        }
        return "";
    }

    public static final int getStringResourceFor(String categoryId) {
        //Ejemplo llega "100", pregunto por el 1, luego por el 100 en gral
        String superCategory = categoryId.split("")[1];
        if (superCategory.equals("1")) {
            if (categoryId.equals(CARPINTERIA)) {
                return CARPINTERIA_STRING_ID;
            } else if (categoryId.equals(CERRAJERIA)) {
                return CERRAJERIA_STRING_ID;
            } else if (categoryId.equals(CLIMATIZACION)) {
                return CLIMATIZACION_STRING_ID;
            } else if (categoryId.equals(CONSTRUCCION)) {
                return CONSTRUCCION_STRING_ID;
            } else if (categoryId.equals(ELECTRICIDAD)) {
                return ELECTRICIDAD_STRING_ID;
            } else if (categoryId.equals(GAS)) {
                return GAS_STRING_ID;
            } else if (categoryId.equals(HERRERIA)) {
                return HERRERIA_STRING_ID;
            } else if (categoryId.equals(JARDINERIA)) {
                return JARDINERIA_STRING_ID;
            } else if (categoryId.equals(LIMPIEZA)) {
                return LIMPIEZA_STRING_ID;
            } else if (categoryId.equals(MUDANZA)) {
                return MUDANZAS_STRING_ID;
            } else if (categoryId.equals(MULTITAREAS)) {
                return MULTITAREAS_STRING_ID;
            } else if (categoryId.equals(NIÑERA)) {
                return NIÑERA_STRING_ID;
            } else if (categoryId.equals(PINTURA)) {
                return PINTURA_STRING_ID;
            } else if (categoryId.equals(PILETAS)) {
                return PILETAS_STRING_ID;
            } else if (categoryId.equals(PLOMERIA)) {
                return PLOMERIA_STRING_ID;
            }
        } else if (superCategory.equals("2")) {
            if (categoryId.equals(LIMPIEZA_E)) {
                return LIMPIEZA_STRING_ID;
            } else if (categoryId.equals(SEGURIDAD)) {
                return SEGURIDAD_STRING_ID;
            } else if (categoryId.equals(INGLES_E)) {
                return INGLES_STRING_ID;
            } else if (categoryId.equals(ORGANIZACION)) {
                return ORGANIZACION_STRING_ID;
            } else if (categoryId.equals(PROVEEDORES)) {
                return PROVEEDORES_STRING_ID;
            } else if (categoryId.equals(MARKETING)) {
                return MARKETING_STRING_ID;
            }
        } else if (superCategory.equals("3")) {
            if (categoryId.equals(INGLES)) {
                return INGLESEDU_STRING_ID;
            } else if (categoryId.equals(CS_EXACTAS)) {
                return EXACTAS_STRING_ID;
            }
        } else if (superCategory.equals("4")) {
            if (categoryId.equals(ARTES_MARCIALES)) {
                return ARTESMARCIALES_STRING_ID;
            } else if (categoryId.equals(PERSONAL)) {
                return PERSONALTRAINER_STRING_ID;
            } else if (categoryId.equals(FUTBOL)) {
                return FUTBOL_STRING_ID;
            } else if (categoryId.equals(CROSSFIT)) {
                return CROSSFIT_STRING_ID;
            }
        } else if (superCategory.equals("5")) {
            if (categoryId.equals(LAVADERO)) {
                return LAVADERO_STRING_ID;
            } else if (categoryId.equals(REPUESTOS)) {
                return REPUESTOS_STRING_ID;
            } else if (categoryId.equals(MECANICO)) {
                return MECANICO_STRING_ID;
            } else if (categoryId.equals(POLARIZADO)) {
                return POLARIZADO_STRING_ID;
            }
        } else if (superCategory.equals("6")) {
            if (categoryId.equals(ESTETICA_DOMICILIO)) {
                return ESTETICA_DOMICILIO_STRING_ID;
            } else if (categoryId.equals(PELUQUERIA)) {
                return PELUQUERIA_STRING_ID;
            } else if (categoryId.equals(SALON)) {
                return SALON_STRING_ID;
            }
        } else if (superCategory.equals("7")) {
            if (categoryId.equals(ENFERMERO)) {
                return ENFERMERO_DOMICILIO_STRING_ID;
            } else if (categoryId.equals(KINESIOLOGIA)) {
                return KINESIOLOGIA_STRING_ID;
            } else if (categoryId.equals(NUTRICIONISTA)) {
                return NUTRICIONISTA_STRING_ID;
            }

            return 0;
        }
        return -1;
    }
}
