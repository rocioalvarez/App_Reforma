package com.soma.estadias2017.app_002;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by estadias2017 on 30/03/17.
 */

public class BuscaDBeneficiario {

    String SOAP_ACTION = "";
    String METHOD_NAME = "busca";
    String NAMESPACE = "http://webservices/";
    //String URL = "http://192.168.1.118:8080/AppMovilReforma/DatosBeneficiario_ws?wsdl";
    String URL = "https://mail.reformasofipo.com/AplicacionMovilws/DatosBeneficiario_ws?wsdl";

    //Llamar servicio web

    public SoapObject invocarBeneficiario(int beneficiario){
        SoapObject result = null;
        try{
            SoapObject request = new
                    SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("cliente", Globals.getCuenta());
            request.addProperty("beneficiario", beneficiario);
            SoapSerializationEnvelope bene = new
                    SoapSerializationEnvelope(SoapEnvelope.VER11);
            bene.setOutputSoapObject(request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_ACTION, bene);

            result=(SoapObject)bene.bodyIn;

        }
        catch (IOException e){
            e.printStackTrace();
        } catch (XmlPullParserException e){
            e.printStackTrace();
        }
        return result;
    }

    public String[] consumir(int opcion) {

        SoapObject bene = invocarBeneficiario(opcion);
        String[] datos = new
                String[bene.getPropertyCount()];

        for (int i=0;i<bene.getPropertyCount();i++)
        {
            SoapObject result2=(SoapObject)
                    bene.getProperty(i);

            String id = result2.getProperty("nombre").toString();
            datos[i] = id;

        }
        return datos;
    }


    public SoapObject spsbeneficiario(int id_beneficiario) {
        SoapObject result = null;
        try {
            SoapObject request = new
                    SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("cliente", Globals.getCuenta());
            request.addProperty("beneficiario", id_beneficiario);

            //request.addProperty("id_beneficiario", id_beneficiario);

            SoapSerializationEnvelope bene = new
                    SoapSerializationEnvelope(SoapEnvelope.VER11);
            bene.setOutputSoapObject(request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_ACTION, bene);

            result = (SoapObject) bene.bodyIn;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return result;
    }
}


