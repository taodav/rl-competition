
    package MCPMDP20;

    import GeneralizedMountainCar.*;

    public class MCPMDP20 extends AbstractProvingMDPMountainCar {

        public MCPMDP20() {
            super();
                setScaleP(0.450743d);
                setScaleV(0.377198d);
                setPOffset(0.499322d);
                setVOffset(0.909199d);
                setPNoiseDivider(15.991039d);
                setVNoiseDivider(17.233545d);
                setAccelBiasMean(1.055730d);
        }
    }