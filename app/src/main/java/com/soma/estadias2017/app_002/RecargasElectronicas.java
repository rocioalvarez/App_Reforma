package com.soma.estadias2017.app_002;

import android.content.Intent;
import android.content.pm.ActivityInfo;
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

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.security.MessageDigest;
import java.util.ArrayList;

import static android.R.layout.simple_spinner_item;

/**
 * Created by SOMA-ROCIO on 01/11/2017.
 * Funcion: Realiza Recargas Electronicas
 */

public class RecargasElectronicas extends AppCompatActivity {

    EditText txtnotelefono, txtctelefono, txtcvoperacion;
    TextView txtnombre, txtrfc, txtdeposito, txtretiro, txtservicio, txtreferencia, txtcodigo, txtmonto,
            txtusuario, txtcliente, txtfolio, txtautorizacion, txtcomision, txtfecha, txtcodigorespuesta,
            txtrespuesta, txtncajero, txtreferencia2, txtpmonto;
    TextView txtresult;
    Spinner carecargasmtc, cuenta, cataestados, idcliente;
    Button btn_enviar;
    Toolbar toolbar;

    String SOAP_ACTION = "";
    String METHOD_NAME = "recarga";
    String NAMESPACE = "http://webservices/";
    String URL = "http://192.168.1.118:8080/AppMovilReforma/RecargasElectronicas_ws?wsdl";
    //String URL = "https://mail.reformasofipo.com/AplicacionMovilws/RecargasElectronicas_ws?wsdl";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_recargas_electronicas);
        txtnombre = (TextView) findViewById(R.id.nombre);
        txtrfc = (TextView) findViewById(R.id.rfc);
        txtnotelefono = (EditText) findViewById(R.id.notelefono);
        txtctelefono = (EditText) findViewById(R.id.ctelefono);
        txtcvoperacion = (EditText) findViewById(R.id.cvoperacion);
        btn_enviar = (Button) findViewById(R.id.envia_recarga);
        txtdeposito = (TextView) findViewById(R.id.deposito);
        txtretiro = (TextView) findViewById(R.id.retiro);
        txtservicio = (TextView) findViewById(R.id.servicio);
        txtreferencia = (TextView) findViewById(R.id.referencia);
        txtcodigo = (TextView) findViewById(R.id.codigo);
        txtmonto = (TextView) findViewById(R.id.monto);
        txtusuario = (TextView) findViewById(R.id.usuario);
        txtcliente = (TextView) findViewById(R.id.cliente);
        txtfolio = (TextView) findViewById(R.id.folio);
        txtautorizacion = (TextView) findViewById(R.id.autorizacion);
        txtcomision = (TextView) findViewById(R.id.comision);
        txtfecha = (TextView) findViewById(R.id.fecha);
        txtcodigorespuesta = (TextView) findViewById(R.id.codigorespuesta);
        txtrespuesta = (TextView) findViewById(R.id.respuesta);
        txtncajero = (TextView) findViewById(R.id.ncajero);
        txtreferencia2 = (TextView) findViewById(R.id.referencia2);
        txtpmonto = (TextView) findViewById(R.id.pmonto);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Politicas
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Catalogo de cuentas que llena el Spinner
        CatalogoCuentas cuentas = new CatalogoCuentas();

        final ArrayList<Cuenta> datos = cuentas.consumir2(2); //indicamos llenar con la opcion 2
        final ArrayAdapter<Cuenta> adapter = new ArrayAdapter<Cuenta>(this,
                android.R.layout.simple_spinner_item, datos);

        cuenta = (Spinner) findViewById(R.id.cuenta);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cuenta.setAdapter(adapter);

        //Catalogo que llena el spinner del tipo de recarga
        CatalogoRecargasE recargasE = new CatalogoRecargasE();

        final ArrayList<Recargas> datos1 = recargasE.consum(1);
        final ArrayAdapter<Recargas> adapter1 = new ArrayAdapter<Recargas>(this,
                android.R.layout.simple_spinner_item, datos1);
        carecargasmtc = (Spinner) findViewById(R.id.carecargasmtc);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carecargasmtc.setAdapter(adapter1);

