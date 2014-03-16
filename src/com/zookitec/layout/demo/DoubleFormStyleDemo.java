/*
 * $Source: e:\\cvshome/explicit3/src/com/zookitec/layout/demo/DoubleFormStyleDemo.java,v $
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
 * Demonstrates how to create two columns of labels and fields by
 * calling FormStyle twice on the same container.
 */
public class DoubleFormStyleDemo extends JPanel {

    private JLabel titleLabel = new JLabel("Title");
    private JLabel firstnameLabel = new JLabel("First Name");
    private JLabel surnameLabel = new JLabel("Surname");
    private JLabel commentLabel = new JLabel("Comment");

    private DemoField titleField = new DemoField("Mr", 30, 21); // preferred size 30, 21
    private DemoField firstnameField = new DemoField("Joe", 80, 21);
    private DemoField surnameField = new DemoField("Bloggs", 80, 21);
    private DemoField commentField = new DemoField("bla bla bla bla bla", 200, 60);


    //A group of components is defined using an array.
    Component [] labels = {titleLabel, firstnameLabel, surnameLabel, commentLabel};
    Component [] fields = {titleField, firstnameField, surnameField, commentField};

    private JLabel telLabel = new JLabel("Tel.");
    private JLabel faxLabel = new JLabel("Fax");
    private JLabel emailLabel = new JLabel("E-Mail");
    private DemoField telField = new DemoField("0123 456789", 100, 21);
    private DemoField faxField = new DemoField("0123 456789", 100, 21);
    private DemoField emailField = new DemoField("info@zookitec.com", 200, 21);

    Component [] labels2 = {telLabel, faxLabel, emailLabel};
    Component [] fields2 = {telField, faxField, emailField};

    private ExplicitLayout layout = new ExplicitLayout();

    public DoubleFormStyleDemo() {
        setLayout(layout);

        commentLabel.setVerticalAlignment(JLabel.TOP);

        //This is the simplest use of FormStyle
        FormStyle.addComponents(this, labels, fields,
                                ContainerEF.left(this).add(5),
                                ContainerEF.top(this).add(5), 5, 5,
                                ContainerEF.centerX(this));


        FormStyle.addComponents(this, labels2, fields2,
                                GroupEF.right(fields).add(10),
                                ContainerEF.top(this).add(5), 5, 5,
                                ContainerEF.right(this).subtract(5));
    }


}



