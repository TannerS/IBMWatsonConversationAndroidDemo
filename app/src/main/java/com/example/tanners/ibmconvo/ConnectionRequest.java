package com.example.tanners.ibmconvo;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.tanners.ibmconvo.data.WatsonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
//import java.util.Base64;
import android.util.Base64;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

//import java.nio.charset.StandardCharsets;

public class ConnectionRequest {
    // type of connection
    public static enum TYPES {
        POST("POST"),
        GET("GET");

        private String type;

        TYPES(String type) {
            this.type = type;
        }

        public String type() {
            return type;
        }

    }

    private HttpURLConnection connection;
    private int mConnectionTimeOut;
    private int mReadTimeOut;
    private HashMap<String, String> mEntries;
    private boolean mIsGood;
    private String mBody;
    private String mUrl;
    private String mRequestype;
    private PrintWriter writer;
    private String mCharset;
    private final String LINE_BREAK = "\r\n";


    public ConnectionRequest(String mUrl) {
        mEntries = new HashMap<String, String>();
        mConnectionTimeOut = 20000;
        mReadTimeOut = 15000;
        mBody = null;
        mIsGood = false;
        mRequestype = TYPES.POST.toString();
//        mRequestype = TYPES.GETGET.toString();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            mCharset = (StandardCharsets.UTF_8.name().toLowerCase(Locale.getDefault()));
        else
            mCharset = "utf-8";

        this.mUrl = mUrl;
    }

    /**
     * set connecton type
     * @param type
     */
    public void setRequestType(TYPES type) {
        mRequestype = type.toString();
    }

    /**
     * charste for data
     * @param charset
     */
    public void setCharSet(String charset)
    {
        mCharset = charset;
    }

    /**
     * timeout
     * @param time
     */
    public void setConnectionTimeOut(int time)
    {
        mConnectionTimeOut = time;
    }

    /**
     * reading timeout
     * @param time
     */
    public void setReadTimeOut(int time)
    {
        mReadTimeOut = time;
    }

    /**
     * add passed in headers to http packet
     * @param entries
     */
//    public void addRequestHeader(HashMap<String, String> entries)
//    {
//        if(entries != null) {
//
//            for (Map.Entry<String, String> entry : entries.entrySet()) {
//                String key = (entry.getKey()).trim();
//                String value = (entry.getValue()).trim();
//                this.mEntries.put(key, value);
//            }
//        }
//    }

    /**
     * add passed in header to http packet
     * @param key
     * @param value
     */
    public void addRequestHeader(String key, String value)
    {
        if(mEntries != null) {
            this.mEntries.put(key, value);
            Log.i("DEBUG1", "DEBUG1");
        }
        else
        {
            mEntries = new HashMap<String, String>();
            mEntries.put(key,value);
        }
    }

    /**
     * add body
     * @param body
     */
    public void addBasicBody(String body)
    {
        this.mBody = body;
    }

    /**
     * this will set the headers into the connection since the add header methods can be called multiple times
     */
    private void setHeaders()
    {
        if(mEntries != null) {

            for (Map.Entry<String, String> entry : mEntries.entrySet()) {
                String key = (entry.getKey()).trim();
                String value = (entry.getValue()).trim();
                connection.setRequestProperty(key, value);
            }
        }
    }

    /**
     * set body
     */
    private void setBody()
    {
//        if(mBody == null || mBody.length() <= 0)
//             set body length
//            connection.setFixedLengthStreamingMode(0);
//        else {
//             set body length
//            connection.setFixedLengthStreamingMode(mBody.length());

            try {
                // open stream to url
                writer = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(), mCharset), false);
                // write to stream
                writer.append(LINE_BREAK);
                writer.append(mBody);

                Log.i("CONNECTION", "CONNECTED: " + mBody);

