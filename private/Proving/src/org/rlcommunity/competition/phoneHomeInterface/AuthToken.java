/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rlcommunity.competition.phoneHomeInterface;

/**
 *
 * @author btanner
 */
public class AuthToken {
private String username;
private String password;

public AuthToken(String username, String password){
    this.username=username;
    this.password=password;
}

public String getUsername(){return username;}
public String getPassword(){return password;}
}
