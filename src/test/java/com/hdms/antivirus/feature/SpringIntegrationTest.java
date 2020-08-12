package com.hdms.antivirus.feature;

import com.hdms.antivirus.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import sun.net.www.protocol.http.HttpURLConnection;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration
//#TODO This is a dirty Code, Need to be refactor it has reached only till green cycle.
public class SpringIntegrationTest {
    static ResponseResults latestResponse = null;
    static VirusScanResponse virusScanResponse = null;

    @Autowired
    protected RestTemplate restTemplate;

    void executeGet(String url) {
        final Map<String, String> headers = new HashMap<> ();
        headers.put ( "Accept", "application/json" );
        final HeaderSettingRequestCallback requestCallback = new HeaderSettingRequestCallback ( headers );
        final ResponseResultErrorHandler errorHandler = new ResponseResultErrorHandler ();

        restTemplate.setErrorHandler ( errorHandler );
        latestResponse = restTemplate.execute ( url, HttpMethod.GET, requestCallback, response -> {
            if (errorHandler.hadError) {
                return (errorHandler.getResults ());
            } else {
                return (new ResponseResults ( response ));
            }
        } );
    }

    void executePost(String url, Map<String, String> param) {
        final Map<String, String> headers = new HashMap<> ();
        headers.put ( "Accept", "application/json" );
        final HeaderSettingRequestCallback requestCallback = new HeaderSettingRequestCallback ( headers );
        final ResponseResultErrorHandler errorHandler = new ResponseResultErrorHandler ();

        if (restTemplate == null) {
            restTemplate = new RestTemplate ();
        }

        restTemplate.setErrorHandler ( errorHandler );
        Map<String, ?> urivariable = param;
        latestResponse = restTemplate
                .execute ( url, HttpMethod.POST, requestCallback, response -> {
                    if (errorHandler.hadError) {
                        return (errorHandler.getResults ());
                    } else {
                        return (new ResponseResults ( response ));
                    }
                }, urivariable );
        System.out.println ( latestResponse.getTheResponse () );
    }

    private class ResponseResultErrorHandler implements ResponseErrorHandler {
        private ResponseResults results = null;
        private Boolean hadError = false;

        private ResponseResults getResults() {
            return results;
        }

        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            hadError = response.getRawStatusCode () >= 400;
            return hadError;
        }

        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            results = new ResponseResults ( response );
        }
    }

    //#TODO Need to clean the method it is copy pest method
    //Need to replace this method with https://www.baeldung.com/spring-rest-template-multipart-upload
    public String sendPostWithAttachedFile(String url, Map<String, String> param) {

        String charset = "UTF-8";
        String attachmentFilePath = param.get ( "file" );
        File binaryFile = new File ( attachmentFilePath );
        String boundary = "------------------------" + Long.toHexString ( System.currentTimeMillis () ); // Just generate some unique random value.
        String CRLF = "\r\n"; // Line separator required by multipart/form-data.
        try {
            //Set POST general headers along with the boundary string (the seperator string of each part)
            URLConnection connection = new URL ( url ).openConnection ();
            connection.setDoOutput ( true );
            connection.setRequestProperty ( "Content-Type", "multipart/form-data; boundary=" + boundary );
            connection.addRequestProperty ( "User-Agent", "CheckpaySrv/1.0.0" );
            connection.addRequestProperty ( "Accept", "*/*" );

            OutputStream output = connection.getOutputStream ();
            PrintWriter writer = new PrintWriter ( new OutputStreamWriter ( output, charset ), true );

            // Send binary file - part
            // Part header
            writer.append ( "--" + boundary ).append ( CRLF );
            writer.append ( "Content-Disposition: form-data; name=\"file\"; filename=\"" + binaryFile.getName () + "\"" ).append ( CRLF );
            writer.append ( "Content-Type: application/octet-stream" ).append ( CRLF );// + URLConnection.guessContentTypeFromName(binaryFile.getName())).append(CRLF);
            writer.append ( CRLF ).flush ();

            // File data
            Files.copy ( binaryFile.toPath (), output );
            output.flush ();

            // End of multipart/form-data.
            writer.append ( CRLF ).append ( "--" + boundary + "--" ).flush ();

            HttpURLConnection connection1 = (HttpURLConnection) connection;
            if (connection1.getResponseCode () != 200) //We operate only on HTTP code 200
                return "";
            InputStream instream = connection1.getInputStream ();
            InputStreamReader isReader = new InputStreamReader ( instream );

            BufferedReader reader = new BufferedReader ( isReader );
            StringBuffer sb = new StringBuffer ();
            String responseMsg;
            while ((responseMsg = reader.readLine ()) != null) {
                sb.append ( responseMsg );
            }

            virusScanResponse = new VirusScanResponse ( connection1.getResponseCode() ,sb.toString () );
            return sb.toString ();

        } catch (Exception e) {
            e.printStackTrace ();
        }
        return "";
    }

}