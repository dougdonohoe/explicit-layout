/*
 * $Source: e:\\cvshome/explicit3/src/com/zookitec/layout/demo/RoundButton.java,v $
 * $Revision: 1.1.1.1 $
 * $Date: 2002/07/31 21:53:42 $
 *
 * Copyright (c) 2001 Zooki Technologies. All rights reserved.
 *
 */
package com.zookitec.layout.demo;

import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;


public class RoundButton extends Component {

    private boolean pressed = false;

    private String label;
    private Dimension preferredSize;

    private Color pressedColor = new Color(204, 0, 0);
    private Color normalColor = new Color(255, 32, 32);

    private Dimension oldSize = new Dimension(0,0);
    private Ellipse2D ellipse = new Ellipse2D.Float();

    public RoundButton(String label) {
        this.label = label;
        setForeground(Color.black);
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
    }


    public void paint(Graphics og) {
        Graphics2D g = (Graphics2D)og;

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Dimension size = getSize();

        g.setColor((pressed) ? pressedColor :  normalColor);
        g.fillOval(1, 1, size.width - 2, size.height - 2);


        g.setColor(Color.black);
        g.drawOval(0, 0, size.width - 1, size.height - 1);


        if (!pressed) {
            g.setColor(Color.white);
            g.drawArc(1, 1, size.width - 3, size.height - 3, 80, 110);
            g.setColor(pressedColor);
            g.drawArc(1, 1, size.width - 3, size.height - 3, 190, 250);
        }

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);

        g.setColor(getForeground());
        FontMetrics fm = getFontMetrics(getFont());
        g.setFont(getFont());
        int x = size.width / 2 - fm.stringWidth(label) / 2;
        int y = size.height / 2 - fm.getHeight() / 2 + fm.getAscent();
        if (pressed) {
            x++;
            y++;
        }
        g.drawString(label, x, y);

    }


    public void processMouseEvent(MouseEvent e) {
        int id = e.getID();
        if (id == MouseEvent.MOUSE_PRESSED) {
            pressed = true;
            repaint();
        } else if (id == MouseEvent.MOUSE_RELEASED) {
            pressed = false;
            repaint();
        }
    }


    public Dimension getPreferredSize() {
        FontMetrics fm = getFontMetrics(getFont());
        return new Dimension(fm.stringWidth(label) + 20, fm.getHeight() + 16);
    }

    public boolean contains(int x, int y) {
        Dimension size = getSize();
        if (!oldSize.equals(size)) {
            oldSize = size;
            ellipse = new Ellipse2D.Float(0,0,size.width, size.height);
        }
        return ellipse.contains(x, y);
    }

    public String toString() {
        return getClass().getName() + "(" + label + ")";
    }
}

