
    package MCPMDP63;

    import GeneralizedMountainCar.*;

    public class MCPMDP63 extends AbstractProvingMDPMountainCar {

        public MCPMDP63() {
            super();
                setScaleP(0.313317d);
                setScaleV(0.895086d);
                setPOffset(-0.856818d);
                setVOffset(0.959629d);
                setPNoiseDivider(19.296022d);
                setVNoiseDivider(7.227788d);
                setAccelBiasMean(1.016887d);
        }
    }