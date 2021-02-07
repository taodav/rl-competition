
    package MCPMDP89;

    import GeneralizedMountainCar.*;

    public class MCPMDP89 extends AbstractProvingMDPMountainCar {

        public MCPMDP89() {
            super();
                setScaleP(0.861735d);
                setScaleV(0.382698d);
                setPOffset(0.562718d);
                setVOffset(-0.864282d);
                setPNoiseDivider(13.131492d);
                setVNoiseDivider(15.676936d);
                setAccelBiasMean(1.002444d);
        }
    }