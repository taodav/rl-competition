
    package MCPMDP13;

    import GeneralizedMountainCar.*;

    public class MCPMDP13 extends AbstractProvingMDPMountainCar {

        public MCPMDP13() {
            super();
                setScaleP(0.486475d);
                setScaleV(0.795420d);
                setPOffset(-0.252547d);
                setVOffset(-0.055550d);
                setPNoiseDivider(12.516867d);
                setVNoiseDivider(13.321354d);
                setAccelBiasMean(1.011256d);
        }
    }