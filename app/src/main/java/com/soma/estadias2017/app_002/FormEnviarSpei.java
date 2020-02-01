package com.soma.estadias2017.app_002;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.Serializable;
import java.security.MessageDigest;

/**
 * Created by estadias2017 on 27/06/17.
 */

public class FormEnviarSpei extends AppCompatActivity implements Serializable {

    public EditText causa_devolucionid, clave_p_b, clave_p_o, clave_rastreo, clave_tc_b, clave_tc_o,
            clave_tipo_operacion, comision, concepto, entrada, estado_pago_speiid, fecha_hora, folio,
            folio_instruccion, iva_pago, monto_pago, nombreb, nombre_estado, nombreo, Numero_cuenta_b,
            Numero_cuenta_o, pagos_speiid, polizaid, prioridad, recobranza, rfnumerica, rfc_curp_b,
            rfc_curp_o,tipo_pagoid, topologia, cvoperacion;
    public Button btn_enviar;
    public TextView result;

    String SOAP_ACTION ="";
    String METHOD_NAME="conect";
    String NAMESPACE="http://test/";
    String URL = "http://192.168.1.118:8080/WSAplicacionBanca/WSConexionSPEI?WSDL";
    //String URL = "https://mail.reformasofipo.com/AplicacionMovilws/WSConexionSPEI?WSDL";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_form_enviar_spei);
        causa_devolucionid = (EditText) findViewById(R.id.causa_devolucionid);
        clave_p_b = (EditText) findViewById(R.id.clave_p_b);
        clave_p_o = (EditText) findViewById(R.id.clave_p_o);
        clave_rastreo = (EditText) findViewById(R.id.clave_rastreo);
        clave_tc_b = (EditText) findViewById(R.id.clave_tc_b);
        clave_tc_o = (EditText) findViewById(R.id.clave_tc_o);
        clave_tipo_operacion = (EditText) findViewById(R.id.clave_tipo_operacion);
        comision = (EditText) findViewById(R.id.comision);
        concepto = (EditText) findViewById(R.id.concepto);
        entrada = (EditText) findViewById(R.id.entrada);
        estado_pago_speiid = (EditText) findViewById(R.id.estado_pago_speiid);
        fecha_hora = (EditText) findViewById(R.id.fecha_hora);
        folio = (EditText) findViewById(R.id.folio);
        folio_instruccion = (EditText) findViewById(R.id.folio_instruccion);
        iva_pago = (EditText) findViewById(R.id.iva_pago);
        monto_pago = (EditText) findViewById(R.id.monto_pago);
        nombreb = (EditText) findViewById(R.id.nombreb);
        nombre_estado = (EditText) findViewById(R.id.nombre_estado);
        nombreo = (EditText) findViewById(R.id.nombreo);
        Numero_cuenta_b = (EditText) findViewById(R.id.Numero_cuenta_b);
        Numero_cuenta_o = (EditText) findViewById(R.id.Numero_cuenta_o);
        pagos_speiid = (EditText) findViewById(R.id.pagos_speiid);
        polizaid = (EditText) findViewById(R.id.polizaid);
        prioridad = (EditText) findViewById(R.id.prioridad);
        recobranza = (EditText) findViewById(R.id.recobranza);
        rfnumerica = (EditText) findViewById(R.id.rfnumerica);
        rfc_curp_b = (EditText) findViewById(R.id.rfc_curp_b);
        rfc_curp_o = (EditText) findViewById(R.id.rfc_curp_o);
        tipo_pagoid = (EditText) findViewById(R.id.tipo_pagoid);
        topologia = (EditText) findViewById(R.id.topologia);
        cvoperacion = (EditText) findViewById(R.id.cvoperacion);
        btn_enviar = (Button) findViewById(R.id.btn_enviar);
        result = (TextView) findViewById(R.id.result);

        Bundle extras = getIntent().getExtras();

        String c_dev = extras.getString("causa_devolucionid ");
        String cv_pb = extras.getString("clave_p_b");
        String cv_po = extras.getString("clave_p_o");
        String cv_ra = extras.getString("clave_rastreo");
        String cv_tb = extras.getString("clave_tc_b");
        String cv_to = extras.getString("clave_tc_o");
        String cv_ti = extras.getString("clave_tipo_operacion");
        String comi = extras.getString("comision");
        String conc = extras.getString("concepto_pago");
        String entr = extras.getString("entrada");
        String est_p = extras.getString("estado_pago_speiid ");
        String fcha = extras.getString("fecha_pago");
        String foli = extras.getString("folio");
        String fo_in = extras.getString("folio_instruccion");
        String iva_p = extras.getString("iva_pago");
        String mont = extras.getString("monto_pago");
        String nom_b = extras.getString("nombre_b");
        String nom_e = extras.getString("nombre_estado");
        String nom_o = extras.getString("nombre_o");
        String cuenta_b = extras.getString("numero_cuenta_b");
        String cuenta_o = extras.getString("numero_cuenta_o");
        String id_pa = extras.getString("pagos_speiid");
        String rfc_b = extras.getString("rfc_curp_b");
        String rfc_o = extras.getString("rfc_curp_o");
        String tpago = extras.getString("tipo_pagoid");
        String topo = extras.getString("topologia");
        String ref_n = extras.getString("ref_numerica");
        String re_co = extras.getString("ref_cobranza1");

