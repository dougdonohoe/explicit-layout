/*
 * $Source: e:\\cvshome/explicit3/src/com/zookitec/layout/demo/ContainerFractionDemo.java,v $
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
 * Demonstrates how to set component widths equal to a fraction of
 * the container's width.
 */
public class ContainerFractionDemo extends JPanel {

    private DemoLabel label1 = new DemoLabel("1 (0.5)");
    private DemoLabel label2 = new DemoLabel("2 (0.3)");
    private DemoLabel label3 = new DemoLabel("3 (fill)");

    private ExplicitLayout layout = new ExplicitLayout();

    public ContainerFractionDemo() {
        Expression defaultY = ContainerEF.top(this);
        Expression defaultHeight = ContainerEF.height(this);

        setLayout(layout);

        //Add label 1 to the left of the container and set its width equal to
        //0.5 * the container's width.
        add(label1, new ExplicitConstraints(label1, ContainerEF.left(this), defaultY,
                    ContainerEF.widthFraction(this, 0.5), defaultHeight));

        //Add label 2 adjacent to label 1 and set its width equal to
        //0.3 * the container's width.
        add(label2, new ExplicitConstraints(label2, ComponentEF.right(label1), defaultY,
                    ContainerEF.widthFraction(this, 0.3), defaultHeight));

        //Add label 3 adjacent to label 2 and set its width to fill
        //the remaining space in the container.
        //As of ExplicitLayout 3.0 this is simplified by the ability to specify
        //the right x coordinate instead of the width.
        add(label3, new ExplicitConstraints(label3, ComponentEF.right(label2), defaultY,
            ContainerEF.right(this), false, defaultHeight, true));
    }
}


