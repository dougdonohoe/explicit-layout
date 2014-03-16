/*
 * $Source: e:\\cvshome/explicit3/src/com/zookitec/layout/demo/AlignDemo.java,v $
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
 * Demonstrates how to align components.
 */
public class AlignDemo extends JPanel {

    //The DemoLabel constructor takes a hard-coded preferred size for
    //the purposes of demonstration.
    private DemoLabel label1 = new DemoLabel("1", 100, 30);
    private DemoLabel label2 = new DemoLabel("2", 50, 30);
    private DemoLabel label3 = new DemoLabel("3", 150, 30);
    private DemoLabel label4 = new DemoLabel("4", 200, 30);
    private DemoLabel label5 = new DemoLabel("5", 100, 30);
    private DemoLabel label6 = new DemoLabel("6", 150, 30);

    private ExplicitLayout layout = new ExplicitLayout();

    public AlignDemo() {
        setLayout(layout);
        Expression rowHeight = ContainerEF.heightFraction(this, 1 / 6.0);

        add(label1, createConstraints(label1, ContainerEF.left(this), 0, rowHeight, 0.0));
        add(label2, createConstraints(label2, ContainerEF.left(this), 1, rowHeight, 0.0));
        add(label3, createConstraints(label3, ContainerEF.centerX(this), 2, rowHeight, 0.5));
        add(label4, createConstraints(label4, ContainerEF.centerX(this), 3, rowHeight, 0.5));
        add(label5, createConstraints(label5, ContainerEF.right(this), 4, rowHeight, 1.0));
        add(label6, createConstraints(label6, ContainerEF.right(this), 5, rowHeight, 1.0));

    }


    /**
     * Create constraints for specified component
     */
    private ExplicitConstraints createConstraints(Component c, Expression x, int row,
                                                  Expression rowHeight, double originX) {
        return new ExplicitConstraints(c, x, ContainerEF.gridY(this, row, rowHeight),
            MathEF.min(ComponentEF.preferredWidth(c), ContainerEF.width(this)),
            MathEF.min(ComponentEF.preferredHeight(c), rowHeight),
            originX, 0.0, true, true);
    }

}


