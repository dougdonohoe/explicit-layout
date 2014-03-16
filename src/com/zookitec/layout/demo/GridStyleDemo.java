/*
 * $Source: e:\\cvshome/explicit3/src/com/zookitec/layout/demo/GridStyleDemo.java,v $
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
 *
 */
public class GridStyleDemo extends JPanel {

    private Component [] labels = new Component[20];

    private ExplicitLayout layout = new ExplicitLayout();

    public GridStyleDemo() {
        setLayout(layout);

        for (int i = 0; i < labels.length; i++) {
            if (i % 3 != 0) {
                labels[i] = new DemoLabel(String.valueOf(i));
            }
        }

        GridStyle.addComponents(this, labels, 4, 5, 5, 5);

    }


}



