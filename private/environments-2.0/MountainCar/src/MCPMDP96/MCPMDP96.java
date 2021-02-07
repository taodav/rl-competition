
    package MCPMDP96;

    import GeneralizedMountainCar.*;

    public class MCPMDP96 extends AbstractProvingMDPMountainCar {

        public MCPMDP96() {
            super();
                setScaleP(0.569450d);
                setScaleV(0.816848d);
                setPOffset(0.657234d);
                setVOffset(-0.009617d);
                setPNoiseDivider(18.989243d);
                setVNoiseDivider(19.460682d);
                setAccelBiasMean(0.885426d);
        }
    }