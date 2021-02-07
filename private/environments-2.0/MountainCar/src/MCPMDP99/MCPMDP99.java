
    package MCPMDP99;

    import GeneralizedMountainCar.*;

    public class MCPMDP99 extends AbstractProvingMDPMountainCar {

        public MCPMDP99() {
            super();
                setScaleP(0.987559d);
                setScaleV(0.953071d);
                setPOffset(0.831284d);
                setVOffset(0.427327d);
                setPNoiseDivider(7.141785d);
                setVNoiseDivider(5.697978d);
                setAccelBiasMean(0.981232d);
        }
    }