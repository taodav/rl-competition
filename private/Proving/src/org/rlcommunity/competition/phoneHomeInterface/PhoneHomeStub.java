/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rlcommunity.competition.phoneHomeInterface;

import java.util.logging.Level;
import java.util.logging.Logger;
import  org.rlcommunity.competition.phoneHomeInterface.exceptions.noMDPsAvailableException;
import java.util.Map;
import java.util.TreeMap;
import  org.rlcommunity.competition.phoneHomeInterface.exceptions.notAuthenticatedException;
import  org.rlcommunity.competition.phoneHomeInterface.exceptions.invalidKeyException;
import  org.rlcommunity.competition.phoneHomeInterface.exceptions.invalidEventException;
import  org.rlcommunity.competition.phoneHomeInterface.exceptions.noKeysAvailableException;
import  org.rlcommunity.competition.proving.Controller;
import  org.rlcommunity.competition.proving.DebugLogger;
import java.io.*;

/**
 *
 * @author btanner
 */
public class PhoneHomeStub implements PhoneHomeConnectionInterface{
    int stepsPerMDP=5000000;
    int keysPerEvent=3;
    int[] MDPsPerEvent=new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1};
    Controller theController=null;
    int[] keysAvailable=new int[]{keysPerEvent,keysPerEvent,keysPerEvent,keysPerEvent,keysPerEvent, keysPerEvent, 1,1,1,1,1, 1};
    
    int[][] keyMDPMap=new int[12][keysPerEvent];
    
    Map<String, Integer> keyToKeyNumMap=new TreeMap<String, Integer>();
    Map<String, Integer> keyToEventNumMap=new TreeMap<String, Integer>();

    public PhoneHomeStub(Controller theController){
        this.theController=theController;
        for(int i=0;i<keyMDPMap.length;i++)
            for(int j=0;j<keyMDPMap[i].length;j++){
                keyMDPMap[i][j]=MDPsPerEvent[i];

            }
    }

    public boolean isAuthenticationValid(AuthToken theAuthToken) {
        if(theAuthToken.getUsername().equals("god")&&theAuthToken.getPassword().equals("password"))return true;
        return true;
    }

    public int howManyKeysAvailable(AuthToken theAuthToken, RLEvent whichEvent) throws invalidEventException, notAuthenticatedException {
        if(!isAuthenticationValid(theAuthToken))throw new notAuthenticatedException();
        if(whichEvent.id()<0||whichEvent.id()>(RLEvent.numEvents()-1))throw new invalidEventException();

        return keysAvailable[whichEvent.id()];
    }

    public String makeNewKey(AuthToken theAuthToken, RLEvent whichEvent) throws notAuthenticatedException, invalidEventException,noKeysAvailableException {
        if(!isAuthenticationValid(theAuthToken))throw new notAuthenticatedException();
        if(whichEvent.id()<0||whichEvent.id()>(RLEvent.numEvents()-1))throw new invalidEventException();
        if(keysAvailable[whichEvent.id()]<=0)throw new noKeysAvailableException();
        
        String newKey="Event:"+whichEvent.id()+"abc"+Math.random();
        int keyNum=keysPerEvent-keysAvailable[whichEvent.id()];
        

        keyToKeyNumMap.put(newKey, keyNum);
        keyToEventNumMap.put(newKey, whichEvent.id());
        keysAvailable[whichEvent.id()]--;
        return newKey;
    }

    public int MDPSRemaining(AuthToken theAuthToken, String theKey) throws invalidKeyException, notAuthenticatedException {
        if(!isAuthenticationValid(theAuthToken))throw new notAuthenticatedException();
        if(theKey==null)throw new invalidKeyException();
        if(!keyToKeyNumMap.containsKey(theKey))throw new invalidKeyException();
        if(!keyToEventNumMap.containsKey(theKey))throw new invalidKeyException();
        int keyNum=keyToKeyNumMap.get(theKey);
        int eventNum=keyToEventNumMap.get(theKey);
        

        return keyMDPMap[eventNum][keyNum];
    }

    public MDPDetails getNextMDP(AuthToken theAuthToken, String theKey) throws invalidKeyException, notAuthenticatedException, noMDPsAvailableException {
//this is new
//It should be the name of the environment AS it will be in the envShell list.
//This means "className".  No package.  We will add "- Java" later.
        String MDPClassName="not set";
            if (!isAuthenticationValid(theAuthToken)) {
                throw new notAuthenticatedException();
            }
            if (theKey == null) {
                throw new invalidKeyException();
            }
            if (!keyToKeyNumMap.containsKey(theKey)) {
                throw new invalidKeyException();
            }
            if (!keyToEventNumMap.containsKey(theKey)) {
                throw new invalidKeyException();
            }
            int keyNum = keyToKeyNumMap.get(theKey);
            int eventNum = keyToEventNumMap.get(theKey);


            int MDPsLeft = MDPSRemaining(theAuthToken, theKey);
            theController.logMessage("MDPsLeft is: "+MDPsLeft);
            
            if (MDPsLeft <= 0) {
                throw new noMDPsAvailableException();
            }
            int MDPNumber = MDPsPerEvent[eventNum] - MDPsLeft;

            String fileName = "";
            if (eventNum == RLEvent.Acrobot.id()) {
                fileName = "MCPMDP" + MDPNumber + ".jar";
            }
            if (eventNum == RLEvent.Tetris.id()) {
                fileName = "tetris/Tetris.jar";
                MDPClassName="TPMDP"+MDPNumber;
            }
            if (eventNum == RLEvent.Helicopter.id()) {
                fileName = "HPMDP" + MDPNumber + ".jar";
            }
            if (eventNum == RLEvent.Octopus.id()) {
                fileName = "RTSPMDP" + MDPNumber + ".txt";
            }
            if (eventNum == RLEvent.Mario.id()) {
                fileName = "keepaway.txt";
            }
            if (eventNum == RLEvent.Testing_Acrobot.id()) {
                fileName = "MCPMDP" + MDPNumber + ".jar";
            }
            if (eventNum == RLEvent.Testing_Tetris.id()) {
                fileName = "TPMDP" + MDPNumber + ".jar";
            }
            if (eventNum == RLEvent.Testing_Helicopter.id()) {
                fileName = "HPMDP" + MDPNumber + ".jar";
            }
            if (eventNum == RLEvent.Testing_Octopus.id()) {
                fileName = "RTSPMDP" + MDPNumber + ".txt";
            }
            if (eventNum == RLEvent.Testing_Mario.id()) {
                fileName = "keepaway.txt";
            }
            String debugJarDir = "../proving-debug-jars/";
            String destJarDir="../system/proving/";

            String fullFilePath = debugJarDir + fileName;
            File sourceJarFile = new File(fullFilePath);
            if (!sourceJarFile.exists()) {
                theController.dieWithErrorMessage("You suck, the jar file: " + sourceJarFile.getName() + " isn't there\nFull path: "+sourceJarFile.getAbsolutePath());
            }
        try {
            Process P = Runtime.getRuntime().exec(new String[]{"cp", fullFilePath, destJarDir});
            P.waitFor();
                        File destJarFile = new File(destJarDir+fileName);
                       destJarFile.deleteOnExit();

        } catch (Exception ex) {
            Logger.getLogger(PhoneHomeStub.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            MDPDetails theMDP = new MDPDetails(fileName, MDPClassName,RLEvent.eventFromNumber(eventNum),MDPNumber, theKey, stepsPerMDP);
            keyMDPMap[eventNum][keyNum]--;
            return theMDP;
    }

    public void sendResults(AuthToken theAuthToken, String theKey, ResultDetails theResult) throws invalidKeyException, notAuthenticatedException {
        if(!isAuthenticationValid(theAuthToken))throw new notAuthenticatedException();
        if(theKey==null)throw new invalidKeyException();
        if(!keyToKeyNumMap.containsKey(theKey))throw new invalidKeyException();
        if(!keyToEventNumMap.containsKey(theKey))throw new invalidKeyException();
        theController.logMessage("Results received in PHONEHOMESTUB\n");
        BufferedReader resultFileReader = null;
        try {
            resultFileReader = new BufferedReader(new FileReader(theResult.getDataFileName().substring(0,theResult.getDataFileName().length()-3)));
            String inputLine = null;
            int section = 0;
            while ((inputLine = resultFileReader.readLine()) != null) {
                if (inputLine.equals("#####"))
                    section++;
                else if (section > 0) {
                    DebugLogger.log("resFile", inputLine);
                }

            }
            resultFileReader.close();
        } catch(java.io.IOException e) {
            System.err.println("Could not open result file. " + e);
            System.exit(1);
        }
    }

}
