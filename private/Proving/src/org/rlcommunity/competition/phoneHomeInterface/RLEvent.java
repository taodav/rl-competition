/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.rlcommunity.competition.phoneHomeInterface;

/**
 *
 * @author btanner
 */
public enum RLEvent {
	Acrobot(1),
	Helicopter(2),
	Octopus(3),
	Tetris(4),
    Polyathlon(5),
    Mario(6),

	Testing_Acrobot(7),
    Testing_Helicopter(8),
	Testing_Octopus(9),
	Testing_Tetris(10),
	Testing_Polyathlon(11),
    Testing_Mario(12);

    private final int id;
	
	RLEvent(int id){
        this.id = id;
    }

    public int id()   {return id;}

    public static RLEvent eventFromNumber(int ord){
        switch (ord) {
            case 0: return RLEvent.Acrobot;
            case 1: return RLEvent.Helicopter;
            case 2: return RLEvent.Octopus;
            case 3: return RLEvent.Tetris;
            case 4: return RLEvent.Polyathlon;
            case 5: return RLEvent.Mario;
            case 6: return RLEvent.Testing_Acrobot;
            case 7: return RLEvent.Testing_Helicopter;
            case 8: return RLEvent.Testing_Octopus;
            case 9: return RLEvent.Testing_Tetris;
            case 10: return RLEvent.Testing_Polyathlon;
            case 11: return RLEvent.Testing_Mario;
        }
        return null;
    }

    public int number() {
        return numberFromID(this.id);
    }

    public static int numberFromID(int id){
        if(id==RLEvent.Helicopter.id())return 1;
        if(id==RLEvent.Acrobot.id())return 0;
        if(id==RLEvent.Tetris.id())return 3;
        if(id==RLEvent.Octopus.id())return 2;
        if(id==RLEvent.Polyathlon.id())return 4;
        if(id==RLEvent.Mario.id())return 5;
        if(id==RLEvent.Testing_Helicopter.id())return 7;
        if(id==RLEvent.Testing_Acrobot.id())return 6;
        if(id==RLEvent.Testing_Tetris.id())return 9;
        if(id==RLEvent.Testing_Octopus.id())return 8;
        if(id==RLEvent.Testing_Polyathlon.id())return 10;
        if(id==RLEvent.Testing_Mario.id())return 11;

        return -1;
    }

    public static RLEvent eventFromID(int id){
    if(id==RLEvent.Helicopter.id())return RLEvent.Helicopter;
    if(id==RLEvent.Acrobot.id())return RLEvent.Acrobot;
    if(id==RLEvent.Tetris.id())return RLEvent.Tetris;
    if(id==RLEvent.Octopus.id())return RLEvent.Octopus;
    if(id==RLEvent.Polyathlon.id())return RLEvent.Polyathlon;
    if(id==RLEvent.Mario.id())return RLEvent.Mario;
    if(id==RLEvent.Testing_Helicopter.id())return RLEvent.Testing_Helicopter;
    if(id==RLEvent.Testing_Acrobot.id())return RLEvent.Testing_Acrobot;
    if(id==RLEvent.Testing_Tetris.id())return RLEvent.Testing_Tetris;
    if(id==RLEvent.Testing_Octopus.id())return RLEvent.Testing_Octopus;
    if(id==RLEvent.Testing_Polyathlon.id())return RLEvent.Testing_Polyathlon;
    if(id==RLEvent.Testing_Mario.id())return RLEvent.Testing_Mario;
    
    return null;
        }

    public static int numEvents(){return 12;}
//For convenience
    public static String name(int id){
        if(id == Helicopter.id())return "Helicopter";
        if(id == Acrobot.id())return "Acrobot";
        if(id == Tetris.id())return "Tetris";
        if(id == Octopus.id())return "Octopus";
        if(id == Polyathlon.id())return "Polyathlon";
        if(id == Mario.id())return "Mario";
        if(id == Testing_Helicopter.id())return "Testing_Helicopter";
        if(id == Testing_Acrobot.id())return "Testing_Acrobot";
        if(id == Testing_Tetris.id())return "Testing_Tetris";
        if(id == Testing_Octopus.id())return "Testing_Octopus";
        if(id == Testing_Polyathlon.id())return "Testing_Polyathlon";
        if(id == Testing_Mario.id())return "Testing_Mario";
        return "Type: "+id+" is unknown Event Type";
    }

    public static String name(RLEvent theEvent) {
        return name(theEvent.id());
    }
}
