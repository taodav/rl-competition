
    package MCPMDP17;

    import GeneralizedMountainCar.*;

    public class MCPMDP17 extends AbstractProvingMDPMountainCar {

        public MCPMDP17() {
            super();
                setScaleP(0.612495d);
                setScaleV(0.565751d);
                setPOffset(0.262957d);
                setVOffset(0.917810d);
                setPNoiseDivider(7.615668d);
                setVNoiseDivider(17.369018d);
                setAccelBiasMean(1.194499d);
        }
    }