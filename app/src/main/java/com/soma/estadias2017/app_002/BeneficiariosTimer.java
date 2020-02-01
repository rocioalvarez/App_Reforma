package com.soma.estadias2017.app_002;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by estadias2017 on 13/06/17.
 */

public class BeneficiariosTimer {

    String SOAP_ACTION = "";
    String METHOD_NAME = "spsbtimer";
    String NAMESPACE = "http://webservices/";
    String URL = "http://192.168.1.118:8080/AppMovilReforma/BeneficiariosTimer_ws?wsdl";
    //String URL = "https://mail.reformasofipo.com/AplicacionMovilws/BeneficiariosTimer_ws?wsdl";


    //Llama servicio web
    public SoapObject invocarTimer(int opcion){
        SoapObject result = null;
        try{
            SoapObject request = new
                    SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("cliente", Globals.getCuenta());
            request.addProperty("idcliente", Globals.getIdusuario());
            SoapSerializationEnvelope timer = new
                    SoapSerializationEnvelope(SoapEnvelope.VER11);
            timer.setOutputSoapObject(request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_ACTION, timer);

            result=(SoapObject)timer.bodyIn;

        }
        catch (IOException e){
            e.printStackTrace();
        } catch (XmlPullParserException e){
            e.printStackTrace();
        }
        return result;
    }

    public String[] consumir(int opcion) {

        SoapObject timer = invocarTimer(opcion);
        String[] datos = new
                String[timer.getPropertyCount()];

        for (int i=0;i<timer.getPropertyCount();i++)
        {
            SoapObject result2=(SoapObject)
                    timer.getProperty(i);

            String id = result2.getProperty("nombre").toString();
            datos[i] = id;

        }
        return datos;
    }


    public ArrayList<BTimer> consumirb (int opcion) {

        SoapObject bene = invocarTimer(opcion);
        ArrayList<BTimer> datos = new ArrayList<>();

        for (int i=0;i<bene.getPropertyCount();i++)
        {
            SoapObject result2=(SoapObject)
                    bene.getProperty(i);
            BTimer c = new BTimer();
            c.setId_beneficiario(Integer.parseInt(result2.getProperty(
                    "id_beneficiario").toString()));
            c.setNombreBeneficiario(result2.getProperty(
                    "nombreBeneficiario").toString());
            datos.add(c);
            }

        return datos;
    }

}
