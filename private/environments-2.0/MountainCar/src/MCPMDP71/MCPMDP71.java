
    package MCPMDP71;

    import GeneralizedMountainCar.*;

    public class MCPMDP71 extends AbstractProvingMDPMountainCar {

        public MCPMDP71() {
            super();
                setScaleP(0.298515d);
                setScaleV(0.993123d);
                setPOffset(-0.564206d);
                setVOffset(0.193487d);
                setPNoiseDivider(9.360392d);
                setVNoiseDivider(19.588783d);
                setAccelBiasMean(0.907873d);
        }
    }