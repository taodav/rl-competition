
    package MCPMDP24;

    import GeneralizedMountainCar.*;

    public class MCPMDP24 extends AbstractProvingMDPMountainCar {

        public MCPMDP24() {
            super();
                setScaleP(0.405501d);
                setScaleV(0.596686d);
                setPOffset(-0.066294d);
                setVOffset(0.088727d);
                setPNoiseDivider(14.729280d);
                setVNoiseDivider(7.370252d);
                setAccelBiasMean(0.914016d);
        }
    }