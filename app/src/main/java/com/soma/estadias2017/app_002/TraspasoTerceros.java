package com.soma.estadias2017.app_002;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.soma.estadias2017.app_002.Huella.FingerprintMainSaldo;
import com.soma.estadias2017.app_002.Huella.FingerprintMainTerceros;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.security.MessageDigest;
import java.util.ArrayList;

import static android.R.layout.simple_spinner_item;


public class TraspasoTerceros extends AppCompatActivity {

    public String idbeneficiario;
    public TextView resultado;
    public EditText beneficiario, concepto, cantidad, cvoperacion, rfcbene, iva, rfc, nombre, nombrebe;
    public Button btn_Fin, btn_datos;
    Spinner cuenta, cbeneficiario, idcliente;
    Toolbar toolbar;

    private String SOAP_ACTION ;
    private String METHOD_NAME ;
    private String NAMESPACE ="http://webservices/";
    private String URL = "http://192.168.1.118:8080/AppMovilReforma/TraspasoTerceros_ws?wsdl";
    //private String URL = "https://mail.reformasofipo.com/AplicacionMovilws/TraspasoTerceros_ws?wsdl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_cuentas_terceras);
        resultado = (TextView) findViewById(R.id.resultado);
        beneficiario = (EditText) findViewById(R.id.beneficiario);
        rfcbene = (EditText) findViewById(R.id.rfcbene);
        concepto = (EditText) findViewById(R.id.concepto);
        cantidad = (EditText) findViewById(R.id.cantidad);
        cvoperacion = (EditText) findViewById(R.id.cvoperacion);
        iva = (EditText) findViewById(R.id.iva);
        rfc = (EditText) findViewById(R.id.RFC);
        rfc = (EditText) findViewById(R.id.rfc);
        nombre = (EditText) findViewById(R.id.nombre);
        nombrebe = (EditText) findViewById(R.id.nombrebe);
        btn_Fin = (Button) findViewById(R.id.btn_Fin);
        btn_datos = (Button) findViewById(R.id.btn_datos);

        //
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        CatalogoCuentas cuentas = new CatalogoCuentas();

        ArrayList<Cuenta> datos = cuentas.consumir2(2);
        final ArrayAdapter<Cuenta> adapter = new ArrayAdapter<Cuenta>(this,
                android.R.layout.simple_spinner_item, datos);

        cuenta = (Spinner) findViewById(R.id.cuenta);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cuenta.setAdapter(adapter);


        ArrayList<Cuenta> datos2 = cuentas.consumir2(2);
        final ArrayAdapter<Cuenta> adapter2 = new ArrayAdapter<Cuenta>(this,
                android.R.layout.simple_spinner_item, datos2);

        cbeneficiario = (Spinner) findViewById(R.id.cbeneficiario);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cbeneficiario.setAdapter(adapter2);


        BuscaBeneficiario Beneficiario = new BuscaBeneficiario();
        final ArrayList<Beneficiario> dato = Beneficiario.consumirBen(1);
        final ArrayAdapter<Beneficiario> adapter7  = new ArrayAdapter<Beneficiario>(this,
                simple_spinner_item, dato);

        idcliente = (Spinner)findViewById(R.id.idcliente);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        idcliente.setAdapter(adapter);

        idcliente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                int id= (int) cbeneficiario.getSelectedItemId();

