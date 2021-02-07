
    package MCPMDP62;

    import GeneralizedMountainCar.*;

    public class MCPMDP62 extends AbstractProvingMDPMountainCar {

        public MCPMDP62() {
            super();
                setScaleP(0.297808d);
                setScaleV(0.642860d);
                setPOffset(-0.282598d);
                setVOffset(0.384456d);
                setPNoiseDivider(13.998643d);
                setVNoiseDivider(7.538834d);
                setAccelBiasMean(1.171451d);
        }
    }