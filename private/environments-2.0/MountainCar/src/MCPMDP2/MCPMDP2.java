
    package MCPMDP2;

    import GeneralizedMountainCar.*;

    public class MCPMDP2 extends AbstractProvingMDPMountainCar {

        public MCPMDP2() {
            super();
                setScaleP(0.547820d);
                setScaleV(0.698165d);
                setPOffset(0.268657d);
                setVOffset(-0.099700d);
                setPNoiseDivider(5.857277d);
                setVNoiseDivider(14.380280d);
                setAccelBiasMean(1.064154d);
        }
    }