        carecargasmtc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                posiciona_datos(datos1);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

    });

        //Catalogo para llenar Spinner del Estado donde se realiza la recarga
        CatalogoEstados estados = new CatalogoEstados();

        final ArrayList<Estados> datos2 = estados.consumeEst(1);
        final ArrayAdapter<Estados> adapter2 = new ArrayAdapter<Estados>(this,
                android.R.layout.simple_spinner_item, datos2);
        cataestados = (Spinner) findViewById(R.id.cataestados);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cataestados.setAdapter(adapter2);


        //LLENA CAMPOS DE NOMBRE Y RFC DEL USUARIO
        CatalogoRfcCliente RfcCliente = new CatalogoRfcCliente();
        final ArrayList<RfcCliente> dato = RfcCliente.consum(1);
        final ArrayAdapter<RfcCliente> adapter7  = new ArrayAdapter<RfcCliente>(this,
                simple_spinner_item, dato);

        idcliente = (Spinner)findViewById(R.id.idcliente);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        idcliente.setAdapter(adapter);

        idcliente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                int id= (int) idcliente.getSelectedItemId();
                int idcliente=dato.get(id).getIdcliente();
                //Llamo la funcion sps_cliente
                sps_cliente(idcliente);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });

        //Boton para guardar/finalizar el pago
        btn_enviar.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {

                String dep = txtdeposito.getText().toString();
                String ret = txtretiro.getText().toString();
                String ser = txtservicio.getText().toString();
                String tel = txtnotelefono.getText().toString();
                String ctel = txtctelefono.getText().toString();
                String ref = txtreferencia.getText().toString();
                String cod = txtcodigo.getText().toString();
                String usu = txtusuario.getText().toString();
                String cli = txtcliente.getText().toString();
                Cuenta cue = adapter.getItem(cuenta.getSelectedItemPosition());
                String fol = txtfolio.getText().toString();
                String aut = txtautorizacion.getText().toString();
                String com = txtcomision.getText().toString();
                String fec = txtfecha.getText().toString();
                Recargas pro = adapter1.getItem(carecargasmtc.getSelectedItemPosition());
                String cor = txtcodigorespuesta.getText().toString();
                String res = txtrespuesta.getText().toString();
                String nca = txtncajero.getText().toString();
                String re2 = txtreferencia2.getText().toString();
                String mon = txtmonto.getText().toString();
                String cvo = txtcvoperacion.getText().toString();

                if(tel.isEmpty()){

                    txtnotelefono.setError("Campo vacio");
                    txtnotelefono.requestFocus();

                }else if(ctel.isEmpty()){

                    txtctelefono.setError("Campo vacio");
                    txtctelefono.requestFocus();

                }else if(tel.equals(ctel)){

                    Invoke(dep, ret, ser, tel, ref, cod, mon, usu, cli, cue.getIdcuenta(), fol, aut,
                            com, fec, pro.getServicio(), cor, res, nca, re2, cvo);

                    Toast.makeText(RecargasElectronicas.this, "Validacion success", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent (RecargasElectronicas.this, ComprobanteRE.class);
                    intent.putExtra("notelefono", tel);
                    intent.putExtra("carecargasmtc", pro.getServicio());
                    intent.putExtra("cuenta", cue.getIdcuenta());
                    intent.putExtra("fecha", fec);
                    intent.putExtra("folio", fol);
                    startActivity(intent);

                    txtnotelefono.setText("");
                    txtctelefono.setText("");
                    txtcvoperacion.setText("");

                }else {

                    Toast.makeText(RecargasElectronicas.this, "El numero no coincide", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void Invoke(String deposito, String retiro, String servicio, String notelefono,
                        String referencia, String codigo, String monto, String usuario,
                        String cliente, String cuenta, String folio, String autorizacion,
                        String comision, String fecha, String producto, String codigorespuesta,
                        String respuesta, String ncajero, String referencia2, String cvoperacion) {
        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.setOutputSoapObject(Request);
            Request.addProperty("deposito", deposito);
            Request.addProperty("retiro", retiro);
            Request.addProperty("servicio", servicio);
            Request.addProperty("notelefono", notelefono);
            Request.addProperty("referencia", referencia);
            Request.addProperty("codigo", codigo);
            Request.addProperty("monto", monto);
            Request.addProperty("usuario", Globals.getCuenta());
            Request.addProperty("cliente", cliente);
            Request.addProperty("cuenta", cuenta);
            Request.addProperty("folio", folio);
            Request.addProperty("autorizacion", autorizacion);
            Request.addProperty("comision", comision);
            Request.addProperty("fecha", fecha);
            Request.addProperty("producto", producto);
            Request.addProperty("codigorespuesta", codigorespuesta);
            Request.addProperty("respuesta", respuesta);
            Request.addProperty("ncajero", ncajero);
            Request.addProperty("referencia2", referencia2);
            Request.addProperty("numcliente", Globals.getCuenta());
            Request.addProperty("cvoperacion", codifica(cvoperacion));

            HttpTransportSE tra = new HttpTransportSE(URL);
            tra.call(SOAP_ACTION, envelope);
            System.out.print(tra.getServiceConnection()); //imprime en consola la conexion

            SoapObject so = (SoapObject) envelope.bodyIn;
            String r = "";
            r = so.getProperty(1).toString();
            txtresult.setText(r);
            Toast.makeText(this, r, Toast.LENGTH_LONG).show();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    //Funcion para buscar el cliente por id y llenar los datos
    public void sps_cliente(int idcliente){
        CatalogoRfcCliente n=new CatalogoRfcCliente();
        SoapObject ref = n.invokeCatalog(idcliente);

        //Llena datos, nombre y rfc
        for (int i = 0; i < ref.getPropertyCount(); i++) {
            SoapObject result = (SoapObject)
                    ref.getProperty(i);
            txtnombre.setText(result.getProperty("nombre").toString());
            txtrfc.setText(result.getProperty("rfc").toString());
        }
    }


    String vcodigo = "0";
    private void posiciona_datos(ArrayList<Recargas> datos1){

        int id = (int) carecargasmtc.getSelectedItemId();
        vcodigo = datos1.get(id).getSku();

        txtcodigo.setText(String.valueOf(vcodigo));
        txtmonto.setText(String.valueOf(datos1.get(id).getMonto()));
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
