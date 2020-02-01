package com.soma.estadias2017.app_002;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by estadias2017 on 15/05/17.
 */

public class BuscaCvrastreo {

    private String SOAP_ACTION = "";
    private String METHOD_NAME = "nuevospei";
    private String NAMESPACE = "http://test/";
    private String URL = "http://192.168.1.118::8080/WSAplicacionBanca/PrincipalSpei?wsdl";
    //private String URL = "https://mail.reformasofipo.com/AplicacionMovilws/PrincipalSpei?wsdl";

    //Llamar servicio web

    public SoapObject invocarCv(int opc){
        SoapObject result3 = null;
        try{
            SoapObject request = new
                    SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("cliente", Globals.getCuenta());

            SoapSerializationEnvelope refer = new
                    SoapSerializationEnvelope(SoapEnvelope.VER11);
            refer.setOutputSoapObject(request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_ACTION, refer);

            result3=(SoapObject)refer.bodyIn;

        }
        catch (IOException e){
            e.printStackTrace();
        } catch (XmlPullParserException e){
            e.printStackTrace();
        }
        return result3;
    }

    public String[] cvrastreo(int opc) {

        SoapObject cv = invocarCv(opc);
        String[] cvrastreo = new
                String[cv.getPropertyCount()];

        for (int i=0;i<cv.getPropertyCount();i++)
        {
            SoapObject result2=(SoapObject)
                    cv.getProperty(i);

            String id = result2.getProperty("cvrastreo").toString();
            cvrastreo[i] = id;

        }
        return cvrastreo;
    }


    public SoapObject spscvrastreo(int cvrastreo) {
        SoapObject result3 = null;
        try {
            SoapObject request = new
                    SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("cliente", Globals.getCuenta());
            request.addProperty("cvrastreo", cvrastreo);

            SoapSerializationEnvelope cv = new
                    SoapSerializationEnvelope(SoapEnvelope.VER11);
            cv.setOutputSoapObject(request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_ACTION, cv);

            result3 = (SoapObject) cv.bodyIn;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return result3;
    }

}

