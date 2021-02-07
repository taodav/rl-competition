
    package MCPMDP22;

    import GeneralizedMountainCar.*;

    public class MCPMDP22 extends AbstractProvingMDPMountainCar {

        public MCPMDP22() {
            super();
                setScaleP(0.271985d);
                setScaleV(0.923134d);
                setPOffset(-0.650643d);
                setVOffset(0.997575d);
                setPNoiseDivider(18.591814d);
                setVNoiseDivider(12.220712d);
                setAccelBiasMean(0.902923d);
        }
    }