
    package MCPMDP40;

    import GeneralizedMountainCar.*;

    public class MCPMDP40 extends AbstractProvingMDPMountainCar {

        public MCPMDP40() {
            super();
                setScaleP(0.663604d);
                setScaleV(0.860187d);
                setPOffset(0.025706d);
                setVOffset(0.926760d);
                setPNoiseDivider(6.256736d);
                setVNoiseDivider(5.117435d);
                setAccelBiasMean(0.903240d);
        }
    }