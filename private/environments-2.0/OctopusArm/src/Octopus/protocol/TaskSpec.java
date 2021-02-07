package Octopus.protocol;

public class TaskSpec {
    
    private int numStateVariables, numActionVariables;
    
    public TaskSpec(int numStateVariables, int numActionVariables) {
        this.numStateVariables = numStateVariables;
        this.numActionVariables = numActionVariables;
    }
    
    public int getNumStateVariables() { return numStateVariables; }
    
    public int getNumActionVariables() { return numActionVariables; }    
}
