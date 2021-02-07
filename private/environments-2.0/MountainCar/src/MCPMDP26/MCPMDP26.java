
    package MCPMDP26;

    import GeneralizedMountainCar.*;

    public class MCPMDP26 extends AbstractProvingMDPMountainCar {

        public MCPMDP26() {
            super();
                setScaleP(0.992314d);
                setScaleV(0.482892d);
                setPOffset(-0.567917d);
                setVOffset(0.858467d);
                setPNoiseDivider(13.038695d);
                setVNoiseDivider(6.940554d);
                setAccelBiasMean(0.815849d);
        }
    }