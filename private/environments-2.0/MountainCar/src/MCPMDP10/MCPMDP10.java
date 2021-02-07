
    package MCPMDP10;

    import GeneralizedMountainCar.*;

    public class MCPMDP10 extends AbstractProvingMDPMountainCar {

        public MCPMDP10() {
            super();
                setScaleP(0.738580d);
                setScaleV(0.472196d);
                setPOffset(-0.437454d);
                setVOffset(-0.141051d);
                setPNoiseDivider(16.946808d);
                setVNoiseDivider(5.223624d);
                setAccelBiasMean(1.092468d);
        }
    }