
    package MCPMDP75;

    import GeneralizedMountainCar.*;

    public class MCPMDP75 extends AbstractProvingMDPMountainCar {

        public MCPMDP75() {
            super();
                setScaleP(0.465462d);
                setScaleV(0.662759d);
                setPOffset(-0.902415d);
                setVOffset(-0.737983d);
                setPNoiseDivider(19.408019d);
                setVNoiseDivider(13.830284d);
                setAccelBiasMean(0.943506d);
        }
    }