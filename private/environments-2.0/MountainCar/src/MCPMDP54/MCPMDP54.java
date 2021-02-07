
    package MCPMDP54;

    import GeneralizedMountainCar.*;

    public class MCPMDP54 extends AbstractProvingMDPMountainCar {

        public MCPMDP54() {
            super();
                setScaleP(0.484687d);
                setScaleV(0.292368d);
                setPOffset(-0.619516d);
                setVOffset(0.466415d);
                setPNoiseDivider(7.926434d);
                setVNoiseDivider(16.534366d);
                setAccelBiasMean(0.808275d);
        }
    }