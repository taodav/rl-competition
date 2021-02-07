
    package MCPMDP79;

    import GeneralizedMountainCar.*;

    public class MCPMDP79 extends AbstractProvingMDPMountainCar {

        public MCPMDP79() {
            super();
                setScaleP(0.373755d);
                setScaleV(0.641829d);
                setPOffset(-0.888344d);
                setVOffset(0.779701d);
                setPNoiseDivider(7.416362d);
                setVNoiseDivider(9.783171d);
                setAccelBiasMean(0.845776d);
        }
    }