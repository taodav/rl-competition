
    package MCPMDP9;

    import GeneralizedMountainCar.*;

    public class MCPMDP9 extends AbstractProvingMDPMountainCar {

        public MCPMDP9() {
            super();
                setScaleP(0.726154d);
                setScaleV(0.950291d);
                setPOffset(0.481540d);
                setVOffset(-0.576939d);
                setPNoiseDivider(17.050956d);
                setVNoiseDivider(16.170901d);
                setAccelBiasMean(1.056047d);
        }
    }