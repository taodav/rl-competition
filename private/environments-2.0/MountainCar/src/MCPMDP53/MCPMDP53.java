
    package MCPMDP53;

    import GeneralizedMountainCar.*;

    public class MCPMDP53 extends AbstractProvingMDPMountainCar {

        public MCPMDP53() {
            super();
                setScaleP(0.350146d);
                setScaleV(0.870943d);
                setPOffset(-0.378487d);
                setVOffset(0.533854d);
                setPNoiseDivider(9.954030d);
                setVNoiseDivider(10.898788d);
                setAccelBiasMean(0.862544d);
        }
    }