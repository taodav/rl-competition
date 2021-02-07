
    package MCPMDP64;

    import GeneralizedMountainCar.*;

    public class MCPMDP64 extends AbstractProvingMDPMountainCar {

        public MCPMDP64() {
            super();
                setScaleP(0.883363d);
                setScaleV(0.996941d);
                setPOffset(-0.623655d);
                setVOffset(-0.416520d);
                setPNoiseDivider(14.308151d);
                setVNoiseDivider(11.343886d);
                setAccelBiasMean(1.112513d);
        }
    }