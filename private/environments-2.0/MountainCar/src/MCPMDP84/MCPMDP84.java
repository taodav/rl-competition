
    package MCPMDP84;

    import GeneralizedMountainCar.*;

    public class MCPMDP84 extends AbstractProvingMDPMountainCar {

        public MCPMDP84() {
            super();
                setScaleP(0.886068d);
                setScaleV(0.620813d);
                setPOffset(-0.577475d);
                setVOffset(0.772037d);
                setPNoiseDivider(18.414547d);
                setVNoiseDivider(8.236091d);
                setAccelBiasMean(1.029144d);
        }
    }