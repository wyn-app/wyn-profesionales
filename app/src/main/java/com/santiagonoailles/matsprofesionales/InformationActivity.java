package com.santiagonoailles.matsprofesionales;

import android.*;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.santiagonoailles.matsprofesionales.entity.Profesional;
import com.santiagonoailles.matsprofesionales.helper.FirebaseHelper;
import com.santiagonoailles.matsprofesionales.helper.ImagePicker;
import com.santiagonoailles.matsprofesionales.helper.MatsSettings;
import com.santiagonoailles.matsprofesionales.helper.ProfessionsHelper;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class InformationActivity extends AppCompatActivity implements OnClickListener, OnMapReadyCallback, AdapterView.OnItemSelectedListener {

    private static final int PICK_IMAGE_ID = 100;
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 101;
    private static final int PERMISSIONS_REQUEST = 102;
    private EditText nameEditText;
    private EditText dniEditText;
    private EditText descriptionEditText;

    private Spinner professionsSpinner;
    private Spinner subcategoriesSpinner;

    private ArrayList<String> professions;
    private LinearLayout professionsContainer;

    private ImageView photoImageView;
    private Button photoButton;
    private Bitmap bitmap;

    private SwitchCompat fastWorkSwitch;
    private SwitchCompat matriculaSwitch;
    private SwitchCompat facturaSwitch;

    private Button gpsButton;
    private GoogleMap googleMap;
    private MapFragment mapFragment;
    private LatLng position;

    private Button registrarseButton;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        this.professions = new ArrayList<String>();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.waitmessage));

        nameEditText = (EditText) findViewById(R.id.information_name_edittext);
        dniEditText = (EditText) findViewById(R.id.information_dni_edittext);
        descriptionEditText = (EditText) findViewById(R.id.information_description_edittext);
        professionsSpinner = (Spinner) findViewById(R.id.information_profession_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.profesiones, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        professionsSpinner.setAdapter(adapter);
        professionsSpinner.setOnItemSelectedListener(this);

        subcategoriesSpinner = (Spinner) findViewById(R.id.information_subcategories_spinner);
        subcategoriesSpinner.setOnItemSelectedListener(this);

        professionsContainer = (LinearLayout) findViewById(R.id.professions_container);
        photoImageView = (ImageView) findViewById(R.id.information_photo_imageview);
        photoImageView.setDrawingCacheEnabled(true);
        photoImageView.buildDrawingCache();
        photoButton = (Button) findViewById(R.id.information_photo_button);
        gpsButton = (Button) findViewById(R.id.information_gps_button);
        fastWorkSwitch = (SwitchCompat) findViewById(R.id.information_fastwork_switch);
        matriculaSwitch = (SwitchCompat) findViewById(R.id.information_matricula_switch);
        facturaSwitch = (SwitchCompat) findViewById(R.id.information_factura_switch);
        registrarseButton = (Button) findViewById(R.id.information_registrarse_button);

        mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapFragment.getView().setVisibility(View.GONE);

        photoButton.setOnClickListener(this);
        registrarseButton.setOnClickListener(this);
        gpsButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.information_registrarse_button:
                progressDialog.show();
                this.validateInformation();
                break;
            case R.id.information_photo_button:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Intent chooseImageIntent = ImagePicker.getPickImageIntent(this);
                    startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            PERMISSIONS_REQUEST);
                }
                break;
            case R.id.information_gps_button:
                try {
                    PlacePicker.IntentBuilder intentBuilder =
                            new PlacePicker.IntentBuilder();
                    Intent intent = intentBuilder.build(this);
                    // Start the intent by requesting a result,
                    // identified by a request code.
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    Log.d("ERROR", e.toString());
                } catch (GooglePlayServicesNotAvailableException e) {
                    Log.d("ERROR", e.toString());
                }
                break;
        }

    }

    private void validateInformation() {
        StringBuffer errorBuffer = new StringBuffer();

        String name = nameEditText.getText().toString();
        if (name.equals("")) {
            errorBuffer.append(getString(R.string.information_name) + "\n");
        }
        String dni = dniEditText.getText().toString();
        if (dni.equals("")) {
            errorBuffer.append(getString(R.string.information_dni) + "\n");
        }
        if (professions.size() == 0) {
            errorBuffer.append(getString(R.string.profession_selection) + "\n");
        }
        if (bitmap == null) {
            errorBuffer.append(getString(R.string.information_photo) + "\n");
        }
        if (position == null) {
            errorBuffer.append(getString(R.string.information_gps));
        }

        if (errorBuffer.toString().equals("")) {
            String description = descriptionEditText.getText().toString();

            Profesional profesional = new Profesional();
            profesional.setName(name);
            profesional.setDni(dni);
            profesional.setDescription(description);
            profesional.setFastWork(this.fastWorkSwitch.isChecked());
            profesional.setRegistered(this.matriculaSwitch.isChecked());
            profesional.setFactura(this.facturaSwitch.isChecked());
            profesional.setPositionInLatLng(position);
            profesional.setProfessions(professions);

            profesional.setEmail(MatsSettings.getEmail(this));

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();
            FirebaseHelper.saveProfessional(profesional, data, InformationActivity.this);

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            startActivity(new Intent(this, NuevaMainActivity.class));
            this.finish();
        } else {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.registro_error_title));
            builder.setMessage(errorBuffer.toString());
            builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PICK_IMAGE_ID:
                    bitmap = ImagePicker.getImageFromResult(this, resultCode, data);
                    photoImageView.setImageBitmap(bitmap);
                    break;
                case PLACE_AUTOCOMPLETE_REQUEST_CODE:
                    Place place = PlaceAutocomplete.getPlace(this, data);
                    position = place.getLatLng();
                    if (googleMap != null) {
                        mapFragment.getView().setVisibility(View.VISIBLE);
                        googleMap.addMarker(new MarkerOptions().position(position)
                                .title("Su ubicación"));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(position));
                        googleMap.animateCamera(CameraUpdateFactory.zoomTo(16));
                    }
                    break;
                default:
                    super.onActivityResult(requestCode, resultCode, data);
                    break;
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.gpsButton.setEnabled(true);
        this.googleMap = googleMap;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        if (adapterView != null && position != 0) {
            if (adapterView.getId() == R.id.information_profession_spinner) {
                int array = 0;
                switch (position) {
                    case 1:
                        array = R.array.subcategories_1;
                        break;
                    case 2:
                        array = R.array.subcategories_2;
                        break;
                    case 3:
                        array = R.array.subcategories_3;
                        break;
                    case 4:
                        array = R.array.subcategories_4;
                        break;
                    case 5:
                        array = R.array.subcategories_5;
                        break;
                    case 6:
                        array = R.array.subcategories_6;
                        break;
                    case 7:
                        array = R.array.subcategories_7;
                        break;
                }
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                        array, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                this.subcategoriesSpinner.setAdapter(adapter);
            } else {
                String convertedProfession = ProfessionsHelper.convertPositionToProfession(professionsSpinner.getSelectedItemPosition(), subcategoriesSpinner.getSelectedItemPosition());

                if (!this.professions.contains(convertedProfession)) {
                    this.professions.add(convertedProfession);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.setMargins(5, 5, 5, 5);
                    TextView textView = (TextView) getLayoutInflater().inflate(R.layout.profession_text_item, null);
                    textView.setLayoutParams(params);
                    textView.setTag(convertedProfession);
                    textView.setText(getString(ProfessionsHelper.getStringResourceFor(convertedProfession)));
                    textView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            removeProfession((String) view.getTag());
                            professionsContainer.removeView(view);
                        }
                    });

                    professionsContainer.addView(textView);
                }
            /*int convertedProfession = ProfessionsHelper.convertPositionToProfession(position - 1);

            if (position != 0 && !this.professions.contains(convertedProfession)) {
                this.professions.add(convertedProfession);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(5, 5, 5, 5);
                TextView textView = (TextView) getLayoutInflater().inflate(R.layout.profession_text_item, null);
                textView.setLayoutParams(params);
                textView.setTag(convertedProfession);
                textView.setText(ProfessionsHelper.getTitleForProfession(convertedProfession, this));
                textView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        removeProfession((Integer) view.getTag());
                        professionsContainer.removeView(view);
                    }
                });

                professionsContainer.addView(textView);
            }*/
            }
        }else if (adapterView.getId() == R.id.information_profession_spinner){
            //limpio el de subcategorias
            this.subcategoriesSpinner.setAdapter(null);
        }
    }

    private void removeProfession(String profession) {
        int pos = -1;
        int count = 0;
        for (String p : professions) {
            if (p.equals(profession))
                pos = count;
            count++;
        }
        if (pos >= 0)
            professions.remove(pos);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
