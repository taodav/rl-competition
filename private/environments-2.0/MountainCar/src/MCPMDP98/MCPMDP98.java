
    package MCPMDP98;

    import GeneralizedMountainCar.*;

    public class MCPMDP98 extends AbstractProvingMDPMountainCar {

        public MCPMDP98() {
            super();
                setScaleP(0.273186d);
                setScaleV(0.900750d);
                setPOffset(-0.030792d);
                setVOffset(-0.986090d);
                setPNoiseDivider(8.884126d);
                setVNoiseDivider(12.740531d);
                setAccelBiasMean(1.185687d);
        }
    }