
    package MCPMDP81;

    import GeneralizedMountainCar.*;

    public class MCPMDP81 extends AbstractProvingMDPMountainCar {

        public MCPMDP81() {
            super();
                setScaleP(0.305410d);
                setScaleV(0.697758d);
                setPOffset(-0.179120d);
                setVOffset(0.173779d);
                setPNoiseDivider(19.550887d);
                setVNoiseDivider(18.053972d);
                setAccelBiasMean(1.019528d);
        }
    }