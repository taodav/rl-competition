
    package MCPMDP29;

    import GeneralizedMountainCar.*;

    public class MCPMDP29 extends AbstractProvingMDPMountainCar {

        public MCPMDP29() {
            super();
                setScaleP(0.529675d);
                setScaleV(0.364044d);
                setPOffset(-0.173305d);
                setVOffset(0.052589d);
                setPNoiseDivider(18.317243d);
                setVNoiseDivider(8.137139d);
                setAccelBiasMean(1.001128d);
        }
    }