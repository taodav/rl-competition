/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package resultgenerator;

/**
 *
 * @author mradkie
 */
public class mdpResult {
    
    eventGenerator events = null;
    
    public mdpResult(eventGenerator events){
        this.events = events;
    }
    
    public mdpResult(String dir){
        this.events = new eventGenerator(dir);
    }
    
}
