package com.soma.estadias2017.app_002;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by estadias2017 on 5/05/17.
 */

public class BuscaReferencia {

    String SOAP_ACTION = "";
    String METHOD_NAME = "spsreferencia";
    String NAMESPACE = "http://webservices/";
    //String URL = "http://192.168.1.118:8080/AppMovilReforma/ReferenciaSPEI_ws?wsdl";
    String URL = "https://mail.reformasofipo.com/AplicacionMovilws/ReferenciaSPEI_ws?wsdl";

    //Llamar servicio web

    public SoapObject invocarReferencia(int opc){
        SoapObject result = null;
        try{
            SoapObject request = new
                    SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("cliente", Globals.getCuenta());

            SoapSerializationEnvelope refer = new
                    SoapSerializationEnvelope(SoapEnvelope.VER11);
            refer.setOutputSoapObject(request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_ACTION, refer);

            result=(SoapObject)refer.bodyIn;

        }
        catch (IOException e){
            e.printStackTrace();
        } catch (XmlPullParserException e){
            e.printStackTrace();
        }
        return result;
    }

    public String[] consumir(int opc) {

        SoapObject refer = invocarReferencia(opc);
        String[] referencia = new
                String[refer.getPropertyCount()];

        for (int i=0;i<refer.getPropertyCount();i++)
        {
            SoapObject result2=(SoapObject)
                    refer.getProperty(i);

            String id = result2.getProperty("referencia").toString();
            referencia[i] = id;

        }
        return referencia;
    }


    public SoapObject spsreferencia(int id_cliente) {
        SoapObject result = null;
        try {
            SoapObject request = new
                    SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("cliente", Globals.getCuenta());
            request.addProperty("id_cliente", id_cliente);

            SoapSerializationEnvelope ref = new
                    SoapSerializationEnvelope(SoapEnvelope.VER11);
            ref.setOutputSoapObject(request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_ACTION, ref);

            result = (SoapObject) ref.bodyIn;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return result;
    }

}
