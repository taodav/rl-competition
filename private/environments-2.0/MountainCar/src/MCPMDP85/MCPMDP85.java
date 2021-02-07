
    package MCPMDP85;

    import GeneralizedMountainCar.*;

    public class MCPMDP85 extends AbstractProvingMDPMountainCar {

        public MCPMDP85() {
            super();
                setScaleP(0.646265d);
                setScaleV(0.261684d);
                setPOffset(-0.662189d);
                setVOffset(-0.180055d);
                setPNoiseDivider(8.367743d);
                setVNoiseDivider(5.644660d);
                setAccelBiasMean(0.820169d);
        }
    }