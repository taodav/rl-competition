
    package MCPMDP52;

    import GeneralizedMountainCar.*;

    public class MCPMDP52 extends AbstractProvingMDPMountainCar {

        public MCPMDP52() {
            super();
                setScaleP(0.634479d);
                setScaleV(0.334594d);
                setPOffset(0.311192d);
                setVOffset(-0.331204d);
                setPNoiseDivider(19.285337d);
                setVNoiseDivider(14.032813d);
                setAccelBiasMean(1.111516d);
        }
    }