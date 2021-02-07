package org.rlcommunity.competition.phoneHomeInterface;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.httpclient.URIException;
import  org.rlcommunity.competition.phoneHomeInterface.exceptions.notAuthenticatedException;
import  org.rlcommunity.competition.phoneHomeInterface.exceptions.invalidKeyException;
import  org.rlcommunity.competition.phoneHomeInterface.exceptions.invalidEventException;
import java.io.*;
import  org.rlcommunity.competition.phoneHomeInterface.exceptions.httpFailureException;
import  org.rlcommunity.competition.phoneHomeInterface.exceptions.noMDPsAvailableException;
import  org.rlcommunity.competition.proving.Controller;
import org.apache.commons.httpclient.URI;
import org.rlcommunity.competition.proving.ProvingApp;

public class PhoneHomeConnection implements PhoneHomeConnectionInterface {

    //private final String serverURL           = "http://rl-competition.org/rlcompserver";
    private final String serverURL           = "http://2009.rl-competition.org:12132";
    private final String baseKeysURL         = serverURL + "/events/";
    private final String baseResultsURL      = serverURL + "/keys/";
    private final String authenticateURL     = serverURL + "/phone_home_authentication";
    private final String keysAvailableURL    = "/keys/new";
    private final String makeKeyURL          = "/keys";
    private final String mdpsRemainingURL    = "/results;mdps_remaining";
    private final String jarInfoURL          = "/results/new";
    private final String getJarURL           = "/results;get_jar";
    private final String sendResultsURL      = "/results";

    private final String localJarPath        = "../system/proving/";

    private Controller theController=null;

    private String prefixWithEvent(String url, RLEvent whichEvent) {
        return baseKeysURL + (whichEvent.id()) + url;
    }
    
    private String prefixWithKey(String url, String key) {
        return baseResultsURL + key + url;
    }
    
    private String authURLParams(AuthToken theAuthToken) {
        return "?login=" + theAuthToken.getUsername() + "&password=" + theAuthToken.getPassword();
    }
    
