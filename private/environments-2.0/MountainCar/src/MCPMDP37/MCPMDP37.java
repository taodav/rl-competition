
    package MCPMDP37;

    import GeneralizedMountainCar.*;

    public class MCPMDP37 extends AbstractProvingMDPMountainCar {

        public MCPMDP37() {
            super();
                setScaleP(0.283899d);
                setScaleV(0.725671d);
                setPOffset(0.127914d);
                setVOffset(0.506851d);
                setPNoiseDivider(7.415488d);
                setVNoiseDivider(19.436035d);
                setAccelBiasMean(1.050617d);
        }
    }