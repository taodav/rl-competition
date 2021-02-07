
    package MCPMDP25;

    import GeneralizedMountainCar.*;

    public class MCPMDP25 extends AbstractProvingMDPMountainCar {

        public MCPMDP25() {
            super();
                setScaleP(0.822111d);
                setScaleV(0.520160d);
                setPOffset(-0.477829d);
                setVOffset(0.383097d);
                setPNoiseDivider(7.888951d);
                setVNoiseDivider(9.296983d);
                setAccelBiasMean(0.928701d);
        }
    }