
    package MCPMDP18;

    import GeneralizedMountainCar.*;

    public class MCPMDP18 extends AbstractProvingMDPMountainCar {

        public MCPMDP18() {
            super();
                setScaleP(0.440561d);
                setScaleV(0.644976d);
                setPOffset(-0.018061d);
                setVOffset(0.700116d);
                setPNoiseDivider(16.974752d);
                setVNoiseDivider(5.597337d);
                setAccelBiasMean(1.130736d);
        }
    }