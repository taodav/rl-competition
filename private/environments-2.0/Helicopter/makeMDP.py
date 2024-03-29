import GenParamSamples
import os

method1 = "setWind0"
method2 = "setWind1"

for i in range(100):
	os.mkdir('./src/HPMDP%d'% (i))
	f = open('./src/HPMDP%d/HPMDP%d.java'% (i,i),'w')

	f.write("""
/*
 * This code was auto-generated by the parameter script. Do not change these values manually.
 */
package HPMDP%d;

import GeneralizedHelicopter.*;

public class HPMDP%d extends AbstractProvingMDPHelicopter{

    public HPMDP%d(){
	super();
        %s(%fd);
    	%s(%fd);
    }
}"""  % (i,i,i, method1, GenParamSamples.sampleP1(i), method2, GenParamSamples.sampleP2(i)))
	f.close()
