package visualizers.RealTimeStrategyVisualizer;


import rlVizLib.general.TinyGlue;
import rlVizLib.visualization.AbstractVisualizer;
import rlVizLib.visualization.VizComponent;
import rlVizLib.visualization.interfaces.GlueStateProvider;

public class RealTimeStrategyVisualizer extends AbstractVisualizer implements GlueStateProvider {

    private TinyGlue theGlueState = null;
    private Parameters parms;
    private State state;
    RTSBackGround rtsBG;
    RTSVizComponent rtsComponent;
    VizComponent scoreComponent;
    int episode = 0;

    public RealTimeStrategyVisualizer(TinyGlue theGlueState) {
        super();

        this.theGlueState = theGlueState;

        rtsBG = new RTSBackGround(this);
        rtsComponent = new RTSVizComponent(this);
        scoreComponent = new RTSScoreComponent(this);

        // get the spec
        RTSSpecResponse specResponse = RTSSpecRequest.Execute();
        String taskspec = specResponse.getTaskSpec();

        taskspec = taskspec.replace('$', '=');
        //System.out.println("Got response: " + taskspec);		

        parms = new Parameters();
        parms.parseTaskSpec(taskspec);

        new_episode();

        super.addVizComponentAtPositionWithSize(rtsBG, 0, 0, 1.0, 1.0);
        super.addVizComponentAtPositionWithSize(rtsComponent, 0, 0, 1.0, 1.0);
        super.addVizComponentAtPositionWithSize(scoreComponent, 0, 0, 1.0, 1.0);
    }

    public synchronized void new_episode() {
        episode++;

        state = new State(parms);
        //state.setMPs(mpstr);	  

        rtsBG.setVars(parms);
        rtsComponent.setVars(parms, state, episode);
    }

    public TinyGlue getTheGlueState() {
        return theGlueState;
    }

    public String getName(){
return "RL-RTS 1.0";
}
}
