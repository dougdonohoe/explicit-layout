/*
 * $Source: e:\\cvshome/explicit3/src/com/zookitec/layout/demo/ExplicitLayoutDemo.java,v $
 * $Revision: 1.1.1.1 $
 * $Date: 2002/07/31 21:53:42 $
 *
 * Copyright (c) 2001 Zooki Technologies. All rights reserved.
 *
 */
package com.zookitec.layout.demo;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import java.io.*;
import java.net.*;
import com.zookitec.layout.*;

public class ExplicitLayoutDemo extends JFrame {

    private static final String TITLE = "ExplicitLayout v3.0 Demonstration";

    private JSplitPane splitPane;
    private JEditorPane descriptionPane;
    private JTabbedPane demoTabbedPane;
    private DemoPane demoPane;
    private SourceCodePane sourceCodePane;

    private boolean about = false;

    public ExplicitLayoutDemo() {
        super(TITLE);

        addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    dispose();
                    System.exit(0);
                }
            });


        Container content = getContentPane();
        content.setLayout(new BorderLayout());

        demoTabbedPane = new JTabbedPane();
        sourceCodePane = new SourceCodePane();
        demoPane = new DemoPane();

        descriptionPane = new JEditorPane();
        descriptionPane.setContentType("text/html");
        descriptionPane.setEditable(false);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionPane);

        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, descriptionScrollPane, demoTabbedPane);
        splitPane.setDividerSize(7);

        JPanel panel = new JPanel(new BorderLayout(), true);
        panel.setBorder(BorderFactory.createEmptyBorder(0,2,2,2));
        panel.add(splitPane, BorderLayout.CENTER);
        content.add(panel, BorderLayout.CENTER);

        setJMenuBar(createMenuBar());
        URL iconURL = getClass().getResource("icon.gif");
        if (iconURL != null) {
            ImageIcon icon = new ImageIcon(iconURL);
            setIconImage(icon.getImage());
        } else {
            System.err.println("Missing icon file: icon.gif");
        }
        initBounds();

        showAbout();
        setVisible(true);
        splitPane.setDividerLocation(1.0);
    }



    private void initBounds() {
        Dimension screenSize = getToolkit().getScreenSize();
        Dimension size;
        setSize(Math.min(screenSize.width, 800), Math.min(screenSize.height, 600));
        size = getSize();
        setLocation(screenSize.width / 2 - size.width / 2, screenSize.height / 2 - size.height / 2);

    }


    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu demosMenu = new JMenu("Standard");
        JMenu proMenu = new JMenu("Professional");
        JMenu helpMenu = new JMenu("Help");

        menuBar.add(demosMenu);
        menuBar.add(proMenu);
        menuBar.add(helpMenu);
        demosMenu.add(new OpenDemoAction("Center", CenterDemo.class));
        demosMenu.add(new OpenDemoAction("Align", AlignDemo.class));
        demosMenu.add(new OpenDemoAction("Simple Form", FormDemo.class));
        demosMenu.add(new OpenDemoAction("Resizing Form", ResizingFormDemo.class));
        demosMenu.add(new OpenDemoAction("Simple Grid", SimpleGridDemo.class));
        demosMenu.add(new OpenDemoAction("Complex Grid", ComplexGridDemo.class));
        demosMenu.add(new OpenDemoAction("Component Visibility", VisibilityDemo.class));
        demosMenu.add(new OpenDemoAction("Vertical Flow", VerticalFlowDemo.class));
        demosMenu.add(new OpenDemoAction("Container Fraction", ContainerFractionDemo.class));
        demosMenu.add(new OpenDemoAction("Preferred Ratio", PreferredRatioDemo.class));


        proMenu.add(new OpenDemoAction("Geometry", GeometryDemo.class));
        proMenu.add(new OpenDemoAction("Ratio Trigger", RatioTriggerDemo.class));
        proMenu.add(new OpenDemoAction("Properties", PropertiesDemo.class));
        proMenu.add(new OpenDemoAction("Simple Form Style", FormStyleDemo.class));
        proMenu.add(new OpenDemoAction("Modified Form Style", AnotherFormStyleDemo.class));
        proMenu.add(new OpenDemoAction("Double Form Style", DoubleFormStyleDemo.class));
        proMenu.add(new OpenDemoAction("Simple Grid Style", GridStyleDemo.class));
        proMenu.add(new OpenDemoAction("Advanced Grid Style", AdvancedGridStyleDemo.class));


        helpMenu.add(new AboutAction());
        return menuBar;
    }


    public void showAbout() {
        if (!about) {
            about = true;
            setTitle(TITLE);
            loadDescription(getClass().getResource("intro.html"));
            demoTabbedPane.removeAll();
            splitPane.setDividerLocation(1.0);
        }
    }



    public void openDemo(String name, Class demoClass) {
        if (about) {
            about = false;
            splitPane.setDividerLocation(0.25);
            demoTabbedPane.addTab("Interactive", demoPane);
            demoTabbedPane.addTab("Source Code", sourceCodePane);
        }
        try {
            String descName = demoClass.getName();
            int index = descName.lastIndexOf('.');
            if (index != -1) {
                descName = descName.substring(index + 1);
            }
            setTitle(TITLE + " - " + name);
            sourceCodePane.setURL(demoClass.getResource(descName + ".java"));
            demoPane.setDemo(name, demoClass);
            loadDescription(demoClass.getResource(descName + ".html"));
        } catch (Exception e) {
            ErrorFactory.showErrorMessage(this, "Unable to show demo - " + name,
                         e.toString(), ErrorFactory.SUPPORT_FOOTER);
            showAbout();
        }
    }

    private void loadDescription(URL url) {
        try {
            if (url == null) {
                descriptionPane.setText("Description missing!");
            } else {
                descriptionPane.setPage(url);

            }
        } catch (IOException e) {
            descriptionPane.setText("Unable to load description " + e);
        }
    }


    class AboutAction extends AbstractAction {
        public AboutAction() {
            super("About");
        }

        public void actionPerformed(ActionEvent e) {
            showAbout();
        }
    }


    class OpenDemoAction extends AbstractAction {

        Class demoClass;

        public OpenDemoAction(String name, Class demoClass) {
            super(name);
            this.demoClass = demoClass;
        }

        public void actionPerformed(ActionEvent e) {
            openDemo((String)this.getValue(Action.NAME), demoClass);
        }

    }

    public static void main(String [] args) {
        new ExplicitLayoutDemo();
    }

}