        causa_devolucionid.setText(c_dev);
        clave_p_b.setText(cv_pb);
        clave_p_o.setText(cv_po);
        clave_rastreo.setText(cv_ra);
        clave_tc_b.setText(cv_tb);
        clave_tc_o.setText(cv_to);
        clave_tipo_operacion.setText(cv_ti);
        comision.setText(comi);
        concepto.setText(conc);
        entrada.setText(entr);
        estado_pago_speiid.setText(est_p);
        fecha_hora.setText(fcha);
        folio.setText(foli);
        folio_instruccion.setText(fo_in);
        iva_pago.setText(iva_p);
        monto_pago.setText(mont);
        nombreb.setText(nom_b);
        nombre_estado.setText(nom_e);
        nombreo.setText(nom_o);
        Numero_cuenta_b.setText(cuenta_b);
        Numero_cuenta_o.setText(cuenta_o);
        pagos_speiid.setText(id_pa);
        rfc_curp_b.setText(rfc_b);
        rfc_curp_o.setText(rfc_o);
        tipo_pagoid.setText(tpago);
        topologia.setText(topo);
        rfnumerica.setText(ref_n);
        recobranza.setText(re_co);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        btn_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //LayoutInflater inflater = getLayoutInflater();
                //View dialoglayout = inflater.inflate(R.layout.popup_enviar_spei, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(FormEnviarSpei.this);
                builder.setMessage("Estas a punto de enviar el pago ¿Desea continuar?")
                    .setTitle("Alerta")
                    .setCancelable(true)
                    .setNegativeButton("Cancelar",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();

                                    }
                                })
                    .setPositiveButton("Continuar",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    String c_dev = causa_devolucionid.getText().toString();
                                    String cv_pb = clave_p_b.getText().toString();
                                    String cv_po = clave_p_o.getText().toString();
                                    String cv_ra = clave_rastreo.getText().toString();
                                    String cv_tb = clave_tc_b.getText().toString();
                                    String cv_to = clave_tc_o.getText().toString();
                                    String cv_ti = clave_tipo_operacion.getText().toString();
                                    String comi = comision.getText().toString();
                                    String conc = concepto.getText().toString();
                                    String entr = entrada.getText().toString();
                                    String est_p = estado_pago_speiid.getText().toString();
                                    String fcha = fecha_hora.getText().toString();
                                    String foli = folio.getText().toString();
                                    String fo_in = folio_instruccion.getText().toString();
                                    String iva_p = iva_pago.getText().toString();
                                    String mont = monto_pago.getText().toString();
                                    String nom_b = nombreb.getText().toString();
                                    String nom_e = nombre_estado.getText().toString();
                                    String nom_o = nombreo.getText().toString();
                                    String cuen_b = Numero_cuenta_b.getText().toString();
                                    String cuen_o = Numero_cuenta_o.getText().toString();
                                    String id_pa = pagos_speiid.getText().toString();
                                    String rfc_b = rfc_curp_b.getText().toString();
                                    String rfc_o = rfc_curp_o.getText().toString();
                                    String tpago = tipo_pagoid.getText().toString();
                                    String topo = topologia.getText().toString();
                                    String ref_n = rfnumerica.getText().toString();
                                    String re_co = recobranza.getText().toString();
                                    String cv_op = cvoperacion.getText().toString();

                                    if (cv_op.isEmpty()){
                                        cvoperacion.setError("Campo vacio");
                                        cvoperacion.requestFocus();
                                    }else {

                                        Conexion(c_dev, cv_pb, cv_po, cv_ra, cv_tb, cv_to, cv_ti, comi, conc, entr,
                                                est_p, fcha, foli, fo_in, iva_p, mont, nom_b, nom_e, nom_o, cuen_b,
                                                cuen_o, id_pa, rfc_b, rfc_o, tpago, topo, cv_op, ref_n, re_co);
                                        }
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });

    }

    private void Conexion(String causa_devolucionid, String clave_p_b, String clave_p_o, String clave_rastreo,
                          String clave_tc_b, String clave_tc_o, String clave_tipo_operacion, String comision,
                          String concepto, String entrada, String estado_pago_speiid, String fecha_hora,
                          String folio, String folio_instruccion, String iva_pago, String monto_pago,
                          String nombreb, String nombre_estado, String nombreo, String Numero_cuenta_b,
                          String Numero_cuenta_o, String pagos_speiid, String rfc_curp_b, String rfc_curp_o,
                          String tipo_pagoid, String topologia, String cvoperacion, String rfnumerica,
                          String recobranza){


        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            Request.addProperty("cliente", Globals.getCuenta());
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.setOutputSoapObject(Request);
            Request.addProperty("causa_devolucionid", causa_devolucionid);
            Request.addProperty("clave_p_b", clave_p_b);
            Request.addProperty("clave_p_o", clave_p_o);
            Request.addProperty("clave_rastreo", clave_rastreo);
            Request.addProperty("clave_tc_b", clave_tc_b);
            Request.addProperty("clave_tc_o", clave_tc_o);
            Request.addProperty("clave_tipo_operacion", clave_tipo_operacion);
            Request.addProperty("comision", comision);
            Request.addProperty("concepto", concepto);
            Request.addProperty("entrada", entrada);
            Request.addProperty("estado_pago_speiid", estado_pago_speiid);
            Request.addProperty("fecha_hora", fecha_hora);
            Request.addProperty("folio", folio);
            Request.addProperty("folio_instruccion", folio_instruccion);
            Request.addProperty("iva_pago", iva_pago);
            Request.addProperty("monto_pago", monto_pago);
            Request.addProperty("nombreb", nombreb);
            Request.addProperty("nombre_estado", nombre_estado);
            Request.addProperty("nombreo", nombreo);
            Request.addProperty("Numero_cuenta_b", Numero_cuenta_b);
            Request.addProperty("Numero_cuenta_o", Numero_cuenta_o);
            Request.addProperty("pagos_speiid", pagos_speiid);
            Request.addProperty("rfc_curp_b", rfc_curp_b);
            Request.addProperty("rfc_curp_o", rfc_curp_o);
            Request.addProperty("tipo_pagoid", tipo_pagoid);
            Request.addProperty("topologia", topologia);
            Request.addProperty("rfnumerica", rfnumerica);
            Request.addProperty("recobranza", recobranza);
            Request.addProperty("cvoperacion", codifica(cvoperacion));

            Log.d("Request ", Request.toString());
            SoapSerializationEnvelope srl = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            srl.setOutputSoapObject(Request);

            HttpTransportSE HttpTransport = new HttpTransportSE(URL);
            HttpTransport.call(SOAP_ACTION, srl);
            System.out.print(HttpTransport.getServiceConnection()); //imprime en consola la conexion

            SoapObject so = (SoapObject) envelope.bodyIn;

            String r = "";
            r = so.getProperty(1).toString();
            result.setText(r);

            Toast.makeText(this, r, Toast.LENGTH_LONG).show();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String codifica(String cadena) {
        //Se crea objeto de messageDigest que permite crear instancia hacia el tipo de encriptacion..
        MessageDigest messageDigest = null;

        //Se crea objeto de string buffer para almacenar la concatenacion de los datos del arreglo.
        StringBuffer sb = null;
        try {
            //Se inicializa el objeto y se le pasa el tipo de encriptacion deseada
            messageDigest = MessageDigest.getInstance("SHA-512");

            //Se extraen los bytes que contiene la cadena
            byte[] encripta = messageDigest.digest(cadena.getBytes());

            //Se inicializa un objeto tipo String buffer
            sb = new StringBuffer();

            //Se recorre el arreglo de bytes que genero messageDigest
            for (byte hashB : encripta) {
                // se concatena los datos obtenidos del arreglo
                sb.append(String.format("%02x", hashB));
            }

        } catch (Exception e) {
//            log.info(e.getMessage());
//            errorService.createError(e.getMessage(), "Error encriptando la cadena: "+ cadena);
        }

        return sb.toString();
    }

    /*
 private  void Invokee(String cvoperacion) {

     SOAP_ACTION ="";
     METHOD_NAME="validaSpei";
     NAMESPACE="http://test/";
     URL = "http://192.168.1.162:8080/WSAplicacionBanca/ValidaEnvioSpei?WSDL";

     try {
         SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
         request.addProperty("cliente", Globals.getCuenta());
         request.addProperty("cvoperacion", cvoperacion);

         SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                 SoapEnvelope.VER11);
         envelope.setOutputSoapObject(request);

         HttpTransportSE tra = new HttpTransportSE(URL);
         tra.call(SOAP_ACTION, envelope);
         System.out.print(tra.getServiceConnection()); //imprime en consola la conexion



         SoapObject resp = (SoapObject) envelope.bodyIn;

         String r = resp.getProperty(1).toString();
         //resultado.setText(r);
         Toast.makeText(this,r,Toast.LENGTH_LONG).show();

     }catch (Exception e) {
         e.printStackTrace();
     }
 }

*/

}
