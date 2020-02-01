package com.soma.estadias2017.app_002;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by estadias2017 on 26/06/17.
 */

public class EnviarSpei extends AppCompatActivity {

    private ListView ListaPago;
    Toolbar toolbar;

    private String SOAP_ACTION = "";
    private String METHOD_NAME = "sps_enviar";
    private String NAMESPACE = "http://webservices/";
    private String URL = "http://192.168.1.118:8080/AppMovilReforma/EnviarSPEI_ws?wsdl";
    //private String URL = "https://mail.reformasofipo.com/AplicacionMovilws/EnviarSPEI_ws?wsdl";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_lista_enviar_spei);
        ListaPago = (ListView) findViewById(R.id.ListaPago);

        //Funcion de la barra de herramientas
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        invocarLista();

    }


    public void invocarLista(){

        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11); // utilizar la version que corresponda:11 o 12
            //envelope.dotNet = true; // para WS ASMX, sólo si fue construido con .Net
            request.addProperty("cliente", Globals.getCuenta());
            request.addProperty("idcliente", Globals.getIdusuario());
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.call(SOAP_ACTION, envelope);

            System.out.print(androidHttpTransport.getServiceConnection()); //imprime en consola la conexion
            SoapObject result = (SoapObject)envelope.bodyIn;

            //obtener los datos mediante una lista
            List<HashMap<String,String>> registros = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> registro;

            if(result.getPropertyCount() == 0) {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("NO HAY DATOS A LISTAR")
                        .setTitle("Aviso")
                        .setNegativeButton("ACEPTAR",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();

                                    }
                                });

                AlertDialog alert = builder.create();
                alert.show();
            }

            for(int i= 0; i< result.getPropertyCount(); i++) {
                String[] testValues = new String[result.getPropertyCount()];
                testValues[i] = result.getProperty(i).toString();
                System.out.print(testValues[i]);

                SoapObject result3 = (SoapObject) result.getProperty(i);
                registro = new HashMap<String, String>();

                registro.put("causa_devolucionid", result3.getProperty("causa_devolucionid").toString());
                registro.put("clave_p_b", result3.getProperty("clave_p_b").toString());
                registro.put("clave_p_o", result3.getProperty("clave_p_o").toString());
                registro.put("clave_rastreo", result3.getProperty("clave_rastreo").toString());
                registro.put("clave_tc_b", result3.getProperty("clave_tc_b").toString());
                registro.put("clave_tc_o", result3.getProperty("clave_tc_o").toString());
                registro.put("clave_tipo_operacion", result3.getProperty("clave_tipo_operacion").toString());
                registro.put("comision",  result3.getProperty("comision").toString());
                registro.put("concepto_pago", result3.getProperty("concepto_pago").toString());
                registro.put("estado_pago_speiid", result3.getProperty("estado_pago_speiid").toString());
                registro.put("fecha_pago", result3.getProperty("fecha_pago").toString());
                registro.put("folio", result3.getProperty("folio").toString());
                registro.put("folio_instruccion", result3.getProperty("folio_instruccion").toString());
                registro.put("iva_pago", result3.getProperty("iva_pago").toString());
                registro.put("monto_pago", result3.getProperty("monto_pago").toString());
                registro.put("nombre_b",  result3.getProperty("nombre_b").toString());
                registro.put("nombre_estado", result3.getProperty("nombre_estado").toString());
                registro.put("nombre_o", result3.getProperty("nombre_o").toString());
                registro.put("numero_cuenta_b", result3.getProperty("numero_cuenta_b").toString());
                registro.put("numero_cuenta_o", result3.getProperty("numero_cuenta_o").toString());
                registro.put("pagos_speiid", result3.getProperty("pagos_speiid").toString());

                registro.put("ref_numerica", result3.getProperty("ref_numerica").toString());
                System.out.println("ALVAREZ ROCIOOOOOOOOOOOOOOOOOOOOOOOOO WUU");
                registro.put("rfc_curp_b", result3.getProperty("rfc_curp_b").toString());
                registro.put("rfc_curp_o", result3.getProperty("rfc_curp_o").toString());
                registro.put("tipo_pagoid", result3.getProperty("tipo_pagoid").toString());
                registro.put("topologia", result3.getProperty("topologia").toString());

                registro.put("ref_cobranza1", result3.getProperty("ref_cobranza1").toString());

                System.out.println("Entra 4");

                registros.add(registro);
            }

            //Crea adaptador para traer los datos a la lista
            SimpleAdapter adaptador = new SimpleAdapter(getApplicationContext(),
                    registros, R.layout.vista_enviar_spei,
                    new String[]{"causa_devolucionid", "clave_p_b", "clave_p_o", "clave_rastreo",
                            "clave_tc_b", "clave_tc_o", "clave_tipo_operacion", "comision",
                            "concepto_pago", "entrada", "estado_pago_speiid", "fecha_pago",
                            "folio", "folio_instruccion", "iva_pago", "monto_pago",
                            "nombrePBeneficiario", "nombrePOrdenante", "nombre_b", "nombre_estado",
                            "nombre_o", "numero_cuenta_b", "numero_cuenta_o", "pagos_speiid",
                            "recobranza",  "rfc_curp_b", "rfc_curp_o", "tipo_pagoid",
                            "topologia", "ref_numerica", "ref_cobranza1" },

                    new int[]{ R.id.causa_devolucionid, R.id.clave_p_b, R.id.clave_p_o,
                            R.id.clave_rastreo, R.id.clave_tc_b, R.id.clave_tc_o,
                            R.id.clave_tipo_operacion, R.id.comision, R.id.concepto_pago,
                            R.id.entrada, R.id.estado_pago_speiid, R.id.fecha_pago,
                            R.id.folio, R.id.folio_instruccion, R.id.iva_pago, R.id.monto_pago,
                            R.id.nombrePBeneficiario, R.id.nombrePOrdenante, R.id.nombre_b,
                            R.id.nombre_estado, R.id.nombre_o, R.id.numero_cuenta_b,
                            R.id.numero_cuenta_o, R.id.pagos_speiid, R.id.recobranza,
                            R.id.rfc_curp_b, R.id.rfc_curp_o, R.id.tipo_pagoid,
                            R.id.topologia, R.id.ref_numerica,
                            R.id.ref_cobranza1 });


            ListaPago.setAdapter(adaptador);

            ListaPago.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {

                    Intent intento = new Intent(getApplicationContext(),

                            FormEnviarSpei.class);
                    Bundle bun = new Bundle();


                    TextView causa_devolucionid = (TextView) arg1.findViewById(R.id.causa_devolucionid);
                    bun.putString("causa_devolucionid", causa_devolucionid.getText().toString());
                    intento.putExtra("causa_devolucionid", causa_devolucionid.getText().toString());
                    intento.putExtras(bun);

                    System.out.println("Entra 8");

                    intento.putExtra("causa_devolucionid", causa_devolucionid.getText().toString());

                    TextView clave_p_b = (TextView) arg1.findViewById(R.id.clave_p_b);
                    intento.putExtra("clave_p_b", clave_p_b.getText().toString());

                    TextView clave_p_o = (TextView) arg1.findViewById(R.id.clave_p_o);
                    intento.putExtra("clave_p_o", clave_p_o.getText().toString());

                    TextView clave_rastreo = (TextView) arg1.findViewById(R.id.clave_rastreo);
                    intento.putExtra("clave_rastreo", clave_rastreo.getText().toString());

                    TextView clave_tc_b = (TextView) arg1.findViewById(R.id.clave_tc_b);
                    intento.putExtra("clave_tc_b", clave_tc_b.getText().toString());

                    TextView clave_tc_o = (TextView) arg1.findViewById(R.id.clave_tc_o);
                    intento.putExtra("clave_tc_o", clave_tc_o.getText().toString());

                    TextView clave_tipo_operacion = (TextView) arg1.findViewById(R.id.clave_tipo_operacion);
                    intento.putExtra("clave_tipo_operacion", clave_tipo_operacion.getText().toString());

                    TextView comision = (TextView) arg1.findViewById(R.id.comision);
                    intento.putExtra("comision", comision.getText().toString());

                    TextView concepto_pago = (TextView) arg1.findViewById(R.id.concepto_pago);
                    intento.putExtra("concepto_pago", concepto_pago.getText().toString());

                    TextView entrada = (TextView) arg1.findViewById(R.id.entrada);
                    intento.putExtra("entrada", entrada.getText().toString());

                    TextView estado_pago_speiid = (TextView) arg1.findViewById(R.id.estado_pago_speiid);
                    intento.putExtra("estado_pago_speiid", estado_pago_speiid.getText().toString());

                    TextView fecha_pago = (TextView) arg1.findViewById(R.id.fecha_pago);
                    intento.putExtra("fecha_pago", fecha_pago.getText().toString());

                    TextView folio = (TextView) arg1.findViewById(R.id.folio);
                    intento.putExtra("folio", folio.getText().toString());

                    TextView folio_instruccion = (TextView) arg1.findViewById(R.id.folio_instruccion);
                    intento.putExtra("folio_instruccion", folio_instruccion.getText().toString());

                    TextView iva_pago = (TextView) arg1.findViewById(R.id.iva_pago);
                    intento.putExtra("iva_pago", iva_pago.getText().toString());

                    TextView monto_pago = (TextView) arg1.findViewById(R.id.monto_pago);
                    intento.putExtra("monto_pago", monto_pago.getText().toString());

                    TextView nombrePBeneficiario = (TextView) arg1.findViewById(R.id.nombrePBeneficiario);
                    intento.putExtra("nombrePBeneficiario", nombrePBeneficiario.getText().toString());

                    TextView nombrePOrdenante = (TextView) arg1.findViewById(R.id.nombrePOrdenante);
                    intento.putExtra("nombrePOrdenante", nombrePOrdenante.getText().toString());

                    TextView nombre_b = (TextView) arg1.findViewById(R.id.nombre_b);
                    intento.putExtra("nombre_b", nombre_b.getText().toString());

                    TextView nombre_estado = (TextView) arg1.findViewById(R.id.nombre_estado);
                    intento.putExtra("nombre_estado", nombre_estado.getText().toString());

                    TextView nombre_o = (TextView) arg1.findViewById(R.id.nombre_o);
                    intento.putExtra("nombre_o", nombre_o.getText().toString());

                    TextView numero_cuenta_b = (TextView) arg1.findViewById(R.id.numero_cuenta_b);
                    intento.putExtra("numero_cuenta_b", numero_cuenta_b.getText().toString());

                    TextView numero_cuenta_o = (TextView) arg1.findViewById(R.id.numero_cuenta_o);
                    intento.putExtra("numero_cuenta_o", numero_cuenta_o.getText().toString());

                    TextView pagos_speiid = (TextView) arg1.findViewById(R.id.pagos_speiid);
                    intento.putExtra("pagos_speiid", pagos_speiid.getText().toString());

                    TextView recobranza = (TextView) arg1.findViewById(R.id.recobranza);
                    intento.putExtra("recobranza", recobranza.getText().toString());

                    TextView rfc_curp_b = (TextView) arg1.findViewById(R.id.rfc_curp_b);
                    intento.putExtra("rfc_curp_b", rfc_curp_b.getText().toString());

                    TextView rfc_curp_o = (TextView) arg1.findViewById(R.id.rfc_curp_o);
                    intento.putExtra("rfc_curp_o", rfc_curp_o.getText().toString());

                    TextView tipo_pagoid = (TextView) arg1.findViewById(R.id.tipo_pagoid);
                    intento.putExtra("tipo_pagoid", tipo_pagoid.getText().toString());

                    TextView topologia = (TextView) arg1.findViewById(R.id.topologia);
                    intento.putExtra("topologia", topologia.getText().toString());

                    TextView ref_numerica = (TextView) arg1.findViewById(R.id.ref_numerica);
                    intento.putExtra("ref_numerica", ref_numerica.getText().toString());

                    TextView ref_cobranza1 = (TextView) arg1.findViewById(R.id.ref_cobranza1);
                    intento.putExtra("ref_cobranza1", ref_cobranza1.getText().toString());


                    System.out.println("Entro hasta el final :)     ");

                    startActivity(intento);
                }
            });

        }

        catch (Exception e){

        }
    }

}
