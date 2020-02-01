package com.soma.estadias2017.app_002;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by estadias2017 on 7/06/17.
 */

public class CatalogoEstatus {

    String SOAP_ACTION = "";
    String METHOD_NAME = "spsestatus";
    String NAMESPACE = "http://webservices/";
    //String URL = "http://192.168.1.118:8080/AppMovilReforma/CatalogoEstatus_ws?wsdl";
    String URL = "https://mail.reformasofipo.com/AplicacionMovilws/CatalogoEstatus_ws?wsdl";

    //Llama servicio web
    public SoapObject invocarStatus(int estatus){
        SoapObject result = null;
        try{
            SoapObject request = new
                    SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("estatus", estatus);

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


    public String[] consumir(int estatus) {

        SoapObject status = invocarStatus(estatus);
        String[] datost = new
                String[status.getPropertyCount()];

        for (int i=0;i<status.getPropertyCount();i++)
        {
            SoapObject result2=(SoapObject)
                    status.getProperty(i);

            String id = result2.getProperty("descripcion").toString();
            datost[i] = id;

        }
        return datost;
    }


    public ArrayList<CEstatus> consumir2(int estatus) {

        SoapObject status = invocarStatus(estatus);
        ArrayList<CEstatus> datost = new ArrayList<>();

        for (int i=0;i<status.getPropertyCount();i++)
        {
            SoapObject result2=(SoapObject)
                    status.getProperty(i);
            CEstatus c = new CEstatus();
            c.setCatalogos(result2.getProperty("catalogos").toString());
            c.setDescripcion(result2.getProperty("descripcion").toString());
            datost.add(c);
        }
        return datost;
    }
}
