package com.soma.estadias2017.app_002.Huella;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.soma.estadias2017.app_002.R;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class FingerprintMainMovimientos extends AppCompatActivity {

    private KeyStore keyStore;
    // Variable used for storing the key in the Android Keystore container
    private static final String KEY_NAME = "Huella";
    private Cipher cipher;
    private TextView textView;
    private TextView sp_movimiento;


    //Consumir Web Service
    String SOAP_ACTION = "";
    String METHOD_NAME = "sdetallado";
    String NAMESPACE = "http://webservices/";
    String URL = "http://192.168.12.115:8080/AppMovilReforma/SaldoDetallado_ws?wsdl";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint_second);


        // Initializing both Android Keyguard Manager and Fingerprint Manager
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);


        textView = (TextView) findViewById(R.id.errorText);
        sp_movimiento = (TextView) findViewById(R.id.sp_movimiento);


        Bundle bundle = getIntent().getExtras();

        String sp_movimiento = bundle.getString("sp_movimiento");

        String PasaMovimiento = sp_movimiento;
        TextView mov = (TextView) findViewById(R.id.sp_movimiento);
        mov.setText(PasaMovimiento);

        //Comprueba si el dispositivo tiiene un sensor de huellas digitales.
        if(!fingerprintManager.isHardwareDetected()){
            /**
             * Se mostrará un mensaje de error si el dispositivo no contiene el hardware de huellas digitales.
             * Sin embargo, si planea implementar un método de autenticación predeterminado,
             * puede redirigir al usuario a una actividad de autenticación predeterminada desde aquí.
             * Ejemplo:
             * Intent intent = new Intent(this, DefaultAuthenticationActivity.class);
             * startActivity(intent);
             */
            textView.setText("Su dispositivo no tiene un sensor de huellas digitales");
        }else {
            //Comprueba si el permiso de huellas esta configurado en el Manifest.
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                textView.setText("El permiso de autenticación de huellas digitales no está habilitado");
            }else{
                // Comprueba si al menos una huella digital esta registrada.
                if (!fingerprintManager.hasEnrolledFingerprints()) {
                    textView.setText("Registre al menos una huella digital en la configuración");
                }else{
                    // Comprueba si al menos se tiene registada una huella.
                    if (!keyguardManager.isKeyguardSecure()) {
                        textView.setText("La seguridad de la pantalla de bloqueo no está habilitada en la configuración");
                    }else{
                        generateKey();


                        if (cipherInit()) {
                            FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
                            FingerprintMovimientos helper = new FingerprintMovimientos(this);
                            helper.startAuth(fingerprintManager, cryptoObject);
                        }
                    }
                }
            }
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    protected void generateKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (Exception e) {
            e.printStackTrace();
        }


        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException("Failed to get KeyGenerator instance", e);
        }


        try {
            keyStore.load(null);
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException |
                InvalidAlgorithmParameterException
                | CertificateException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    public boolean cipherInit() {
        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }


        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }
}
