/*
 * $Source: e:\\cvshome/explicit3/src/com/zookitec/layout/demo/ResizingFormDemo.java,v $
 * $Revision: 1.1.1.1 $
 * $Date: 2002/07/31 21:53:42 $
 *
 * Copyright (c) 2001 Zooki Technologies. All rights reserved.
 *
 */
package com.zookitec.layout.demo;

import java.awt.*;
import java.awt.event.*;
import com.zookitec.layout.*;
import javax.swing.*;
import java.io.*;

/**
 * Demonstrates how to create a form that resizes to fit the container.
 */
public class ResizingFormDemo extends JPanel {


    private static final int HGAP = 15;
    private static final int VGAP = 10;

    private DemoLabel titleLabel = new DemoLabel("Title");
    private DemoLabel firstnameLabel = new DemoLabel("First Name");
    private DemoLabel surnameLabel = new DemoLabel("Surname");
    private DemoLabel emailLabel = new DemoLabel("E-mail");
    private DemoLabel commentLabel = new DemoLabel("Comment");
    private DemoLabel panelLabel = new DemoLabel("Panel");

    private DemoField titleField = new DemoField("Mr", 30, 21); // preferred size 30, 21
    private DemoField firstnameField = new DemoField("Joe", 120, 21);
    private DemoField surnameField = new DemoField("Bloggs", 120, 21);
    private DemoField commentField = new DemoField("bla bla bla bla bla", 200, 21);
    private DemoField emailField = new DemoField("info@zookitec.com", 200, 21);


    private ExplicitLayout layout = new ExplicitLayout();

    public ResizingFormDemo() {

        setLayout(layout);

        //A group of label components is defined using an array.
        Component [] labelGroup = {titleLabel, firstnameLabel, surnameLabel, emailLabel, commentLabel};

        //Add the first label a fixed distance from the top of the container.
        add(titleLabel, new ExplicitConstraints(titleLabel,
                            ContainerEF.left(this).add(HGAP),
                            ContainerEF.top(this).add(VGAP),
                            null, null, 0.0, 0.0, true, true));

        //Add the remaining labels, each VGAP pixels below the previous label and field.
        add(firstnameLabel, createBelowConstraints(firstnameLabel, titleLabel, titleField, VGAP));
        add(surnameLabel, createBelowConstraints(surnameLabel, firstnameLabel, firstnameField, VGAP));
        add(commentLabel, createBelowConstraints(commentLabel, surnameLabel, surnameField, VGAP));
        add(emailLabel, createBelowConstraints(emailLabel, commentLabel, commentField, VGAP));


        //Create the expression for the field x coordinate to GAP pixels to the right of
        //the group of label components.
        Expression fieldX = GroupEF.right(labelGroup).add(HGAP);

        //Define an expression for the width available for field components.
        Expression availableWidth = ContainerEF.right(this).subtract(fieldX).subtract(HGAP);

        //Add the title field aligned to its label.
        add(titleField, new ExplicitConstraints(titleField,
                            fieldX, ComponentEF.top(titleLabel),
                            null, null, 0.0, 0.0, true, true));

        //Add the firstname field aligned to its label.
        //Set the width to the smaller of its preferred size and half the available width.
        add(firstnameField,  new ExplicitConstraints(firstnameField,
                          fieldX, ComponentEF.top(firstnameLabel),
                          MathEF.min(ComponentEF.preferredWidth(firstnameField),
                                     availableWidth.divide(2)),
                          null, 0.0, 0.0, true, true));



        //Add the surname field aligned to its label.
        //Set the width to the smaller of its preferred size and half the available width.
        add(surnameField, new ExplicitConstraints(surnameField,
                          fieldX, ComponentEF.top(surnameLabel),
                          MathEF.min(ComponentEF.preferredWidth(surnameField),
                                     availableWidth.divide(2)),
                          null, 0.0, 0.0, true, true));


        //Add the comment field aligned to its label.
        //Set the width to fill the available width.
        //Set the height to fill the space between its top and the email field.
        add(commentField, new ExplicitConstraints(commentField,
                    fieldX, ComponentEF.top(commentLabel), availableWidth,
                    MathEF.max(
                        ContainerEF.bottom(this).subtract(ComponentEF.top(commentLabel)).
                            subtract(ComponentEF.preferredHeight(emailField).add(2 * VGAP)),
                        ComponentEF.preferredHeight(commentField)),
                  0.0, 0.0, true, true));

        //Add the email field aligned to its label;
        //Set the width to the smaller of its preferred size and the available width.
        add(emailField, new ExplicitConstraints(emailField,
                            fieldX, ComponentEF.top(emailLabel),
                            MathEF.min(ComponentEF.preferredWidth(emailField), availableWidth),
                            null, 0.0, 0.0, true, true));

        //Add a panel (just a label in this case) right aligned in the container
        //and vertically aligned with the top of the title label.
        //Set the width to half the available width minus a gap.
        //Set the hight to fill the space between its top and the bottom of the surname field.
        add(panelLabel, new ExplicitConstraints(panelLabel,
            ContainerEF.right(this).subtract(HGAP),
            ComponentEF.top(titleLabel),
            availableWidth.divide(2).subtract(HGAP),
            ComponentEF.bottom(surnameField).subtract(ComponentEF.top(titleLabel)),
            1.0, 0.0, true, true));

    }

    /**
     * Creates constraints for component c such that it is positioned
     * gap pixels below the lowest bottom of components above1 and above2.
     * This can be used to position a label, gap pixels below both the label
     * and field above it; the field may have a different height to the label.
     *
     */
    public ExplicitConstraints createBelowConstraints(Component c, Component above1, Component above2, int gap) {
        return new ExplicitConstraints(c,
            ComponentEF.left(above1),
            MathEF.max(ComponentEF.bottom(above1), ComponentEF.bottom(above2)).add(gap),
            null, null, 0.0, 0.0, true, true);
    }


}


