package com.soma.estadias2017.app_002;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.StrictMode;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.security.MessageDigest;
import java.util.ArrayList;

import static android.R.layout.simple_spinner_item;

/**
 * Created by estadias2017 on 29/09/17.
 */

public class NuevoSpei extends AppCompatActivity {

    public TextView cordenante, cuentaspei, institucion,resultado;
    public EditText  RFC, nombreBeneficiario, rfcBeneficiario, IVA,
            concepto, cantidad, cvoperacion, cvrastreo, referencia;
    public Button enviar;
    Spinner idcliente;
    Toolbar toolbar;


    /*
    MODIFICACIONES
	1.- SE DEBERA HACER POR NUMERO DE CUENTnA CLABE
    Al digitar el número de cuenta clabe, por default se debe tomar el banco al que se hará el depósito y el nombre del beneficiario.
*/

    //private String SOAP_ACTION = "";
    //private String METHOD_NAME = "nuevospei";
    private String NAMESPACE = "http://webservices/";
    private String URL = "http://192.168.1.118:8080/WSAplicacionBanca/NuevoSPEI_ws?wsdl";
    //private String URL = "https://mail.reformasofipo.com/AplicacionMovilws/NuevoSPEI_ws?wsdl";

    private String SOAP_ACTION = "";
    private String METHOD_NAME = "nuevospei";
   // private String NAMESPACE = "http://servidor.ws/";
    //private String URL = "http://192.168.0.113:8098/REFORMA_WS?wsdl";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_nuevo_spei);
        resultado = (TextView) findViewById(R.id.resultado);
        cordenante = (TextView) findViewById(R.id.cordenante);
        RFC = (EditText) findViewById(R.id.RFC);
        nombreBeneficiario = (EditText) findViewById(R.id.nombreBeneficiario);
        rfcBeneficiario =(EditText) findViewById(R.id.rfcBeneficiario);
        cuentaspei = (TextView) findViewById(R.id.cuentaspei);
        institucion = (TextView) findViewById(R.id.institucion);
        concepto = (EditText) findViewById(R.id.concepto);
        cantidad = (EditText) findViewById(R.id.cantidad);
        IVA = (EditText) findViewById(R.id.IVA);
        cvrastreo = (EditText) findViewById(R.id.cvrastreo);
        referencia = (EditText) findViewById(R.id.referencia);
        cvoperacion = (EditText) findViewById(R.id.cvoperacion);
        enviar = (Button) findViewById(R.id.enviar);

        //Catalogo llena datos "referencia", "cliente", "clave rastreo"
        BeneficiariosTimer BTimer = new BeneficiariosTimer();
        final ArrayList<BTimer> datos = BTimer.consumirb(1);
        final ArrayAdapter<BTimer> adapter  = new ArrayAdapter<BTimer>(this,
                simple_spinner_item, datos);

        idcliente = (Spinner)findViewById(R.id.idcliente);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        idcliente.setAdapter(adapter);

        idcliente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                int id= (int) idcliente.getSelectedItemId();

                int idcliente=datos.get(id).getId_beneficiario();
                sps_beneficiario(idcliente);

                sps_referencia(idcliente);

                sps_cliente(idcliente);

