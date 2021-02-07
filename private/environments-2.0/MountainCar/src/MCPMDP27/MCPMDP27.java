
    package MCPMDP27;

    import GeneralizedMountainCar.*;

    public class MCPMDP27 extends AbstractProvingMDPMountainCar {

        public MCPMDP27() {
            super();
                setScaleP(0.608092d);
                setScaleV(0.250969d);
                setPOffset(-0.231083d);
                setVOffset(0.744933d);
                setPNoiseDivider(19.368819d);
                setVNoiseDivider(5.927509d);
                setAccelBiasMean(0.871745d);
        }
    }