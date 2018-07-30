package com.santiagonoailles.matsprofesionales;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.santiagonoailles.matsprofesionales.entity.Presupuesto;
import com.santiagonoailles.matsprofesionales.entity.RespuestaPresupuesto;
import com.santiagonoailles.matsprofesionales.helper.FirebaseHelper;
import com.santiagonoailles.matsprofesionales.helper.MatsSettings;
import com.squareup.picasso.Picasso;

public class PresupuestoActivity extends AppCompatActivity {

    public static Presupuesto currentPresupuesto;

    private EditText priceEditText;
    private EditText detalleEditText;
    private SwitchCompat materialesSwitchCompat;
    private TextView detalleCliente;
    private ImageView presupuestoImageView;
    private int price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presupuesto);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.presupuesto_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        priceEditText = (EditText) findViewById(R.id.presupuesto_price_edittext);
        detalleEditText = (EditText) findViewById(R.id.presupuesto_detalle_edittext);
        materialesSwitchCompat = (SwitchCompat) findViewById(R.id.presupuesto_materiales_switch);
        detalleCliente = (TextView) findViewById(R.id.presupuesto_detalle_cliente);
        presupuestoImageView = (ImageView) findViewById(R.id.presupuesto_image);

        detalleCliente.setText(currentPresupuesto.getDetalleUsuario());
        if (currentPresupuesto.getUrlImage() != null && !currentPresupuesto.getUrlImage().equals("")) {
            Picasso.with(this).load(currentPresupuesto.getUrlImage()).into(presupuestoImageView);
        }else{
            presupuestoImageView.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.presupuesto, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_guardar:
                String error = this.validateData();
                if (error.equals("")) {
                    String detalle = this.detalleEditText.getText().toString();

                    final RespuestaPresupuesto respuestaPresupuesto = new RespuestaPresupuesto();

                    respuestaPresupuesto.setDetalleProfesional(detalle);
                    respuestaPresupuesto.setPrice(price);
                    respuestaPresupuesto.setProfessionalId(MatsSettings.getProfessionalId(this));
                    respuestaPresupuesto.setMateriales(materialesSwitchCompat.isChecked());

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(getString(R.string.presupuesto_confirm_title));
                    String materialesMessage;
                    if (materialesSwitchCompat.isChecked()){
                        materialesMessage = getString(R.string.incluir_materiales);
                    } else{
                        materialesMessage = getString(R.string.sinmateriales);
                    }
                    builder.setMessage(String.format(getString(R.string.presupuesto_confirm_message), price)
                                        + "\n" + materialesMessage);
                    builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            FirebaseHelper.sendRespuestaPresupuesto(currentPresupuesto.getId(), respuestaPresupuesto, String.valueOf(currentPresupuesto.getPosRespuesta()), MatsSettings.getProfessionalId(PresupuestoActivity.this));
                            PresupuestoActivity.this.finish();
                        }
                    });
                    builder.show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(getString(R.string.registro_error_title));
                    builder.setMessage(error.toString());
                    builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                }
                return true;
            case R.id.nav_cancelar:
                FirebaseHelper.cancelPresupuesto(MatsSettings.getProfessionalId(this), currentPresupuesto.getId());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String validateData() {
        StringBuffer errorBuffer = new StringBuffer();

        if (this.priceEditText.getText().toString().equals("")) {
            errorBuffer.append(getString(R.string.price) + "\n");
        } else {
            price = Integer.valueOf(this.priceEditText.getText().toString());
        }

        return errorBuffer.toString();
    }
}
