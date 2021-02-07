
    package MCPMDP93;

    import GeneralizedMountainCar.*;

    public class MCPMDP93 extends AbstractProvingMDPMountainCar {

        public MCPMDP93() {
            super();
                setScaleP(0.341877d);
                setScaleV(0.953000d);
                setPOffset(-0.075257d);
                setVOffset(-0.430226d);
                setPNoiseDivider(15.787396d);
                setVNoiseDivider(5.416273d);
                setAccelBiasMean(0.993154d);
        }
    }