/*
 * $Source: e:\\cvshome/explicit3/src/com/zookitec/layout/demo/CenterDemo.java,v $
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
 * Demonstrates how to center a component within its container
 */
public class CenterDemo extends JPanel {

    private DemoLabel label1 = new DemoLabel("1", 320, 240);
    private ExplicitLayout layout = new ExplicitLayout();

    public CenterDemo() {
        setLayout(layout);

        add(label1, new ExplicitConstraints(label1,
                ContainerEF.centerX(this),
                ContainerEF.centerY(this),
                MathEF.min(ComponentEF.preferredWidth(label1), ContainerEF.width(this)),
                MathEF.min(ComponentEF.preferredHeight(label1), ContainerEF.height(this)),
                0.5, 0.5, true, true));
    }


}


