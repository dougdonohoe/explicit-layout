/*
 * $Source: e:\\cvshome/explicit3/src/com/zookitec/layout/demo/VerticalFlowDemo.java,v $
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
 * Demonstrates how to layout components in a vertical flow components.
 */
public class VerticalFlowDemo extends JPanel {

    public static final int GAP = 10;

    private DemoLabel label1 = new DemoLabel("1", 100, 20);
    private DemoLabel label2 = new DemoLabel("2", 50, 40);
    private DemoLabel label3 = new DemoLabel("3", 150, 20);
    private DemoLabel label4 = new DemoLabel("4", 200, 50);
    private DemoLabel label5 = new DemoLabel("5", 100, 20);
    private DemoLabel label6 = new DemoLabel("6", 100, 30);

    private ExplicitLayout layout = new ExplicitLayout();

    public VerticalFlowDemo() {
        setLayout(layout);

        ExplicitConstraints ec = new ExplicitConstraints(label1);
        ec.setY(ContainerEF.top(this));
        add(label1, ec);
        add(label2, createConstraints(label2, label1));
        add(label3, createConstraints(label3, label2));
        add(label4, createConstraints(label4, label3));
        add(label5, createConstraints(label5, label4));
        add(label6, createConstraints(label6, label5));

    }

    /**
     * Create constraints for component c, such that its y coordinate is GAP pixels below component a.
     *
     * @param c the component to get the constraints for.
     * @param a the component immediately above component c.
     */
    private ExplicitConstraints createConstraints(Component c, Component a) {
        ExplicitConstraints ec = new ExplicitConstraints(c);
        ec.setY(ComponentEF.bottom(a).add(GAP));
        return ec;
    }
}


