package com.soma.estadias2017.app_002;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by estadias2017 on 28/03/17.
 */

public class TipoCuenta {

    String SOAP_ACTION = "";
    String METHOD_NAME = "TipoC";
    String NAMESPACE = "http://webservices/";
    //String URL = "http://192.168.1.118:8080/AppMovilReforma/TipoCuenta_ws?wsdl";
    String URL = "https://mail.reformasofipo.com/AplicacionMovilws/TipoCuenta_ws?wsdl";


    public SoapObject invocarCuenta(int catalogo){
        SoapObject result = null;
        try{
            SoapObject request = new
                    SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("numcliente", Globals.getCuenta());
            request.addProperty("catalogo", catalogo);

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


    public String[] consumir(int catalogo) {

        SoapObject cuent = invocarCuenta(catalogo);
        String[] datos = new
                String[cuent.getPropertyCount()];

        for (int i=0;i<cuent.getPropertyCount();i++)
        {
            SoapObject result2=(SoapObject)
                    cuent.getProperty(i);

            String catalogos = result2.getProperty("descripcion").toString();
            datos[i] = catalogos;

        }
        return datos;
    }


    public ArrayList<TCuenta> consumir2(int catalogo) {

        SoapObject cuent = invocarCuenta(catalogo);
        ArrayList<TCuenta> datos = new ArrayList<>();

        for (int i=0;i<cuent.getPropertyCount();i++)
        {
            SoapObject result2=(SoapObject)
                    cuent.getProperty(i);
            TCuenta cu = new TCuenta();
            cu.setCatalogos(result2.getProperty("catalogos").toString());
            cu.setDescripcion(result2.getProperty("descripcion").toString());
            datos.add(cu);
        }
        return datos;
    }
}
