/*
 * $Source: e:\\cvshome/explicit3/src/com/zookitec/layout/demo/ComplexGridDemo.java,v $
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
public class ComplexGridDemo extends JPanel {

    private static final Insets INSETS = new Insets(15,15,15,15);

    private DemoLabel label1 = new DemoLabel("2 x 1 no gap");
    private DemoLabel label2 = new DemoLabel("1 x 1");
    private DemoLabel label3 = new DemoLabel("1 x 2");
    private DemoLabel label4 = new DemoLabel("3 x 1");
    private DemoLabel label5 = new DemoLabel("1 x 1");
    private DemoLabel label6 = new DemoLabel("3 x 2 big gap");
    private DemoLabel label7 = new DemoLabel("1 x 5");
    private DemoLabel label8 = new DemoLabel("4 x 1");

    private ExplicitLayout layout = new ExplicitLayout();

    public ComplexGridDemo() {
        setLayout(layout);

        //Create expressions for the cell width and height of a 5 x 5 grid
        Expression cellWidth = ContainerEF.widthFraction(this, 1/5.0);
        Expression cellHeight = ContainerEF.heightFraction(this, 1/5.0);

        //Add the components width the specified grid constraints.
        add(label1, createConstraint(label1, 0, 0, 2, 1, cellWidth, cellHeight, 0));
        add(label2, createConstraint(label2, 0, 1, 1, 1, cellWidth, cellHeight, 2));
        add(label3, createConstraint(label3, 0, 2, 1, 2, cellWidth, cellHeight, 2));
        add(label4, createConstraint(label4, 1, 1, 3, 1, cellWidth, cellHeight, 2));
        add(label5, createConstraint(label5, 3, 0, 1, 1, cellWidth, cellHeight, 2));
        add(label6, createConstraint(label6, 1, 2, 3, 2, cellWidth, cellHeight, 15));
        add(label7, createConstraint(label7, 4, 0, 1, 5, cellWidth, cellHeight, 2));
        add(label8, createConstraint(label8, 0, 4, 4, 1, cellWidth, cellHeight, 2));

    }


    /**
     * Creates constraints for the specified component c.
     * The component's position is specified by a column and row of the grid.
     * The component's size is specified by a width and height in grid cells.
     * A specified gap is also placed around the component.
     *
     */
    private ExplicitConstraints createConstraint(Component c, int column, int row,
                                                        int width, int height,
                                                        Expression cellWidth, Expression cellHeight,
                                                        int gap) {
        return new ExplicitConstraints(c,
            ContainerEF.gridX(this, column, cellWidth).add(gap),
            ContainerEF.gridY(this, row, cellHeight).add(gap),
            cellWidth.multiply(width).subtract(2 * gap),
            cellHeight.multiply(height).subtract(2 * gap),
            0.0,0.0, true, true);
    }


    public Insets getInsets() {
        return INSETS;
    }

}
