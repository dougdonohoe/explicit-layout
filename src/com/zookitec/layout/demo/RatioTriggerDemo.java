/*
 * $Source: e:\\cvshome/explicit3/src/com/zookitec/layout/demo/RatioTriggerDemo.java,v $
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

/**
 * Demonstrates the ability to switch between sets of layout constraints at
 * runtime. In this case, the switch is triggered by the container width/height ratio.
 */
public class RatioTriggerDemo extends JPanel implements LayoutListener {

    private static final Insets INSETS = new Insets(5,5,5,5);

    private static final int GAP = 5;

    private static final int WIDE = 1;
    private static final int NORMAL = 2;

    private JScrollPane scroller;
    private JTextArea textArea = new JTextArea();
    private JButton agreeButton = new JButton("Agree");
    private JButton disagreeButton = new JButton("Disagree");

    private ExplicitConstraints textEC;
    private ExplicitConstraints agreeEC;
    private ExplicitConstraints disagreeEC;
    private ExplicitConstraints textECwide;
    private ExplicitConstraints agreeECwide;
    private ExplicitConstraints disagreeECwide;

    private int ratioState = -1;

    public RatioTriggerDemo() {
        ExplicitLayout layout = new ExplicitLayout();
        setLayout(layout);

        scroller = new JScrollPane(textArea);

        initRandomText();

        initNormalConstraints();
        initWideConstraints();

        //These constraints are just placeholders
        add(scroller, new ExplicitConstraints(scroller));
        add(agreeButton, new ExplicitConstraints(agreeButton));
        add(disagreeButton, new ExplicitConstraints(disagreeButton));

        layout.setLayoutListener(this);

    }

    /**
     * Defines constraints for a 'normal' size layout.
     */
    private void initNormalConstraints() {
        Component [] buttons = new Component[]{agreeButton, disagreeButton};

        textEC = new ExplicitConstraints(scroller);
        agreeEC = new ExplicitConstraints(agreeButton);
        disagreeEC = new ExplicitConstraints(disagreeButton);

        textEC.setWidth(ContainerEF.width(this));
        textEC.setHeight(ContainerEF.height(this).subtract(GroupEF.heightMax(buttons)).subtract(GAP));

        disagreeEC.setX(ContainerEF.right(this));
        disagreeEC.setOriginX(ExplicitConstraints.RIGHT);
        disagreeEC.setY(ComponentEF.bottom(scroller).add(GAP));


        agreeEC.setX(ComponentEF.left(disagreeButton).subtract(GAP));
        agreeEC.setOriginX(ExplicitConstraints.RIGHT);
        agreeEC.setY(disagreeEC.getY());
    }

    /**
     * Defines constraints for a 'wide' layout.
     */
    private void initWideConstraints() {
        Component [] buttons = new Component[]{agreeButton, disagreeButton};

        textECwide = new ExplicitConstraints(scroller);
        agreeECwide = new ExplicitConstraints(agreeButton);
        disagreeECwide = new ExplicitConstraints(disagreeButton);

        textECwide.setWidth(ContainerEF.width(this).subtract(GroupEF.widthMax(buttons)).subtract(GAP));
        textECwide.setHeight(ContainerEF.height(this));

        agreeECwide.setX(ComponentEF.right(scroller).add(GAP));
        agreeECwide.setWidth(GroupEF.preferredWidthMax(buttons));

        disagreeECwide.setX(agreeECwide.getX());
        disagreeECwide.setY(ComponentEF.bottom(agreeButton).add(GAP));
        disagreeECwide.setWidth(agreeECwide.getWidth());
    }


    /**
     * Implements LayoutListener. Switches between 'normal' and 'wide'
     * layout constraints depending on container width / height ratio.
     */
    public void beforeLayout(ExplicitLayout layout) {
        Dimension size = getSize();
        if ((double)size.width / size.height > 2) {
            //set constraints for wide container
            if (ratioState != WIDE) {
                ratioState = WIDE;
                layout.getConstraints(scroller).copy(textECwide);
                layout.getConstraints(agreeButton).copy(agreeECwide);
                layout.getConstraints(disagreeButton).copy(disagreeECwide);
            }
        } else {
            if (ratioState != NORMAL) {
                ratioState = NORMAL;
                //set constraints for normal or tall container
                layout.getConstraints(scroller).copy(textEC);
                layout.getConstraints(agreeButton).copy(agreeEC);
                layout.getConstraints(disagreeButton).copy(disagreeEC);
            }
        }
    }


    public Insets getInsets() {
        return INSETS;
    }


    private void initRandomText() {
       String newline = System.getProperty("line.separator");
        for (int i = 0; i < 20; i++) {
            int count = 5 + (int)(Math.random() * 10);
            textArea.append(String.valueOf((char)('A' + (int)(Math.random() * 26))));
            for (int j = 0; j < count; j++) {
                int len = 2 + (int)(Math.random() * 6);
                for (int k = 0; k < len; k++) {
                    textArea.append(String.valueOf((char)('a' + (int)(Math.random() * 26))));
                }
                textArea.append(" ");
            }
            textArea.append(newline);
        }
    }

}
