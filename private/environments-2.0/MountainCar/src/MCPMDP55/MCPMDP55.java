
    package MCPMDP55;

    import GeneralizedMountainCar.*;

    public class MCPMDP55 extends AbstractProvingMDPMountainCar {

        public MCPMDP55() {
            super();
                setScaleP(0.278778d);
                setScaleV(0.526807d);
                setPOffset(-0.634277d);
                setVOffset(-0.576652d);
                setPNoiseDivider(10.742294d);
                setVNoiseDivider(11.622292d);
                setAccelBiasMean(1.040219d);
        }
    }