package com.soma.estadias2017.app_002;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.HttpsTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by SOMA-ROCIO on 04/10/2017.
 * Carga Ordenes para enviar pagos masivos
 */
public class CargaSpei extends AppCompatActivity {

    private String SOAP_ACTION ="";
    private String METHOD_NAME="validaSpei";
    private String NAMESPACE="http://test/";
    //private String URL = "http://192.168.1.118:8080/WSAplicacionBanca/WSValidaMasivoSPEIi?WSDL";
    private String URL = "https://mail.reformasofipo.com/AplicacionMovilws/WSValidaMasivoSPEIi?WSDL";

    public EditText cvoperacion;
    public Button cargar;
    Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_cargaspei);

        cvoperacion = (EditText) findViewById(R.id.cvoperacion);
        cargar = (Button) findViewById(R.id.cargar);

        //Funcion de la barra de herramientas
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        //Boton para cargar archivo
        cargar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String cvo = cvoperacion.getText().toString();
                        invocarSpei(cvo);
                    }
                }
        );
    }

    public void invocarSpei(String cvoperacion){
        try{
            SoapObject request = new SoapObject(NAMESPACE,METHOD_NAME);
            request.addProperty("cliente", Globals.getCuenta());
            request.addProperty("cvoperacion", cvoperacion);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            HttpTransportSE tra = new HttpTransportSE (URL);
            tra.call(SOAP_ACTION, envelope);
            System.out.print(tra.getServiceConnection()); //imprime en consola la conexion

            SoapObject result = (SoapObject)envelope.bodyIn;

        } catch (HttpResponseException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
