
    package MCPMDP8;

    import GeneralizedMountainCar.*;

    public class MCPMDP8 extends AbstractProvingMDPMountainCar {

        public MCPMDP8() {
            super();
                setScaleP(0.289350d);
                setScaleV(0.702593d);
                setPOffset(-0.492042d);
                setVOffset(-0.960422d);
                setPNoiseDivider(10.416220d);
                setVNoiseDivider(6.739081d);
                setAccelBiasMean(1.002015d);
        }
    }