
    package MCPMDP58;

    import GeneralizedMountainCar.*;

    public class MCPMDP58 extends AbstractProvingMDPMountainCar {

        public MCPMDP58() {
            super();
                setScaleP(0.421875d);
                setScaleV(0.666068d);
                setPOffset(-0.282196d);
                setVOffset(0.446041d);
                setPNoiseDivider(7.432984d);
                setVNoiseDivider(5.809421d);
                setAccelBiasMean(1.012255d);
        }
    }