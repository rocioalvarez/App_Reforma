package com.soma.estadias2017.app_002.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.soma.estadias2017.app_002.FormGestionSpei;
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

public class GSPEIFragment extends Fragment {

    private ListView listaGs;

    //Consumir Web Service
    String SOAP_ACTION = "";
    String METHOD_NAME = "gestion";
    String NAMESPACE = "http://webservices/";
    String URL = "http://192.168.1.81:8081/AppMovilReforma/GestionSPEI_ws?wsdl";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_gspei, container, false);
        listaGs = (ListView) view.findViewById(R.id.lgs);

        InvocaGestion();

        return view;
    }

    //Metodo que invoca
    public void InvocaGestion(){
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11); // utilizar la version que corresponda:11 o 12
            //envelope.dotNet = true; // para WS ASMX, sólo si fue construido con .Net
            request.addProperty("cliente", Globals.getCuenta());
            request.addProperty("idcliente", Globals.getIdusuario());
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
                registro.put("pagos_speiid", result3.getProperty("pagos_speiid").toString());
                registro.put("nombre_o", result3.getProperty("nombre_o").toString());
                registro.put("rfc_curp_o", result3.getProperty("rfc_curp_o").toString());
                registro.put("numero_cuenta_o", result3.getProperty("numero_cuenta_o").toString());
                registro.put("rfc_curp_b", result3.getProperty("rfc_curp_b").toString());
                registro.put("numero_cuenta_b", result3.getProperty("numero_cuenta_b").toString());
                registro.put("ref_numerica", result3.getProperty("ref_numerica").toString());
                registro.put("iva_pago", result3.getProperty("iva_pago").toString());
                registro.put("nombre_estado", result3.getProperty("nombre_estado").toString());
                registro.put("concepto_pago",  result3.getProperty("concepto_pago").toString());
                registro.put("clave_rastreo", result3.getProperty("clave_rastreo").toString());
                registro.put("nombre_b",  result3.getProperty("nombre_b").toString());
                registro.put("monto_pago", result3.getProperty("monto_pago").toString());
                registro.put("comision",  result3.getProperty("comision").toString());
                registro.put("fecha_pago", result3.getProperty("fecha_pago").toString());
                registro.put("clave_p_b", result3.getProperty("clave_p_b").toString());

                registros.add(registro);
            }

            //Crea adaptador para traer los datos a la lista
            SimpleAdapter adaptador = new SimpleAdapter(getContext(),
                    registros, R.layout.vista_gestion_spei,
                    new String[]{"pagos_speiid", "nombre_o","rfc_curp_o", "numero_cuenta_o",
                            "rfc_curp_b", "numero_cuenta_b", "ref_numerica",  "iva_pago",
                            "nombre_estado", "concepto_pago", "clave_rastreo", "nombre_b",
                            "monto_pago", "comision", "fecha_pago", "clave_p_b"},

                    new int[]{R.id.pagos_speiid, R.id.nombre_o, R.id.rfc_curp_o,
                            R.id.numero_cuenta_o, R.id.rfc_curp_b, R.id.numero_cuenta_b,
                            R.id.ref_numerica, R.id.iva_pago, R.id.nombre_estado,
                            R.id.concepto_pago, R.id.clave_rastreo, R.id.nombre_b,
                            R.id.monto_pago, R.id.comision, R.id.fecha_pago, R.id.clave_p_b});

            listaGs.setAdapter(adaptador);

            listaGs.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                        long arg3) {
                    Intent intento = new Intent(getContext(),

                            FormGestionSpei.class);
                    Bundle bun = new Bundle();

                    TextView nombre_o = (TextView) arg1.findViewById(R.id.nombre_o);
                    bun.putString("nombre_o", nombre_o.getText().toString());
                    intento.putExtra("nombre_o", nombre_o.getText().toString());
                    intento.putExtras(bun);

                    intento.putExtra("nombre_o", nombre_o.getText().toString());

                    TextView rfc_curp_o = (TextView) arg1.findViewById(R.id.rfc_curp_o);
                    intento.putExtra("rfc_curp_o", rfc_curp_o.getText().toString());

                    TextView pagos_speiid = (TextView) arg1.findViewById(R.id.pagos_speiid);
                    intento.putExtra("pagos_speiid", pagos_speiid.getText().toString());

                    TextView numero_cuenta_o = (TextView) arg1.findViewById(R.id.numero_cuenta_o);
                    intento.putExtra("numero_cuenta_o", numero_cuenta_o.getText().toString());

                    TextView nombre_b = (TextView) arg1.findViewById(R.id.nombre_b);
                    intento.putExtra("nombre_b", nombre_b.getText().toString());

                    TextView rfc_curp_b = (TextView) arg1.findViewById(R.id.rfc_curp_b);
                    intento.putExtra("rfc_curp_b", rfc_curp_b.getText().toString());

                    TextView numero_cuenta_b = (TextView) arg1.findViewById(R.id.numero_cuenta_b);
                    intento.putExtra("numero_cuenta_b", numero_cuenta_b.getText().toString());

                    TextView concepto_pago = (TextView) arg1.findViewById(R.id.concepto_pago);
                    intento.putExtra("concepto_pago", concepto_pago.getText().toString());

                    TextView clave_rastreo = (TextView) arg1.findViewById(R.id.clave_rastreo);
                    intento.putExtra("clave_rastreo", clave_rastreo.getText().toString());

                    TextView ref_numerica = (TextView) arg1.findViewById(R.id.ref_numerica);
                    intento.putExtra("ref_numerica", ref_numerica.getText().toString());

                    TextView monto_pago = (TextView) arg1.findViewById(R.id.monto_pago);
                    intento.putExtra("monto_pago", monto_pago.getText().toString());

                    TextView iva_pago = (TextView) arg1.findViewById(R.id.iva_pago);
                    intento.putExtra("iva_pago", iva_pago.getText().toString());

                    TextView fecha_pago = (TextView) arg1.findViewById(R.id.fecha_pago);
                    intento.putExtra("fecha_pago", fecha_pago.getText().toString());

                    TextView clave_p_b = (TextView) arg1.findViewById(R.id.clave_p_b);
                    intento.putExtra("clave_p_b", clave_p_b.getText().toString());

                    startActivity(intento);
                }
            });

        }
        catch (Exception e){

        }
    }

}