                sps_cvrastreo(idcliente);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });

        //Barra de herramientas
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        enviar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                //Mensaje que muestra al abrir el formulario
                Toast toast1 =
                        Toast.makeText(getApplicationContext(),
                                "Proceso completado correctamente",
                                Toast.LENGTH_LONG);
                toast1.show();

                concepto.setText("");
                cantidad.setText("");
                cvoperacion.setText("");


            }
           /*     String co = cordenante.getText().toString();
                String rf = RFC.getText().toString();
                BTimer b = adapter.getItem(idcliente.getSelectedItemPosition());
                String nom = nombreBeneficiario.getText().toString();
                String rfcB = rfcBeneficiario.getText().toString();
                String cue = cuentaspei.getText().toString();
                String ins = institucion.getText().toString();
                String con = concepto.getText().toString();
                String can = cantidad.getText().toString();
                String IV = IVA.getText().toString();
                String cvr = cvrastreo.getText().toString();
                String ref = referencia.getText().toString();
                String cv = cvoperacion.getText().toString();

                if (con.isEmpty()){
                    concepto.setError("Campo vacio");
                }else if (can.isEmpty()){
                    cantidad.setError("Campo vacio");
                    cantidad.requestFocus();
                }else if (cv.isEmpty()){
                    cvoperacion.setError("Campo vacio");
                    cvoperacion.requestFocus();
                }else {

                    InvocarSPEI(co, rf, b.getId_beneficiario(), nom, rfcB, cue, ins, con, can, IV,
                            cvr, ref, cv);

                    concepto.setText("");
                    cantidad.setText("");
                    cvoperacion.setText("");

                    //Funcion para que refresque los datos del formulario
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                    //

                    Intent inten2 = new Intent(getApplicationContext(), EnviarSpei.class);
                    startActivity(inten2);

                }

            }
            */
        });
    }


    private void InvocarSPEI(String cordenante, String RFC, int idbeneficiario, String nombreBeneficiario,
                             String rfcBeneficiario, String cuentaspei, String institucion,
                             String concepto, String cantidad, String IVA, String cvrastreo,
                             String referencia, String cvoperacion) {

        try {

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("idcliente",  Globals.getIdusuario());
            request.addProperty("cliente", Globals.getCuenta());
            request.addProperty("cordenante", cordenante);
            request.addProperty("RFC", RFC);
            request.addProperty("idbeneficiario",idbeneficiario);
            request.addProperty("nombreBeneficiario", nombreBeneficiario);
            request.addProperty("rfcBeneficiario", rfcBeneficiario);
            request.addProperty("cuentaspei", cuentaspei);
            request.addProperty("institucion", institucion);
            request.addProperty("concepto",concepto);
            request.addProperty("cantidad", cantidad);
            request.addProperty("IVA", IVA);
            request.addProperty("cvrastreo", cvrastreo);
            request.addProperty("referencia", referencia);
            request.addProperty("cvoperacion", codifica(cvoperacion));

            Log.d("Request ", request.toString());
            SoapSerializationEnvelope srl = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            srl.setOutputSoapObject(request);

            HttpTransportSE HttpTransport = new HttpTransportSE(URL);
            HttpTransport.call(SOAP_ACTION, srl);

            SoapObject so = ( SoapObject ) srl.bodyIn;

            String r = "";
            r = so.getProperty(1).toString();
            resultado.setText(r);

            Toast.makeText(this, r, Toast.LENGTH_LONG).show();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sps_referencia(int idcliente){
        BuscaReferencia n=new BuscaReferencia();
        SoapObject ref = n.spsreferencia(idcliente);

        for (int i = 0; i < ref.getPropertyCount(); i++) {
            SoapObject result = (SoapObject)
                    ref.getProperty(i);
            referencia.setText(result.getProperty("referencia").toString());

        }
    }

    public void sps_cliente(int idcliente){
        BuscaCliente n=new BuscaCliente();
        SoapObject ref = n.spscliente(idcliente);

        for (int i = 0; i < ref.getPropertyCount(); i++) {
            SoapObject result = (SoapObject)
                    ref.getProperty(i);
            cordenante.setText(result.getProperty("cuentaspei").toString());
            RFC.setText(result.getProperty("rfc").toString());

        }
    }

    public void sps_beneficiario(int idbeneficiario){
        BuscaDBeneficiario n=new BuscaDBeneficiario();
        SoapObject bene = n.spsbeneficiario(idbeneficiario);

        for (int i = 0; i < bene.getPropertyCount(); i++) {
            SoapObject result2 = (SoapObject)
                    bene.getProperty(i);
            nombreBeneficiario.setText(result2.getProperty("nombre").toString());
            rfcBeneficiario.setText(result2.getProperty("rfc").toString());
            cuentaspei.setText(result2.getProperty("cuentaspei").toString());
            institucion.setText(result2.getProperty("institucion").toString());

        }
    }

    public void sps_cvrastreo(int idcliente){
        ClaveRastreo n=new ClaveRastreo();

        SoapObject cve = n.spscvrastreo(idcliente);

        for (int i = 0; i < cve.getPropertyCount(); i++) {

            SoapPrimitive result = (SoapPrimitive)
                    cve.getProperty(0);

            cvrastreo.setText(result.toString());

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