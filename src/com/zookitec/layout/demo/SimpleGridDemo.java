/*
 * $Source: e:\\cvshome/explicit3/src/com/zookitec/layout/demo/SimpleGridDemo.java,v $
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
 * Demonstrates how to lay components out on a grid.
 */
public class SimpleGridDemo extends JPanel {

    private DemoLabel label1 = new DemoLabel("1 (0,0)");
    private DemoLabel label2 = new DemoLabel("2 (2,0)");
    private DemoLabel label3 = new DemoLabel("3 (0,1)");
    private DemoLabel label4 = new DemoLabel("4 (1,1)");
    private DemoLabel label5 = new DemoLabel("5 (2,1)");

    private ExplicitLayout layout = new ExplicitLayout();

    public SimpleGridDemo() {
        setLayout(layout);

        //Create expressions for the cell width and height of a 3 x 2 grid
        Expression cellWidth = ContainerEF.widthFraction(this, 1/3.0);
        Expression cellHeight = ContainerEF.heightFraction(this, 1/2.0);

        add(label1, createConstraint(label1, 0, 0, cellWidth, cellHeight));
        add(label2, createConstraint(label2, 2, 0, cellWidth, cellHeight));
        add(label3, createConstraint(label3, 0, 1, cellWidth, cellHeight));
        add(label4, createConstraint(label4, 1, 1, cellWidth, cellHeight));
        add(label5, createConstraint(label5, 2, 1, cellWidth, cellHeight));

    }

    /**
     * Creates constraints for the specified component c.
     * The component's position is specified by a column and row of the grid.
     * The component's size is equal to the grid cell size.
     *
     */
    private ExplicitConstraints createConstraint(Component c, int col, int row,
                                                 Expression cellWidth, Expression cellHeight) {
        return new ExplicitConstraints(c,
            ContainerEF.gridX(this, col, cellWidth), ContainerEF.gridY(this, row, cellHeight),
            cellWidth, cellHeight, 0.0, 0.0, true, true);
    }



}
