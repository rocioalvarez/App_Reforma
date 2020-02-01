package com.soma.estadias2017.app_002;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by estadias2017 on 3/05/17.
 */

public class FormGestionSpei extends AppCompatActivity implements Serializable {

    public EditText rfc_curp_o, nombre_o, numero_cuenta_o, nombre_b, rfc_curp_b, numero_cuenta_b,
            concepto_pago, cvrastreo, referencia, monto_pago, iva_pago, cvoperacion, topologia,
            idpago, fecha, ref_cobranza, prioridad, tipo_pago, cuentaspei, tipo_operacion;

    public Button actualizar;
    public Button eliminar;
    Toolbar toolbar;

    Button btn_finalizar;
    Button btn_cancelar;
    LayoutInflater layoutInflater;
    View popupView;
    PopupWindow popupWindow;

    String SOAP_ACTION = "";
    String METHOD_NAME = "formu_gspei";
    String NAMESPACE = "http://test/";
    String URL = "http://192.168.1.118:8080/WSAplicacionBanca/WSGestionSPEI?WSDL";


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_form_gestion_spei);
        nombre_o = (EditText) findViewById(R.id.nombre_o);
        rfc_curp_o = (EditText) findViewById(R.id.rfc_curp_o);
        numero_cuenta_o = (EditText) findViewById(R.id.numero_cuenta_o);
        nombre_b = (EditText) findViewById(R.id.nombre_b);
        rfc_curp_b = (EditText) findViewById(R.id.rfc_curp_b);
        numero_cuenta_b = (EditText) findViewById(R.id.numero_cuenta_b);
        concepto_pago = (EditText) findViewById(R.id.concepto_pago);
        cvrastreo = (EditText) findViewById(R.id.cvrastreo);
        referencia = (EditText) findViewById(R.id.referencia);
        monto_pago = (EditText) findViewById(R.id.monto_pago);
        iva_pago = (EditText) findViewById(R.id.iva_pago);
        topologia = (EditText) findViewById(R.id.topologia);
        idpago = (EditText) findViewById(R.id.idpago);
        fecha = (EditText) findViewById(R.id.fecha);
        ref_cobranza = (EditText) findViewById(R.id.ref_cobranza);
        prioridad = (EditText) findViewById(R.id.prioridad);
        tipo_pago = (EditText) findViewById(R.id.tipo_pago);
        cuentaspei = (EditText) findViewById(R.id.cuentaspei);
        tipo_operacion = (EditText) findViewById(R.id.tipo_operacion);
        cvoperacion = (EditText) findViewById(R.id.cvoperacion);
        actualizar = (Button) findViewById(R.id.actualizar);
        eliminar = (Button) findViewById(R.id.eliminar);


        Bundle extras = getIntent().getExtras();
        String id = extras.getString("pagos_speiid");
        String nom = extras.getString("nombre_o");
        String rfc_o = extras.getString("rfc_curp_o");
        String cuenta_o = extras.getString("numero_cuenta_o");
        String rfc_b = extras.getString("rfc_curp_b");
        String cuenta_b = extras.getString("numero_cuenta_b");
        String refe = extras.getString("ref_numerica");
        String iva = extras.getString("iva_pago");
        String concpto = extras.getString("concepto_pago");
        String cvrast = extras.getString("clave_rastreo");
        String nomb_b = extras.getString("nombre_b");
        String monto = extras.getString("monto_pago");
        String fcha = extras.getString("fecha_pago");
        String ref = extras.getString("clave_p_b");
        //String pri = extras.getString("prioridad");

        nombre_o.setText(nom);
        rfc_curp_o.setText(rfc_o);
        numero_cuenta_o.setText(cuenta_o);
        nombre_b.setText(nomb_b);
        rfc_curp_b.setText(rfc_b);
        numero_cuenta_b.setText(cuenta_b);
        concepto_pago.setText(concpto);
        cvrastreo.setText(cvrast);
        referencia.setText(refe);
        monto_pago.setText(monto);
        iva_pago.setText(iva);
        idpago.setText(id);
        fecha.setText(fcha);
        ref_cobranza.setText(ref);
        //prioridad.setText(pri);

        //Funcion de la barra de herramientas
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

       /* btn_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom_o = nombre_o.getText().toString();
                String rfc_o = rfc_curp_o.getText().toString();
                String cuenta_o = numero_cuenta_o.getText().toString();
                String nom_b = nombre_b.getText().toString();
                String rfc_b = rfc_curp_b.getText().toString();
                String cuenta_b = numero_cuenta_b.getText().toString();
                String concpto = concepto_pago.getText().toString();

                String refe = referencia.getText().toString();
                String monto = monto_pago.getText().toString();
                String iva = iva_pago.getText().toString();


                String id = idpago.getText().toString();
                String cvrast = cvrastreo.getText().toString();
                String t = topologia.getText().toString();
                String pri = prioridad.getText().toString();
                String fcha = fecha.getText().toString();
                String tp = tipo_pago.getText().toString();
                String cuent = cuentaspei.getText().toString();
                String ref = ref_cobranza.getText().toString();

               // Cuenta c= adapter.getItem(cuenta.getSelectedItemPosition());


                String to = tipo_operacion.getSelectedItem().toString();
                String cv = cvoperacion.getText().toString();

                InvocarCdeta(nom_o, rfc_o, cuenta_o, nom_b, rfc_b, cuenta_b, concpto, refe, monto,
                    iva, id, cvrast, t, pri, fcha, tp, cuent, ref, to, cv);

                cvoperacion.setText("");
            }
        });
        */


        //Boton para Actualizar
        actualizar.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v){

                        String nom_o = nombre_o.getText().toString();
                        String rfc_o = rfc_curp_o.getText().toString();
                        String cue_o = numero_cuenta_o.getText().toString();
                        String nom_b = nombre_b.getText().toString();
                        String rfc_b = rfc_curp_b.getText().toString();
                        String cue_b = numero_cuenta_b.getText().toString();
                        String conc = concepto_pago.getText().toString();
                        String refe = referencia.getText().toString();
                        String mont = monto_pago.getText().toString();
                        String iva_p = iva_pago.getText().toString();
                        String id_pa = idpago.getText().toString();
                        String cvras = cvrastreo.getText().toString();
                        String topo = topologia.getText().toString();
                        String prio = prioridad.getText().toString();
                        String fcha = fecha.getText().toString();
                        String ti_pa = tipo_pago.getText().toString();
                        String cuen = cuentaspei.getText().toString();
                        String re_co = ref_cobranza.getText().toString();
                        String ti_op = tipo_operacion.getText().toString();
                        String cv_op = cvoperacion.getText().toString();

                        if (cv_op.isEmpty()){
                            cvoperacion.setError("Campo vacio");
                            cvoperacion.requestFocus();

                        } else {
                            InvocarCdeta(nom_o, rfc_o, cue_o, nom_b, rfc_b, cue_b, conc, refe, mont, iva_p,
                                    id_pa, cvras, topo, prio, fcha, ti_pa, cuen, re_co, ti_op, cv_op);


                            // Ocultar teclado virtual
                        /*
                        InputMethodManager imm =
                                (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                        layoutInflater =(LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                        popupView = layoutInflater.inflate(R.layout.popup_enviar_spei, null);
                        popupWindow = new PopupWindow(popupView, RadioGroup.LayoutParams.WRAP_CONTENT,
                                RadioGroup.LayoutParams.WRAP_CONTENT);

                        btn_cancelar = (Button)popupView.findViewById(R.id.id_cancelar);
                        btn_cancelar.setOnClickListener(new Button.OnClickListener(){

                            @Override
                            public void onClick(View v) {
                                popupWindow.dismiss();
                            }});

                        popupWindow.showAsDropDown(btn_cancelar, 50, 0);
                        */

                        }
                    }
                });

                        /*
        Button actualizar = (Button) findViewById(R.id.actualizar);
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();

                View dialoglayout = inflater.inflate(R.layout.popup_enviar_spei, null);



                AlertDialog.Builder builder = new AlertDialog.Builder(FormGestionSpei.this);
                builder.setView(dialoglayout);
                builder.show();
            }
        });

*/

        //Boton para Eliminar
        eliminar.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v){

                        String id = idpago.getText().toString();
                        String cv = cvoperacion.getText().toString();
                        int opc=1;

                        if (cv.isEmpty()){
                            cvoperacion.setError("Campo vacio");
                            cvoperacion.requestFocus();

                        }else {

                            InvocarElmininar(id, cv, opc);

                            //Funcion para que refresque los datos del formulario
                            Intent intent = new Intent(getApplicationContext(), GestionSpei.class);
                            finish();
                            startActivity(intent);

                        }
                    }
                });
    }



    private void InvocarCdeta(String nombre_o, String rfc_curp_o, String numero_cuenta_o,
                              String nombre_b, String rfc_curp_b, String numero_cuenta_b,
                              String concepto_pago,  String ref_numerica, String monto_pago,
                              String iva_pago,  String pagos_speiid, String clave_rastreo,
                              String topologia, String prioridad, String fecha_pago,
                              String tipo_pago,  String clave_p_b, String ref_cobranza,
                              String tipo_operacion, String cvoperacion) {

        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);

            Request.addProperty("cliente", Globals.getCuenta());
            Request.addProperty("idcliente", Globals.getIdusuario());
            Request.addProperty("nombre_o", nombre_o);
            Request.addProperty("rfc_curp_o", rfc_curp_o);
            Request.addProperty("numero_cuenta_o", numero_cuenta_o);
            Request.addProperty("nombre_b", nombre_b);
            Request.addProperty("rfc_curp_b", rfc_curp_b);
            Request.addProperty("numero_cuenta_b", numero_cuenta_b);
            Request.addProperty("monto_pago", monto_pago);
            Request.addProperty("iva_pago", iva_pago);
            Request.addProperty("idpago", pagos_speiid);
            Request.addProperty("cvrastreo", clave_rastreo);
            Request.addProperty("topologia", "T");
            Request.addProperty("prioridad", 0);
            Request.addProperty("fecha", fecha_pago);
            Request.addProperty("tipo_pago", 0);
            Request.addProperty("cuentaspei", clave_p_b);
            Request.addProperty("referencia", ref_numerica);
            Request.addProperty("ref_cobranza", ref_cobranza );
            Request.addProperty("concepto", concepto_pago);
            Request.addProperty("tipo_operacion", tipo_operacion);
            Request.addProperty("cvoperacion", cvoperacion);

            envelope.setOutputSoapObject(Request);
            HttpTransportSE HttpTransport = new HttpTransportSE(URL);
            HttpTransport.call(SOAP_ACTION, envelope);

            SoapObject resp = (SoapObject) envelope.bodyIn;

            String r = resp.getProperty(1).toString();
            //resultado.setText(r);
            Toast.makeText(this,r,Toast.LENGTH_LONG).show();

        }
        catch (Exception e){

        }
    }

    private void InvocarElmininar (String pagos_speiid, String cvoperacion, int opc) {

        String SOAP_ACTION = "";
        String METHOD_NAME = "spd_pago";
        String NAMESPACE = "http://test/";
        String URL = "http://192.168.1.81:8080/WSAplicacionBanca/WSEliminaPago?WSDL";

        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);

            Request.addProperty("cliente", Globals.getCuenta());
            Request.addProperty("idcliente", Globals.getIdusuario());
            Request.addProperty("idpago", pagos_speiid);
            Request.addProperty("cvoperacion", cvoperacion);
            Request.addProperty("opc", 1);

            envelope.setOutputSoapObject(Request);
            HttpTransportSE HttpTransport = new HttpTransportSE(URL);
            HttpTransport.call(SOAP_ACTION, envelope);

            SoapObject resp = (SoapObject) envelope.bodyIn;

            String r = resp.getProperty(1).toString();
            //resultado.setText(r);
            Toast.makeText(this,r,Toast.LENGTH_LONG).show();

        }
        catch (Exception e){
        }
    }
}
