
    package MCPMDP60;

    import GeneralizedMountainCar.*;

    public class MCPMDP60 extends AbstractProvingMDPMountainCar {

        public MCPMDP60() {
            super();
                setScaleP(0.352157d);
                setScaleV(0.278500d);
                setPOffset(0.068350d);
                setVOffset(-0.260751d);
                setPNoiseDivider(8.441786d);
                setVNoiseDivider(17.619136d);
                setAccelBiasMean(1.166678d);
        }
    }