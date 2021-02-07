
    package MCPMDP45;

    import GeneralizedMountainCar.*;

    public class MCPMDP45 extends AbstractProvingMDPMountainCar {

        public MCPMDP45() {
            super();
                setScaleP(0.724034d);
                setScaleV(0.277272d);
                setPOffset(0.117533d);
                setVOffset(0.288123d);
                setPNoiseDivider(12.645696d);
                setVNoiseDivider(9.825217d);
                setAccelBiasMean(0.978163d);
        }
    }