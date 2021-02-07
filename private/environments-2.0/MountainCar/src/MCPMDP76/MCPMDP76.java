
    package MCPMDP76;

    import GeneralizedMountainCar.*;

    public class MCPMDP76 extends AbstractProvingMDPMountainCar {

        public MCPMDP76() {
            super();
                setScaleP(0.311682d);
                setScaleV(0.418280d);
                setPOffset(0.690403d);
                setVOffset(0.233214d);
                setPNoiseDivider(6.273116d);
                setVNoiseDivider(10.968318d);
                setAccelBiasMean(1.165448d);
        }
    }