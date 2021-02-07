
    package MCPMDP42;

    import GeneralizedMountainCar.*;

    public class MCPMDP42 extends AbstractProvingMDPMountainCar {

        public MCPMDP42() {
            super();
                setScaleP(0.541918d);
                setScaleV(0.394026d);
                setPOffset(0.356100d);
                setVOffset(0.531509d);
                setPNoiseDivider(8.487068d);
                setVNoiseDivider(11.374834d);
                setAccelBiasMean(1.046153d);
        }
    }