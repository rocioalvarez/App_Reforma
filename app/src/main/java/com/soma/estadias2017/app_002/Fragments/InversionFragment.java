package com.soma.estadias2017.app_002.Fragments;

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
import android.widget.TextView;

import com.soma.estadias2017.app_002.FormRetiroInteres;
import com.soma.estadias2017.app_002.Globals;
import com.soma.estadias2017.app_002.Invertir;
import com.soma.estadias2017.app_002.R;
import com.soma.estadias2017.app_002.ReporteInversion;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by estadias2017 on 29/05/17.
 */

public class InversionFragment extends Fragment {

    public ListView lista_retiro;
    public Toolbar toolbar;

    public String SOAP_ACTION = "";
    public String METHOD_NAME = "RetiroInv";
    String NAMESPACE = "http://webservices/";
    String URL = "http://192.168.1.81:8081/AppMovilReforma/ReporteInversion_ws?wsdl";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_inversion, container, false);
        lista_retiro = (ListView) view.findViewById(R.id.lista_retiro);

        invokeList();
        return view;
    }

    private void invokeList() {

        try{
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11); // utilizar la version que corresponda:11 o 12
            //envelope.dotNet = true; // para WS ASMX, sólo si fue construido con .Net
            request.addProperty("cliente", Globals.getCuenta());
            request.addProperty("idcliente", Globals.getIdusuario());
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.call(SOAP_ACTION, envelope);

            System.out.print(androidHttpTransport.getServiceConnection()); //imprime en consola la conexion
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

                registro.put("deposito", result3.getProperty("deposito").toString());
                registro.put("dias", result3.getProperty("dias").toString());
                registro.put("fecha", result3.getProperty("fecha").toString());
                registro.put("interes", result3.getProperty("interes").toString());
                registro.put("inversion", result3.getProperty("inversion").toString());
                registro.put("inversionid", result3.getProperty("inversionid").toString());
                registro.put("tasa", result3.getProperty("tasa").toString());
                registro.put("vencimiento", result3.getProperty("vencimiento").toString());

                registros.add(registro);
            }


            //Crea adaptador para traer los datos a la lista
            SimpleAdapter adaptador = new SimpleAdapter(getContext(),
                    registros, R.layout.vista_retiro_interes,
                    new String[]{"deposito", "dias", "fecha", "interes", "inversion", "inversionid",
                            "tasa", "vencimiento"},

                    new int[]{ R.id.deposito, R.id.dias, R.id.fecha, R.id.interes, R.id.inversion,
                            R.id.inversionid, R.id.tasa, R.id.vencimiento});


            lista_retiro.setAdapter(adaptador);
            lista_retiro.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {

                    Intent intento = new Intent(getContext(),
                            FormRetiroInteres.class);

                    Bundle bun = new Bundle();

                    TextView deposito = (TextView) arg1.findViewById(R.id.deposito);
                    bun.putString("deposito", deposito.getText().toString());
                    intento.putExtra("deposito", deposito.getText().toString());
                    intento.putExtras(bun);

                    intento.putExtra("deposito", deposito.getText().toString());

                    TextView dias = (TextView) arg1.findViewById(R.id.dias);
                    intento.putExtra("dias", dias.getText().toString());

                    TextView fecha = (TextView) arg1.findViewById(R.id.fecha);
                    intento.putExtra("fecha", fecha.getText().toString());

                    TextView interes = (TextView) arg1.findViewById(R.id.interes);
                    intento.putExtra("interes", interes.getText().toString());

                    TextView inversion = (TextView) arg1.findViewById(R.id.inversion);
                    intento.putExtra("inversion", inversion.getText().toString());

                    TextView inversionid = (TextView) arg1.findViewById(R.id.inversionid);
                    intento.putExtra("inversionid", inversionid.getText().toString());

                    TextView tasa = (TextView) arg1.findViewById(R.id.tasa);
                    intento.putExtra("tasa", tasa.getText().toString());

                    TextView vencimiento = (TextView) arg1.findViewById(R.id.vencimiento);
                    intento.putExtra("vencimiento", vencimiento.getText().toString());

                    startActivity(intento);
                }
            });

        }

        catch (Exception e){

        }
    }

}