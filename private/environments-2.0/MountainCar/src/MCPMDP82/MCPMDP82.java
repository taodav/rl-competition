
    package MCPMDP82;

    import GeneralizedMountainCar.*;

    public class MCPMDP82 extends AbstractProvingMDPMountainCar {

        public MCPMDP82() {
            super();
                setScaleP(0.737648d);
                setScaleV(0.612407d);
                setPOffset(0.686662d);
                setVOffset(0.505730d);
                setPNoiseDivider(12.771825d);
                setVNoiseDivider(15.645181d);
                setAccelBiasMean(0.989332d);
        }
    }