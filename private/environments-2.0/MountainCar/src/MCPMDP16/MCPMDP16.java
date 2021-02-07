
    package MCPMDP16;

    import GeneralizedMountainCar.*;

    public class MCPMDP16 extends AbstractProvingMDPMountainCar {

        public MCPMDP16() {
            super();
                setScaleP(0.458866d);
                setScaleV(0.303436d);
                setPOffset(0.282118d);
                setVOffset(-0.288091d);
                setPNoiseDivider(14.172122d);
                setVNoiseDivider(10.272174d);
                setAccelBiasMean(0.987940d);
        }
    }