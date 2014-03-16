/*
 * $Source: e:\\cvshome/explicit3/src/com/zookitec/layout/demo/VisibilityDemo.java,v $
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
 *
 */
public class VisibilityDemo extends JPanel {

    private static final int GAP = 10;
    private static final Insets INSETS = new Insets(GAP, GAP, GAP, GAP);

    private JButton button1 = new JButton("One");
    private JButton button2 = new JButton("Two");
    private JButton button3 = new JButton("Three");
    private JButton button4 = new JButton("Four");
    private JButton button5 = new JButton("Five");
    private JButton button6 = new JButton("Six");

    private JLabel checkLabel = new JLabel("Uncheck to make invisible");
    private JCheckBox check2 = new JCheckBox("Two visible", true);
    private JCheckBox check5 = new JCheckBox("Five visible", true);
    private JCheckBox check13 = new JCheckBox("One and Three visible", true);

    private ExplicitLayout layout = new ExplicitLayout();

    public VisibilityDemo() {
        setLayout(layout);

        Expression maxWidth = GroupEF.preferredWidthMax(
                new Component[]{button1,button2,button3,button4,button5, button6});

        Component [] topRow = new Component[]{button1, button2, button3};

        add(button1, new ExplicitConstraints(button1,
                    ContainerEF.left(this), ContainerEF.top(this),
                    maxWidth, null, 0.0, 0.0, true, true));

        add(button2, new ExplicitConstraints(button2,
                    ComponentEF.right(button1).add(wGap(button1, GAP)), ContainerEF.top(this),
                    maxWidth, null, 0.0, 0.0, true, true));

        add(button3, new ExplicitConstraints(button3,
                    ComponentEF.right(button2).add(wGap(button2, GAP)), ContainerEF.top(this),
                    maxWidth, null, 0.0, 0.0, true, true));

        add(button4, new ExplicitConstraints(button4,
                    ContainerEF.left(this),
                    GroupEF.bottom(topRow).add(ghGap(topRow, GAP)),
                    maxWidth, null, 0.0, 0.0, true, true));

        add(button5, new ExplicitConstraints(button5,
                    ComponentEF.right(button4).add(GAP), ComponentEF.top(button4),
                    maxWidth, null, 0.0, 0.0, false, true));

        add(button6, new ExplicitConstraints(button6,
                    ComponentEF.right(button5).add(GAP), ComponentEF.top(button5),
                    maxWidth, null, 0.0, 0.0, true, true));



        //Add check boxes to control button visibility
        add(check13, new ExplicitConstraints(check13,
                    ContainerEF.left(this), ContainerEF.bottom(this),
                    null, null, 0.0, 1.0, true, true));

        add(check5, new ExplicitConstraints(check5,
                    ContainerEF.left(this), ComponentEF.top(check13),
                    null, null, 0.0, 1.0, true, true));

        add(check2, new ExplicitConstraints(check2,
                    ContainerEF.left(this), ComponentEF.top(check5),
                    null, null, 0.0, 1.0, true, true));

        add(checkLabel, new ExplicitConstraints(checkLabel,
                    ContainerEF.left(this), ComponentEF.top(check2),
                    null, null, 0.0, 1.0, true, true));

        check2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                button2.setVisible(check2.isSelected());
            }
        });

        check5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                button5.setVisible(check5.isSelected());
            }
        });

        check13.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                button1.setVisible(check13.isSelected());
                button3.setVisible(check13.isSelected());
            }
        });

        check2.setBackground(null);
        check5.setBackground(null);
        check13.setBackground(null);

    }

    /**
     * Gets an expression for a gap that is the smaller of
     * the width of component c and the specified width.
     */
    private Expression wGap(Component c, int width) {
        return MathEF.min(ComponentEF.width(c), MathEF.constant(width));
    }

    /**
     * Gets an expression for a gap that is the smaller of
     * the height of component group c and the specified height.
     */
    private Expression ghGap(Component [] c, int height) {
        return MathEF.min(GroupEF.height(c), MathEF.constant(height));
    }


    public Insets getInsets() {
        return INSETS;
    }

}


