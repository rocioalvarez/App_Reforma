package com.soma.estadias2017.app_002;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by estadias2017 on 19/06/17.
 */

public class ClaveRastreo {

    private String SOAP_ACTION = "";
    private String METHOD_NAME = "sps_cvrastreo";
    private String NAMESPACE = "http://webservices/";
    private String URL = "http://192.168.1.118:8080/AppMovilReforma/CvRastreo_ws?wsdl";
    //private String URL = "https://mail.reformasofipo.com/AplicacionMovilws/CvRastreo_ws?wsdl";


    public SoapObject invocarCve(int opc){
        SoapObject result = null;
        try{
            SoapObject request = new
                    SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("cliente", Globals.getCuenta());

            SoapSerializationEnvelope cve = new
                    SoapSerializationEnvelope(SoapEnvelope.VER11);
            cve.setOutputSoapObject(request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_ACTION, cve);

            result=(SoapObject)cve.bodyIn;

        }
        catch (IOException e){
            e.printStackTrace();
        } catch (XmlPullParserException e){
            e.printStackTrace();
        }
        return result;
    }


    public String[] consumir(int opc) {

        SoapObject cve = invocarCve(opc);
        String[] clave = new
                String[cve.getPropertyCount()];

        for (int i=0;i<cve.getPropertyCount();i++)
        {
            SoapObject result2=(SoapObject)
                    cve.getProperty(i);

            String id = result2.getProperty("clave").toString();
            clave[i] = id;

        }
        return clave;
    }


    public SoapObject spscvrastreo (int id_cliente) {
        SoapObject resulto = null;
        try {
            SoapObject request = new
                    SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("cliente", Globals.getCuenta());
            //request.addProperty("id_cliente", id_cliente);

            SoapSerializationEnvelope clav = new
                    SoapSerializationEnvelope(SoapEnvelope.VER11);
            clav.setOutputSoapObject(request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_ACTION, clav);

            resulto = (SoapObject) clav.bodyIn;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return resulto;
    }
}
