
    package MCPMDP28;

    import GeneralizedMountainCar.*;

    public class MCPMDP28 extends AbstractProvingMDPMountainCar {

        public MCPMDP28() {
            super();
                setScaleP(0.828469d);
                setScaleV(0.647523d);
                setPOffset(0.365732d);
                setVOffset(-0.792780d);
                setPNoiseDivider(14.140323d);
                setVNoiseDivider(8.646427d);
                setAccelBiasMean(0.807646d);
        }
    }