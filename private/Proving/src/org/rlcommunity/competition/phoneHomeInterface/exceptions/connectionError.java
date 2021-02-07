/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rlcommunity.competition.phoneHomeInterface.exceptions;

/**
 *
 * @author mark
 */
public class connectionError extends RuntimeException {

    public connectionError(String message) {
        super(message);
    }
    
}
