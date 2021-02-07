
    package MCPMDP95;

    import GeneralizedMountainCar.*;

    public class MCPMDP95 extends AbstractProvingMDPMountainCar {

        public MCPMDP95() {
            super();
                setScaleP(0.653326d);
                setScaleV(0.343585d);
                setPOffset(-0.099658d);
                setVOffset(0.753110d);
                setPNoiseDivider(8.059396d);
                setVNoiseDivider(17.214560d);
                setAccelBiasMean(1.142427d);
        }
    }