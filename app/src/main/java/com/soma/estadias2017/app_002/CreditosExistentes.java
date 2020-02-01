package com.soma.estadias2017.app_002;

import android.os.Bundle;
import android.os.StrictMode;

import com.soma.estadias2017.app_002.CreditosE;
import com.soma.estadias2017.app_002.Globals;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by estadias2017 on 27/03/17.
 *
 * Funcion: Poblar Spinner "Credito a depositar" - submodulo Abono a Creditos
 */

public class CreditosExistentes {

    String SOAP_ACTION = "";
    String METHOD_NAME = "Creditos";
    String NAMESPACE = "http://webservices/";
    String URL = "http://192.168.1.118:8080/AppMovilReforma/CreditosExistentes_ws?wsdl";
    //String URL = "https://mail.reformasofipo.com/AplicacionMovilws/CreditosExistentes_ws?wsdl";

//Llamar servicio web
    public SoapObject invocarCreditos(int id){
        SoapObject result = null;
        try{
            SoapObject request = new
                    SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("cliente",Globals.getCuenta());
            request.addProperty("idcliente",Globals.getIdusuario());
            request.addProperty("opcion", id);

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


    public String[] consumir(int id) {

        SoapObject cuent = invocarCreditos(id);
        String[] dato = new
                String[cuent.getPropertyCount()];

        for (int i=0;i<cuent.getPropertyCount();i++)
        {
            SoapObject result2=(SoapObject)
                    cuent.getProperty(i);

            String creditoid = result2.getProperty("referencia").toString();
            dato[i] = creditoid;

        }
        return dato;
    }


    public ArrayList<CreditosE> consumir2(int opcion) {

        SoapObject cuent = invocarCreditos(opcion);
        ArrayList<CreditosE> dato = new ArrayList<>();

        for (int i=0;i<cuent.getPropertyCount();i++)
        {
            SoapObject result2=(SoapObject)
                    cuent.getProperty(i);
            CreditosE cr = new CreditosE();
            cr.setId_credito(Integer.parseInt(result2.getProperty("creditoid").toString()));
            cr.setReferencia(result2.getProperty("referencia").toString());
            dato.add(cr);
        }
        return dato;
    }
}