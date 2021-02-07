
    package MCPMDP92;

    import GeneralizedMountainCar.*;

    public class MCPMDP92 extends AbstractProvingMDPMountainCar {

        public MCPMDP92() {
            super();
                setScaleP(0.655499d);
                setScaleV(0.705026d);
                setPOffset(0.517333d);
                setVOffset(0.086670d);
                setPNoiseDivider(18.022297d);
                setVNoiseDivider(18.972918d);
                setAccelBiasMean(0.910730d);
        }
    }