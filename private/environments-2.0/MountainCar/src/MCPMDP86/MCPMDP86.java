
    package MCPMDP86;

    import GeneralizedMountainCar.*;

    public class MCPMDP86 extends AbstractProvingMDPMountainCar {

        public MCPMDP86() {
            super();
                setScaleP(0.841862d);
                setScaleV(0.868978d);
                setPOffset(-0.127545d);
                setVOffset(0.157414d);
                setPNoiseDivider(8.706814d);
                setVNoiseDivider(6.038352d);
                setAccelBiasMean(0.895436d);
        }
    }