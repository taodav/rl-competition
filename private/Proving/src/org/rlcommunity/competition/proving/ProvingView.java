/*
 * ProvingView.java
 */

package org.rlcommunity.competition.proving;

import org.jdesktop.application.Action;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import org.rlcommunity.competition.phoneHomeInterface.RLEvent;

/**
 * The application's main frame.
 */
public class ProvingView extends FrameView implements ViewInterface{
Controller theController=null;

    public ProvingView(SingleFrameApplication app) {
        super(app);
        this.theController=new Controller(this);
              
        initComponents();
        this.disableAll();
        this.setDefaultButtonNames();
        this.bLogin.setEnabled(true);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        authPanel = new javax.swing.JPanel();
        TFusername = new javax.swing.JTextField();
        PFpassword = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        bLogin = new javax.swing.JButton();
        bLogout = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        controlPanel = new javax.swing.JPanel();
        bRun = new javax.swing.JButton();
        eventSelectPanel = new javax.swing.JPanel();
        helicopterRadio = new javax.swing.JRadioButton();
        acrobotRadio = new javax.swing.JRadioButton();
        octopusRadio = new javax.swing.JRadioButton();
        tetrisRadio = new javax.swing.JRadioButton();
        polyRadio = new javax.swing.JRadioButton();
        marioRadio = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        testingAcrobotRadio = new javax.swing.JRadioButton();
        testingHelicopterRadio = new javax.swing.JRadioButton();
        testingOctopusRadio = new javax.swing.JRadioButton();
        testingTetrisRadio = new javax.swing.JRadioButton();
        testingPolyRadio = new javax.swing.JRadioButton();
        testingMarioRadio = new javax.swing.JRadioButton();
        scrollPaneOuterPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TAconsole = new javax.swing.JTextArea();
        progressPanel = new javax.swing.JPanel();
        TimeElapsed = new javax.swing.JLabel();
        episodeProgressBar = new javax.swing.JProgressBar();
        mdpProgressBar = new javax.swing.JProgressBar();
        TimeRemaining = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        eventSelectButtonGroup = new javax.swing.ButtonGroup();
        resultsWindow = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        quitButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        TAresults = new javax.swing.JTextArea();
        criticalErrorDialog = new javax.swing.JDialog();
        jPanel2 = new javax.swing.JPanel();
        quitButton1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        TAError = new javax.swing.JTextArea();

        mainPanel.setName("mainPanel"); // NOI18N

        authPanel.setName("authPanel"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(org.rlcommunity.competition.proving.ProvingApp.class).getContext().getResourceMap(ProvingView.class);
        TFusername.setText(resourceMap.getString("TFusername.text")); // NOI18N
        TFusername.setName("TFusername"); // NOI18N

        PFpassword.setText(resourceMap.getString("PFpassword.text")); // NOI18N
        PFpassword.setName("PFpassword"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        bLogin.setText(resourceMap.getString("bLogin.text")); // NOI18N
        bLogin.setFocusCycleRoot(true);
        bLogin.setName("bLogin"); // NOI18N
        bLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bLoginActionPerformed(evt);
            }
        });

