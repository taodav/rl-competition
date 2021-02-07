
    package MCPMDP72;

    import GeneralizedMountainCar.*;

    public class MCPMDP72 extends AbstractProvingMDPMountainCar {

        public MCPMDP72() {
            super();
                setScaleP(0.440640d);
                setScaleV(0.467002d);
                setPOffset(-0.510156d);
                setVOffset(-0.288623d);
                setPNoiseDivider(16.545076d);
                setVNoiseDivider(8.248262d);
                setAccelBiasMean(0.872821d);
        }
    }