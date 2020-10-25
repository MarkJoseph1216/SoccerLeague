package com.sports.footballcage.JSON;
import android.content.Context;
import android.util.Log;

import com.sports.footballcage.R;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

public class HttpHandler {

    private static final String TAG = HttpHandler.class.getSimpleName();

    public HttpHandler() {
    }

    public String makeServiceCall(String reqUrl) {
        String response = null;
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
            conn.setRequestProperty("Cache-Control", "max-age=604800");

            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    public String makeServiceCallBank(String reqUrl, Context context) {
        String response = null;
        try {
            URL url = new URL(reqUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            InputStream cert1 = context.getResources().openRawResource(R.raw.certificate);

            KeyManagerFactory kmf = KeyManagerFactory.getInstance("X509"); //Initializes KeyManagerFactory
            kmf.init(getTrustedCertificate(context), "qwerty123".toCharArray()); //Gets the SSL certificate and password for initialization
            KeyManager[] keyManagers = kmf.getKeyManagers(); //Sets kmf as a KeyManager
            SSLContext sslContext = SSLContext.getInstance("TLS"); //Create SSLContext

            byte[] bytes = IOUtils.toByteArray(cert1); //Convert SSL Certificate to bytes
            ByteArrayInputStream derInputStream = new ByteArrayInputStream(bytes); //Create BytesArrayInputStream
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509"); //Generate Certificate
            X509Certificate cert = (X509Certificate) certificateFactory
                    .generateCertificate(derInputStream);
            String alias = cert.getSubjectX500Principal().getName(); //Set Certificate as String
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType()); //Create Keystore
            trustStore.load(null); // load
            trustStore.setCertificateEntry(alias, cert); //Set SSL certificate

            TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
            tmf.init(trustStore);
            TrustManager[] trustManagers = tmf.getTrustManagers();

            sslContext.init(keyManagers, trustManagers, null);
            conn.setSSLSocketFactory(sslContext.getSocketFactory());  //Set SSL certificate to URL
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDefaultUseCaches(false);
            conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
            conn.setRequestProperty("Cache-Control", "max-age=604800");

            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }


    public String convertStreamToString(InputStream is) throws UnsupportedEncodingException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    private KeyStore getTrustedCertificate(Context context) {
        KeyStore trusted = null;
        InputStream in = null;
        try {
            trusted = KeyStore.getInstance("PKCS12");
            in = context.getResources().openRawResource(R.raw.cert);
            trusted.load(in, "qwerty123".toCharArray());
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return trusted;

    }
}