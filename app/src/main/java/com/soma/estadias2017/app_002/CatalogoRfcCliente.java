package com.soma.estadias2017.app_002;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by SOMA-ROCIO on 09/10/2017.
 * Funcion: Catalogo que llena los campos Nombre y Rfc de la clase PagoServicios
 */

public class CatalogoRfcCliente {

    String SOAP_ACTION = "";
    String METHOD_NAME = "rfc_beneficiario";
    String NAMESPACE = "http://webservices/";
    String URL = "http://192.168.1.118:8080/AppMovilReforma/RfcCliente_ws?wsdl";
    //String URL = "https://mail.reformasofipo.com/AplicacionMovilws/RfcCliente_ws?wsdl";


    // *** Funcion Soap para indicar los parametros que requiere el web service *** //
    public SoapObject invokeCatalog(int idcliente){
        SoapObject result = null;
        try{
            SoapObject request = new
                    SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("beneficiario", Globals.getCuenta());

            SoapSerializationEnvelope client = new
                    SoapSerializationEnvelope(SoapEnvelope.VER11);
            client.setOutputSoapObject(request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_ACTION, client);

            result=(SoapObject)client.bodyIn;

        }
        catch (IOException e){
            e.printStackTrace();
        } catch (XmlPullParserException e){
            e.printStackTrace();
        }
        return result;
    }

    // *** ArrayList que llena automaticamente los campos de nombre y rfc de la clase PagoServicios *** //
    public ArrayList<RfcCliente> consum (int opcion) {

        SoapObject client = invokeCatalog (opcion);
        ArrayList<RfcCliente> dato = new ArrayList<>();

        for (int i=0;i<client.getPropertyCount();i++)
        {
            SoapObject result2=(SoapObject)
                    client.getProperty(i);
            RfcCliente c = new RfcCliente();
            c.setIdcliente(Integer.parseInt(result2.getProperty("idcliente").toString()));
            c.setNombre(result2.getProperty("nombre").toString());
            c.setReferencia(Integer.parseInt(result2.getProperty("referencia").toString()));
            c.setRfc(result2.getProperty("rfc").toString());
            dato.add(c);
        }
        return dato;
    }

/*
    public SoapObject spscliente(int opcion) {
        SoapObject result = null;
        try {
            SoapObject request = new
                    SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("beneficiario", Globals.getCuenta());

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
*/

}
