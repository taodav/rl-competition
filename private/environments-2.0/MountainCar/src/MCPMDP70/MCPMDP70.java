
    package MCPMDP70;

    import GeneralizedMountainCar.*;

    public class MCPMDP70 extends AbstractProvingMDPMountainCar {

        public MCPMDP70() {
            super();
                setScaleP(0.892668d);
                setScaleV(0.798255d);
                setPOffset(0.297832d);
                setVOffset(-0.211196d);
                setPNoiseDivider(13.129064d);
                setVNoiseDivider(19.208644d);
                setAccelBiasMean(1.059001d);
        }
    }