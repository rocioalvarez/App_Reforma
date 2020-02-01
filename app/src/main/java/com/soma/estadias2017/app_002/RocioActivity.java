package com.soma.estadias2017.app_002;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;


    public class  RocioActivity extends AppCompatActivity {

        private SharedPreferences preferences;

        static String NAMESPACE;
        static String METHODNAME;
        static String URL;
        static String SOAP_ACTION;

        //Nombre del Cliente
        String nombreCliente = "";

        //Varible para validar Login con BD
        private String validaLogin = "";

        private static final String TAG = "LoginActivity";
        private static final int REQUEST_SIGNUP = 0;

        //Give your SharedPreferences file a name and save it to a static variable
        public static final String PREFS_NAME = "MyPrefsFile";

        TextInputLayout lblEmail;
        TextInputEditText _emailText;
        TextInputLayout lblPass;
        TextInputEditText _passwordText;
        TextInputLayout lblPhone;
        TextInputEditText _phoneText;
        Button _loginButton;
        TextView _signupLink;


      TextView resultado;
      EditText usuario, cv_acceso;


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            resultado = findViewById(R.id.resultado);
            usuario =  findViewById(R.id.usuario);
            cv_acceso = findViewById(R.id.cv_acceso);
            _loginButton = findViewById(R.id.btn_ingresar);


            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0); // 0 - for private mode

            //Get "hasLoggedIn" value. If the value doesn't exist yet false is returned
            boolean hasLoggedIn = settings.getBoolean("hasLoggedIn", false);

            if(hasLoggedIn)
            {
                //Go directly to main activity.
                Intent intent = new Intent();
                intent.setClass(RocioActivity.this, MainActivity.class);
                startActivity(intent);
                RocioActivity.this.finish();
            }

            _loginButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //Obtener El Telefono
                    //TelephonyManager tMgr = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
                    //String mPhoneNumber = tMgr.getLine1Number();

                    String usu = usuario.getText().toString();
                    String cva = cv_acceso.getText().toString();
                   // String phone = _phoneText.getText().toString();

                    NAMESPACE = "http://webservices/";
                    METHODNAME = "Login";
                    URL = "https://mail.reformasofipo.com/AplicacionMovilws/Login_ws?wsdl";
                    SOAP_ACTION = "";

                    new loginConsulta(RocioActivity.this).execute(usu, cva);
                }
            });


        }

        public void login() {
            Log.d(TAG, "Login");

            if (!validate()) {
                onLoginFailed();
                return;
            }

            _loginButton.setEnabled(false);

            // TODO: Implement your own authentication logic here.

            onLoginSuccess();
        }


        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == REQUEST_SIGNUP) {
                if (resultCode == RESULT_OK) {

                    // TODO: Implement successful signup logic here
                    // By default we just finish the Activity and log them in automatically
                    this.finish();
                }
            }
        }

        @Override
        public void onBackPressed() {
            // disable going back to the MainActivity
            moveTaskToBack(true);
        }

        public void onLoginSuccess() {

            //User has successfully logged in, save this information
            // We need an Editor object to make preference changes.
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0); // 0 - for private mode
            SharedPreferences.Editor editor = settings.edit();

            //Set "hasLoggedIn" to true
            editor.putBoolean("hasLoggedIn", true);

            // Commit the edits!
            editor.commit();

            preferences = getSharedPreferences("loginPrefs",MODE_PRIVATE);
            //preferences.edit().putString("nombreCliente",nombreCliente).commit();
            preferences.edit().putString("usuario",usuario.getText().toString()).commit();

            Intent intent = new Intent(RocioActivity.this, Ping.class);
            startActivity(intent);
            RocioActivity.this.finish();

        }

        public void onLoginFailed() {
            Toast.makeText(getBaseContext(), "Error al ingresar datos", Toast.LENGTH_LONG).show();

            _loginButton.setEnabled(true);
        }

        public boolean validate() {
            boolean valid = true;

            String us = usuario.getText().toString();
            String cv = cv_acceso.getText().toString();
           // String phone = _phoneText.getText().toString();

            if (us.isEmpty()) {
                usuario.setError("Ingrese el Usuario");
                valid = false;
            } else {
                usuario.setError(null);
            }

            if (cv.isEmpty()) {
                cv_acceso.setError("Ingrese la Contraseña");
                valid = false;
            } else {
                cv_acceso.setError(null);
            }


            if(validaLogin.equals("0")){
                usuario.setError("Credenciales incorrectas, verifique la información proporcionada.");
                valid = false;
            }else if(validaLogin.equals("")) {
                usuario.setError("Ocurrio un error al verificar los datos, por favor intente de nuevo o verifique la conexión.");
                valid = false;
            }else{
                usuario.setError(null);
            }

            return valid;
        }

        private class loginConsulta extends AsyncTask<String, String,String> {

            private final String DIALOG_MESSAGE = "Comprobando Datos";

            private ProgressDialog mDialog = null;

            private void setDialog(Context context) {
                this.mDialog = new ProgressDialog(context, R.style.AppTheme);
                this.mDialog.setMessage(DIALOG_MESSAGE);
                this.mDialog.setCancelable(false);
            }

            public loginConsulta(Context context) {
                this.setDialog(context);
            }

            @Override
            protected void onPreExecute() {
                this.mDialog.show();
            }


            //METODO TRABAJA EN SEGUNDO PLANO
            @Override
            protected String doInBackground(String... params) {

                String result = "";

                SoapObject request = new SoapObject(NAMESPACE, METHODNAME);

                request.addProperty("usuario", params[0]);
                request.addProperty("cv_acceso", params[1]);
                //equest.addProperty("telefono", params[2]);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = false;
                envelope.setOutputSoapObject(request);
                HttpTransportSE transportSE = new HttpTransportSE(URL);

                try {
                    transportSE.call(SOAP_ACTION, envelope);

                    // Resultado
                    String resultado = envelope.getResponse().toString();

                    SoapObject resultLogin=(SoapObject)envelope.bodyIn;

                    if (resultLogin != null) {
                        validaLogin = resultLogin.getProperty(0).toString();
                        nombreCliente = resultLogin.getProperty(2).toString();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                login();

            }
        }

    }

