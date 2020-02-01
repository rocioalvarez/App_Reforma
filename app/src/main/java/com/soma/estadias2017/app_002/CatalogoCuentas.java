package com.soma.estadias2017.app_002;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by estadias2017 on 23/03/17.
 */


public class CatalogoCuentas extends AppCompatActivity {

    String SOAP_ACTION = "";
    String METHOD_NAME = "CatalogoC";
    String NAMESPACE = "http://webservices/";
    //String URL = "http://192.168.1.118:8080/AppMovilReforma/CatalogoCuentas_ws?wsdl";
    String URL = "https://mail.reformasofipo.com/AplicacionMovilws/CatalogoCuentas_ws?wsdl";

    /*
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }*/

    public SoapObject invocarCuentas(int opcion){
        SoapObject result = null;
        try{
            SoapObject request = new
                    SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("paccion", opcion);
            SoapSerializationEnvelope cuent = new
                    SoapSerializationEnvelope(SoapEnvelope.VER11);
            cuent.setOutputSoapObject(request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_ACTION, cuent);

            result=(SoapObject)cuent.bodyIn;

        }
        catch (IOException e){
            e.printStackTrace();
        } catch (XmlPullParserException e){
            e.printStackTrace();
        }
        return result;
    }

    public String[] consumir(int opcion) {

        SoapObject cuent = invocarCuentas(opcion);
        String[] datos = new
                String[cuent.getPropertyCount()];


        for (int i=0;i<cuent.getPropertyCount();i++)
        {
            SoapObject result2=(SoapObject)
                    cuent.getProperty(i);

            String id = result2.getProperty("descripcion").toString();
            datos[i] = id;

        }
        return datos;
    }

    public ArrayList<Cuenta> consumir2(int opcion) {

        SoapObject cuent = invocarCuentas(opcion);
        ArrayList<Cuenta> datos = new ArrayList<>();

        for (int i=0;i<cuent.getPropertyCount();i++)
        {
            SoapObject result2=(SoapObject)
                    cuent.getProperty(i);
            Cuenta c = new Cuenta();
            c.setIdcuenta(result2.getProperty("idcuenta").toString());
            c.setDescripcion(result2.getProperty("descripcion").toString());
            datos.add(c);
        }
        return datos;
    }
}



