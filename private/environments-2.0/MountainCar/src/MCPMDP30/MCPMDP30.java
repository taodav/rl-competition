
    package MCPMDP30;

    import GeneralizedMountainCar.*;

    public class MCPMDP30 extends AbstractProvingMDPMountainCar {

        public MCPMDP30() {
            super();
                setScaleP(0.539438d);
                setScaleV(0.441390d);
                setPOffset(0.542158d);
                setVOffset(-0.422599d);
                setPNoiseDivider(13.099905d);
                setVNoiseDivider(6.754848d);
                setAccelBiasMean(1.073642d);
        }
    }