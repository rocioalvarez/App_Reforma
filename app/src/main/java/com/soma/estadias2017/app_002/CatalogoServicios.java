package com.soma.estadias2017.app_002;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by SOMA-YAZMIN on 01/11/2017.
 */

public class CatalogoServicios {

    String SOAP_ACTION = "";
    String METHOD_NAME = "catalogServicio";
    String NAMESPACE = "http://webservices/";
    String URL = "http://192.168.1.118:8080/AppMovilReforma/CatalogoServicios_ws?wsdl";
    //String URL = "https://mail.reformasofipo.com/AplicacionMovilws/CatalogoServicios_ws?wsdl";

    public SoapObject invokServicio(int idcliente){
        SoapObject result = null;
        try{
            SoapObject request = new
                    SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("idcliente", idcliente);

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


    public ArrayList<Servicios> consumir1(int idcliente) {

        SoapObject servi = invokServicio(idcliente);
        ArrayList<Servicios> data = new ArrayList<>();

        for (int i=0;i<servi.getPropertyCount();i++)
        {
            SoapObject resul=(SoapObject)
                    servi.getProperty(i);
            Servicios cu = new Servicios();
            cu.setServicio(resul.getProperty("servicio").toString());
            cu.setMonto(Double.parseDouble(resul.getProperty("monto").toString()));
            cu.setDescripcion(resul.getProperty("descripcion").toString());
            cu.setSku(resul.getProperty("sku").toString());
            data.add(cu);
        }
        return data;
    }
}
