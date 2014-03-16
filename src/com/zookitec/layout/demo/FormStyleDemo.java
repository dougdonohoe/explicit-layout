/*
 * $Source: e:\\cvshome/explicit3/src/com/zookitec/layout/demo/FormStyleDemo.java,v $
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
import com.zookitec.layout.styles.*;

/**
 * Demonstrates how to add all the components for a simple form
 * by calling a single method to the FormStyle class.
 * This is the simplest use of FormStyle.
 *
 */
public class FormStyleDemo extends JPanel {

    private JLabel titleLabel = new JLabel("Title");
    private JLabel firstnameLabel = new JLabel("First Name");
    private JLabel surnameLabel = new JLabel("Surname");
    private JLabel emailLabel = new JLabel("E-mail");
    private JLabel commentLabel = new JLabel("Comment");

    private DemoField titleField = new DemoField("Mr", 30, 21); // preferred size 30, 21
    private DemoField firstnameField = new DemoField("Joe", 80, 21);
    private DemoField surnameField = new DemoField("Bloggs", 80, 21);
    private DemoField commentField = new DemoField("bla bla bla bla bla", 200, 60);
    private DemoField emailField = new DemoField("info@zookitec.com", 200, 21);

    //A group of components is defined using an array.
    Component [] labels = {titleLabel, firstnameLabel, surnameLabel, emailLabel, commentLabel};
    Component [] fields = {titleField, firstnameField, surnameField, emailField, commentField};


    private ExplicitLayout layout = new ExplicitLayout();

    public FormStyleDemo() {
        setLayout(layout);

        commentLabel.setVerticalAlignment(JLabel.TOP);

        //This is the simplest use of FormStyle
        FormStyle.addComponents(this, labels, fields);

    }


}



