package com.soma.estadias2017.app_002;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by estadias2017 on 9/05/17.
 */

public class NuevaReferencia {

    String SOAP_ACTION = "";
    String METHOD_NAME = "spsreferencia";
    String NAMESPACE = "http://test/";
    String URL = "http://192.168.1.118:8080/WSAplicacionBanca/WSReferenciaSPEI?WSDL";
    //String URL = "https://mail.reformasofipo.com/AplicacionMovilws/WSReferenciaSPEI?WSDL";

    //Llamar servicio web
    public SoapObject invocarCliente(int referencia){
        SoapObject result = null;
        try{
            SoapObject request = new
                    SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("cliente", Globals.getCuenta());
            request.addProperty("referencia", referencia);

            SoapSerializationEnvelope ref = new
                    SoapSerializationEnvelope(SoapEnvelope.VER11);
            ref.setOutputSoapObject(request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_ACTION, ref);

            result=(SoapObject)ref.bodyIn;

        }
        catch (IOException e){
            e.printStackTrace();
        } catch (XmlPullParserException e){
            e.printStackTrace();
        }
        return result;
    }


    public ArrayList<Referencia> consumirRef(int referencia) {

        SoapObject cliente = invocarCliente(referencia);
        ArrayList<Referencia> dato = new ArrayList<>();

        for (int i=0;i<cliente.getPropertyCount();i++)
        {
            SoapObject result2=(SoapObject)
                    cliente.getProperty(i);
            Referencia r = new Referencia();
            r.setReferencia(result2.getProperty("referencia").toString());

            dato.add(r);
        }
        return dato;
    }


    public SoapObject spsreferencia(String referencia) {
        SoapObject result = null;
        try {
            SoapObject request = new
                    SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("cliente", Globals.getCuenta());
            request.addProperty("referencia", referencia);

            //request.addProperty("id_beneficiario", id_beneficiario);

            SoapSerializationEnvelope cliente = new
                    SoapSerializationEnvelope(SoapEnvelope.VER11);
            cliente.setOutputSoapObject(request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_ACTION, cliente);

            result = (SoapObject) cliente.bodyIn;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return result;
    }
}
