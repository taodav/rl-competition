/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rlcommunity.competition.phoneHomeInterface;

import  org.rlcommunity.competition.phoneHomeInterface.exceptions.notAuthenticatedException;
import  org.rlcommunity.competition.phoneHomeInterface.exceptions.invalidKeyException;
import  org.rlcommunity.competition.phoneHomeInterface.exceptions.invalidEventException;
import  org.rlcommunity.competition.phoneHomeInterface.exceptions.noKeysAvailableException;
import  org.rlcommunity.competition.phoneHomeInterface.exceptions.noMDPsAvailableException;

/**
 *
 * @author btanner
 */
public interface PhoneHomeConnectionInterface {
    boolean isAuthenticationValid(AuthToken theAuthToken);

    int howManyKeysAvailable(AuthToken theAuthToken, RLEvent whichEvent) throws invalidEventException, notAuthenticatedException;
    String makeNewKey(AuthToken theAuthToken, RLEvent whichEvent) throws notAuthenticatedException,invalidEventException,noKeysAvailableException;

        
    int MDPSRemaining(AuthToken theAuthToken, String theKey) throws invalidKeyException, notAuthenticatedException;
    MDPDetails getNextMDP(AuthToken theAuthToken, String theKey) throws invalidKeyException, notAuthenticatedException,noMDPsAvailableException;

    void sendResults(AuthToken theAuthToken, String theKey, ResultDetails theResult) throws invalidKeyException, notAuthenticatedException;
}
