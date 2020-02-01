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
 * Created by estadias2017 on 27/03/17.
 */

public class CatalogoPlazo {


    String SOAP_ACTION = "";
    String METHOD_NAME = "CatalogoP";
    String NAMESPACE = "http://webservices/";
    String URL = "http://192.168.1.118:8080/AppMovilReforma/CatalogoPlazo_ws?wsdl";
    //String URL = "https://mail.reformasofipo.com/AplicacionMovilws/CatalogoPlazo_ws?wsdl";

      //Llama servicio web

    public SoapObject invocarPlazo(int opcion){
        SoapObject result = null;
        try{
            SoapObject request = new
                    SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("paccion", opcion);
            SoapSerializationEnvelope pla = new
                    SoapSerializationEnvelope(SoapEnvelope.VER11);
            pla.setOutputSoapObject(request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_ACTION, pla);

            result=(SoapObject)pla.bodyIn;

        }
        catch (IOException e){
            e.printStackTrace();
        } catch (XmlPullParserException e){
            e.printStackTrace();
        }
        return result;
    }

    public String[] consumir(int opcion) {

        SoapObject pla = invocarPlazo(opcion);
        String[] datos = new
                String[pla.getPropertyCount()];


        for (int i=0;i<pla.getPropertyCount();i++)
        {
            SoapObject result2=(SoapObject)
                    pla.getProperty(i);

            String id = result2.getProperty("descripcion").toString();
            datos[i] = id;

        }
        return datos;
    }

    public ArrayList<Plazo> consumir2(int opcion) {

        SoapObject pla = invocarPlazo(opcion);
        ArrayList<Plazo> datos = new ArrayList<>();


        for (int i=0;i<pla.getPropertyCount();i++)
        {
            SoapObject result2=(SoapObject)
                    pla.getProperty(i);
            Plazo p = new Plazo();
            p.setPlazo(result2.getProperty("plazo").toString());
            p.setDescripcion(result2.getProperty("descripcion").toString());
            datos.add(p);
        }
        return datos;
    }


    public SoapObject spsPlazo(int idcliente) {
        SoapObject result = null;
        try {
            SoapObject request = new
                    SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("cliente", Globals.getCuenta());
            request.addProperty("idcliente", Globals.getIdusuario());

            SoapSerializationEnvelope plaz = new
                    SoapSerializationEnvelope(SoapEnvelope.VER11);
            plaz.setOutputSoapObject(request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_ACTION, plaz);

            result = (SoapObject) plaz.bodyIn;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return result;
    }
}