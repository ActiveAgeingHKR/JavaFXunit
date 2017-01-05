/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aajavafx;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import sun.misc.BASE64Encoder;

/**
 *
 * @author panos
 */
public class SSLConnection {

    public static enum CONTENT_TYPE {
        TEXT, JSON
    };

    public static enum ACCEPT_TYPE {
        TEXT, JSON
    };

    public static enum USER_MODE {
        ADMIN, EMPLOYEE
    };
    public final static String TLS = "TLS";
    private SSLContext sslContext;
    private String RESTfulServerUrl = "";
    //= "https://192.168.1.99:8181/MainServerREST/api/"
    private static char[] KEYSTORE_PASSWORD = "changeit".toCharArray();
    //private SSLSocketFactory socketFactory;
    private final String REST_SERVER_ADMIN_USERNAME = "ADMIN";
    private final String REST_SERVER_ADMIN_PASSWORD = "password";
    private final String REST_SERVER_EMPLOYEE_USERNAME = "EMPLOYEE";
    private final String REST_SERVER_EMPLOYEE_PASSWORD = "password";
    private String endoding;
    CONTENT_TYPE conType;
    ACCEPT_TYPE acc_type;
    USER_MODE userMode;

    /**
     *
     * @param RESTfulServerUrl is the url of the RESTful server i.e
     * "https://192.168.1.99:8181/MainServerREST/api/"
     * @param USERNAME the username to authenticate i.e EMPLOYEE or ADMIN
     * @param PASSWORD the password of the account
     * @param
     *
     */
    public SSLConnection(String RESTfulServerUrl) {

        try {
            this.RESTfulServerUrl = RESTfulServerUrl;

            sslContext = SSLContext.getInstance(TLS);
            sslContext.init(new KeyManager[0], new TrustManager[]{new DefaultTrustManager()}, new SecureRandom());
            SSLContext.setDefault(sslContext);
            System.out.println(sslContext.getSocketFactory().toString());
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(SSLConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyManagementException ex) {
            Logger.getLogger(SSLConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Sends an HTTP GET request to servel
     * @return String : Returns the response code in string
     * @param restfulServiceUrl the service you will use i.e customers
     * @param parameter any parameters that you want to pass to the service.
     * Type "" for no parameters
     * @param content_type What you are sending json or plain text i.e
     * @param accept What you are receiving from the server
     * @param authenticate as ADMIN or EMPLOYEE?
     * @return
     * @throws Exception
     */
    public String doGet(String restfulServiceUrl, String parameter, CONTENT_TYPE content_type, ACCEPT_TYPE accept, USER_MODE userMode) throws Exception {

        conType = content_type;
        acc_type = accept;
        this.userMode = userMode;
        String usernamePassword = "";

        if (this.userMode == USER_MODE.ADMIN) {
            usernamePassword = REST_SERVER_ADMIN_USERNAME + ":" + REST_SERVER_ADMIN_PASSWORD;
        } else if (this.userMode == USER_MODE.EMPLOYEE) {
            usernamePassword = REST_SERVER_EMPLOYEE_USERNAME + ":" + REST_SERVER_EMPLOYEE_PASSWORD;
        }

        BASE64Encoder b64 = new BASE64Encoder();
        String endoding = b64.encode(usernamePassword.getBytes());

        URL obj = new URL(RESTfulServerUrl + restfulServiceUrl + "/" + parameter);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String arg0, SSLSession arg1) {
                return true;
            }
        });

        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", "Basic " + endoding);
        String accepts = "";
        String content = "";

        if (conType == CONTENT_TYPE.TEXT) {
            content = "text/plain";
        } else if (conType == CONTENT_TYPE.JSON) {
            content = "application/json";
        }

        con.setRequestProperty("Accept", accepts);
        con.setRequestProperty("Content-Type", content);
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + obj.toString());
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
//        System.out.println(response.toString());
        return response.toString();
    }

