
    package MCPMDP49;

    import GeneralizedMountainCar.*;

    public class MCPMDP49 extends AbstractProvingMDPMountainCar {

        public MCPMDP49() {
            super();
                setScaleP(0.707691d);
                setScaleV(0.643354d);
                setPOffset(-0.712792d);
                setVOffset(-0.155966d);
                setPNoiseDivider(18.816279d);
                setVNoiseDivider(5.980206d);
                setAccelBiasMean(0.819830d);
        }
    }