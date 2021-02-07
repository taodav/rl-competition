
    package MCPMDP15;

    import GeneralizedMountainCar.*;

    public class MCPMDP15 extends AbstractProvingMDPMountainCar {

        public MCPMDP15() {
            super();
                setScaleP(0.275915d);
                setScaleV(0.910813d);
                setPOffset(0.687320d);
                setVOffset(-0.455888d);
                setPNoiseDivider(12.676833d);
                setVNoiseDivider(18.351151d);
                setAccelBiasMean(1.189575d);
        }
    }