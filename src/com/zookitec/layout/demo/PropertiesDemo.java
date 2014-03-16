/*
 * $Source: e:\\cvshome/explicit3/src/com/zookitec/layout/demo/PropertiesDemo.java,v $
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
import com.zookitec.layout.*;
import com.zookitec.layout.source.*;

import java.io.*;
import java.util.*;
import java.net.*;

/**
 */
public class PropertiesDemo extends JPanel {

    public static final String PROPERTIES_RESOURCE = "layout.properties";

    private Insets insets = new Insets(5,5,5,5);

    private PropertiesConstraintsSource pcs;
    private PropertiesConstraintsEditor editor;

    private ExplicitLayout layout = new ExplicitLayout();

    private JButton button1 =  new JButton("one");
    private JButton button2 =  new JButton("two");
    private JButton button3 =  new JButton("three");
    private JButton button4 =  new JButton("four");
    private JTextArea field1 =  new JTextArea(10,10);
    private JTextArea field2 =  new JTextArea(10,10);
    private JScrollPane scroll1 = new JScrollPane(field1);
    private JScrollPane scroll2 = new JScrollPane(field2);

    public PropertiesDemo() throws IOException {

        setLayout(layout);
        add(button1, new ExplicitConstraints(button1, "button1"));
        add(button2, new ExplicitConstraints(button2, "button2"));
        add(button3, new ExplicitConstraints(button3, "button3"));
        add(button4, new ExplicitConstraints(button4, "button4"));
        add(scroll1, new ExplicitConstraints(scroll1, "field1"));
        add(scroll2, new ExplicitConstraints(scroll2, "field2"));

        //Load constraints from a properties file.
        URL url = getClass().getResource(PROPERTIES_RESOURCE);
        if (url == null) {
            throw new NullPointerException("Missing constraints properties file : " + PROPERTIES_RESOURCE);
        }
        pcs = new PropertiesConstraintsSource(url);
        editor = new PropertiesConstraintsEditor(pcs, this);
        try {
            pcs.defineConstraints(layout, null);
        } catch (InvalidConstraintsException e) {
            ErrorFactory.showErrorMessage(this, "Invalid Constraints Attribute",
                        e.getMessage(), ErrorFactory.SUPPORT_FOOTER);
        }
    }

    /**
     * This catches any infinitely recursive definitions that you may
     * accidentally introduce when experimenting with the constraints properties.
     * It pops up a message box to tell you where the problem is.
     */
    public void doLayout() {
        try {
            super.doLayout();
        } catch (IllegalStateException e) {
            ErrorFactory.showErrorMessage(this, "Illegal State",
                        e.getMessage(), "");
        }
    }



    public Component getEditor() {
        return editor;
    }

    public Insets getInsets() {
        return insets;
    }

}

