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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by estadias2017 on 3/05/17.
 */

public class GestionSpei extends AppCompatActivity {

    private ListView listaGs;
    Toolbar toolbar;

    //Consumir Web Service
    String SOAP_ACTION = "";
    String METHOD_NAME = "gestion";
    String NAMESPACE = "http://webservices/";
    String URL = "http://192.168.1.118:8080/WSAplicacionBanca/GestionSPEI_ws?wsdl";
    //String URL = "https://mail.reformasofipo.com/AplicacionMovilws/GestionSPEI_ws?wsdl";

    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.vista_lista_gestion_spei);
        listaGs = (ListView) findViewById(R.id.lgs);

        //Funcion de la barra de herramientas
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //Declarar metodo para invocar los datos
        InvocaGestion();
    }


    //Metodo que invoca
    public void InvocaGestion(){
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
            System.out.print(androidHttpTransport.getServiceConnection()); //imprimir en consola la conexion
            SoapObject result = (SoapObject)envelope.bodyIn;
            //obtener los datos mediante una lista
            List<HashMap<String,String>> registros = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> registro;


            if (result.getPropertyCount() == 0){
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
                registro.put("pagos_speiid", result3.getProperty("pagos_speiid").toString());
                registro.put("nombre_o", result3.getProperty("nombre_o").toString());
                registro.put("rfc_curp_o", result3.getProperty("rfc_curp_o").toString());
                registro.put("numero_cuenta_o", result3.getProperty("numero_cuenta_o").toString());
                registro.put("rfc_curp_b", result3.getProperty("rfc_curp_b").toString());
                registro.put("numero_cuenta_b", result3.getProperty("numero_cuenta_b").toString());
                registro.put("ref_numerica", result3.getProperty("ref_numerica").toString());
                registro.put("iva_pago", result3.getProperty("iva_pago").toString());
                registro.put("nombre_estado", result3.getProperty("nombre_estado").toString());
                registro.put("concepto_pago",  result3.getProperty("concepto_pago").toString());
                registro.put("clave_rastreo", result3.getProperty("clave_rastreo").toString());
                registro.put("nombre_b",  result3.getProperty("nombre_b").toString());
                registro.put("monto_pago", result3.getProperty("monto_pago").toString());
                registro.put("comision",  result3.getProperty("comision").toString());
                registro.put("fecha_pago", result3.getProperty("fecha_pago").toString());
                registro.put("clave_p_b", result3.getProperty("clave_p_b").toString());

                registros.add(registro);
            }

            //Crea adaptador para traer los datos a la lista
            SimpleAdapter adaptador = new SimpleAdapter(getApplicationContext(),
                    registros, R.layout.vista_gestion_spei,
                    new String[]{"pagos_speiid", "nombre_o","rfc_curp_o", "numero_cuenta_o",
                            "rfc_curp_b", "numero_cuenta_b", "ref_numerica",  "iva_pago",
                            "nombre_estado", "concepto_pago", "clave_rastreo", "nombre_b",
                            "monto_pago", "comision", "fecha_pago", "clave_p_b"},

                    new int[]{R.id.pagos_speiid, R.id.nombre_o, R.id.rfc_curp_o,
                            R.id.numero_cuenta_o, R.id.rfc_curp_b, R.id.numero_cuenta_b,
                            R.id.ref_numerica, R.id.iva_pago, R.id.nombre_estado,
                            R.id.concepto_pago, R.id.clave_rastreo, R.id.nombre_b,
                            R.id.monto_pago, R.id.comision, R.id.fecha_pago, R.id.clave_p_b});

            listaGs.setAdapter(adaptador);

            listaGs.post(new Runnable() {
                @Override
                public void run() {

                    }
            });

            listaGs.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {
                    System.out.println("datos:   " + arg0);
                    Intent intento = new Intent(getApplicationContext(),

                            FormGestionSpei.class);
                    Bundle bun = new Bundle();

                    TextView nombre_o = (TextView) arg1.findViewById(R.id.nombre_o);
                    bun.putString("nombre_o", nombre_o.getText().toString());
                    intento.putExtra("nombre_o", nombre_o.getText().toString());
                    intento.putExtras(bun);

                    intento.putExtra("nombre_o", nombre_o.getText().toString());

                    TextView rfc_curp_o = (TextView) arg1.findViewById(R.id.rfc_curp_o);
                    intento.putExtra("rfc_curp_o", rfc_curp_o.getText().toString());

                    TextView pagos_speiid = (TextView) arg1.findViewById(R.id.pagos_speiid);
                    intento.putExtra("pagos_speiid", pagos_speiid.getText().toString());

                    TextView numero_cuenta_o = (TextView) arg1.findViewById(R.id.numero_cuenta_o);
                    intento.putExtra("numero_cuenta_o", numero_cuenta_o.getText().toString());

                    TextView nombre_b = (TextView) arg1.findViewById(R.id.nombre_b);
                    intento.putExtra("nombre_b", nombre_b.getText().toString());

                    TextView rfc_curp_b = (TextView) arg1.findViewById(R.id.rfc_curp_b);
                    intento.putExtra("rfc_curp_b", rfc_curp_b.getText().toString());

                    TextView numero_cuenta_b = (TextView) arg1.findViewById(R.id.numero_cuenta_b);
                    intento.putExtra("numero_cuenta_b", numero_cuenta_b.getText().toString());

                    TextView concepto_pago = (TextView) arg1.findViewById(R.id.concepto_pago);
                    intento.putExtra("concepto_pago", concepto_pago.getText().toString());

                    TextView clave_rastreo = (TextView) arg1.findViewById(R.id.clave_rastreo);
                    intento.putExtra("clave_rastreo", clave_rastreo.getText().toString());

                    TextView ref_numerica = (TextView) arg1.findViewById(R.id.ref_numerica);
                    intento.putExtra("ref_numerica", ref_numerica.getText().toString());

                    TextView monto_pago = (TextView) arg1.findViewById(R.id.monto_pago);
                    intento.putExtra("monto_pago", monto_pago.getText().toString());

                    TextView iva_pago = (TextView) arg1.findViewById(R.id.iva_pago);
                    intento.putExtra("iva_pago", iva_pago.getText().toString());

                    TextView fecha_pago = (TextView) arg1.findViewById(R.id.fecha_pago);
                    intento.putExtra("fecha_pago", fecha_pago.getText().toString());

                    TextView clave_p_b = (TextView) arg1.findViewById(R.id.clave_p_b);
                    intento.putExtra("clave_p_b", clave_p_b.getText().toString());

                    startActivity(intento);
                }
            });

        }
        catch (Exception e){

        }
    }
}
