/*
 * $Source: e:\\cvshome/explicit3/src/com/zookitec/layout/demo/DemoApplet.java,v $
 * $Revision: 1.1.1.1 $
 * $Date: 2002/07/31 21:53:42 $
 *
 * Copyright (c) 2001 Zooki Technologies. All rights reserved.
 *
 */
package com.zookitec.layout.demo;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;

import java.io.*;
import com.zookitec.layout.*;

public class DemoApplet extends Applet {

    private static final int SIZE = 9;

    private int x1,y1,x2,y2;
    private Component demo;

    private int corner = -1;
    private int xOfs;
    private int yOfs;

    public void init() {
        setBackground(new Color(192,192,255));
        setLayout(null);
        String className = getParameter("demoClass");
        if (className != null) {
            try {
                Class demoClass = Class.forName(className);
                demo = (Container)demoClass.newInstance();
            } catch (Exception e) {
                demo = new Label("Failed to create demo : className");
                e.printStackTrace();
            }
        } else {
            demo = new Label("Missing demoClass parameter");
        }
        add(demo);

        enableEvents(AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public void start() {
        x1 = SIZE;
        y1 = SIZE;
        x2 = getSize().width - SIZE;
        y2 = getSize().height - SIZE;
        resizeDemo();
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.black);
        g.drawRect(x1 - SIZE / 2 - 1, y1 - SIZE / 2 - 1, x2 - x1 + SIZE, y2 - y1 + SIZE);

        g.setColor(Color.black);
        g.drawRect(x1 - SIZE, y1 - SIZE, SIZE - 1, SIZE - 1);
        g.drawRect(x1 - SIZE, y2, SIZE - 1, SIZE - 1);
        g.drawRect(x2, y1 - SIZE, SIZE - 1, SIZE - 1);
        g.drawRect(x2, y2, SIZE - 1, SIZE - 1);
        g.setColor(Color.white);
        g.fillRect(x1 - SIZE + 1, y1 - SIZE + 1, SIZE - 2, SIZE - 2);
        g.fillRect(x1 - SIZE + 1, y2+1, SIZE - 2, SIZE - 2);
        g.fillRect(x2+1, y1 - SIZE + 1, SIZE - 2, SIZE - 2);
        g.fillRect(x2+1, y2+1, SIZE - 2, SIZE - 2);

    }


    public void processMouseEvent(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            int mx = e.getX();
            int my = e.getY();
            if (isHit(mx, my, x1, y1)) {
                corner = 1;
            } else if (isHit(mx, my, x2, y1)) {
                corner = 2;
            } else if (isHit(mx, my, x2, y2)) {
                corner = 3;
            } else if (isHit(mx, my, x1, y2)) {
                corner = 4;
            } else {
                corner = 0;
                xOfs = mx - x1;
                yOfs = my - y1;
            }
        }  else {
            corner = -1;
        }
    }

    private boolean isHit(int mx, int my, int x, int y) {
        int range = SIZE + 5;
        return (Math.abs(mx - x) < range && Math.abs(my - y) < range);
    }


    public void processMouseMotionEvent(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_DRAGGED && corner != -1) {
            Dimension size = getSize();
            if (corner == 1) {
                x1 = bound(e.getX(), SIZE, x2 - SIZE);
                y1 = bound(e.getY(), SIZE, y2 - SIZE);
            } else if (corner == 2) {
                x2 = bound(e.getX(), x1 + SIZE, size.width - SIZE);
                y1 = bound(e.getY(), SIZE, y2 - SIZE);
            } else if (corner == 3) {
                x2 = bound(e.getX(), x1 + SIZE, size.width - SIZE);
                y2 = bound(e.getY(), y1 + SIZE, size.height - SIZE);
            } else if (corner == 4) {
                x1 = bound(e.getX(), SIZE, x2 - SIZE);
                y2 = bound(e.getY(), y1 + SIZE, size.height - SIZE);
            } else if (corner == 0) {
                int w = x2 - x1;
                int h = y2 - y1;
                x1 = bound(e.getX() - xOfs, SIZE, size.width - w - SIZE);
                y1 = bound(e.getY() - yOfs, SIZE, size.height - h - SIZE);
                x2 = x1 + w;
                y2 = y1 + h;
            }
            resizeDemo();
        }
    }

    private int bound(int value, int min, int max) {
        return (value < min) ? min : (value > max) ? max : value;
    }


    private void resizeDemo() {
        demo.setBounds(x1, y1, x2 - x1, y2 - y1);
        demo.validate();
        repaint();
    }


}

