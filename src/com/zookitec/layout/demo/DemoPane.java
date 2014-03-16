/*
 * $Source: e:\\cvshome/explicit3/src/com/zookitec/layout/demo/DemoPane.java,v $
 * $Revision: 1.1.1.1 $
 * $Date: 2002/07/31 21:53:42 $
 *
 * Copyright (c) 2001 Zooki Technologies. All rights reserved.
 *
 */
package com.zookitec.layout.demo;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.*;


public class DemoPane extends JPanel {

    private JDesktopPane desktop;
    private JInternalFrame frame;
    private JInternalFrame edframe;

    private JPanel emptyPanel;

    public DemoPane() {
        desktop = new JDesktopPane();

        setLayout(new BorderLayout(5,5));
        add(new JLabel("Try resizing the frame below to see how it affects the component layout"), BorderLayout.NORTH);
        add(desktop, BorderLayout.CENTER);

        emptyPanel = new JPanel();

        frame = new JInternalFrame("", true, false, false, false);
        desktop.add(frame);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setBackground(new Color(192,192,255));
        frame.setBounds(10,10, 400, 300);
        URL iconURL = getClass().getResource("icon.gif");
        ImageIcon icon = null;
        if (iconURL != null) {
            icon = new ImageIcon(getClass().getResource("icon.gif"));
        } else {
            System.err.println("Missing icon file: icon.gif");
        }
        edframe = new JInternalFrame("", true, false, false, true);
        desktop.add(edframe);
        edframe.getContentPane().setLayout(new BorderLayout());
        edframe.setBackground(new Color(192,192,255));
        edframe.setBounds(300, 40, 400, 300);

        if (icon != null) {
            frame.setFrameIcon(icon);
            edframe.setFrameIcon(icon);
        }

        frame.setVisible(true);
        edframe.setVisible(false);

    }


    public void setDemo(String name, Class demoClass) throws Exception {
        frame.setVisible(false);
        edframe.setVisible(false);

        Component component = (Component)demoClass.newInstance();

        frame.getContentPane().removeAll();
        frame.getContentPane().add(component, BorderLayout.CENTER);
        frame.setTitle(name);
        frame.setVisible(true);


        if (component instanceof PropertiesDemo) {
            PropertiesDemo pd = (PropertiesDemo)component;
            Component editor = pd.getEditor();
            edframe.getContentPane().removeAll();
            edframe.getContentPane().add(editor, BorderLayout.CENTER);
            edframe.setTitle("Constraints Editor - " + PropertiesDemo.PROPERTIES_RESOURCE);
            edframe.setVisible(true);
        } else {
            edframe.setVisible(false);
        }
    }

}


