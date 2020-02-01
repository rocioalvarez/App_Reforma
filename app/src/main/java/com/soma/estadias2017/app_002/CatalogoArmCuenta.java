package com.soma.estadias2017.app_002;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by SOMA-ROCIO on 06/03/2018.
 */

public class CatalogoArmCuenta {

    String SOAP_ACTION = "";
    String METHOD_NAME = "armcuenta";
    String NAMESPACE = "http://test/";
    //String URL = "http://192.168.1.118:8080/WSAplicacionBanca/ArmaCuenta?WSDL";
    String URL = "https://mail.reformasofipo.com/AplicacionMovilws/ArmaCuenta?WSDL";


    public SoapObject invoca_arm(int opc){
        SoapObject result = null;
        try{
            SoapObject request = new
                    SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("cliente", Globals.getCuenta());
            request.addProperty("servicio", servicio);
            request.addProperty("op", op);

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


/*
    public ArrayList<CuentaServ> invoca (int opc) {

        SoapObject servi = invoca_arm(opc);
        ArrayList<CuentaServ> data = new ArrayList<>();

        for (int i=0;i<servi.getPropertyCount();i++)
        {
            SoapObject result2=(SoapObject)
                    servi.getProperty(i);
            CuentaServ cu = new CuentaServ();
            cu.setFecha(result2.getProperty("fecha").toString());
            cu.setFolio(Integer.parseInt(result2.getProperty("folio").toString()));
            cu.setUrl(result2.getProperty("url").toString());
            cu.setCadena(Integer.parseInt(result2.getProperty("cadena").toString()));
            cu.setEstablecimiento(Integer.parseInt(result2.getProperty("establecimiento").toString()));
            cu.setTerminal(Integer.parseInt(result2.getProperty("terminal").toString()));
            cu.setCajero(result2.getProperty("cajero").toString());
            cu.setPass(result2.getProperty("pass").toString());
            data.add(cu);
        }
        return data;
    }
    */

    int op = 1;
    int servicio = 1;

    public SoapObject spsarmcuenta(int opc) {
        SoapObject result = null;
        try {
            SoapObject request = new
                    SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("cliente", Globals.getCuenta());
            request.addProperty("servicio", servicio);
            request.addProperty("op", op);

            SoapSerializationEnvelope arm = new
                    SoapSerializationEnvelope(SoapEnvelope.VER11);
            arm.setOutputSoapObject(request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_ACTION, arm);

            result = (SoapObject) arm.bodyIn;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return result;
    }



    int opci = 2;
    int servici = 2;

    public SoapObject arm_recarga (int opc) {
        SoapObject result = null;
        try {
            SoapObject request = new
                    SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("cliente", Globals.getCuenta());
            request.addProperty("servicio", servici);
            request.addProperty("op", opci);

            SoapSerializationEnvelope arm = new
                    SoapSerializationEnvelope(SoapEnvelope.VER11);
            arm.setOutputSoapObject(request);
            HttpTransportSE transport = new HttpTransportSE(URL);
            transport.call(SOAP_ACTION, arm);

            result = (SoapObject) arm.bodyIn;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return result;
    }
}
