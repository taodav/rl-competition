
    package MCPMDP3;

    import GeneralizedMountainCar.*;

    public class MCPMDP3 extends AbstractProvingMDPMountainCar {

        public MCPMDP3() {
            super();
                setScaleP(0.640707d);
                setScaleV(0.652546d);
                setPOffset(0.873121d);
                setVOffset(0.405276d);
                setPNoiseDivider(16.874945d);
                setVNoiseDivider(13.876172d);
                setAccelBiasMean(1.054079d);
        }
    }