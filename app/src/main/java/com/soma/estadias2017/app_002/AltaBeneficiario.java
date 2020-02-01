package com.soma.estadias2017.app_002;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 * Created by estadias2017 on 18/02/17.
 *
 * Funcion: dar de Alta un Beneficiario
 */

public class AltaBeneficiario extends AppCompatActivity {


    public EditText nombreBeneficiario, satRC, beActivo, cuenta, estatusC, paccion, idcuenta;
    private Button guardarBe;
    public TextView resultado;
    Toolbar toolbar;
    Spinner tipoCuenta, catalogos, estatus;

    //Consumir Web Service
    String SOAP_ACTION = "";
    String METHOD_NAME = "AltaBeneficiario";
    String NAMESPACE = "http://webservices/";
    //String URL = "http://192.168.1.118:8080/AppMovilReforma/AltaBeneficiario_ws?wsdl";
    String URL = "https://mail.reformasofipo.com/AplicacionMovilws/AltaBeneficiario_ws?wsdl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_alta_beneficiario);
        resultado = (TextView) findViewById(R.id.resultado);
        nombreBeneficiario = (EditText) findViewById(R.id.nombreBeneficiario);
        satRC = (EditText) findViewById(R.id.satRC);
        cuenta = (EditText) findViewById(R.id.cuenta);
        estatusC = (EditText) findViewById(R.id.estatusC);
        paccion = (EditText) findViewById(R.id.paccion);
        idcuenta = (EditText) findViewById(R.id.idcuenta);
        guardarBe = (Button) findViewById(R.id.btn_fin_altab);


        //Arreglo para llenar Spinner tipoCuenta
        TipoCuenta cuentas = new TipoCuenta();

        ArrayList<TCuenta> datos = cuentas.consumir2(1);
        final ArrayAdapter<TCuenta> adapter = new ArrayAdapter<TCuenta>(this,
                android.R.layout.simple_spinner_item, datos);

        tipoCuenta = (Spinner) findViewById(R.id.tipoCuenta);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        tipoCuenta.setAdapter(adapter);

        //Arreglo2 para llenar Spinner catalogos
        TipoParticipante cuent = new TipoParticipante();

        ArrayList<TCuenta> dato = cuent.consumir3(1);
        final ArrayAdapter<TCuenta> adapter1 = new ArrayAdapter<TCuenta>(this,
                android.R.layout.simple_spinner_item, dato);

        catalogos = (Spinner) findViewById(R.id.catalogos);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        catalogos.setAdapter(adapter1);

        //Arreglo para llenar el combo de estatus
        CatalogoEstatus status = new CatalogoEstatus();

        ArrayList<CEstatus> datost = status.consumir2(1);
        final ArrayAdapter<CEstatus> adapter0 = new ArrayAdapter<CEstatus>(this,
                android.R.layout.simple_spinner_item, datost);

        estatus = (Spinner) findViewById(R.id.estatus);
        adapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        estatus.setAdapter(adapter0);


        //Mensaje que muestra al abrir el formulario
        Toast toast1 =
                Toast.makeText(getApplicationContext(),
                        "Sigue los pasos para dar de Alta un Beneficiario", Toast.LENGTH_SHORT);
        toast1.show();

        //Funcion de la barra de herramientas
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        //Boton que almacena los datos
        guardarBe.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {

                        TCuenta cu = adapter.getItem(tipoCuenta.getSelectedItemPosition());
                        int id = 0;
                        String nom_b = nombreBeneficiario.getText().toString();
                        String rfc = satRC.getText().toString();
                        int beActivo = 1;
                        TCuenta ca = adapter1.getItem(catalogos.getSelectedItemPosition());
                        String cuen = cuenta.getText().toString();
                        String idc = idcuenta.getText().toString();
                        int estatusC = 1;
                        int paccion = 1;
                        CEstatus sta = adapter0.getItem(estatus.getSelectedItemPosition());

                        System.out.println("cuenta " + cu.getCatalogos() + " DES " +
                                cu.getDescripcion()
                        );

                        if (nom_b.isEmpty()){
                            nombreBeneficiario.setError("Campo vacio");
                            nombreBeneficiario.requestFocus();
                        }else if (rfc.isEmpty()){
                            satRC.setError("Campo vacio");
                            satRC.requestFocus();
                        }else if (cuen.isEmpty()){
                            cuenta.setError("Campo vacio");
                            cuenta.requestFocus();
                        }else if (idc.isEmpty()){
                            idcuenta.setError("Campo vacio");
                            idcuenta.requestFocus();
                        }else {

                            //Declarar metodo para invocar los datos
                            InvocarAl(id, nom_b, rfc, beActivo, cu.getCatalogos(), ca.getCatalogos(),
                                    cuen, estatusC, paccion, sta.getCatalogos());

                            // Algoritmo para limpiar los EditText después de guardar.
                            tipoCuenta.setAdapter(adapter);
                            nombreBeneficiario.setText("");
                            satRC.setText("");
                            catalogos.setAdapter(adapter1);
                            cuenta.setText("");
                            idcuenta.setText("");
                        }

                    }
                });

    }

    //Metodo
    private void InvocarAl(int id, String nombreBeneficiario, String satRC, int beActivo,
                           String tipoCuenta, String catalogos, String cuenta, int estatusC,
                           int paccion, String estatus) {

        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            Request.addProperty("numcliente", Globals.getCuenta());
            Request.addProperty("idcliente", Globals.getIdusuario());
            Request.addProperty("id", id);
            Request.addProperty("nombreBeneficiario", nombreBeneficiario);
            Request.addProperty("satRC", satRC);
            Request.addProperty("beActivo", beActivo);
            Request.addProperty("tipoCuenta", tipoCuenta);
            Request.addProperty("catalogos", catalogos);
            Request.addProperty("cuenta", cuenta);
            Request.addProperty("estatusC", estatusC);
            Request.addProperty("paccion", paccion);
            Request.addProperty("estatus", estatus);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11); // utilizar la version que corresponda:11 o 12
            envelope.setOutputSoapObject(Request);

            HttpTransportSE tra = new HttpTransportSE(URL);
            tra.call(SOAP_ACTION, envelope);

            SoapObject respuesta = (SoapObject) envelope.bodyIn;

            String r = respuesta.getProperty(1).toString();
            resultado.setText(r); //imprimir resultado
            Toast.makeText(this, r, Toast.LENGTH_LONG).show();


        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
