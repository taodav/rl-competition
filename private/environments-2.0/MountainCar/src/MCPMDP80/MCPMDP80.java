
    package MCPMDP80;

    import GeneralizedMountainCar.*;

    public class MCPMDP80 extends AbstractProvingMDPMountainCar {

        public MCPMDP80() {
            super();
                setScaleP(0.938416d);
                setScaleV(0.448864d);
                setPOffset(0.647272d);
                setVOffset(0.988002d);
                setPNoiseDivider(15.836145d);
                setVNoiseDivider(13.769867d);
                setAccelBiasMean(0.896926d);
        }
    }