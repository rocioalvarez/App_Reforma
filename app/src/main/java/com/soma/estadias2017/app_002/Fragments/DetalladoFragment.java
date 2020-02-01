package com.soma.estadias2017.app_002.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.soma.estadias2017.app_002.ActualizarBeneficiario;
import com.soma.estadias2017.app_002.AltaBeneficiario;
import com.soma.estadias2017.app_002.CatalogoCuentas;
import com.soma.estadias2017.app_002.Cuenta;
import com.soma.estadias2017.app_002.Globals;
import com.soma.estadias2017.app_002.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by estadias2017 on 12/04/17.
 */

public class DetalladoFragment extends Fragment {

    private ListView li_movimiento;
    private Button btn_consultar;
    Toolbar toolbar;
    Spinner sp_movimiento;

    //Consumir Web Service
    String SOAP_ACTION = "";
    String METHOD_NAME = "sdetallado";
    String NAMESPACE = "http://webservices/";
    String URL = "http://192.168.1.81:8081/AppMovilReforma/SaldoDetallado_ws?wsdl";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_detallado, container, false);

        li_movimiento = (ListView) view.findViewById(R.id.li_movimiento);
        btn_consultar = (Button) view.findViewById(R.id.btn_consultar);


        InvocaSDetallado("V2");

        return view;
    }

    //Metodo que invoca
    private void InvocaSDetallado(String movimiento){
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("cliente", Globals.getCuenta());
            request.addProperty("movimiento", movimiento);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);// utilizar la version que corresponda:11 o 12
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.call(SOAP_ACTION, envelope);

            SoapObject resp = (SoapObject) envelope.bodyIn;

            List<HashMap<String,String>> regi = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> regis;

            for (int i =0; i<resp.getPropertyCount(); i++){
                SoapObject object = (SoapObject) resp.getProperty(i);

                String a = object.getProperty("fecha").toString();
                String b = object.getProperty("deposito").toString();
                String c = object.getProperty("retiro").toString();
                String d = object.getProperty("descripcion").toString();
                String e = object.getProperty("referencia").toString();
                System.out.print(a+b+c+d);

                regis = new HashMap<String, String>();
                regis.put("fecha", a);
                regis.put("deposito", b);
                regis.put("retiro", c);
                regis.put("descripcion", d);
                regis.put("referencia", e);

                regi.add(regis);
            }

            //Adaptador para traer los datos a la lista
            SimpleAdapter adaptador = new SimpleAdapter(getContext(),
                    regi, R.layout.vista_saldo_detallado,
                    new String[]{"fecha", "deposito", "retiro", "descripcion", "referencia"},
                    new int[]{R.id.fecha,R.id.deposito,  R.id.retiro, R.id.descripcion,
                            R.id.referencia});

            li_movimiento.setAdapter(adaptador);
        }

        catch (Exception e){
        }
    }

}