/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rlcommunity.competition.phoneHomeInterface;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.rlcommunity.competition.phoneHomeInterface.exceptions.httpFailureException;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.methods.multipart.*;
import  org.rlcommunity.competition.phoneHomeInterface.exceptions.connectionError;

/**
 *
 * @author mark
 */
public class HTTPConnection {
    
    HttpClient client = new HttpClient();
    HttpMethod method = null;
    
    public HTTPConnection(String urlString, String method) {
        if (method.equals("GET"))
            this.method = new GetMethod(urlString);
        else if (method.equals("POST"))
            this.method = new PostMethod(urlString);
    }
    
    public void execute() throws httpFailureException, connectionError {
        int status = 0;
        try {
            //IOException, HTTPException
            status = this.client.executeMethod(this.method);
        } catch (HttpException ex) {
            Logger.getLogger(HTTPConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            throw new connectionError("A connection error occured");
        }
        if (status != 200)
            throw new httpFailureException(status);
    }
    
    // TODO: need to throw an exception when status is not 200
    // differentiate between invalidKey and notAuthenticated
    public BufferedReader getResponse() throws httpFailureException, connectionError {
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        int status = 0;
        try {
            //IOException, HTTPException
            status = this.client.executeMethod(this.method);
        } catch (HttpException ex) {
            Logger.getLogger(HTTPConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            throw new connectionError("A connection error occured");
        }
        if (status != 200) {
            throw new httpFailureException(status);
        }
        try {
            is = this.method.getResponseBodyAsStream();
        } catch (IOException ex) {
            Logger.getLogger(HTTPConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        isr = new InputStreamReader(is);
        br = new BufferedReader(isr);
        return br;
    }
    
    public void saveResponse(String fileName) throws httpFailureException, connectionError {
        InputStream is = null;
        BufferedInputStream bufInputStream = null;
        FileOutputStream fileStream = null;
        BufferedOutputStream bufFileStream = null;
        int status = 0;
        int buffer = 0;
        try {
            //IOException, HTTPException
            status = this.client.executeMethod(this.method);
        } catch (HttpException ex) {
            Logger.getLogger(HTTPConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            throw new connectionError("A connection error occured");
        }
        if (status != 200) {
            throw new httpFailureException(status);
        }
        try {
            is = this.method.getResponseBodyAsStream();
            bufInputStream = new BufferedInputStream(is);
        } catch (IOException ex) {
            Logger.getLogger(HTTPConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            fileStream = new FileOutputStream(fileName);
            bufFileStream = new BufferedOutputStream(fileStream);
            while ((buffer = bufInputStream.read()) != -1) {
                bufFileStream.write(buffer);
            }
            bufFileStream.flush();
            bufFileStream.close();
            bufInputStream.close();
            
            //Schedule the file to be deleted when the program is complete.  Not completely ideal, but ok
            new File(fileName).deleteOnExit();
                  
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HTTPConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HTTPConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendFile(String fileName) throws httpFailureException, connectionError {
        int status = 0;
        File f = null;
        PostMethod filePost = null;
        try {
            f = new File(fileName);
            filePost = (PostMethod) this.method;
            Part[] parts = {new FilePart("results", f)};
            filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HTTPConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HTTPConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            status = this.client.executeMethod(filePost);
        } catch (HttpException ex) {
            Logger.getLogger(HTTPConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch(IOException ex) {
            throw new connectionError("A connection error occured");
        } 
        if (status != 200) {
            throw new httpFailureException(status);
        }
    }

    public void close() {
        this.method.releaseConnection();
    }
}
