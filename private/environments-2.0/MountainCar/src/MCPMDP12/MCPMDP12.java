
    package MCPMDP12;

    import GeneralizedMountainCar.*;

    public class MCPMDP12 extends AbstractProvingMDPMountainCar {

        public MCPMDP12() {
            super();
                setScaleP(0.654147d);
                setScaleV(0.643817d);
                setPOffset(0.821485d);
                setVOffset(0.488183d);
                setPNoiseDivider(6.556047d);
                setVNoiseDivider(10.127480d);
                setAccelBiasMean(0.813844d);
        }
    }