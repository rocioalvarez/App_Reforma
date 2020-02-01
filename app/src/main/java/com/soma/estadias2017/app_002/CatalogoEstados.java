package com.soma.estadias2017.app_002;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by SOMA-ROCIO on 13/11/2017.
 * función: funcion catalogo, llena el spinner cataestados de la clase RecargasElectronicas.
 */

public class CatalogoEstados {

    String SOAP_ACTION = "";
    String METHOD_NAME = "estados";
    String NAMESPACE = "http://webservices/";
    String URL = "http://192.168.1.118:8080/AppMovilReforma/CatalogoEstados_ws?wsdl";
    //String URL = "https://mail.reformasofipo.com/AplicacionMovilws/CatalogoEstados_ws?wsdl";



    public SoapObject invocaEst(int opcion){
        SoapObject result = null;
        try{
            SoapObject request = new
                    SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("opcion", opcion);

            SoapSerializationEnvelope estados = new
                    SoapSerializationEnvelope(SoapEnvelope.VER11);
            estados.setOutputSoapObject(request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_ACTION, estados);

            result=(SoapObject)estados.bodyIn;

        }
        catch (IOException e){
            e.printStackTrace();
        } catch (XmlPullParserException e){
            e.printStackTrace();
        }
        return result;

    }

    public String[] consumir(int opcion) {

        SoapObject estados = invocaEst(opcion);
        String[] datos = new
                String[estados.getPropertyCount()];

        for (int i=0;i<estados.getPropertyCount();i++)
        {
            SoapObject result=(SoapObject)
                    estados.getProperty(i);

            String est = result.getProperty("descripcion").toString();
            datos[i] = est;

        }
        return datos;
    }


    public ArrayList<Estados> consumeEst(int opcion) {

        SoapObject estados = invocaEst(opcion);
        ArrayList<Estados> datos = new ArrayList<>();

        for (int i=0;i<estados.getPropertyCount();i++)
        {
            SoapObject result2=(SoapObject)
                    estados.getProperty(i);
            Estados es = new Estados();
            es.setServicio(result2.getProperty("servicio").toString());
            es.setMonto(Double.parseDouble(result2.getProperty("monto").toString()));
            es.setDescripcion(result2.getProperty("descripcion").toString());
            datos.add(es);
        }
        return datos;
    }
}
