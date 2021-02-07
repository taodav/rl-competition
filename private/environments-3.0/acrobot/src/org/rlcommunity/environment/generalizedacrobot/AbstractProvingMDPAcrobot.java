package org.rlcommunity.environment.generalizedacrobot;
import  org.rlcommunity.environment.acrobot.Acrobot;
/**
 *
 * @author JAMH
 */
public abstract class AbstractProvingMDPAcrobot extends Acrobot {
	
	
	   public static void main(String [ ] args){
	   	System.out.println("Hello, I am proving MDP of Acrobot RL Competition 20009");
	   }

        
        public AbstractProvingMDPAcrobot() {
            super();
            //super.recordLogs=true;
            //super.allowSaveLoadSeed=false;
            //super.allowSaveLoadState=false;
        }
        
}
