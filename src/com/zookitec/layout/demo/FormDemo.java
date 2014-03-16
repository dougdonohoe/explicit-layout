/*
 * $Source: e:\\cvshome/explicit3/src/com/zookitec/layout/demo/FormDemo.java,v $
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

/**
 * Demonstrates how to create a simple form.
 *
 * Since ExplicitLayout v3.0 Professional, this style of layout
 * can be achieved much easier using the FormStyle class.
 */
public class FormDemo extends JPanel {


    private static final int HGAP = 20;
    private static final int VGAP = 10;

    private DemoLabel titleLabel = new DemoLabel("Title");
    private DemoLabel firstnameLabel = new DemoLabel("First Name");
    private DemoLabel surnameLabel = new DemoLabel("Surname");
    private DemoLabel emailLabel = new DemoLabel("E-mail");
    private DemoLabel commentLabel = new DemoLabel("Comment");

    private DemoField titleField = new DemoField("Mr", 30, 21); // preferred size 30, 21
    private DemoField firstnameField = new DemoField("Joe", 80, 21);
    private DemoField surnameField = new DemoField("Bloggs", 80, 21);
    private DemoField commentField = new DemoField("bla bla bla bla bla", 200, 60);
    private DemoField emailField = new DemoField("info@zookitec.com", 200, 21);

    private ExplicitLayout layout = new ExplicitLayout();

    public FormDemo() {
        setLayout(layout);

        //A group of components is defined using an array.
        Component [] labelGroup = {titleLabel, firstnameLabel, surnameLabel, emailLabel, commentLabel};

        //Set the x coordinate to a fixed distance from the left of the container.
        Expression labelX = ContainerEF.left(this).add(20);

        //Add the first label a fixed distance from the top of the container.
        add(titleLabel, new ExplicitConstraints(titleLabel, labelX, ContainerEF.top(this).add(20)));

        //Add the remaining labels, each VGAP pixels below the previous label and field.
        add(firstnameLabel, createBelowConstraints(firstnameLabel, labelX, titleLabel, titleField, VGAP));
        add(surnameLabel, createBelowConstraints(surnameLabel, labelX, firstnameLabel, firstnameField, VGAP));
        add(commentLabel, createBelowConstraints(commentLabel, labelX, surnameLabel, surnameField, VGAP));
        add(emailLabel, createBelowConstraints(emailLabel, labelX, commentLabel, commentField, VGAP));

        //Sets the field x coordinate to GAP pixels to the right of
        //the group of label components (see labelGroup defined above).
        Expression fieldX = GroupEF.right(labelGroup).add(HGAP);

        //Add the field components so that each is alined with its label.
        add(titleField,  createAlignConstraints(titleField, fieldX, titleLabel));
        add(firstnameField, createAlignConstraints(firstnameField, fieldX, firstnameLabel));
        add(surnameField, createAlignConstraints(surnameField, fieldX, surnameLabel));
        add(commentField, createAlignConstraints(commentField, fieldX, commentLabel));
        add(emailField, createAlignConstraints(emailField, fieldX, emailLabel));

    }

    /**
     * Creates constraints for component c such that it is positioned
     * gap pixels below the lowest bottom of components a1 and a2.
     * This can be used to position a label, gap pixels below both the label
     * and field above it; the field may have a different height to the label.
     *
     */
    public ExplicitConstraints createBelowConstraints(Component c, Expression x,
                                Component above1, Component above2, int gap) {
        return new ExplicitConstraints(c, x, MathEF.max(ComponentEF.bottom(above1),
                           ComponentEF.bottom(above2)).add(gap));
    }

    /**
     * Creates constraints for the specified field such that its top
     * is aligned with the top of the specified label.
     *
     */
    public ExplicitConstraints createAlignConstraints(Component field, Expression x, Component label) {
        return new ExplicitConstraints(field, x, ComponentEF.top(label));
    }

}


