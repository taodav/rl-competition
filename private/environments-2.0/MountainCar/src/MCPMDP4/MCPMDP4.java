
    package MCPMDP4;

    import GeneralizedMountainCar.*;

    public class MCPMDP4 extends AbstractProvingMDPMountainCar {

        public MCPMDP4() {
            super();
                setScaleP(0.862438d);
                setScaleV(0.935058d);
                setPOffset(0.170209d);
                setVOffset(-0.106166d);
                setPNoiseDivider(7.775180d);
                setVNoiseDivider(10.220476d);
                setAccelBiasMean(0.998223d);
        }
    }