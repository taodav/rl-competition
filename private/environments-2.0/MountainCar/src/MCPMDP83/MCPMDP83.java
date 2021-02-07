
    package MCPMDP83;

    import GeneralizedMountainCar.*;

    public class MCPMDP83 extends AbstractProvingMDPMountainCar {

        public MCPMDP83() {
            super();
                setScaleP(0.430842d);
                setScaleV(0.652714d);
                setPOffset(-0.640863d);
                setVOffset(0.184850d);
                setPNoiseDivider(7.367681d);
                setVNoiseDivider(14.815443d);
                setAccelBiasMean(0.902429d);
        }
    }