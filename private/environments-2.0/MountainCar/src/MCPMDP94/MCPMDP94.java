
    package MCPMDP94;

    import GeneralizedMountainCar.*;

    public class MCPMDP94 extends AbstractProvingMDPMountainCar {

        public MCPMDP94() {
            super();
                setScaleP(0.332163d);
                setScaleV(0.416464d);
                setPOffset(0.837306d);
                setVOffset(0.248467d);
                setPNoiseDivider(12.008511d);
                setVNoiseDivider(13.803412d);
                setAccelBiasMean(1.062073d);
        }
    }