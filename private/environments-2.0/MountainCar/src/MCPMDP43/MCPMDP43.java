
    package MCPMDP43;

    import GeneralizedMountainCar.*;

    public class MCPMDP43 extends AbstractProvingMDPMountainCar {

        public MCPMDP43() {
            super();
                setScaleP(0.352240d);
                setScaleV(0.523677d);
                setPOffset(-0.186896d);
                setVOffset(0.623807d);
                setPNoiseDivider(12.789029d);
                setVNoiseDivider(19.266411d);
                setAccelBiasMean(1.172877d);
        }
    }