                writer.flush();
                // close stream
                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {

            }
//        }
    }

    /**
     * defauly settings
     */
    private void setDefaults()
    {
//        if(mBody != null)
            // means we will be sending content
            connection.setDoOutput(true);
//        else
            // means we will be NOT sending content
//            connection.setDoOutput(false);
        // allow redirects
        connection.setInstanceFollowRedirects(true);
        // timeouts
        connection.setConnectTimeout(mConnectionTimeOut);
        connection.setReadTimeout(mReadTimeOut);
        // caches, not tested
        connection.setUseCaches(true);
        try {
            // method type
            connection.setRequestMethod(mRequestype);
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        // set charset
        connection.setRequestProperty("charset", mCharset);
    }

    /**
     * connect to url
     * @return
     */
    public boolean connect()
    {
        try
        {
            // open connect from url object
            connection = (HttpURLConnection) (new URL(mUrl)).openConnection();
            // set options
            mIsGood = true;

            setDefaults();
            setHeaders();
            setBody();
            Log.i("CONNECTION", "GOOD");

        }
        catch (IOException e)
        {
            Log.i("CONNECTION", "BAD");
            mIsGood = false;
            e.printStackTrace();
        }

        if(connection == null)
            Log.i("CONNECTION", "IS NULL");


        return mIsGood;
    }


    public void setBasicAuth(String username, String password)
    {
        String encoded = null;  //Java 8
//            encoded = Base64.getEncoder().encodeToString((username+":"+password).getBytes(StandardCharsets.UTF_8));
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            encoded =  java.util.Base64.getEncoder().encodeToString((username+":"+password).getBytes(StandardCharsets.UTF_8));

//            Log.i("BASE64", encoded);


            byte[] bytes = (username+":"+password).getBytes(StandardCharsets.UTF_8);
            encoded =  Base64.encodeToString(bytes, Base64.DEFAULT);

            Log.i("BASE64", encoded);
//            encoded =  Base64.encodeToString(bytes, Base64.NO_PADDING);


//            Log.i("BASE64", encoded);
//
//            encoded =  Base64.encodeToString(bytes, Base64.NO_WRAP);
//
//            Log.i("BASE64", encoded);
//            encoded =  Base64.encodeToString(bytes, Base64.NO_CLOSE);
//
//            Log.i("BASE64", encoded);
//


//            encoded =  Base64.encodeToString(username+":"+password).getBytes(StandardCharsets.UTF_8);
//        }

//        Log.i("AUTH", encoded);

//        addRequestHeader("Authorization", "Basic "+encoded);
        addRequestHeader("Authorization", "Basic OWEwNTdmMmYtYzI3Yi00N2RhLTg5NzktODYxY2Q5N2VhZmYxOnp2MFdXWENXTjdHMA==");
    }

//    public String getResponse() throws IOException {
//        BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
//        StringBuilder sb = new StringBuilder();
//        String output;
//
//        while ((output = br.readLine()) != null) {
//            sb.append(output);
//            return sb.toString();
//        }
//
//        return sb.toString();
//    }

    public WatsonResponse getResponse() throws IOException {

        if(connection.getErrorStream() != null) {
            String err = IOUtils.toString(connection.getErrorStream());

            Log.i("OUTPUT", err);
            Log.i("OUTPUT", mBody);

        }


        String response = IOUtils.toString(connection.getInputStream());

        Log.i("OUTPUT", response);

        ObjectMapper objectMapper = new ObjectMapper();
        // map objects from json
        WatsonResponse watsonResponse = (objectMapper.readValue(response, WatsonResponse.class));
        // close connection
        closeConnection();
        return watsonResponse;

    }


    /**
     * close connection
     */
    public void closeConnection()
    {
        if(connection != null)
        {
            connection.disconnect();
        }
    }

    /**
     * get current connection
     * @return
     */
    public HttpURLConnection getConnection()
    {
        if(mIsGood)
            return this.connection;
        else
            return null;
    }

    /**
     * check if connection is good
     * @return
     */
    public boolean isGood()
    {
        return mIsGood;
    }
}