/*
 * $Source: e:\\cvshome/explicit3/src/com/zookitec/layout/demo/PreferredRatioDemo.java,v $
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

/**
 * Demonstrates how to set heights in a ratio based on the preferred size of components.
 */
public class PreferredRatioDemo extends Container {

    private DemoLabel label1 = new DemoLabel("1", 100, 20);
    private DemoLabel label2 = new DemoLabel("2", 50, 40);
    private DemoLabel label3 = new DemoLabel("3", 150, 20);
    private DemoLabel label4 = new DemoLabel("4", 200, 50);
    private DemoLabel label5 = new DemoLabel("5", 100, 20);
    private DemoLabel label6 = new DemoLabel("6", 100, 30);

    private ExplicitLayout layout = new ExplicitLayout();

    public PreferredRatioDemo() {
        setLayout(layout);

        Expression rowHeight = ContainerEF.heightFraction(this, 1 / 6.0);

        //Define a group of label components as an array.
        Component [] group = {label1, label2, label3, label4, label5, label6};

        //Define an expression for the sum of the preferred heights
        //of components in the group.
        Expression groupHeight = GroupEF.preferredHeightSum(group);

        add(label1, createConstraints(label1, null, groupHeight));
        add(label2, createConstraints(label2, label1, groupHeight));
        add(label3, createConstraints(label3, label2, groupHeight));
        add(label4, createConstraints(label4, label3, groupHeight));
        add(label5, createConstraints(label5, label4, groupHeight));
        add(label6, createConstraints(label6, label5, groupHeight));

    }

    /**
     * Creates constraints fot the specified component, c
     * The component, c, is positioned below component a.
     * The height is set to container height * preferred height / group height.
     *
     */
    private ExplicitConstraints createConstraints(Component c, Component a, Expression groupHeight) {
        ExplicitConstraints ec = new ExplicitConstraints(c);
        if (a == null) {
            ec.setY(ContainerEF.top(this));
        } else {
            ec.setY(ComponentEF.bottom(a));
        }
        ec.setHeight(ComponentEF.preferredHeight(c).divide(groupHeight).multiply(ContainerEF.height(this)));
        return ec;
    }
}


