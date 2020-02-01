package com.soma.estadias2017.app_002.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.soma.estadias2017.app_002.Globals;
import com.soma.estadias2017.app_002.R;
import com.soma.estadias2017.app_002.SaldoDetallado;
import com.soma.estadias2017.app_002.SaldoGlobal;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by estadias2017 on 9/03/17.
 */

public class SaldosFragment extends Fragment {


    ListView listaSg;


    //Consumir Web Service
    String SOAP_ACTION = "";
    String METHOD_NAME = "sglobal";
    String NAMESPACE = "http://webservices/";
    String URL = "http://192.168.1.118:8080/AppMovilReforma/SaldoGlobal_ws?wsdl";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_saldos, container, false);
        listaSg = (ListView) view.findViewById(R.id.lsg);
        InvocaSGlobalW();


        return view;
    }

    public void InvocaSGlobalW(){
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11); // utilizar la version que corresponda:11 o 12
            //envelope.dotNet = true; // para WS ASMX, sólo si fue construido con .Net
            request.addProperty("cliente", Globals.getCuenta());
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.call(SOAP_ACTION, envelope);

            System.out.print(androidHttpTransport.getServiceConnection()); //imprimir en consola la conexion
            SoapObject result = (SoapObject)envelope.bodyIn;

            //obtener los datos mediante una lista
            List<HashMap<String,String>> registros = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> registro;

            String[] testValues = new String[result.getPropertyCount()];

            for(int i= 0; i< result.getPropertyCount(); i++) {
                testValues[i] = result.getProperty(i).toString();
                System.out.print(testValues[i]);

                SoapObject result3 = (SoapObject) result.getProperty(i);
                registro = new HashMap<String, String>();
                registro.put("nombre", result3.getProperty("nombre").toString());
                registro.put("saldo",  result3.getProperty("saldo").toString());

            }
                //registros.add(registro);

            //Crea adaptador para traer los datos a la lista
            SimpleAdapter adaptador = new SimpleAdapter(getContext(),
                    registros, R.layout.vista_saldo_global,
                    new String[]{"nombre", "saldo"},
                    new int[]{R.id.nombre, R.id.saldo});

            listaSg.setAdapter(adaptador);

        }
        catch (Exception e){

        }
    }


}