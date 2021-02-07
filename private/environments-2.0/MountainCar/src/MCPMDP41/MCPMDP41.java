
    package MCPMDP41;

    import GeneralizedMountainCar.*;

    public class MCPMDP41 extends AbstractProvingMDPMountainCar {

        public MCPMDP41() {
            super();
                setScaleP(0.991644d);
                setScaleV(0.895442d);
                setPOffset(-0.696216d);
                setVOffset(-0.083163d);
                setPNoiseDivider(5.353689d);
                setVNoiseDivider(8.040684d);
                setAccelBiasMean(1.186244d);
        }
    }