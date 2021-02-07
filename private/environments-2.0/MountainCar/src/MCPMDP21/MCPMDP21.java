
    package MCPMDP21;

    import GeneralizedMountainCar.*;

    public class MCPMDP21 extends AbstractProvingMDPMountainCar {

        public MCPMDP21() {
            super();
                setScaleP(0.877749d);
                setScaleV(0.555765d);
                setPOffset(-0.499383d);
                setVOffset(0.465338d);
                setPNoiseDivider(10.946703d);
                setVNoiseDivider(14.322308d);
                setAccelBiasMean(0.902129d);
        }
    }