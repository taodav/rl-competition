
    package MCPMDP88;

    import GeneralizedMountainCar.*;

    public class MCPMDP88 extends AbstractProvingMDPMountainCar {

        public MCPMDP88() {
            super();
                setScaleP(0.582112d);
                setScaleV(0.670316d);
                setPOffset(-0.098286d);
                setVOffset(-0.550816d);
                setPNoiseDivider(18.162925d);
                setVNoiseDivider(5.253562d);
                setAccelBiasMean(0.984622d);
        }
    }