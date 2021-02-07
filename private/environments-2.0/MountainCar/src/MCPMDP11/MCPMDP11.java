
    package MCPMDP11;

    import GeneralizedMountainCar.*;

    public class MCPMDP11 extends AbstractProvingMDPMountainCar {

        public MCPMDP11() {
            super();
                setScaleP(0.423666d);
                setScaleV(0.336779d);
                setPOffset(0.551498d);
                setVOffset(0.333793d);
                setPNoiseDivider(6.955830d);
                setVNoiseDivider(12.919716d);
                setAccelBiasMean(1.158436d);
        }
    }