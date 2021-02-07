
    package MCPMDP50;

    import GeneralizedMountainCar.*;

    public class MCPMDP50 extends AbstractProvingMDPMountainCar {

        public MCPMDP50() {
            super();
                setScaleP(0.648591d);
                setScaleV(0.521149d);
                setPOffset(-0.097079d);
                setVOffset(0.328886d);
                setPNoiseDivider(10.755508d);
                setVNoiseDivider(6.751192d);
                setAccelBiasMean(0.909668d);
        }
    }