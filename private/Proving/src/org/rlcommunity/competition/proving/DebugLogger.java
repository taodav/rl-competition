/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rlcommunity.competition.proving;

/**
 *
 * @author mark
 */
public class DebugLogger {

    private static final boolean debug = false;
    
    public static void log(String source, String message) {
        if (debug)
            System.out.println(source + ": " + message);
    }
}