    private String versionURLParams() {
        URI versionURI = null;
        String provingAppVersion = ProvingApp.class.getPackage().getImplementationVersion();
        String phoneHomeVersion = getClass().getPackage().getImplementationVersion();
        String versionString = "rlVizVersion=" + rlVizLib.rlVizCore.getVersion();
        versionString += "&provingAppVersion=" + provingAppVersion;
        versionString += "&phoneHomeVersion=" + phoneHomeVersion;
        
        try {
            versionURI = new URI(versionString, false);       
        } catch (URIException ex) {
            Logger.getLogger(PhoneHomeConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {
            Logger.getLogger(PhoneHomeConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return versionURI.toString();
    }
    
    public String allParams(AuthToken theAuthToken) {
        return authURLParams(theAuthToken) + "&" +  versionURLParams();
    }

    public PhoneHomeConnection(Controller theController) {
        this.theController=theController;
    }


    public boolean isAuthenticationValid(AuthToken theAuthToken) {
        boolean isValid = false;
        HTTPConnection con = new HTTPConnection(authenticateURL + allParams(theAuthToken), "POST");
        try {
            con.execute();
            con.close();
        } catch(httpFailureException e) {
            if (e.getStatus() == 401)
                return false;
            else {
                System.err.println("An http error occured. Got status " + e.getStatus());
                Thread.dumpStack();
                System.exit(1);
            }
        }
        return true;
    }

    public int howManyKeysAvailable(AuthToken theAuthToken, RLEvent whichEvent) throws invalidEventException, notAuthenticatedException {
        if (RLEvent.eventFromID(whichEvent.id()) == null)
            throw new invalidEventException();
        HTTPConnection con = new HTTPConnection(prefixWithEvent(keysAvailableURL,whichEvent) + allParams(theAuthToken), "GET");
        BufferedReader response = null;

        try {
            response = con.getResponse();
        } catch(httpFailureException e) {
            if (e.getStatus() == 401)
                throw new notAuthenticatedException();
            else if(e.getStatus() == 500){
                System.err.println("We are assuming there was a problem getting the keys for the testing domains");
                return 0;
            }
            else {
                System.err.println("An http error occured. Got status " + e.getStatus());
                                Thread.dumpStack();

                System.exit(1);
            }
        }
        int keysAvailable = 0;
        try {
            keysAvailable = Integer.parseInt(response.readLine());
        } catch(java.io.IOException e) {
            System.err.println("IO Execption: " + e);
            System.exit(1);
        }
        con.close();
        return keysAvailable;
    }

    public String makeNewKey(AuthToken theAuthToken, RLEvent whichEvent) throws notAuthenticatedException,invalidEventException {
        if (RLEvent.eventFromID(whichEvent.id()) == null)
            throw new invalidEventException();
        HTTPConnection con = new HTTPConnection(prefixWithEvent(makeKeyURL,whichEvent) + allParams(theAuthToken), "POST");
        BufferedReader response = null;
        try {
            response = con.getResponse();
        } catch(httpFailureException e) {
            if (e.getStatus() == 401)
                throw new notAuthenticatedException();
            else {
                System.err.println("An http error occured. Got status " + e.getStatus());
                                Thread.dumpStack();

                System.exit(1);
            }
        }
        String key = null;
        try {
            key = response.readLine();
        } catch(java.io.IOException e) {
            System.err.println("IO Exception: " + e);
            System.exit(1);
        }
        con.close();
        return key;
    }

    public int MDPSRemaining(AuthToken theAuthToken, String theKey) throws invalidKeyException, notAuthenticatedException {
        HTTPConnection con = new HTTPConnection(prefixWithKey(mdpsRemainingURL,theKey) + allParams(theAuthToken), "GET");
        BufferedReader response = null;
        int mdps_remaining = 0;
        try {
            response = con.getResponse();
        } catch(httpFailureException e) {
            handleFailure(e);
        }
        try {
            mdps_remaining = Integer.parseInt(response.readLine());
        } catch(java.io.IOException e) {
            System.err.println("IO Exception: " + e);
            System.exit(1);
        }
        con.close();
        return mdps_remaining;
    }

    public MDPDetails getNextMDP(AuthToken theAuthToken, String theKey) throws invalidKeyException, notAuthenticatedException,noMDPsAvailableException {
        HTTPConnection con = new HTTPConnection(prefixWithKey(jarInfoURL,theKey) + allParams(theAuthToken), "GET");
        BufferedReader response = null;
        String jarName = null;
        String jarFullName = localJarPath;
        
        //Check if the destination directory for the jars is there and if not, make it!
        File destDir=new File(localJarPath);
        destDir.mkdirs();
        
        int event_id = 0;
        int mdp_id = 0;
        int total_steps = 0;
        int log_frequency = 0;
        String MDPClassName=null;
        try {
            response = con.getResponse();
        } catch (httpFailureException e) {
            handleFailure(e);
        }
        try {
            event_id = Integer.parseInt(response.readLine());
            jarName = response.readLine();

            jarFullName += "/" + jarName;
            mdp_id = Integer.parseInt(response.readLine());

            total_steps = Integer.parseInt(response.readLine());
            log_frequency = Integer.parseInt(response.readLine());
            MDPClassName=response.readLine();

        } catch(java.io.IOException e) {
            System.err.println("IO Exception: " + e);
            System.exit(1);
        }
        con.close();
        con = new HTTPConnection(prefixWithKey(getJarURL,theKey) + allParams(theAuthToken), "GET");
        try {
            con.saveResponse(jarFullName);
            new File(jarFullName).deleteOnExit();
        } catch(httpFailureException e) {
            handleFailure(e);
        }
        con.close();
        
        MDPDetails theDetails=new MDPDetails(jarName,MDPClassName,RLEvent.eventFromID(event_id),mdp_id,theKey,total_steps, log_frequency);
        return theDetails;
    }

    public void sendResults(AuthToken theAuthToken, String theKey, ResultDetails theResult) throws invalidKeyException, notAuthenticatedException {
        HTTPConnection con = new HTTPConnection(prefixWithKey(sendResultsURL,theKey) + allParams(theAuthToken) + "&mdp_id=" + theResult.getMDPId(), "POST");
        try { 
            con.sendFile(theResult.getDataFileName());
            con.close();
        } catch(httpFailureException e) {
            handleFailure(e);
        }
        con.close();
    }

    private void handleFailure(httpFailureException e) throws invalidKeyException, notAuthenticatedException{
        if (e.getStatus() == 404)
            throw new invalidKeyException();
        else if (e.getStatus() == 401)
            throw new notAuthenticatedException();
        else {
            System.err.println("An http error occured. Got status " + e.getStatus());
            Thread.dumpStack();
            System.exit(1);
        }
    }
    
    public static void main(String[] args){
        System.out.println(PhoneHomeConnection.class.getPackage().getImplementationVersion());
    
    
    }
}