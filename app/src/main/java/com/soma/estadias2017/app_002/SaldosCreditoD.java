package com.soma.estadias2017.app_002;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.soma.estadias2017.app_002.R.id.capital;
import static com.soma.estadias2017.app_002.R.id.creditoid;
import static com.soma.estadias2017.app_002.R.id.interes;
import static com.soma.estadias2017.app_002.R.id.iva;
import static com.soma.estadias2017.app_002.R.id.montocredito;
import static com.soma.estadias2017.app_002.R.id.moratorio;
import static com.soma.estadias2017.app_002.R.id.total;

//import static com.soma.estadias2017.app_002.CreditoD.idcredito;

/**
 * Created by estadias2017 on 17/02/17.
 */

public class SaldosCreditoD extends AppCompatActivity implements Serializable {

    public ListView listcre;
    TextView saldo_credito, capital, interes, moratorio, iva, total, creditoid,cliente;

    String SOAP_ACTION = "";
    String METHOD_NAME = "Detallado";
    String NAMESPACE = "http://webservices/";
    //String URL = "http://192.168.1.118:8080/AppMovilReforma/CreditoDetallado_ws?wsdl";
    String URL = "https://mail.reformasofipo.com/AplicacionMovilws/CreditoDetallado_ws?wsdl";


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_saldos_creditod);
        listcre = (ListView) findViewById(R.id.listcre);
        saldo_credito = (TextView) findViewById(R.id.saldo_credito);
        capital = (TextView) findViewById(R.id.capital);
        interes = (TextView) findViewById(R.id.interes);
        moratorio = (TextView) findViewById(R.id.moratorio);
        iva = (TextView) findViewById(R.id.iva);
        total = (TextView) findViewById(R.id.total);
        creditoid = (TextView) findViewById(R.id.creditoid);
        cliente = (TextView) findViewById(R.id.cliente);

        System.out.println("mensaje 0: " + creditoid);


        Bundle extras = getIntent().getExtras();

        //String ide = extras.getString("creditoid");
        String in = extras.getString("interes");
        String iv = extras.getString("iva");
        String mo = extras.getString("moratorio");
        String mc = extras.getString("montocredito");
        String to = extras.getString("total");

        //creditoid.setText(ide);
        interes.setText(in);
        iva.setText(iv);
        moratorio.setText(mo);
        saldo_credito.setText(mc);
        total.setText(to);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        InvocarCdeta();

    }


    private void InvocarCdeta(){
        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);

            Request.addProperty("cliente", Globals.getCuenta());
            Request.addProperty("creditoid", this.creditoid);

            envelope.setOutputSoapObject(Request);

            HttpTransportSE HttpTransport = new HttpTransportSE(URL);
            HttpTransport.call(SOAP_ACTION, envelope);

            SoapObject resp = (SoapObject) envelope.bodyIn;

            List<HashMap<String,String>> reg = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> regist;


            String[] testValues = new String[resp.getPropertyCount()];

            for (int i =0; i<resp.getPropertyCount(); i++){

                testValues[i] = resp.getProperty(i).toString();
                System.out.print(testValues[i]);

                SoapObject object = (SoapObject) resp.getProperty(i);
                regist = new HashMap<String, String>();
                regist.put("saldo_credito", object.getProperty("saldo_credito").toString());
                regist.put("capital", object.getProperty("capital").toString());
                regist.put("interes", object.getProperty("interes").toString());
                regist.put("moratorio", object.getProperty("moratorio").toString());
                regist.put("iva", object.getProperty("iva").toString());
                regist.put("total", object.getProperty("total").toString());

                reg.add(regist);
            }


            SimpleAdapter adaptador = new SimpleAdapter(getApplicationContext(), reg,
                    R.layout.vista_saldos_creditod,
                    new String[]{"saldo_credito", "capital", "interes", "moratorio", "iva",
                            "total"},
                    new int[]{R.id.saldo_credito, R.id.capital, R.id.interes, R.id.moratorio,
                            R.id.iva, R.id.total});

            listcre.setAdapter(adaptador);

        }
        catch (Exception e){

        }
    }

}
