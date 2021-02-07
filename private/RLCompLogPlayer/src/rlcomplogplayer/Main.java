/*
 * TODO: 
 * clean up code (duplicate code in places?)
 * ghosting issue in tetris?
 * load times kinda long... maybe some sort of buffering for next visualizer?
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rlcomplogplayer;

import acrobotlogplayer.AcrobotPanel;
import catmouselogplayer.CatMousePanel;
import gridworldlogplayer.GridworldPanel;
import helicopterlogplayer.HelicopterPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;


import javax.swing.JLabel;
import javax.swing.JPanel;
import mountaincarlogplayer.MCPanel;
import tetrislogplayer.*;

/**
 *
 * @author mradkie
 */
public class Main implements ActionListener {

    String pathToLogFile = "";// = "../../results/";
    String filename = "";//"acro";//8.results";
    BufferedReader fileRead = null;
    private boolean shouldRun = true;
    private JButton pauseButton;
    private JButton newButton;
    private JButton nextButton;
    private boolean canUpdate = true;
    private String filenameWithPath = "";
    final JFileChooser fc = new JFileChooser();
    private JFrame theMainFrame;
    AbstractVizPanel theVizPanel = null;
    static VizControl theVizControls = null;
    private double SPEED_FACTOR;
    private static double MCSPEED = 0.25;
    private static double DEFAULTSPEED = 1.0;
//    private Vector<String> fileVector=new Vector<String>();
    private Map<String, Vector<File>> EventToFileNameMap = new TreeMap<String, Vector<File>>();
    private JPanel infoPanel;
//    private int numFiles = 0;
    private Random randomGenerator = new Random();
    private String eventName = "";
    private JLabel nameLabel = null;
    private JLabel scoreLabel = null;
    private JLabel eventLabel = null;
    private JLabel mdpLabel = null;
    private JLabel stepLabel = null;
    private boolean paused = false;

    public Main() {
        theMainFrame = new JFrame();
        theMainFrame.getContentPane().setLayout(new BorderLayout());
        theMainFrame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent we) {
                try {
                    cleanup();
                    System.exit(0);
                } catch (Throwable ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        SPEED_FACTOR = DEFAULTSPEED;
        //set up the buttons
        pauseButton = new JButton("Pause");
        pauseButton.addActionListener(this);

        newButton = new JButton("New");
        newButton.addActionListener(this);

        nextButton = new JButton("Next");
        nextButton.addActionListener(this);

        theMainFrame.setSize(1200, 800);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(pauseButton);
        buttonPanel.add(newButton);
        buttonPanel.add(nextButton);
        theMainFrame.getContentPane().add(buttonPanel, "South");

        infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));
        infoPanel.setPreferredSize(new Dimension(450, 200));
        Font theFont = new Font("Verdana",Font.PLAIN,28);
        nameLabel=new JLabel("");
        nameLabel.setFont(theFont);
        mdpLabel=new JLabel("MDP Num");
        mdpLabel.setFont(theFont);
        eventLabel=new JLabel(eventName);
        eventLabel.setFont(theFont);
        scoreLabel=new JLabel("Score");
        scoreLabel.setFont(theFont);
        stepLabel = new JLabel("Steps");
        stepLabel.setFont(theFont);
        infoPanel.add(nameLabel);
        infoPanel.add(eventLabel);
        infoPanel.add(mdpLabel);
        infoPanel.add(scoreLabel);
        infoPanel.add(stepLabel);
        theMainFrame.getContentPane().add(infoPanel, "West");

//        int index = 0;

        //temp vector stuff
//        fileVector.addAll(generateFileList("../../results/processedData"));
        addFilesToMap(generateFileList("../../results/processedData"));
//        fileVector.add("../../results/processedData/event_11_team_jamartinh_Thu_Jun_26_03_50_14_-0600_2008");
        if (EventToFileNameMap.size() < 1) {//filename.length() < 1) {
            loadNewFileWithFileChooser();
        }
        while (theVizPanel == null) {
            filenameWithPath = getNextFileName();
            setupVizPanel();
        }

        theMainFrame.setVisible(true);

        int vizSpeed = 20;

