
    package MCPMDP87;

    import GeneralizedMountainCar.*;

    public class MCPMDP87 extends AbstractProvingMDPMountainCar {

        public MCPMDP87() {
            super();
                setScaleP(0.865169d);
                setScaleV(0.917044d);
                setPOffset(0.204465d);
                setVOffset(-0.407854d);
                setPNoiseDivider(14.522753d);
                setVNoiseDivider(19.411694d);
                setAccelBiasMean(0.868020d);
        }
    }