
    package MCPMDP1;

    import GeneralizedMountainCar.*;

    public class MCPMDP1 extends AbstractProvingMDPMountainCar {

        public MCPMDP1() {
            super();
                setScaleP(0.460256d);
                setScaleV(0.627522d);
                setPOffset(0.393611d);
                setVOffset(0.269693d);
                setPNoiseDivider(5.854105d);
                setVNoiseDivider(9.712364d);
                setAccelBiasMean(0.955015d);
        }
    }