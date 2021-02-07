
    package MCPMDP91;

    import GeneralizedMountainCar.*;

    public class MCPMDP91 extends AbstractProvingMDPMountainCar {

        public MCPMDP91() {
            super();
                setScaleP(0.278406d);
                setScaleV(0.404252d);
                setPOffset(-0.488133d);
                setVOffset(0.652215d);
                setPNoiseDivider(6.207507d);
                setVNoiseDivider(18.184001d);
                setAccelBiasMean(1.022600d);
        }
    }