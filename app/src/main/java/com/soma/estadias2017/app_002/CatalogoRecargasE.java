package com.soma.estadias2017.app_002;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by SOMA-ROCIO on 11/11/2017.
 * Función: funcion catalogo, que llena el spinner carecargasmtc con el tipo de servicio de la
 * clase RecargasElectronicas.
 */

public class CatalogoRecargasE {

    String SOAP_ACTION = "";
    String METHOD_NAME = "telcel";
    String NAMESPACE = "http://webservices/";
    String URL = "http://192.168.1.118:8080/AppMovilReforma/CatalogoRecargas_ws?wsdl";
    //String URL = "https://mail.reformasofipo.com/AplicacionMovilws/CatalogoRecargas_ws?wsdl";

    public SoapObject invokRecarga(int opc){
        SoapObject result = null;
        try{
            SoapObject request = new
                    SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("opc", opc);

            SoapSerializationEnvelope recarga = new
                    SoapSerializationEnvelope(SoapEnvelope.VER11);
            recarga.setOutputSoapObject(request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_ACTION, recarga);

            result=(SoapObject)recarga.bodyIn;

        }
        catch (IOException e){
            e.printStackTrace();
        } catch (XmlPullParserException e){
            e.printStackTrace();
        }
        return result;
    }


    public ArrayList<Recargas> consum(int opc) {

        SoapObject servi = invokRecarga(opc);
        ArrayList<Recargas> datos = new ArrayList<>();

        for (int i=0;i<servi.getPropertyCount();i++)
        {
            SoapObject result2=(SoapObject)
                    servi.getProperty(i);
            Recargas re = new Recargas();
            re.setServicio(result2.getProperty("servicio").toString());
            re.setMonto(Double.parseDouble(result2.getProperty("monto").toString()));
            re.setSku(result2.getProperty("sku").toString());
            datos.add(re);
        }
        return datos;
    }
}