        while (true) {
            if (!canUpdate && !paused) {
                filenameWithPath = getNextFileName();

                setupVizPanel();
                if (theVizPanel == null) {
                    continue;
                }
                //this seems to fix the mountaincar to mountaincar problem.
                theMainFrame.paintComponents(theVizPanel.getGraphics());
            }
            vizSpeed = theVizControls.getSpeedValue();

            try {
                if (shouldRun) {
                    if (canUpdate = theVizPanel.update()) {
                        updateLabels();
                        theVizPanel.repaint();
                    }
                }
                if (vizSpeed == 100) {
                    Thread.yield();
                } else {
                    Thread.sleep((int) ((100 - vizSpeed) * this.SPEED_FACTOR));
                }

            //Thread.currentThread().yield();
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public boolean canUpdate() {
        return this.canUpdate;
    }

    public boolean shouldRun() {
        return this.shouldRun;
    }

    public boolean updatePanel() {
        canUpdate = theVizPanel.update();
        return canUpdate;
    }

    public void cleanup() {
    }

    public void setupVizPanel() {
        try {
            if (theVizPanel != null) {
                theMainFrame.getContentPane().remove(theVizPanel);
                theVizPanel = null;
            }
            boolean found = false;
            this.fileRead = new BufferedReader(new FileReader(new File(filenameWithPath)));
            String readLine = "";
            while (fileRead.ready() && !found) {
                readLine = fileRead.readLine();

                if (readLine.startsWith("EventName")) {
                    readLine = readLine.substring(10, readLine.length());

                    //if its a testing, we want to strip Testing_ off the front
                    if (readLine.startsWith("Testing")) {
                        readLine = readLine.substring(8);
                        eventName = readLine;
                    }
                    if (readLine.equalsIgnoreCase("tetris")) {
                        this.SPEED_FACTOR = 2;
                        theVizPanel = new TetrisPanel(filenameWithPath);
                    }
                    if (readLine.equalsIgnoreCase("mountaincar")) {
                        this.SPEED_FACTOR = MCSPEED;
                        theVizPanel = new MCPanel(filenameWithPath);
                    }
                    if (readLine.equalsIgnoreCase("helicopter")) {
                        this.SPEED_FACTOR = 2;
                        theVizPanel = new HelicopterPanel(filenameWithPath);
                    }
                    if (readLine.equalsIgnoreCase("Polyathlon")) {
                        theVizPanel = getPolyPanel(filenameWithPath);
                        this.SPEED_FACTOR = 5;
                    }
                    found = true;
                }
            }
            fileRead.close();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (theVizPanel != null) {
            theMainFrame.getContentPane().add(theVizPanel, "Center");
            //theMainFrame.setSize(theVizPanel.getDesiredX() + 10, theVizPanel.getDesiredY() + 75);
            theMainFrame.setTitle(theVizPanel.getTeamName() + ":" + theVizPanel.getMDPNum());
        }
    }

    public void updateLabels(){
        NumberFormat F=new DecimalFormat("#######.##");
        nameLabel.setText(getTeamName(theVizPanel.getTeamName()));
        eventLabel.setText("Event:\t\t"+eventName);
        mdpLabel.setText("MDP Number:\t"+theVizPanel.getMDPNum());
        scoreLabel.setText("Score:\t"+F.format(theVizPanel.getReward()));
        stepLabel.setText("Steps:\t"+theVizPanel.getCurSteps()+"/"+theVizPanel.getMaxSteps());
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        theVizControls = VizControl.getInstance();
        theVizControls.setUpControls();

        Main m1 = new Main();
    }

    private void addFileToMap(File theFile) {
        String theFileName = theFile.getName();
        String thisEventName = theFileName.substring(0, theFileName.indexOf("team") - 1);

        if (!EventToFileNameMap.containsKey(thisEventName)) {
            EventToFileNameMap.put(thisEventName, new Vector<File>());
        }
        EventToFileNameMap.get(thisEventName).add(theFile);

    }

    private void addFilesToMap(File[] selectedFiles) {
        for (File file : selectedFiles) {
            addFileToMap(file);
        }
    }

    private void addFilesToMap(Vector<String> generateFileList) {
        for (String thisFileName : generateFileList) {
            addFileToMap(new File(thisFileName));
        }
    }
    int _currentMainEventIndex = 7;
    int _currentSubEventIndex = 0;
    //These are in reverse order, so the counts for event 12, then poly, then 10, 9, mountain car, helicotper
    int[] _eventCounts = new int[]{1, 10, 1, 1, 1, 1};

    private String getNextFileName() {
        if(EventToFileNameMap.size()==0)throw new RuntimeException("No files in the EventToFileMap");
        String eventToLoad = "event_" + _currentMainEventIndex;
 //       if(!EventToFileNameMap.containsKey(eventToLoad))throw new RuntimeException("No files in the EventToFileMap for: "+eventToLoad);

        _currentSubEventIndex++;
        if (_currentSubEventIndex >= _eventCounts[12 - _currentMainEventIndex]) {
            _currentSubEventIndex = 0;
            _currentMainEventIndex++;
        }
        if (_currentMainEventIndex > 12) {
            _currentMainEventIndex = 7;
        }
        
        Vector<File> theseEventFiles = EventToFileNameMap.get(eventToLoad);
        
        if(theseEventFiles!=null&&theseEventFiles.size()>0){
            int randInt = randomGenerator.nextInt(theseEventFiles.size());
            System.out.println("Event: "+eventToLoad+" randomly: "+randInt+" from [0, "+theseEventFiles.size()+")");
            return theseEventFiles.get(randInt).getAbsolutePath();
        }else{
            System.out.println("Event: "+eventToLoad+" bailing into recursive call");
            return getNextFileName();
        }

    }

    private AbstractVizPanel getPolyPanel(String filename) {
        AbstractVizPanel tempPanel = null;
        int mdp = 0;
        try {
            boolean found = false;
            String readIn = "";
            while (fileRead.ready() && !found) {
                readIn = fileRead.readLine();
                if (readIn.startsWith("MDP:")) {
                    mdp = Integer.parseInt(readIn.substring(4, readIn.length()));
                    found = true;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        switch (mdp) {
            case 0:
                tempPanel = new GridworldPanel(filename);
                break;
            case 1:
                tempPanel = new MCPanel(filename);
                SPEED_FACTOR = MCSPEED;
                break;
            case 2:
                tempPanel = new GridworldPanel(filename);
                break;
            case 3:
                tempPanel = new AcrobotPanel(filename);
                break;
            case 4:
                tempPanel = new MCPanel(filename);
                SPEED_FACTOR = MCSPEED;
                break;
            case 5:
                tempPanel = new AcrobotPanel(filename);
                break;
            case 50:
                tempPanel = null;
                break;
            case 51:
                tempPanel = new GridworldPanel(filename);
                break;
            case 52:
                tempPanel = new GridworldPanel(filename);
                break;
            case 53:
                tempPanel = null;
                break;
            case 54:
                tempPanel = new CatMousePanel(filename);
                break;
            case 55:
                tempPanel = new AcrobotPanel(filename);
                break;
            case 56:
                tempPanel = new CatMousePanel(filename);
                break;
            case 57:
                tempPanel = new MCPanel(filename);
                SPEED_FACTOR = MCSPEED;
                break;
            case 58:
                tempPanel = new MCPanel(filename);
                SPEED_FACTOR = MCSPEED;
                break;
            case 59:
                tempPanel = new GridworldPanel(filename);
                break;
            case 60:
                tempPanel = new CatMousePanel(filename);
                break;
            case 61:
                tempPanel = new AcrobotPanel(filename);
                break;
            case 62:
                tempPanel = null;
                break;
            case 63:
                tempPanel = null;
                break;
            case 64:
                tempPanel = new GridworldPanel(filename);
                break;
            default:
                tempPanel = null;
                break;
        }

        return tempPanel;
    }

    private void loadNewFileWithFileChooser() {
        fc.setCurrentDirectory(new File(pathToLogFile));

        shouldRun = false;
        int returnVal = fc.showOpenDialog(theMainFrame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            filenameWithPath = fc.getSelectedFile().getAbsolutePath();
            addFilesToMap(fc.getSelectedFiles());
            setupVizPanel();
        }
        if (theVizPanel == null) {
            loadNewFileWithFileChooser();
        }
        theVizPanel.repaint();
        pauseButton.setText("Start");
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newButton) {
            loadNewFileWithFileChooser();
        } else if (e.getSource() == nextButton) {
            canUpdate = false;
        } else {
            if (shouldRun) {
                pauseButton.setText("Start");
                paused = true;
            } else {
                pauseButton.setText("Pause");
                paused = false;
            }

            shouldRun = !shouldRun;
        }
    }

    public Vector<String> generateFileList(String directoryPath) {
        File directoryObject = new File(directoryPath);

        assert (directoryObject.isDirectory());
        File[] theFileList = directoryObject.listFiles();
        FileFilter theFilter = new stringFileFilter("event_");
        Vector<String> returnVec = new Vector<String>();

        if (theFileList == null) {
            return null;
        }

        for (File thisFile : theFileList) {
            if (theFilter.accept(thisFile)) {
                returnVec.add(thisFile.getAbsolutePath());
            }
        }
        return returnVec;
    //return null;
    }

    public String getTeamName(String username) {
        String teamname = "";

        if (username.contains("maia")) {
            teamname = "Loria INRIA - MAIA";
        } else if (username.contains("Cryptoxic")) {
            teamname = "Enigma";
        } else if (username.contains("acriric")) {
            teamname = "Acritic";
        } else if (username.contains("attack")) {
            teamname = "Massive Attack";
        } else if (username.contains("atum")) {
            teamname = "METU Razors";
        } else if (username.contains("cejb")) {
            teamname = "cejb";
        } else if (username.contains("get2dachoppa")) {
            teamname = "Get to da Choppa!!!!";
        } else if (username.contains("god")) {
            teamname = "Team Tech Committee";
        } else if (username.contains("god2")) {
            teamname = "Team Tech Committee";
        } else if (username.contains("jamartinh")) {
            teamname = "JAMH";
        } else if (username.contains("nipg")) {
            teamname = "Neural Information Processing Group, Eotvos Lorand University";
        } else if (username.contains("njurl")) {
            teamname = "NJU RLOF";
        } else if (username.contains("nomolos")) {
            teamname = "Protoss";
        } else if (username.contains("ondrasej")) {
            teamname = "Charles University in Prague";
        } else if (username.contains("pankaj")) {
            teamname = "Saras";
        } else if (username.contains("rlftw")) {
            teamname = "rl ftwbbq";
        } else if (username.contains("rtsrlai")) {
            teamname = "RTS RLAI Team (bis)";
        } else if (username.contains("uva")) {
            teamname = "UvA";
        } else if (username.contains("venom")) {
            teamname = "The Cobras";
        } else if (username.contains("waikato")) {
            teamname = "SmartCraft";
        } else {
            teamname = "unknown";
        }

        return teamname;
    }
}
