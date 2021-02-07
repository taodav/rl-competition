
    package MCPMDP38;

    import GeneralizedMountainCar.*;

    public class MCPMDP38 extends AbstractProvingMDPMountainCar {

        public MCPMDP38() {
            super();
                setScaleP(0.705037d);
                setScaleV(0.560866d);
                setPOffset(-0.013241d);
                setVOffset(0.356481d);
                setPNoiseDivider(19.278271d);
                setVNoiseDivider(14.034915d);
                setAccelBiasMean(0.801914d);
        }
    }