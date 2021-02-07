
    package MCPMDP48;

    import GeneralizedMountainCar.*;

    public class MCPMDP48 extends AbstractProvingMDPMountainCar {

        public MCPMDP48() {
            super();
                setScaleP(0.633536d);
                setScaleV(0.349435d);
                setPOffset(0.463535d);
                setVOffset(0.196565d);
                setPNoiseDivider(6.558820d);
                setVNoiseDivider(18.902273d);
                setAccelBiasMean(0.955063d);
        }
    }