                int idcliente=dato.get(id).getIdcliente();
                sps_cliente(idcliente);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });


        btn_Fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cuenta c = adapter.getItem(cuenta.getSelectedItemPosition());
                String bene = beneficiario.getText().toString();
                Cuenta cbene = adapter.getItem(cbeneficiario.getSelectedItemPosition());
                String con = concepto.getText().toString();
                String rfcb = rfcbene.getText().toString();
                String cant = cantidad.getText().toString();
                String cvo = cvoperacion.getText().toString();
                String iv = iva.getText().toString();
                String nom = nombre.getText().toString();
                String rfc_o = rfc.getText().toString();

                if (bene.isEmpty()){
                    beneficiario.setError("Campo vacio");
                    beneficiario.requestFocus();
                }else if (con.isEmpty()){
                    concepto.setError("Campo vacio");
                    concepto.requestFocus();
                }else if (cant.isEmpty()) {
                    cantidad.setError("Campo vacio");
                    cantidad.requestFocus();

                }
                    if (cvo.isEmpty()){
                        cvoperacion.setError("Campo vacio");
                        cvoperacion.requestFocus();
                    }else {

                     /*   Intent inten1 = new Intent(getApplicationContext(), ComprobanteTraspasoTerceros.class);
                        System.out.print("entra");
                        startActivity(inten1);
                        */

                        InvocarTerceros(c.getIdcuenta(), bene, cbene.getIdcuenta(), con, rfcb, cant,
                                cvo, iv, nom, rfc_o);


                        Intent intent = new Intent(TraspasoTerceros.this, ComprobanteTraspasoTerceros.class);

                        intent.putExtra("cuenta", c.getIdcuenta());
                        intent.putExtra("beneficiario", beneficiario.getText().toString());
                        intent.putExtra("nombrebe", nombrebe.getText().toString());
                        intent.putExtra("rfcbene", rfcbene.getText().toString());
                        intent.putExtra("cbeneficiario", cbene.getDescripcion());
                        intent.putExtra("cantidad", cantidad.getText().toString());
                        intent.putExtra("concepto", concepto.getText().toString());
                        startActivity(intent);


                        cantidad.setText("");
                        beneficiario.setText("");
                        concepto.setText("");
                        cvoperacion.setText("");
                        iva.setText("");
                        rfcbene.setText("");
                        rfc.setText("");
                    }
                }

        });


        btn_datos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String bene = beneficiario.getText().toString();

                InvocarBene(bene);
            }
        });
    }


    private void InvocarTerceros(String cuenta, String beneficiario, String cbeneficiario,
                                 String concepto, String rfcbene,  String cantidad,
                                 String cvoperacion, String iva,  String nombre, String rfc) {

        SOAP_ACTION = "";
        METHOD_NAME = "Terceros";

        try {

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            request.addProperty("clienteid", Globals.getIdusuario());
            request.addProperty("cliente", Globals.getCuenta());

            request.addProperty("nombre_o", nombre);
            request.addProperty("rfc_curp_o", rfc);

            request.addProperty("cuenta", cuenta);
            request.addProperty("concepto", concepto);
            request.addProperty("rfcbene", rfcbene);
            request.addProperty("cantidad", cantidad);
            request.addProperty("beneficiario", beneficiario);
            request.addProperty("idbeneficiario", idbeneficiario);
            request.addProperty("cbeneficiario", cbeneficiario);
            request.addProperty("cvoperacion", codifica(cvoperacion));
            request.addProperty("iva", iva);
            request.addProperty("rfc", rfc);


            SoapSerializationEnvelope srl = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            srl.setOutputSoapObject(request);

            HttpTransportSE HttpTransport = new HttpTransportSE(URL);
            HttpTransport.call(SOAP_ACTION, srl);

            SoapObject so = (SoapObject) srl.bodyIn;

            String r = "";
            r = so.getProperty(1).toString();
            resultado.setText(r);
            Toast.makeText(this, r, Toast.LENGTH_LONG).show();


            } catch (Exception e) {
            e.printStackTrace();
            }

    }

    private void InvocarBene(String beneficiario) {

        SOAP_ACTION = "";
        METHOD_NAME = "beneficiario";

        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            request.addProperty("beneficiario", beneficiario);
            request.addProperty("idbeneficiario", idbeneficiario);

            SoapSerializationEnvelope srl = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            srl.setOutputSoapObject(request);

            HttpTransportSE HttpTransport = new HttpTransportSE(URL);
            HttpTransport.call(SOAP_ACTION, srl);

            SoapObject so = (SoapObject) srl.bodyIn;

            String[] r = new String[3];

            r[0] = so.getProperty(0).toString();//id
            r[1] = so.getProperty(1).toString();//nombre
            r[2] = so.getProperty(2).toString();//rfc
            //r[3] = so.getProperty(4).toString();

            rfcbene.setText(r[2]);
            nombrebe.setText(r[1]);
            idbeneficiario=r[0];

            }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sps_cliente(int idcliente){
        BuscaCliente n=new BuscaCliente();
        SoapObject ref = n.spscliente(idcliente);


        for (int i = 0; i < ref.getPropertyCount(); i++) {
            SoapObject result = (SoapObject)
                    ref.getProperty(i);
            nombre.setText(result.getProperty("nombre").toString());
            rfc.setText(result.getProperty("rfc").toString());


        }
    }

    public String codifica(String cadena) {
        //Se crea objeto de messageDigest que permite crear instancia hacia el tipo de encriptacion..
        MessageDigest messageDigest = null;

        //Se crea objeto de string buffer para almacenar la concatenacion de los datos del arreglo.
        StringBuffer sb = null;
        try {
            //Se inicializa el objeto y se le pasa el tipo de encriptacion deseada
            messageDigest = MessageDigest.getInstance("SHA-512");

            //Se extraen los bytes que contiene la cadena
            byte[] encripta = messageDigest.digest(cadena.getBytes());

            //Se inicializa un objeto tipo String buffer
            sb = new StringBuffer();

            //Se recorre el arreglo de bytes que genero messageDigest
            for (byte hashB : encripta) {
                // se concatena los datos obtenidos del arreglo
                sb.append(String.format("%02x", hashB));
            }

        } catch (Exception e) {
//            log.info(e.getMessage());
//            errorService.createError(e.getMessage(), "Error encriptando la cadena: "+ cadena);
        }

        return sb.toString();
    }
}