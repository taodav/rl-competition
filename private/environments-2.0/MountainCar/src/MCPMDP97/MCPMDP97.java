
    package MCPMDP97;

    import GeneralizedMountainCar.*;

    public class MCPMDP97 extends AbstractProvingMDPMountainCar {

        public MCPMDP97() {
            super();
                setScaleP(0.599015d);
                setScaleV(0.653330d);
                setPOffset(-0.127923d);
                setVOffset(-0.322770d);
                setPNoiseDivider(19.024888d);
                setVNoiseDivider(12.012147d);
                setAccelBiasMean(1.101216d);
        }
    }