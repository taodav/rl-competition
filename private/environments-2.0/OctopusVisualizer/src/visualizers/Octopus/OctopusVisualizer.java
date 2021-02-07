/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package visualizers.Octopus;

import rlVizLib.general.TinyGlue;
import rlVizLib.visualization.AbstractVisualizer;
import rlVizLib.visualization.GenericScoreComponent;
import rlVizLib.visualization.VizComponent;
import rlVizLib.visualization.interfaces.DynamicControlTarget;
import rlVizLib.visualization.interfaces.GlueStateProvider;

/**
 *
 * @author btanner
 */
public class OctopusVisualizer extends AbstractVisualizer implements GlueStateProvider{
    private TinyGlue glueState;
    private DynamicControlTarget theControlTarget;

    
      public OctopusVisualizer(TinyGlue glueState, DynamicControlTarget theControlTarget) {
        super();

        this.glueState = glueState;
        this.theControlTarget = theControlTarget;
        setupVizComponents();
        addDesiredExtras();


    }

    protected void setupVizComponents() {
        VizComponent scoreComponent = new GenericScoreComponent(this);
        super.addVizComponentAtPositionWithSize(scoreComponent, 0, 0, 1.0, 1.0);

    }

    protected void addDesiredExtras() {
        addPreferenceComponents();
    }

    public void addPreferenceComponents() {

    }

    public TinyGlue getTheGlueState() {
        return glueState;
    }

    
}
