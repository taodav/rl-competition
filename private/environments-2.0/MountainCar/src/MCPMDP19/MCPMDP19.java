
    package MCPMDP19;

    import GeneralizedMountainCar.*;

    public class MCPMDP19 extends AbstractProvingMDPMountainCar {

        public MCPMDP19() {
            super();
                setScaleP(0.655474d);
                setScaleV(0.683855d);
                setPOffset(0.161683d);
                setVOffset(-0.789887d);
                setPNoiseDivider(14.379840d);
                setVNoiseDivider(7.258977d);
                setAccelBiasMean(1.045566d);
        }
    }