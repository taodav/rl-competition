
    package MCPMDP23;

    import GeneralizedMountainCar.*;

    public class MCPMDP23 extends AbstractProvingMDPMountainCar {

        public MCPMDP23() {
            super();
                setScaleP(0.266804d);
                setScaleV(0.297016d);
                setPOffset(0.533951d);
                setVOffset(-0.933662d);
                setPNoiseDivider(15.067342d);
                setVNoiseDivider(9.285733d);
                setAccelBiasMean(0.935903d);
        }
    }