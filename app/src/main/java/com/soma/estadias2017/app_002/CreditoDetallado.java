package com.soma.estadias2017.app_002;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.widget.Spinner;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalHashtable;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;


import static com.soma.estadias2017.app_002.R.id.creditoid;
/**
 * Created by estadias2017 on 25/03/17.
 *
 * Función: poblar Spinner con el Credito Detallado
 */

public class CreditoDetallado  {

    String SOAP_ACTION = "";
    String METHOD_NAME = "Detallado";
    String NAMESPACE = "http://webservices/";
    String URL = "http://192.168.1.118:8080/AppMovilReforma/CreditoDetallado_ws?WSDL";
    //String URL = "https://mail.reformasofipo.com/AplicacionMovilws/CreditoDetallado_ws?WSDL";

    //Llamar servicio web
    public SoapObject invocarCredito(int idcredito) {
        SoapObject result = null;
        try {
            SoapObject request = new
                    SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("cliente", Globals.getCuenta());
            request.addProperty("idcredito", idcredito);

            SoapSerializationEnvelope credi = new
                    SoapSerializationEnvelope(SoapEnvelope.VER11);
            credi.setOutputSoapObject(request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_ACTION, credi);

            result = (SoapObject) credi.bodyIn;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return result;
    }


    public String[] consumir(int idcredito) {

        SoapObject credi = invocarCredito(idcredito);
        String[] datos = new
                String[credi.getPropertyCount()];

        for (int i = 0; i < credi.getPropertyCount(); i++) {
            SoapObject result2 = (SoapObject)
                    credi.getProperty(i);

            String id = result2.getProperty("idcredito").toString();
            datos[i] = id;

        }
        return datos;

    }


    public ArrayList<CreditoD> consume (int idcredito){

        SoapObject credito = invocarCredito(idcredito);
        ArrayList<CreditoD> datos = new ArrayList<>();

        for (int i = 0; i < credito.getPropertyCount(); i++) {
            SoapObject result2 = (SoapObject)
                    credito.getProperty(i);
            CreditoD c = new CreditoD();
            c.setTotal(Integer.parseInt(result2.getProperty("total").toString()));
            c.setCapital(Integer.parseInt(result2.getProperty("capital").toString()));
            c.setInteres(Integer.parseInt(result2.getProperty("interes").toString()));
            c.setMoratorio(Integer.parseInt(result2.getProperty("moratorio").toString()));
            c.setIva(Integer.parseInt(result2.getProperty("iva").toString()));

                datos.add(c);
            }
            return datos;
        }


    public SoapObject spsabonosCredito(int idcredito) {
        SoapObject result = null;
        try {
            SoapObject request = new
                    SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("cliente", Globals.getCuenta());
            request.addProperty("idcredito", idcredito);

            SoapSerializationEnvelope credi = new
                    SoapSerializationEnvelope(SoapEnvelope.VER11);
            credi.setOutputSoapObject(request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_ACTION, credi);

            result = (SoapObject) credi.bodyIn;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return result;
    }
}



