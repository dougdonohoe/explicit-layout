/*
 * $Source: e:\\cvshome/explicit3/src/com/zookitec/layout/demo/AdvancedGridStyleDemo.java,v $
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
public class AdvancedGridStyleDemo extends JPanel {

    private Component [] components = new Component[20];

    private ExplicitLayout layout = new ExplicitLayout();

    public AdvancedGridStyleDemo() {
        setLayout(layout);

        for (int i = 0; i < components.length; i++) {
            if (i != 0 && i != 1 && i != 5 && i != 6 && i != 19
                && i != 8 && i != 9) {
                components[i] = new DemoLabel(String.valueOf(i));
            }
        }

        components[0] = new DemoField("Hello", 150, 100);

        GridStyle.Options [] options = new GridStyle.Options[components.length];

        options[0] = new GridStyle.Options(2,2, null, false, false,
                                           GridStyle.ALIGN_LEFT, GridStyle.ALIGN_CENTER);
        options[18] = new GridStyle.Options(2,1);
        options[12] = new GridStyle.Options(1,1, new Insets(5,0,5,0));


        //Add components using GridStyle
        GridStyle.addComponents(this, components, 4, 5, 5, 5,
                                ContainerEF.xFraction(this, 0.10),
                                ContainerEF.top(this),
                                ContainerEF.xFraction(this, 0.90),
                                ContainerEF.yFraction(this, 0.75),
                                options);


    }


}



