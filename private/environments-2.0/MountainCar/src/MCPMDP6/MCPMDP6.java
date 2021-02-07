
    package MCPMDP6;

    import GeneralizedMountainCar.*;

    public class MCPMDP6 extends AbstractProvingMDPMountainCar {

        public MCPMDP6() {
            super();
                setScaleP(0.353343d);
                setScaleV(0.825607d);
                setPOffset(0.341008d);
                setVOffset(0.838462d);
                setPNoiseDivider(19.418397d);
                setVNoiseDivider(9.897015d);
                setAccelBiasMean(1.050548d);
        }
    }