
    package MCPMDP35;

    import GeneralizedMountainCar.*;

    public class MCPMDP35 extends AbstractProvingMDPMountainCar {

        public MCPMDP35() {
            super();
                setScaleP(0.376341d);
                setScaleV(0.810515d);
                setPOffset(-0.482346d);
                setVOffset(0.364002d);
                setPNoiseDivider(15.094856d);
                setVNoiseDivider(19.814532d);
                setAccelBiasMean(0.989944d);
        }
    }