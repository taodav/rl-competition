package visualizers.RealTimeStrategyVisualizer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import rlVizLib.general.TinyGlue;
import rlVizLib.visualization.VizComponent;
import rlglue.types.Observation;


public class RTSVizComponent implements VizComponent  {
	private RealTimeStrategyVisualizer rtsVis = null;
	private TinyGlue tg;
	
	int last_step; 
	int episode = -1;
	boolean waiting_new_episode;
	
	private Parameters parms;
	private State state;
	
  Color bgColor;    
  int myId;	
	
	public RTSVizComponent(RealTimeStrategyVisualizer rtsVisualizer) {
		rtsVis = rtsVisualizer;
		tg = rtsVis.getTheGlueState();
		
		myId = 1; // RL player is always ID 1
    last_step = -1;
    waiting_new_episode = false;   
		
    bgColor = new Color((float)0.6, (float)0.3, (float)0.3);
    
	}
	
	public void setVars(Parameters parms, State state, int episode)
	{
    this.parms = parms; 
    this.state = state;
    waiting_new_episode = false;
    last_step = -1;
    this.episode = episode;    
	}
	
	int scale(int x) { return x; }
	
  private void fillCircle(Graphics2D g, Color c, int x, int y, int radius)
  {
    g.setColor(c);
    
    int xp = scale(x);
    int yp = scale(y);
    int rp = scale(radius);
    
    int topleft_x = xp - rp;
    int topleft_y = yp - rp;
        
    g.fillOval(topleft_x, topleft_y, 2*rp, 2*rp);
  }

  private void drawCircle(Graphics2D g, Color c, int x, int y, int radius)
  {
    g.setColor(c);
    
    int xp = scale(x);
    int yp = scale(y);
    int rp = scale(radius);
    
    int topleft_x = xp - rp;
    int topleft_y = yp - rp;
        
    g.drawOval(topleft_x, topleft_y, 2*rp, 2*rp);
  }
  
  private double getSf(GameObj obj)
  {
    if (obj.getType().equals("worker")) 
      return (((double)obj.hp) / ((double)parms.worker_hp));
    else if (obj.getType().equals("marine")) 
      return (((double)obj.hp) / ((double)parms.marine_hp));
    else if (obj.getType().equals("base")) 
      return (((double)obj.hp) / ((double)parms.base_hp));
    else
      return 1.0;
  }
  
  private void drawObject(Graphics2D g, GameObj obj)
  {
    Color c = null; 
    //System.out.println("Drawing object " + obj.id);
    
    if (obj.hp <= 0)  // obj is dead :(
      return;         // don't draw it
        
    if (obj.owner == myId)
      c = Color.BLUE;
    else if (obj.owner == 2 || obj.getType().equals("mineral_patch"))
      c = Color.yellow;
    else
      c = Color.RED;
    
    double hpsf = getSf(obj);  // scale by HP    
    int re = c.getRed(), gr = c.getGreen(), bl = c.getBlue();
    re = (re == 0 ? 0 : 50 + (int)(hpsf*(re - 50)));
    gr = (gr == 0 ? 0 : 50 + (int)(hpsf*(gr - 50)));
    bl = (bl == 0 ? 0 : 50 + (int)(hpsf*(bl - 50)));    
    c = new Color(re, gr, bl);
    
    if (obj.getType().equals("worker"))
      drawCircle(g, c, obj.x, obj.y, obj.radius);        
    else 
      fillCircle(g, c, obj.x, obj.y, obj.radius);        
  }
	
  public void render(Graphics2D g) {
	    //System.out.println("ts="+tg.getTimeStep());
	    
	    AffineTransform saveAT = g.getTransform();
		  //g.scale(.01, .01);
      g.scale(1.0/parms.width, 1.0/parms.height);
		    
		  Observation lastObs = tg.getLastObservation(); 
		  
		  if (lastObs == null)
		  {
		    //System.out.println("lastObs == null");
		    return;
		  }
		  else
		  {
		    state.reset();
		    state.applyObservation(lastObs);
		  }
		  		    
		  /*  
			int transX = (int)(UtilityShop.normalizeValue( cartVis.currentXPos(), cartVis.getLeftCartBound(),cartVis.getRightCartBound())*(90.0f-10.0f) + 10.0f);
			int transY = 80;
			g.fillRect(transX-10, transY, 20 ,5);
			int x2 = transX + (int)(poleLength*Math.cos(cartVis.getAngle()));
			int y2 = transY + (int)(poleLength*Math.sin(cartVis.getAngle()));
			g.drawLine(transX, transY, x2, y2);
			*/
		  
	    for (GameObj obj : state.objects)
	    {    
	      if (obj.owner == myId)
	        if (obj.hp > 0)
	          fillCircle(g, Color.LIGHT_GRAY, obj.x, obj.y, obj.sight_range);      
	    }
	    
	    // mineral patches
      for (GameObj obj : state.mps)
        drawObject(g, obj);

	    // mineral patches and bases next
	    for (GameObj obj : state.objects)
	    {
	      if (obj.getType().equals("mineral_patch"))
	        drawObject(g, obj);
	    }

	    for (GameObj obj : state.objects)
	    {
	      if (obj.getType().equals("base"))
	        drawObject(g, obj);
	    }
	    
	    // workers and marines
	    for (GameObj obj : state.objects)
	    {
	      if (obj.getType().equals("worker"))
	        drawObject(g, obj);
	    }

	    for (GameObj obj : state.objects)
	    {
	      if (obj.getType().equals("marine"))
	        drawObject(g, obj);
	    }
		  
			
	    g.setTransform(saveAT);			
	}
	
	public boolean update() {
    int currentTimeStep = tg.getTimeStep();  
    
    //System.out.println("cts ls = " + currentTimeStep + " " + last_step);

    if (currentTimeStep < last_step)
    {
      rtsVis.new_episode();
      return false;           
    }
    
    if(currentTimeStep > last_step){
      last_step = currentTimeStep;
      return true;
    }
    return false;   
	 
	}

}