        bLogout.setText(resourceMap.getString("bLogout.text")); // NOI18N
        bLogout.setEnabled(false);
        bLogout.setName("bLogout"); // NOI18N
        bLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bLogoutActionPerformed(evt);
            }
        });

        jLabel1.setName("jLabel1"); // NOI18N

        org.jdesktop.layout.GroupLayout authPanelLayout = new org.jdesktop.layout.GroupLayout(authPanel);
        authPanel.setLayout(authPanelLayout);
        authPanelLayout.setHorizontalGroup(
            authPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(authPanelLayout.createSequentialGroup()
                .add(jLabel3)
                .add(1, 1, 1)
                .add(TFusername, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 120, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel4)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(PFpassword, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 101, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(bLogin)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(bLogout))
            .add(authPanelLayout.createSequentialGroup()
                .add(240, 240, 240)
                .add(jLabel1))
        );
        authPanelLayout.setVerticalGroup(
            authPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(authPanelLayout.createSequentialGroup()
                .add(authPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel4)
                    .add(bLogin)
                    .add(bLogout)
                    .add(jLabel3)
                    .add(TFusername, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(PFpassword, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel1))
        );

        controlPanel.setName("controlPanel"); // NOI18N

        bRun.setEnabled(false);
        bRun.setLabel(resourceMap.getString("bRun.label")); // NOI18N
        bRun.setName("bRun"); // NOI18N
        bRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRunActionPerformed(evt);
            }
        });

        eventSelectPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("eventSelectPanel.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("eventSelectPanel.border.titleFont"), resourceMap.getColor("eventSelectPanel.border.titleColor"))); // NOI18N
        eventSelectPanel.setEnabled(false);
        eventSelectPanel.setName("eventSelectPanel"); // NOI18N

        eventSelectButtonGroup.add(helicopterRadio);
        helicopterRadio.setText(resourceMap.getString("helicopterRadio.text")); // NOI18N
        helicopterRadio.setEnabled(false);
        helicopterRadio.setName("helicopterRadio"); // NOI18N
        helicopterRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helicopterRadioActionPerformed(evt);
            }
        });

        eventSelectButtonGroup.add(acrobotRadio);
        acrobotRadio.setText(resourceMap.getString("acrobotRadio.text")); // NOI18N
        acrobotRadio.setEnabled(false);
        acrobotRadio.setName("acrobotRadio"); // NOI18N
        acrobotRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acrobotRadioActionPerformed(evt);
            }
        });

        eventSelectButtonGroup.add(octopusRadio);
        octopusRadio.setText(resourceMap.getString("octopusRadio.text")); // NOI18N
        octopusRadio.setEnabled(false);
        octopusRadio.setName("octopusRadio"); // NOI18N
        octopusRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                octopusRadioActionPerformed(evt);
            }
        });

        eventSelectButtonGroup.add(tetrisRadio);
        tetrisRadio.setText(resourceMap.getString("tetrisRadio.text")); // NOI18N
        tetrisRadio.setEnabled(false);
        tetrisRadio.setName("tetrisRadio"); // NOI18N
        tetrisRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tetrisRadioActionPerformed(evt);
            }
        });

        eventSelectButtonGroup.add(polyRadio);
        polyRadio.setText(resourceMap.getString("polyRadio.text")); // NOI18N
        polyRadio.setEnabled(false);
        polyRadio.setContentAreaFilled(false);
        polyRadio.setEnabled(false);
        polyRadio.setName("polyRadio"); // NOI18N
        polyRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                polyRadioActionPerformed(evt);
            }
        });

        eventSelectButtonGroup.add(marioRadio);
        marioRadio.setText(resourceMap.getString("marioRadio.text")); // NOI18N
        marioRadio.setEnabled(false);
        marioRadio.setName("marioRadio"); // NOI18N
        marioRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                marioRadioActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout eventSelectPanelLayout = new org.jdesktop.layout.GroupLayout(eventSelectPanel);
        eventSelectPanel.setLayout(eventSelectPanelLayout);
        eventSelectPanelLayout.setHorizontalGroup(
            eventSelectPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(acrobotRadio, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
            .add(helicopterRadio, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
            .add(octopusRadio, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
            .add(tetrisRadio, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
            .add(polyRadio, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
            .add(marioRadio, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
        );
        eventSelectPanelLayout.setVerticalGroup(
            eventSelectPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(eventSelectPanelLayout.createSequentialGroup()
                .add(acrobotRadio)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(helicopterRadio)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(octopusRadio)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(tetrisRadio)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(polyRadio, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 22, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(marioRadio)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("jPanel3.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("jPanel3.border.titleFont"))); // NOI18N
        jPanel3.setName("jPanel3"); // NOI18N

        eventSelectButtonGroup.add(testingAcrobotRadio);
        testingAcrobotRadio.setText(resourceMap.getString("testingAcrobotRadio.text")); // NOI18N
        testingAcrobotRadio.setEnabled(false);
        testingAcrobotRadio.setName("testingAcrobotRadio"); // NOI18N
        testingAcrobotRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testingAcrobotRadioActionPerformed(evt);
            }
        });

        eventSelectButtonGroup.add(testingHelicopterRadio);
        testingHelicopterRadio.setText(resourceMap.getString("testingHelicopterRadio.text")); // NOI18N
        testingHelicopterRadio.setEnabled(false);
        testingHelicopterRadio.setName("testingHelicopterRadio"); // NOI18N
        testingHelicopterRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testingHelicopterRadioActionPerformed(evt);
            }
        });

        eventSelectButtonGroup.add(testingOctopusRadio);
        testingOctopusRadio.setText(resourceMap.getString("testingOctopusRadio.text")); // NOI18N
        testingOctopusRadio.setEnabled(false);
        testingOctopusRadio.setName("testingOctopusRadio"); // NOI18N
        testingOctopusRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testingOctopusRadioActionPerformed(evt);
            }
        });

        eventSelectButtonGroup.add(testingTetrisRadio);
        testingTetrisRadio.setText(resourceMap.getString("testingTetrisRadio.text")); // NOI18N
        testingTetrisRadio.setEnabled(false);
        testingTetrisRadio.setName("testingTetrisRadio"); // NOI18N
        testingTetrisRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testingTetrisRadioActionPerformed(evt);
            }
        });

        eventSelectButtonGroup.add(testingPolyRadio);
        testingPolyRadio.setText(resourceMap.getString("testingPolyRadio.text")); // NOI18N
        testingPolyRadio.setEnabled(false);
        testingPolyRadio.setName("testingPolyRadio"); // NOI18N
        testingPolyRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testingPolyRadioActionPerformed(evt);
            }
        });

        eventSelectButtonGroup.add(testingMarioRadio);
        testingMarioRadio.setText(resourceMap.getString("testingMarioRadio.text")); // NOI18N
        testingMarioRadio.setEnabled(false);
        testingMarioRadio.setName("testingMarioRadio"); // NOI18N
        testingMarioRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testingMarioRadioActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(testingAcrobotRadio, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
            .add(testingHelicopterRadio, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
            .add(testingOctopusRadio, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
            .add(testingTetrisRadio, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
            .add(testingPolyRadio, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
            .add(testingMarioRadio, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .add(testingAcrobotRadio)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(testingHelicopterRadio)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(testingOctopusRadio)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(testingTetrisRadio)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(testingPolyRadio)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(testingMarioRadio)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout controlPanelLayout = new org.jdesktop.layout.GroupLayout(controlPanel);
        controlPanel.setLayout(controlPanelLayout);
        controlPanelLayout.setHorizontalGroup(
            controlPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(controlPanelLayout.createSequentialGroup()
                .add(controlPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(controlPanelLayout.createSequentialGroup()
                        .add(140, 140, 140)
                        .add(bRun, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 160, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, controlPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                        .add(eventSelectPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jPanel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        controlPanelLayout.setVerticalGroup(
            controlPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(controlPanelLayout.createSequentialGroup()
                .add(eventSelectPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 175, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(bRun)
                .addContainerGap())
        );

        scrollPaneOuterPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("scrollPaneOuterPanel.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("scrollPaneOuterPanel.border.titleFont"))); // NOI18N
        scrollPaneOuterPanel.setName("scrollPaneOuterPanel"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        TAconsole.setBackground(resourceMap.getColor("TAconsole.background")); // NOI18N
        TAconsole.setColumns(20);
        TAconsole.setEditable(false);
        TAconsole.setFont(resourceMap.getFont("TAconsole.font")); // NOI18N
        TAconsole.setLineWrap(true);
        TAconsole.setRows(5);
        TAconsole.setBorder(null);
        TAconsole.setName("TAconsole"); // NOI18N
        jScrollPane1.setViewportView(TAconsole);

        org.jdesktop.layout.GroupLayout scrollPaneOuterPanelLayout = new org.jdesktop.layout.GroupLayout(scrollPaneOuterPanel);
        scrollPaneOuterPanel.setLayout(scrollPaneOuterPanelLayout);
        scrollPaneOuterPanelLayout.setHorizontalGroup(
            scrollPaneOuterPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
        );
        scrollPaneOuterPanelLayout.setVerticalGroup(
            scrollPaneOuterPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
        );

        progressPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("progressPanel.border.title"), javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("progressPanel.border.titleFont"))); // NOI18N
        progressPanel.setName("progressPanel"); // NOI18N

        TimeElapsed.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        TimeElapsed.setText(resourceMap.getString("TimeElapsed.text")); // NOI18N
        TimeElapsed.setName("TimeElapsed"); // NOI18N

        episodeProgressBar.setName("episodeProgressBar"); // NOI18N

        mdpProgressBar.setName("mdpProgressBar"); // NOI18N

        TimeRemaining.setBackground(resourceMap.getColor("TimeRemaining.background")); // NOI18N
        TimeRemaining.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        TimeRemaining.setText(resourceMap.getString("TimeRemaining.text")); // NOI18N
        TimeRemaining.setName("TimeRemaining"); // NOI18N

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        org.jdesktop.layout.GroupLayout progressPanelLayout = new org.jdesktop.layout.GroupLayout(progressPanel);
        progressPanel.setLayout(progressPanelLayout);
        progressPanelLayout.setHorizontalGroup(
            progressPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(progressPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(progressPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, mdpProgressBar, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
                    .add(episodeProgressBar, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
                    .add(jLabel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
                    .add(jLabel5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE))
                .add(progressPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(progressPanelLayout.createSequentialGroup()
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(progressPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, TimeElapsed, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
                            .add(jLabel7, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
                            .add(jLabel6, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)))
                    .add(progressPanelLayout.createSequentialGroup()
                        .add(9, 9, 9)
                        .add(TimeRemaining, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)))
                .addContainerGap())
        );
        progressPanelLayout.setVerticalGroup(
            progressPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(progressPanelLayout.createSequentialGroup()
                .add(progressPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(jLabel7))
                .add(4, 4, 4)
                .add(progressPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(progressPanelLayout.createSequentialGroup()
                        .add(episodeProgressBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel5))
                    .add(progressPanelLayout.createSequentialGroup()
                        .add(TimeElapsed)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jLabel6)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(progressPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(TimeRemaining)
                    .add(mdpProgressBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(20, 20, 20))
        );

        org.jdesktop.layout.GroupLayout mainPanelLayout = new org.jdesktop.layout.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainPanelLayout.createSequentialGroup()
                .add(controlPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(scrollPaneOuterPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .add(authPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(progressPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainPanelLayout.createSequentialGroup()
                .add(authPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(scrollPaneOuterPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(controlPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(progressPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 145, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(26, 26, 26))
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(org.rlcommunity.competition.proving.ProvingApp.class).getContext().getActionMap(ProvingView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        resultsWindow.setAlwaysOnTop(true);
        resultsWindow.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        resultsWindow.setName("resultsWindow"); // NOI18N
        resultsWindow.setVisible(false);
        //resultsWindow.setLocation(this.getFrame().getLocation());
        resultsWindow.setSize(600,400);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(350, 150));

        quitButton.setText(resourceMap.getString("quitButton.text")); // NOI18N
        quitButton.setName("quitButton"); // NOI18N
        quitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitButtonActionPerformed(evt);
            }
        });

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        TAresults.setColumns(20);
        TAresults.setEditable(false);
        TAresults.setFont(resourceMap.getFont("TAresults.font")); // NOI18N
        TAresults.setRows(5);
        TAresults.setBorder(null);
        TAresults.setName("TAresults"); // NOI18N
        jScrollPane2.setViewportView(TAresults);

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .add(319, 319, 319)
                .add(quitButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 112, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(83, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 341, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(quitButton)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout resultsWindowLayout = new org.jdesktop.layout.GroupLayout(resultsWindow.getContentPane());
        resultsWindow.getContentPane().setLayout(resultsWindowLayout);
        resultsWindowLayout.setHorizontalGroup(
            resultsWindowLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE)
        );
        resultsWindowLayout.setVerticalGroup(
            resultsWindowLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
        );

        criticalErrorDialog.setAlwaysOnTop(true);
        criticalErrorDialog.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        criticalErrorDialog.setName("criticalErrorDialog"); // NOI18N
        resultsWindow.setVisible(false);
        //resultsWindow.setLocation(this.getFrame().getLocation());
        resultsWindow.setSize(600,400);

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setPreferredSize(new java.awt.Dimension(350, 150));

        quitButton1.setText(resourceMap.getString("quitButton1.text")); // NOI18N
        quitButton1.setName("quitButton1"); // NOI18N
        quitButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitButton1ActionPerformed(evt);
            }
        });

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        TAError.setColumns(20);
        TAError.setEditable(false);
        TAError.setFont(resourceMap.getFont("TAError.font")); // NOI18N
        TAError.setRows(5);
        TAError.setText(resourceMap.getString("TAError.text")); // NOI18N
        TAError.setBorder(null);
        TAError.setName("TAError"); // NOI18N
        jScrollPane3.setViewportView(TAError);

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup()
                .add(319, 319, 319)
                .add(quitButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 112, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(jScrollPane3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 341, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(quitButton1)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout criticalErrorDialogLayout = new org.jdesktop.layout.GroupLayout(criticalErrorDialog.getContentPane());
        criticalErrorDialog.getContentPane().setLayout(criticalErrorDialogLayout);
        criticalErrorDialogLayout.setHorizontalGroup(
            criticalErrorDialogLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
        );
        criticalErrorDialogLayout.setVerticalGroup(
            criticalErrorDialogLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
    }// </editor-fold>//GEN-END:initComponents

    private void bLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bLoginActionPerformed
    String userName=TFusername.getText();
    String password=new String(PFpassword.getPassword());
    
    theController.makeLoginEvent(userName,password);

        // TODO add your handling code here:
}//GEN-LAST:event_bLoginActionPerformed

    private void bLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bLogoutActionPerformed
        theController.makeLogoutEvent();
        // TODO add your handling code here:
    }//GEN-LAST:event_bLogoutActionPerformed

    private void acrobotRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acrobotRadioActionPerformed
        theController.eventSelected(RLEvent.Acrobot);
}//GEN-LAST:event_acrobotRadioActionPerformed

    private void helicopterRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helicopterRadioActionPerformed
        theController.eventSelected(RLEvent.Helicopter);
        // TODO add your handling code here:
    }//GEN-LAST:event_helicopterRadioActionPerformed

    private void octopusRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_octopusRadioActionPerformed
        theController.eventSelected(RLEvent.Octopus);
        // TODO add your handling code here:
}//GEN-LAST:event_octopusRadioActionPerformed

    private void tetrisRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tetrisRadioActionPerformed
        theController.eventSelected(RLEvent.Tetris);
        // TODO add your handling code here:
    }//GEN-LAST:event_tetrisRadioActionPerformed

    private void bRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRunActionPerformed
        theController.makeRunExperimentEvent();
        // TODO add your handling code here:
    }//GEN-LAST:event_bRunActionPerformed

    private void polyRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_polyRadioActionPerformed
        theController.eventSelected(RLEvent.Polyathlon);
        // TODO add your handling code here:
    }//GEN-LAST:event_polyRadioActionPerformed

private void quitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitButtonActionPerformed
// TODO add your handling code here:
        theController.quitProvingApp();
}//GEN-LAST:event_quitButtonActionPerformed

private void quitButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitButton1ActionPerformed
    // TODO add your handling code here:
    System.exit(1);
}//GEN-LAST:event_quitButton1ActionPerformed

private void marioRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_marioRadioActionPerformed
     theController.eventSelected(RLEvent.Mario);
    // TODO add your handling code here:
}//GEN-LAST:event_marioRadioActionPerformed

private void testingAcrobotRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testingAcrobotRadioActionPerformed
    // TODO add your handling code here:
    theController.eventSelected(RLEvent.Testing_Acrobot);
}//GEN-LAST:event_testingAcrobotRadioActionPerformed

private void testingHelicopterRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testingHelicopterRadioActionPerformed
    theController.eventSelected(RLEvent.Testing_Helicopter);
    // TODO add your handling code here:
}//GEN-LAST:event_testingHelicopterRadioActionPerformed

private void testingOctopusRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testingOctopusRadioActionPerformed
    theController.eventSelected(RLEvent.Testing_Octopus);
    // TODO add your handling code here:
}//GEN-LAST:event_testingOctopusRadioActionPerformed

private void testingTetrisRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testingTetrisRadioActionPerformed
    theController.eventSelected(RLEvent.Testing_Tetris);
    // TODO add your handling code here:
}//GEN-LAST:event_testingTetrisRadioActionPerformed

private void testingPolyRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testingPolyRadioActionPerformed
    theController.eventSelected(RLEvent.Testing_Polyathlon);
    // TODO add your handling code here:
}//GEN-LAST:event_testingPolyRadioActionPerformed

private void testingMarioRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testingMarioRadioActionPerformed
    theController.eventSelected(RLEvent.Testing_Mario);
    // TODO add your handling code here:
}//GEN-LAST:event_testingMarioRadioActionPerformed

    @Action
    public void eventSelectPressed() {
        theController.someEventButtonPressed();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPasswordField PFpassword;
    public javax.swing.JTextArea TAError;
    public javax.swing.JTextArea TAconsole;
    public javax.swing.JTextArea TAresults;
    public javax.swing.JTextField TFusername;
    public javax.swing.JLabel TimeElapsed;
    public javax.swing.JLabel TimeRemaining;
    public javax.swing.JRadioButton acrobotRadio;
    private javax.swing.JPanel authPanel;
    public javax.swing.JButton bLogin;
    public javax.swing.JButton bLogout;
    public javax.swing.JButton bRun;
    public javax.swing.JPanel controlPanel;
    public javax.swing.JDialog criticalErrorDialog;
    public javax.swing.JProgressBar episodeProgressBar;
    public javax.swing.ButtonGroup eventSelectButtonGroup;
    private javax.swing.JPanel eventSelectPanel;
    public javax.swing.JRadioButton helicopterRadio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel mainPanel;
    public javax.swing.JRadioButton marioRadio;
    public javax.swing.JProgressBar mdpProgressBar;
    private javax.swing.JMenuBar menuBar;
    public javax.swing.JRadioButton octopusRadio;
    public javax.swing.JRadioButton polyRadio;
    public javax.swing.JPanel progressPanel;
    public javax.swing.JButton quitButton;
    public javax.swing.JButton quitButton1;
    public javax.swing.JDialog resultsWindow;
    public javax.swing.JPanel scrollPaneOuterPanel;
    private javax.swing.JRadioButton testingAcrobotRadio;
    private javax.swing.JRadioButton testingHelicopterRadio;
    private javax.swing.JRadioButton testingMarioRadio;
    private javax.swing.JRadioButton testingOctopusRadio;
    private javax.swing.JRadioButton testingPolyRadio;
    private javax.swing.JRadioButton testingTetrisRadio;
    public javax.swing.JRadioButton tetrisRadio;
    // End of variables declaration//GEN-END:variables


    private JDialog aboutBox;
    
    //Apple only stuff
    public static String programName="RL Competition Proving App";
        public static void showAboutBox() {
        //default title and icon
        String theMessage = programName+" was created by Brian Tanner, Matthew Radkie, and Mark Lee at the University of Alberta.";
        theMessage += "\nYou're probably using as part of the RL Competition.  Good luck!  http://rl-competition.org";
        theMessage += "\nCopyright 2007.";
        theMessage += "\nemail: brian@rl-competition.org";
        theMessage += "\nRLVizLib Version: "+rlVizLib.rlVizCore.getSpecVersion()+" "+rlVizLib.rlVizCore.getImplementationVersion();
        theMessage += "\nProving Software Version: "+ProvingView.class.getPackage().getSpecificationVersion()+" "+ProvingView.class.getPackage().getImplementationVersion();
        JOptionPane.showMessageDialog(null, theMessage, "About " + programName, JOptionPane.INFORMATION_MESSAGE);
    }
        static {
        if (System.getProperty("mrj.version") != null) {
            // the Mac specific code will go here
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        }
    }
        
    public void setDefaultButtonNames() {
        helicopterRadio.setText("Helicopter Hovering (runs remaining this week: ?)");
        acrobotRadio.setText("Acrobot (runs remaining this week: ?)");
        octopusRadio.setText("Octopus (runs remaining this week: ?)");
        tetrisRadio.setText("Tetris (runs remaining this week: ?)");
        polyRadio.setText("Polyathalon (runs remaining this week: ?)");
        marioRadio.setText("Mario (runs remaining this week: ?)");
        testingHelicopterRadio.setText("Helicopter Hovering (runs remaining: ?)");
        testingAcrobotRadio.setText("Acrobot (runs remaining: ?)");
        testingOctopusRadio.setText("Octopus (runs remaining: ?)");
        testingTetrisRadio.setText("Tetris (runs remaining: ?)");
        testingPolyRadio.setText("Polyathalon (runs remaining: ?)");
        testingMarioRadio.setText("Mario (runs remaining: ?)");
    }
    
    public void setNumberRunsAvailable(RLEvent theEvent, int num){
        switch(theEvent){
            case Helicopter :
                helicopterRadio.setText("Helicopter Hovering (runs remaining this week: " + num + ")");
                break;
            case Acrobot :
                acrobotRadio.setText("Acrobot (runs remaining this week: " + num + ")");
                break;
            case Tetris :
                tetrisRadio.setText("Tetris (runs remaining this week: " + num + ")");
                break;
            case Octopus :
                octopusRadio.setText("Octopus (runs remaining this week: " + +num + ")");
                break;
            case Polyathlon :
                polyRadio.setText("Polyathalon (runs remaining this week: " + num + ")");
                break;
            case Mario :
                marioRadio.setText("Mario (runs remaining this week: " + num + ")");
                break;
            case Testing_Helicopter :
                testingHelicopterRadio.setText("Helicopter Hovering (runs remaining: " + num + ")");
                break;
            case Testing_Acrobot :
                testingAcrobotRadio.setText("Acrobot (runs remaining: " + num + ")");
                break;
            case Testing_Tetris :
                testingTetrisRadio.setText("Tetris (runs remaining: " + num + ")");
                break;
            case Testing_Octopus :
                testingOctopusRadio.setText("Octopus (runs remaining: " + +num + ")");
                break;
            case Testing_Polyathlon :
                testingPolyRadio.setText("Polyathalon (runs remaining: " + num + ")");
                break;
            case Testing_Mario :
                testingMarioRadio.setText("Mario (runs remaining: " + num + ")");
                break;
            default:
                System.exit(1);
                break;
        }
    }
    
    public void setVisibilities(boolean allowed[], boolean validAuthToken) {
        //if there are keys available, set the radiobuttons to visible
        helicopterRadio.setEnabled(allowed[RLEvent.Helicopter.number()]);
        acrobotRadio.setEnabled(allowed[RLEvent.Acrobot.number()]);
        tetrisRadio.setEnabled(allowed[RLEvent.Tetris.number()]);
        octopusRadio.setEnabled(allowed[RLEvent.Octopus.number()]);
        polyRadio.setEnabled(allowed[RLEvent.Polyathlon.number()]);
        marioRadio.setEnabled(allowed[RLEvent.Mario.number()]);
        testingHelicopterRadio.setEnabled(allowed[RLEvent.Testing_Helicopter.number()]);
        testingAcrobotRadio.setEnabled(allowed[RLEvent.Testing_Acrobot.number()]);
        testingTetrisRadio.setEnabled(allowed[RLEvent.Testing_Tetris.number()]);
        testingOctopusRadio.setEnabled(allowed[RLEvent.Testing_Octopus.number()]);
        testingPolyRadio.setEnabled(allowed[RLEvent.Testing_Polyathlon.number()]);
        testingMarioRadio.setEnabled(allowed[RLEvent.Testing_Mario.number()]);
        //Disable the run buttons by default
        bRun.setEnabled(false);

        boolean somethingEnabled = false;
        for (boolean b : allowed) {
            somethingEnabled |= b;
        }
        boolean somethingSelected = false;
        somethingSelected |= helicopterRadio.isSelected();
        somethingSelected |= acrobotRadio.isSelected();
        somethingSelected |= tetrisRadio.isSelected();
        somethingSelected |= octopusRadio.isSelected();
        somethingSelected |= polyRadio.isSelected();
        somethingSelected |= marioRadio.isSelected();
        somethingSelected |= testingHelicopterRadio.isSelected();
        somethingSelected |= testingAcrobotRadio.isSelected();
        somethingSelected |= testingTetrisRadio.isSelected();
        somethingSelected |= testingOctopusRadio.isSelected();
        somethingSelected |= testingPolyRadio.isSelected();
        somethingSelected |= testingMarioRadio.isSelected();

        if (validAuthToken) {
            TFusername.setEnabled(false);
            PFpassword.setEnabled(false);
            bLogin.setEnabled(false);
            bLogout.setEnabled(true);
            progressPanel.setVisible(true);

            if (somethingEnabled && somethingSelected) {
                bRun.setEnabled(true);

            }
        } else {
            bLogin.setEnabled(true);
            bLogout.setEnabled(false);
            TFusername.setEnabled(true);
            PFpassword.setEnabled(true);
            progressPanel.setVisible(false);

        }
    }
    
    public void disableAll() {
        //disable radio buttons
        helicopterRadio.setEnabled(false);
        acrobotRadio.setEnabled(false);
        tetrisRadio.setEnabled(false);
        octopusRadio.setEnabled(false);
        polyRadio.setEnabled(false);
        marioRadio.setEnabled(false);
        testingHelicopterRadio.setEnabled(false);
        testingAcrobotRadio.setEnabled(false);
        testingTetrisRadio.setEnabled(false);
        testingOctopusRadio.setEnabled(false);
        testingPolyRadio.setEnabled(false);
        testingMarioRadio.setEnabled(false);
        
        bLogin.setEnabled(false);
        bLogout.setEnabled(false);


        //Disable the run buttons by default
        bRun.setEnabled(false);
    }
    

    public void dieWithResultsMessage(String theMessage) {
        disableAll();
        theMessage=theController.decorateMessage(theMessage);
        JOptionPane.showMessageDialog(null, theMessage, "Results", JOptionPane.INFORMATION_MESSAGE);
        System.exit(1);
    }
    
    public void logMessage(String theMessage) {
        //Probably a much better way to do this
        String currentText = TAconsole.getText();
        TAconsole.setText(currentText + theMessage);
        TAconsole.setCaretPosition(TAconsole.getDocument().getLength());
    }
    
    public void logError(String theMessage) {
        //Probably a much better way to do this
        String currentText = TAError.getText();
        TAError.setText(currentText + theMessage);
        TAError.setCaretPosition(TAError.getDocument().getLength());
    }
    
    public void updateView(String currentTime, String estimateTimeRemaining,int stepsPerMDP,int numMDPs,int stepsThisMDP,int totalRunSteps){
        TimeElapsed.setText(currentTime);

        episodeProgressBar.setMinimum(0);
        episodeProgressBar.setMaximum(stepsPerMDP);
        episodeProgressBar.setValue(stepsThisMDP);

        TimeRemaining.setText(estimateTimeRemaining);

        mdpProgressBar.setMinimum(0);
        mdpProgressBar.setMaximum(stepsPerMDP * numMDPs);
        mdpProgressBar.setValue(totalRunSteps);
    }

}
