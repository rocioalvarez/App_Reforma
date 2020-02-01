package com.soma.estadias2017.app_002;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by estadias2017 on 28/03/17.
 */

public class BuscaBeneficiario {

    String SOAP_ACTION = "";
    String METHOD_NAME = "BuscaBene";
    String NAMESPACE = "http://webservices/";
    String URL = "http://192.168.1.118:8080/AppMovilReforma/BuscaBeneficiario_ws?wsdl";
    //String URL = "https://mail.reformasofipo.com/AplicacionMovilws/BuscaBeneficiario_ws?wsdl";

   //Llamar servicio web

    public SoapObject invocarBeneficiario(int opcion){
        SoapObject result = null;
        try{
            SoapObject request = new
                    SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("cliente", Globals.getCuenta());
            request.addProperty("beneficiario", Globals.getIdusuario());
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

    public ArrayList<Beneficiario> consumirBen(int opcion) {

        SoapObject bene = invocarBeneficiario(opcion);
        ArrayList<Beneficiario> datos = new ArrayList<>();

        for (int i=0;i<bene.getPropertyCount();i++)
        {
            SoapObject result2=(SoapObject)
                    bene.getProperty(i);
            Beneficiario c = new Beneficiario();
            c.setCuentaspei(result2.getProperty("cuentaspei").toString());
            c.setIdcliente(Integer.parseInt(result2.getProperty("idcliente").toString()));
            c.setInstitucion(result2.getProperty("institucion").toString());
            c.setNombre(result2.getProperty("nombre").toString());
            c.setReferencia(Integer.parseInt(result2.getProperty("referencia").toString()));
            c.setRfc(result2.getProperty("rfc").toString());
            //Log.d("Beneficiario",result2.getProperty("id_beneficiario").toString());
            datos.add(c);
        }
        return datos;
    }


}
