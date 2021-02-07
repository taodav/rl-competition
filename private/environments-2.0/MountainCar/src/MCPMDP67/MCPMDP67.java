
    package MCPMDP67;

    import GeneralizedMountainCar.*;

    public class MCPMDP67 extends AbstractProvingMDPMountainCar {

        public MCPMDP67() {
            super();
                setScaleP(0.672421d);
                setScaleV(0.313870d);
                setPOffset(0.897115d);
                setVOffset(-0.895784d);
                setPNoiseDivider(17.673725d);
                setVNoiseDivider(8.938973d);
                setAccelBiasMean(1.058540d);
        }
    }