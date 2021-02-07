
    package MCPMDP65;

    import GeneralizedMountainCar.*;

    public class MCPMDP65 extends AbstractProvingMDPMountainCar {

        public MCPMDP65() {
            super();
                setScaleP(0.473211d);
                setScaleV(0.297247d);
                setPOffset(0.976155d);
                setVOffset(0.956763d);
                setPNoiseDivider(13.254509d);
                setVNoiseDivider(11.432978d);
                setAccelBiasMean(0.845479d);
        }
    }