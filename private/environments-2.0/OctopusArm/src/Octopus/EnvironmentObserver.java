package Octopus;
public interface EnvironmentObserver {
    
    public void episodeStarted();
    
    public void stateChanged(double reward);
    
    public void episodeFinished();
}
