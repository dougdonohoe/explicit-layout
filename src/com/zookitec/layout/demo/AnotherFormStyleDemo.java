/*
 * $Source: e:\\cvshome/explicit3/src/com/zookitec/layout/demo/AnotherFormStyleDemo.java,v $
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
 * Demonstrates how to add all the components of a simple form
 * by calling a single method to the FormStyle class.
 *
 * Demonstrates how you can easily add to layouts
 * defined with the styles package.
 *
 * Demonstrates how to modify the layout of components
 * added by FormStyle.
 */
public class AnotherFormStyleDemo extends JPanel {

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

    private JPanel separatePanel = new JPanel();

    //A group of components is defined using an array.
    Component [] labels = {titleLabel, firstnameLabel, surnameLabel, emailLabel, commentLabel};
    Component [] fields = {titleField, firstnameField, surnameField, emailField, commentField};


    private ExplicitLayout layout = new ExplicitLayout();

    public AnotherFormStyleDemo() {
        setLayout(layout);

        commentLabel.setVerticalAlignment(JLabel.TOP);
        separatePanel.setBorder(BorderFactory.createEtchedBorder());

        //Add label and field components using FormStyle
        FormStyle.addComponents(this, labels, fields,
                                ContainerEF.left(this).add(5),
                                ContainerEF.top(this).add(5),
                                20, 10, ContainerEF.right(this).subtract(5));

        //Modify some constraints defined by FormStyle
        for (int i = 0; i < 3; i++) {
            ExplicitConstraints ec = layout.getConstraints(fields[i]);
            ec.setWidth(MathEF.min(ec.getWidth(),
                                   ComponentEF.preferredWidth(fields[i])));
        }

        //Add another component to the layout relative to components
        //added by FormStyle
        add(separatePanel, new ExplicitConstraints(separatePanel,
                   ComponentEF.left(commentField),
                   ComponentEF.bottom(commentField).add(5),
                   ContainerEF.right(this).subtract(5), false,
                   ContainerEF.bottom(this).subtract(5), false));
    }


}



