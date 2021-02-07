/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package resultgenerator;

import java.io.File;
import java.io.FileFilter;
import java.util.Vector;
import mdpparser.abstractResult;
import mdpparser.helicopterResult;
import mdpparser.keepawayResult;
import mdpparser.mountaincarResult;
import mdpparser.polyathlonResult;
import mdpparser.rtsResult;
import mdpparser.tetrisResult;

/**
 *
 * @author mradkie
 */
public class resultGenerator {
    private final static int HELINUM = 1;
    private final static int MCNUM = 2;
    private final static int TETRISNUM = 3;
    private final static int RTSNUM = 4;
    private final static int KANUM = 6;
    private final static int POLYNUM = 5;
    private final static int NUMEVENTS = 6;
    
    private final static String resultDir = "../../results/";
    private String directoryPath = null;
    private Vector<abstractResult> resultVector = new Vector<abstractResult>();
    private Vector<FileFilter> theFilters=new Vector<FileFilter>();
    
    public resultGenerator(String directoryPath){
        this.directoryPath = directoryPath;
    }
    
    public void addFilter(FileFilter newFilter){
        theFilters.add(newFilter);
    }
    public void addFilter(String filterString){
        theFilters.add(new stringFileFilter(filterString));
    }
    
    public void generateFileList(){        
        File directoryObject = new File(directoryPath);
        assert (directoryObject.isDirectory());
        File[] theFileList = directoryObject.listFiles();

        if (theFileList == null) {
            return;
        }

        for (File thisFile : theFileList) {
            boolean matchAll=true;
            for (FileFilter thisFilter : theFilters)matchAll&=thisFilter.accept(thisFile);
            
            if(matchAll) resultVector.add(getResultFromFile(thisFile));
        }
    }
    
    public static abstractResult getResultFromFile(File resultFile){
       abstractResult resultObject = null;
       String fileName = resultFile.getName().toLowerCase();
       if(fileName.contains("mountaincar") || fileName.contains("event_"+(MCNUM+NUMEVENTS))){
           resultObject = new mountaincarResult(resultFile);
       }else if(fileName.contains("helicopter") || fileName.contains("event_"+(HELINUM+NUMEVENTS))){
           resultObject = new helicopterResult(resultFile);
       }else if(fileName.contains("tetris") || fileName.contains("event_"+(TETRISNUM+NUMEVENTS))){
           resultObject = new tetrisResult(resultFile);
       }else if(fileName.contains("polyathlon") || fileName.contains("event_"+(POLYNUM+NUMEVENTS))){
           resultObject = new polyathlonResult(resultFile);
       }else if(fileName.contains("rts") || fileName.contains("event_"+(RTSNUM+NUMEVENTS))){
           resultObject = new rtsResult(resultFile);
       }else if(fileName.contains("keepaway") || fileName.contains("event_"+(KANUM+NUMEVENTS))){
           resultObject = new keepawayResult(resultFile);
       }else{
          //ideally this case would parse the event name out of the file...
           //this isnt trivially doable since the generic class has an abstract
           //method
       }
       
       return resultObject;
    }
    public static abstractResult copyResult(abstractResult oldResult){
       abstractResult newResult = null;
       int event = oldResult.getEventNum();
       
       if(oldResult.getEventNum() == HELINUM || oldResult.getEventNum() == HELINUM+NUMEVENTS){
           newResult = new helicopterResult(oldResult);
       }else if(oldResult.getEventNum() == MCNUM || oldResult.getEventNum() == MCNUM+NUMEVENTS){
           newResult = new mountaincarResult(oldResult);
       }else if(oldResult.getEventNum() == TETRISNUM || oldResult.getEventNum() == TETRISNUM+NUMEVENTS){
           newResult = new tetrisResult(oldResult);
       }else if(oldResult.getEventNum() == POLYNUM || oldResult.getEventNum() == POLYNUM+NUMEVENTS){
           newResult = new polyathlonResult(oldResult);
       }
       
       return newResult;
    }
    
    public abstractResult getResultAt(int index){
        assert(index < resultVector.size());
        return copyResult(resultVector.elementAt(index));
    }
    
    public Vector<abstractResult> getResultVector(){
        if(this.resultVector == null) return null;
        return (Vector<abstractResult>) this.resultVector.clone();
    }
//       public static void main(String[] args) {
//        resultGenerator gen = new resultGenerator(resultDir + "processedData");
//        //gen.addFilter("mc");
//        gen.addFilter("event_8_");
//        gen.generateFileList();
//        
//        FileWriter fileOut = null;
//        try {
//            fileOut = new FileWriter(resultDir+"output.txt");
//        } catch (IOException ex) {
//            Logger.getLogger(resultGenerator.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        //abstractResult mdp = getResultFromFile(new File("../../results/helicopter.results"));
//        for(abstractResult mdp : gen.getResultVector()){
//            if(fileOut == null) 
//                mdp.writeToFile(fileOut);
//        }
//        try {
//            fileOut.close();
//        } catch (IOException ex) {
//            Logger.getLogger(resultGenerator.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
}
