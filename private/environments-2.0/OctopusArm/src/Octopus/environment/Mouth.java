package Octopus.environment;

import Octopus.config.*;
import java.awt.Shape;
import java.awt.geom.*;

public class Mouth {
    
    private Shape shape;
    
    public Mouth(MouthSpec spec) {
        shape = new Ellipse2D.Double(spec.getX(), spec.getY(),
                spec.getWidth(), spec.getHeight());
    }
    
    public Shape getShape() {
        return shape;
    }
}
