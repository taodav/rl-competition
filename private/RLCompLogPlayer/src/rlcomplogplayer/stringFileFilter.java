/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rlcomplogplayer;

import java.io.File;
import java.io.FileFilter;

/**
 *
 * @author mradkie
 */
public class stringFileFilter implements FileFilter {

    private String filterString;
    
    public stringFileFilter(String filterString){
        this.filterString = new String(filterString);
    }
    public boolean accept(File pathName) {
        String stringPath=pathName.getPath();
        return stringPath.contains(filterString);
    }

}