    /**
     * Sends an HTTP POST request to servel
     * @return String : Returns the response code in string
     * @param restfulServiceUrl the service you will use i.e customers
     * @param parameter any parameters that you want to pass to the service.
     * Type "" for no parameters
     * @param content_type What you are sending json or plain text i.e
     * @param accept What you are receiving from the server
     * @param authenticate as ADMIN or EMPLOYEE?
     * @return
     * @throws Exception
     */
    public String doPost(String restfulServiceUrl, String parameter, CONTENT_TYPE content_type, ACCEPT_TYPE accept, USER_MODE userMode) throws Exception {

        conType = content_type;
        acc_type = accept;
        this.userMode = userMode;
        String usernamePassword = "";

        if (this.userMode == USER_MODE.ADMIN) {
            usernamePassword = REST_SERVER_ADMIN_USERNAME + ":" + REST_SERVER_ADMIN_PASSWORD;
        } else if (this.userMode == USER_MODE.EMPLOYEE) {
            usernamePassword = REST_SERVER_EMPLOYEE_USERNAME + ":" + REST_SERVER_EMPLOYEE_PASSWORD;
        }

        BASE64Encoder b64 = new BASE64Encoder();
        String endoding = b64.encode(usernamePassword.getBytes());
        URL obj = new URL(RESTfulServerUrl + restfulServiceUrl);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String arg0, SSLSession arg1) {
                return true;
            }
        });

        con.setRequestMethod("POST");
        con.setRequestProperty("Authorization", "Basic " + endoding);
        String accepts = "";
        String content = "";

        if (conType == CONTENT_TYPE.TEXT) {
            content = "text/plain";
        } else if (conType == CONTENT_TYPE.JSON) {
            content = "application/json";
        }

        con.setRequestProperty("Accept", accepts);
        con.setRequestProperty("Content-Type", content);
        
        // Send post request
        con.setDoOutput(true);
        con.setDoInput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.write(parameter.getBytes());
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + obj.toString()+"/"+parameter);
        System.out.println("Response Code : " + responseCode);

     
        return String.valueOf(responseCode);
    }
    
    /**
 * Sends an HTTP PUT request to servel
 * @return String : Returns the response code in string
 * @param restfulServiceUrl the service you will use i.e customers
 * @param parameter any parameters that you want to pass to the service. Type "" for no parameters
 * @param content_type What you are sending json or plain text i.e 
 * @param accept What you are receiving from the server
 * @param authenticate as ADMIN or EMPLOYEE?
 * @return
 * @throws Exception
 */

    public String doPut(String restfulServiceUrl, String parameter, CONTENT_TYPE content_type, ACCEPT_TYPE accept, USER_MODE userMode) throws Exception {

       conType = content_type;
        acc_type = accept;
        this.userMode = userMode;
        String usernamePassword = "";

        if (this.userMode == USER_MODE.ADMIN) {
            usernamePassword = REST_SERVER_ADMIN_USERNAME + ":" + REST_SERVER_ADMIN_PASSWORD;
        } else if (this.userMode == USER_MODE.EMPLOYEE) {
            usernamePassword = REST_SERVER_EMPLOYEE_USERNAME + ":" + REST_SERVER_EMPLOYEE_PASSWORD;
        }

        BASE64Encoder b64 = new BASE64Encoder();
        String endoding = b64.encode(usernamePassword.getBytes());
        URL obj = new URL(RESTfulServerUrl + restfulServiceUrl);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String arg0, SSLSession arg1) {
                return true;
            }
        });

        con.setRequestMethod("PUT");
        con.setRequestProperty("Authorization", "Basic " + endoding);
        String accepts = "";
        String content = "";

        if (conType == CONTENT_TYPE.TEXT) {
            content = "text/plain";
        } else if (conType == CONTENT_TYPE.JSON) {
            content = "application/json";
        }

        con.setRequestProperty("Accept", accepts);
        con.setRequestProperty("Content-Type", content);
        
        // Send post request
        con.setDoOutput(true);
        con.setDoInput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.write(parameter.getBytes());
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'PUT' request to URL : " + obj.toString()+"/"+parameter);
        System.out.println("Response Code : " + responseCode);

     
        return String.valueOf(responseCode);
    }

    /**
 * Sends an HTTP DELETE request to servel
 * @return String : Returns the response code in string
 * @param restfulServiceUrl the service you will use i.e customers
 * @param parameter any parameters that you want to pass to the service. Type "" for no parameters
 * @param content_type What you are sending json or plain text i.e 
 * @param accept What you are receiving from the server
 * @param authenticate as ADMIN or EMPLOYEE?
 * @return
 * @throws Exception
 */
    public String doDelete(String restfulServiceUrl, String parameter, CONTENT_TYPE content_type, ACCEPT_TYPE accept, USER_MODE userMode) throws Exception {

        conType = content_type;
        acc_type = accept;
        this.userMode = userMode;
        String usernamePassword = "";

        if (this.userMode == USER_MODE.ADMIN) {
            usernamePassword = REST_SERVER_ADMIN_USERNAME + ":" + REST_SERVER_ADMIN_PASSWORD;
        } else if (this.userMode == USER_MODE.EMPLOYEE) {
            usernamePassword = REST_SERVER_EMPLOYEE_USERNAME + ":" + REST_SERVER_EMPLOYEE_PASSWORD;
        }

        BASE64Encoder b64 = new BASE64Encoder();
        String endoding = b64.encode(usernamePassword.getBytes());

        URL obj = new URL(RESTfulServerUrl + restfulServiceUrl + "/" + parameter);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String arg0, SSLSession arg1) {
                return true;
            }
        });

        con.setRequestMethod("DELETE");
        con.setRequestProperty("Authorization", "Basic " + endoding);
        String accepts = "";
        String content = "";

        if (conType == CONTENT_TYPE.TEXT) {
            content = "text/plain";
        } else if (conType == CONTENT_TYPE.JSON) {
            content = "application/json";
        }

        con.setRequestProperty("Accept", accepts);
        con.setRequestProperty("Content-Type", content);
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + RESTfulServerUrl);
        System.out.println("Response Code : " + responseCode);

       
        //print result
//        System.out.println(response.toString());
        return String.valueOf(responseCode);

    }

    private static class DefaultTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

}
