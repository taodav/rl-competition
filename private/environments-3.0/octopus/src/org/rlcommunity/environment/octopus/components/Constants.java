package org.rlcommunity.environment.octopus.components;

import org.rlcommunity.environment.octopus.config.*;

public class Constants {
    
    private static ConstantSet constants;
    
    public static void init(ConstantSet c) {
        if (constants != null) {
            throw new IllegalStateException("Constants may only be initialized once.");
        }
        
        constants = c;
    }
    
    public static ConstantSet get() { return constants; }
}
