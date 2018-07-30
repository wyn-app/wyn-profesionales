package com.santiagonoailles.matsprofesionales.helper;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.santiagonoailles.matsprofesionales.EditInformationActivity;
import com.santiagonoailles.matsprofesionales.InformationActivity;
import com.santiagonoailles.matsprofesionales.entity.Informe;
import com.santiagonoailles.matsprofesionales.entity.Presupuesto;
import com.santiagonoailles.matsprofesionales.entity.Profesional;
import com.santiagonoailles.matsprofesionales.entity.RespuestaPresupuesto;
import com.santiagonoailles.matsprofesionales.fragment.HomeFragment;
import com.santiagonoailles.matsprofesionales.fragment.InformesFragment;
import com.santiagonoailles.matsprofesionales.fragment.PresupuestosFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by nahuelul on 3/7/17.
 */

public class FirebaseHelper {

    private static final String PROFESIONALES = "profesionales";
    private static final String PROFESSION = "profession";
    private static final String INFORMES = "informes";
    private static final String PROFETIONALID = "professionalId";
    private static final String CONFIRMDATE = "confirmadoDate";
    private static final String STATE = "state";
    private static final String PHOTO_URL = "pictureUrl";
    private static final String PROFESSIONAL_POSITION = "professional_position";
    private static final String PRESUPUESTOS = "presupuestos";
    private static final String RESPUESTAS = "respuestas";
    private static final String EMAIL = "email";
    private static final String TOKEN = "token";


    public static void saveInforme(Informe informe) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(INFORMES).push();

