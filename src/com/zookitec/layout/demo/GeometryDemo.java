/*
 * $Source: e:\\cvshome/explicit3/src/com/zookitec/layout/demo/GeometryDemo.java,v $
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
import com.zookitec.layout.source.*;

import java.io.*;

/**
 * Demonstrates the use of the GeomEF expression factory for laying out components
 * on a line or an ellipse
 */
public class GeometryDemo extends JPanel  {

    private ExplicitLayout layout = new ExplicitLayout();

    private RoundButton button1 = new RoundButton("Home");
    private RoundButton button2 = new RoundButton("Products");
    private RoundButton button3 = new RoundButton("Support");
    private RoundButton button4 = new RoundButton("About Us");

    private JButton button5 = new JButton("one");
    private JButton button6 = new JButton("two");
    private JButton button7 = new JButton("three");
    private JButton button8 = new JButton("four");

    //The following layout expressions are declared here for use in
    //the paint method.
    private Expression centerX;
    private Expression centerY;
    private Expression radiusX;
    private Expression radiusY;
    private Expression x1;
    private Expression y1;
    private Expression x2;
    private Expression y2;

    public GeometryDemo() {

        setLayout(layout);

        Component [] roundButtons = {button1, button2, button3, button4};
        centerX = ContainerEF.centerX(this);
        centerY = ContainerEF.centerY(this);
        Expression buttonWidth = GroupEF.preferredWidthMax(roundButtons);
        Expression buttonHeight = GroupEF.preferredHeightMax(roundButtons);
        radiusX = ContainerEF.widthFraction(this, 0.5).subtract(buttonWidth.divide(2)).subtract(10);
        radiusY = ContainerEF.heightFraction(this, 0.5).subtract(buttonHeight.divide(2)).subtract(10);

        add(button1, new ExplicitConstraints(button1,
                    GeomEF.ellipseX(centerX, radiusX, 90),
                    GeomEF.ellipseY(centerY, radiusY, 90),
                    buttonWidth, buttonHeight, 0.5, 0.5, true, true));

        add(button2, new ExplicitConstraints(button2,
                    GeomEF.ellipseX(centerX, radiusX, 60),
                    GeomEF.ellipseY(centerY, radiusY, 60),
                    buttonWidth, buttonHeight, 0.5, 0.5, true, true));

        add(button3, new ExplicitConstraints(button3,
                    GeomEF.ellipseX(centerX, radiusX, 30),
                    GeomEF.ellipseY(centerY, radiusY, 30),
                    buttonWidth, buttonHeight, 0.5, 0.5, true, true));

        add(button4, new ExplicitConstraints(button4,
                    GeomEF.ellipseX(centerX, radiusX, 0),
                    GeomEF.ellipseY(centerY, radiusY, 0),
                    buttonWidth, buttonHeight, 0.5, 0.5, true, true));


        //Layout the rectangular buttons on a line from top left to bottom center.

        Component [] buttons = {button5, button6, button7, button8};
        Expression maxWidth = GroupEF.preferredWidthMax(buttons);
        x1 = ContainerEF.left(this);
        y1 = ContainerEF.top(this).add(ComponentEF.preferredHeight(button5));
        x2 = ContainerEF.centerX(this);
        y2 = ContainerEF.bottom(this);

        add(button5, new ExplicitConstraints(button5,
                        GeomEF.lineX(x1, x2, 0.05),
                        GeomEF.lineY(y1, y2, 0.05),
                        maxWidth, null, 0.0, 1.0, true, true));

        add(button6, new ExplicitConstraints(button6,
                        GeomEF.lineX(x1, x2, 0.35),
                        GeomEF.lineY(y1, y2, 0.35),
                        maxWidth, null, 0.0, 1.0, true, true));


        add(button7, new ExplicitConstraints(button7,
                        GeomEF.lineX(x1, x2, 0.65),
                        GeomEF.lineY(y1, y2, 0.65),
                        maxWidth, null, 0.0, 1.0, true, true));


        add(button8, new ExplicitConstraints(button8,
                        GeomEF.lineX(x1, x2, 0.95),
                        GeomEF.lineY(y1, y2, 0.95),
                        maxWidth, null, 0.0, 1.0, true, true));

    }


    /**
     * Paints the line and ellipse arc on which the layout is based using
     * the same layout expressions used by ExplicitLayout.
     */
    public void paintComponent(Graphics og) {
        Graphics2D g = (Graphics2D)og;
        Dimension size = getSize();
        Stroke oldStroke = g.getStroke();
        
        g.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0.0F, new float[]{5,5}, 0.0F));

        g.setColor(Color.black);

        //Note the use of layout expressions x1,y1, x2, y2
        g.drawLine((int)x1.getValue(layout), (int)y1.getValue(layout),
                   (int)x2.getValue(layout), (int)y2.getValue(layout));


        //Note the use of layout expressions radiusX and radiusY
        int rx = (int)radiusX.getValue(layout);
        int ry = (int)radiusY.getValue(layout);

        g.drawArc((int)centerX.getValue(layout) -  rx, (int)centerY.getValue(layout) -  ry,
                  2 * rx, 2 * ry, 330, 150);

        g.setStroke(oldStroke);
        
    }

}

