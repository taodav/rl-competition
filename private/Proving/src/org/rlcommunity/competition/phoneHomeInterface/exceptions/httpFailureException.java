/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rlcommunity.competition.phoneHomeInterface.exceptions;

/**
 *
 * @author mark
 */
public class httpFailureException extends Exception {

    private int status;
    
    public httpFailureException(int status) {
        this.status = status;
    }
    
    public int getStatus() {
        return this.status;
    }
}
