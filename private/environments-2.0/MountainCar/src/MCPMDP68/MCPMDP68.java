
    package MCPMDP68;

    import GeneralizedMountainCar.*;

    public class MCPMDP68 extends AbstractProvingMDPMountainCar {

        public MCPMDP68() {
            super();
                setScaleP(0.938546d);
                setScaleV(0.766786d);
                setPOffset(-0.977588d);
                setVOffset(-0.326453d);
                setPNoiseDivider(9.436017d);
                setVNoiseDivider(19.166074d);
                setAccelBiasMean(1.137032d);
        }
    }