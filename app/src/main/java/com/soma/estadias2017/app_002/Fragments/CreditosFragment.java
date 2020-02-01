package com.soma.estadias2017.app_002.Fragments;

/**
 * Created by estadias2017 on 9/03/17.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.soma.estadias2017.app_002.AbonoCreditos;
import com.soma.estadias2017.app_002.Globals;
import com.soma.estadias2017.app_002.R;
import com.soma.estadias2017.app_002.SaldosCredito;
import com.soma.estadias2017.app_002.SaldosCreditoD;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CreditosFragment extends Fragment {

    private ListView lsc;
    Toolbar toolbar;

    String SOAP_ACTION = "";
    String METHOD_NAME = "Creditos";
    String NAMESPACE = "http://webservices/";
    String URL = "http://192.168.1.81:8081/AppMovilReforma/CreditosExistentes_ws?wsdl";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_creditos, container, false);

        lsc = (ListView) view.findViewById(R.id.lsc);

        InvocaSCreditoW();


        return view;
    }


    private void InvocaSCreditoW(){
        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);

            Request.addProperty("cliente", Globals.getCuenta());
            Request.addProperty("idcliente", Globals.getIdusuario());

            envelope.setOutputSoapObject(Request);

            HttpTransportSE HttpTransport = new HttpTransportSE(URL);
            HttpTransport.call(SOAP_ACTION, envelope);

            //System.out.print(androidHttpTransport.getServiceConnection());

            SoapObject resp = (SoapObject) envelope.bodyIn;

            List<HashMap<String,String>> reg = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> regist;

            String[] testValues = new String[resp.getPropertyCount()];

            for (int i =0; i<resp.getPropertyCount(); i++){

                testValues[i] = resp.getProperty(i).toString();
                System.out.print(testValues[i]);

                SoapObject object = (SoapObject) resp.getProperty(i);
                regist = new HashMap<String, String>();
                regist.put("creditoid", object.getProperty("creditoid").toString());
                regist.put("referencia", object.getProperty("referencia").toString());
                regist.put("fechaotorgamiento", object.getProperty("fechaotorgamiento").toString());
                regist.put("montocredito", object.getProperty("montocredito").toString());

                reg.add(regist);
            }

            SimpleAdapter adaptador = new SimpleAdapter(getContext(), reg,
                    R.layout.vista_saldos_credito,
                    new String[]{"creditoid", "referencia", "fechaotorgamiento", "montocredito"},

                    new int[]{R.id.creditoid, R.id.referencia, R.id.fechaotorgamiento,
                            R.id.montocredito});

            lsc.setAdapter(adaptador);


            lsc.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {
                    Intent intento = new Intent(getContext(),
                            SaldosCreditoD.class);

                    startActivity(intento);
                }
            });

        }
        catch (Exception e){

        }
    }
}