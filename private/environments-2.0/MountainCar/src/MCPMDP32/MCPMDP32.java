
    package MCPMDP32;

    import GeneralizedMountainCar.*;

    public class MCPMDP32 extends AbstractProvingMDPMountainCar {

        public MCPMDP32() {
            super();
                setScaleP(0.980078d);
                setScaleV(0.505835d);
                setPOffset(-0.906519d);
                setVOffset(0.281004d);
                setPNoiseDivider(13.700652d);
                setVNoiseDivider(6.624998d);
                setAccelBiasMean(0.985622d);
        }
    }