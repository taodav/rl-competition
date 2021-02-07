
    package MCPMDP61;

    import GeneralizedMountainCar.*;

    public class MCPMDP61 extends AbstractProvingMDPMountainCar {

        public MCPMDP61() {
            super();
                setScaleP(0.932138d);
                setScaleV(0.582057d);
                setPOffset(0.095022d);
                setVOffset(0.132335d);
                setPNoiseDivider(9.687471d);
                setVNoiseDivider(19.169052d);
                setAccelBiasMean(1.140593d);
        }
    }