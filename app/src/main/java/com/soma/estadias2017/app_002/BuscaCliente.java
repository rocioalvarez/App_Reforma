package com.soma.estadias2017.app_002;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by estadias2017 on 5/05/17.
 */

public class BuscaCliente {

    String SOAP_ACTION = "";
    String METHOD_NAME = "bcliente";
    String NAMESPACE = "http://webservices/";
    String URL = "http://192.168.1.118:8080/AppMovilReforma/BuscaCliente_ws?wsdl";
    //String URL = "https://mail.reformasofipo.com/AplicacionMovilws/BuscaCliente_ws?wsdl";

    //Llamar servicio web
    public SoapObject invocarCliente(int opcion){
        SoapObject result = null;
        try{
            SoapObject request = new
                    SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("cliente", Globals.getCuenta());

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


    public String[] consume (int opc) {

        SoapObject clie = invocarCliente(opc);
        String[] cliente = new
                String[clie.getPropertyCount()];

        for (int i=0;i<clie.getPropertyCount();i++)
        {
            SoapObject result2=(SoapObject)
                    clie.getProperty(i);

            String id = result2.getProperty("cliente").toString();
            cliente[i] = id;

        }
        return cliente;
    }


    public SoapObject spscliente(int idcliente) {
        SoapObject result = null;
        try {
            SoapObject request = new
                    SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("cliente", Globals.getCuenta());
            request.addProperty("idcliente", idcliente);

            SoapSerializationEnvelope cli = new
                    SoapSerializationEnvelope(SoapEnvelope.VER11);
            cli.setOutputSoapObject(request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_ACTION, cli);

            result = (SoapObject) cli.bodyIn;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return result;
    }
}


