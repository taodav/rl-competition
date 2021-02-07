
    package MCPMDP73;

    import GeneralizedMountainCar.*;

    public class MCPMDP73 extends AbstractProvingMDPMountainCar {

        public MCPMDP73() {
            super();
                setScaleP(0.367048d);
                setScaleV(0.673574d);
                setPOffset(-0.915078d);
                setVOffset(0.674308d);
                setPNoiseDivider(10.191020d);
                setVNoiseDivider(12.607060d);
                setAccelBiasMean(0.925115d);
        }
    }