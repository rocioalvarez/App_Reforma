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

public class TipoParticipante {

    String SOAP_ACTION = "";
    String METHOD_NAME = "Participante";
    String NAMESPACE = "http://webservices/";
    //String URL = "http://192.168.1.118:8080/AppMovilReforma/TipoParticipante_ws?wsdl";
    String URL = "https://mail.reformasofipo.com/AplicacionMovilws/TipoParticipante_ws?wsdl";


    public SoapObject invocarCuenta(int opcion){
        SoapObject result = null;
        try{
            SoapObject request = new
                    SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("numcliente", Globals.getCuenta());

            request.addProperty("catalogo", opcion);
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

        SoapObject cuent = invocarCuenta(opcion);
        String[] dato = new
                String[cuent.getPropertyCount()];

        for (int i=0;i<cuent.getPropertyCount();i++)
        {
            SoapObject result2=(SoapObject)
                    cuent.getProperty(i);

            String catalogos = result2.getProperty("descripcion").toString();
            dato[i] = catalogos;

        }
        return dato;
    }


    public ArrayList<TCuenta> consumir3(int opcion) {

        SoapObject cuent = invocarCuenta(opcion);
        ArrayList<TCuenta> dato = new ArrayList<>();

        for (int i=0;i<cuent.getPropertyCount();i++)
        {
            SoapObject result2=(SoapObject)
                    cuent.getProperty(i);
            TCuenta cu = new TCuenta();
            cu.setCatalogos(result2.getProperty("catalogos").toString());
            cu.setDescripcion(result2.getProperty("descripcion").toString());
            dato.add(cu);
        }
        return dato;
    }
}

