
    package MCPMDP44;

    import GeneralizedMountainCar.*;

    public class MCPMDP44 extends AbstractProvingMDPMountainCar {

        public MCPMDP44() {
            super();
                setScaleP(0.607179d);
                setScaleV(0.921838d);
                setPOffset(0.441651d);
                setVOffset(0.740749d);
                setPNoiseDivider(5.115933d);
                setVNoiseDivider(14.976061d);
                setAccelBiasMean(1.067475d);
        }
    }