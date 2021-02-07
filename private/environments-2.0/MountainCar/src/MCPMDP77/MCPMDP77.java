
    package MCPMDP77;

    import GeneralizedMountainCar.*;

    public class MCPMDP77 extends AbstractProvingMDPMountainCar {

        public MCPMDP77() {
            super();
                setScaleP(0.315612d);
                setScaleV(0.287853d);
                setPOffset(-0.264719d);
                setVOffset(0.090880d);
                setPNoiseDivider(13.059114d);
                setVNoiseDivider(15.202945d);
                setAccelBiasMean(1.037506d);
        }
    }