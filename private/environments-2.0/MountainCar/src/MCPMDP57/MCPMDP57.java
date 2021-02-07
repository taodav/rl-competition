
    package MCPMDP57;

    import GeneralizedMountainCar.*;

    public class MCPMDP57 extends AbstractProvingMDPMountainCar {

        public MCPMDP57() {
            super();
                setScaleP(0.524432d);
                setScaleV(0.469962d);
                setPOffset(-0.899843d);
                setVOffset(-0.544714d);
                setPNoiseDivider(9.429878d);
                setVNoiseDivider(13.490520d);
                setAccelBiasMean(1.140352d);
        }
    }