        myRef.setValue(informe);
    }

    public static void loadInformes(String userId, final int informeType, final OnLoadInformes listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(INFORMES);

        Query professionalsQuery = myRef.orderByChild(PROFETIONALID).equalTo(userId);

        professionalsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Informe> informes = new ArrayList<Informe>();
                for (DataSnapshot dataObject : dataSnapshot.getChildren()) {
                    Informe informe = dataObject.getValue(Informe.class);
                    informe.setId(dataObject.getKey());
                    Date dateInforme = informe.getConfirmadoDate();
                    if (informeType == InformesFragment.INFORME_TYPE_SOLICITADO) {
                        if (dateInforme == null) {
                            informes.add(informe);
                        } else {
                            int differenceDate = dateInforme.compareTo(new Date());
                            if (differenceDate >= 0) {
                                informes.add(informe);
                            }
                        }
                    } else {
                        if (dateInforme != null) {
                            int differenceDate = dateInforme.compareTo(new Date());
                            if (differenceDate < 0) {
                                informes.add(informe);
                            }
                        }
                    }
                }

                listener.loadInformes(informes);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void cancelInforme(String id) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(INFORMES).child(id);

        myRef.child(STATE).setValue(Informe.STATE_CANCELADO_POR_PROFESIONAL);
    }

    public static void setTurnoToInforme(String informeId, Date date) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(INFORMES).child(informeId);

        myRef.child(CONFIRMDATE).setValue(date);
        myRef.child(STATE).setValue(Informe.STATE_VALIDADO);
    }

    public static void saveProfessional(Profesional profesional) {
        //Subir el profesional a la base
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseRef = database.getReference().child(PROFESIONALES).push();
        profesional.setId(databaseRef.getKey());
        databaseRef.setValue(profesional);

        //Asociar la posicion con GeoFire
        DatabaseReference geoRef = database.getReference(PROFESSIONAL_POSITION);
        GeoFire geoFire = new GeoFire(geoRef);
        LatLng pos = profesional.getPositionInLatLng();
        geoFire.setLocation(databaseRef.getKey(), new GeoLocation(pos.latitude, pos.longitude));

        //Por cada profesion agregar el key a la base "profession_x"
        for (Object professionId : profesional.getProfessions()) {
            DatabaseReference professionRef = database.getReference(PROFESSION + "_" + String.valueOf(professionId));
            professionRef.child(databaseRef.getKey()).setValue(true);
        }
    }

    public static void saveProfessional(Profesional profesional, byte[] data, Context context) {
        //Subir el profesional a la base
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseRef = database.getReference().child(PROFESIONALES).push();
        profesional.setId(databaseRef.getKey());
        databaseRef.setValue(profesional);

        MatsSettings.setProfessionalId(databaseRef.getKey(), context);
        setToken(databaseRef.getKey(), MatsSettings.getToken(context));

        //Asociar la posicion con GeoFire
        DatabaseReference geoRef = database.getReference(PROFESSIONAL_POSITION);
        GeoFire geoFire = new GeoFire(geoRef);
        LatLng pos = profesional.getPositionInLatLng();
        geoFire.setLocation(databaseRef.getKey(), new GeoLocation(pos.latitude, pos.longitude));

        //Subir la foto al storage y relacionarlo con el profesional
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child(databaseRef.getKey() + ".jpg");
        UploadTask uploadTask = storageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                databaseRef.child(PHOTO_URL).setValue(downloadUrl.toString());
            }
        });
        //Por cada profesion agregar el key a la base "profession_x"
        for (Object professionId : profesional.getProfessions()) {
            DatabaseReference professionRef = database.getReference(PROFESSION + "_" + String.valueOf(professionId));
            professionRef.child(databaseRef.getKey()).setValue(true);
        }
    }

    public static void cancelPresupuesto(String proffesionalId, String presupuestoId) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference deleteRef = database.getReference(PROFESIONALES).child(proffesionalId).child(PRESUPUESTOS).child(presupuestoId);
        deleteRef.removeValue();
    }

    public static void loadPresupuestos(String professionalId, final OnLoadPresupuestos listener) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(PROFESIONALES).child(professionalId).child(PRESUPUESTOS);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataObject : dataSnapshot.getChildren()) {
                    final long posRespuesta = (long) dataObject.getValue();
                    DatabaseReference presuRef = database.getReference(PRESUPUESTOS).child(dataObject.getKey());
                    presuRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Presupuesto presupuesto = dataSnapshot.getValue(Presupuesto.class);
                            presupuesto.setPosRespuesta(posRespuesta);

                            listener.loadPresupuestos(presupuesto);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void sendRespuestaPresupuesto(String presupeustoId, RespuestaPresupuesto respuesta, String respuestaPos, String professionalId) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(PRESUPUESTOS).child(presupeustoId).child(RESPUESTAS).child(respuestaPos);

        myRef.setValue(respuesta);

        DatabaseReference deleteRef = database.getReference(PROFESIONALES).child(professionalId).child(PRESUPUESTOS).child(presupeustoId);
        deleteRef.removeValue();
    }

    public static void findProfessionalByEmail(String email, final LoginProfesional listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(PROFESIONALES);

        Query mQuery = myRef.orderByChild(EMAIL).equalTo(email);
        mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataObject : dataSnapshot.getChildren()) {
                    Profesional profesional = dataObject.getValue(Profesional.class);
                    listener.onProfesionalLogin(profesional);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void findHomeData(String professionalId, final OnDataHomeLoaded listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference presupuestosRef = database.getReference(PROFESIONALES).child(professionalId).child(PRESUPUESTOS);
        presupuestosRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int presupuestos = 0;
                for (DataSnapshot dataObject : dataSnapshot.getChildren()) {
                    presupuestos++;
                }
                listener.onPresupuestosLoaded(presupuestos);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference workRef = database.getReference(INFORMES);
        Query mQuery = workRef.orderByChild(PROFETIONALID).equalTo(professionalId);
        mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int worksToday = 0;
                int sinConfirmar = 0;
                for (DataSnapshot dataObject : dataSnapshot.getChildren()) {
                    Informe informe = dataObject.getValue(Informe.class);
                    if (informe.getConfirmadoDate() != null) {
                        Calendar dateInforme = Calendar.getInstance();
                        dateInforme.setTime(informe.getConfirmadoDate());
                        if (this.compareDate(dateInforme)) {
                            worksToday++;
                        }
                    } else {
                        sinConfirmar++;
                    }
                }
                listener.onWorksLoaded(worksToday);
                listener.onSinConfrimarLoadeded(sinConfirmar);
            }

            private boolean compareDate(Calendar dateInforme) {
                boolean result = false;
                Calendar today = Calendar.getInstance();

                if (dateInforme.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)
                        && dateInforme.get(Calendar.MONTH) == today.get(Calendar.MONTH)
                        && dateInforme.get(Calendar.YEAR) == today.get(Calendar.YEAR)) {
                    result = true;
                }

                return result;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void setToken(String professionalId, String refreshedToken) {
        if (!professionalId.equals("")) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference(PROFESIONALES).child(professionalId);

            ref.child(TOKEN).setValue(refreshedToken);
        }
    }

    public static void updateProfessional(String professionalId, Profesional profesional, byte[] data) {
        //Subir el profesional a la base
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseRef = database.getReference().child(PROFESIONALES).child(professionalId);
        databaseRef.setValue(profesional);

        //Asociar la posicion con GeoFire
        DatabaseReference geoRef = database.getReference(PROFESSIONAL_POSITION);
        GeoFire geoFire = new GeoFire(geoRef);
        LatLng pos = profesional.getPositionInLatLng();
        geoFire.setLocation(databaseRef.getKey(), new GeoLocation(pos.latitude, pos.longitude));

        //Subir la foto al storage y relacionarlo con el profesional
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child(databaseRef.getKey() + ".jpg");
        UploadTask uploadTask = storageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                databaseRef.child(PHOTO_URL).setValue(downloadUrl.toString());
            }
        });
        //Por cada profesion agregar el key a la base "profession_x"
        for (Object professionId : profesional.getProfessions()) {
            DatabaseReference professionRef = database.getReference(PROFESSION + "_" + String.valueOf(professionId));
            professionRef.child(professionalId).setValue(true);
        }
    }

    public static void findProfessional(String professionalId, final LoadProfesional listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference().child(PROFESIONALES).child(professionalId);
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Profesional profesional = dataSnapshot.getValue(Profesional.class);
                listener.onLoadProfesional(profesional);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
