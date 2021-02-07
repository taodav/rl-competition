
    package MCPMDP90;

    import GeneralizedMountainCar.*;

    public class MCPMDP90 extends AbstractProvingMDPMountainCar {

        public MCPMDP90() {
            super();
                setScaleP(0.361451d);
                setScaleV(0.837236d);
                setPOffset(0.905993d);
                setVOffset(-0.655544d);
                setPNoiseDivider(5.594603d);
                setVNoiseDivider(11.840727d);
                setAccelBiasMean(1.126547d);
        }
    }