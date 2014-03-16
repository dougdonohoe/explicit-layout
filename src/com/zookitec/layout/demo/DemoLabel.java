/*
 * $Source: e:\\cvshome/explicit3/src/com/zookitec/layout/demo/DemoLabel.java,v $
 * $Revision: 1.1.1.1 $
 * $Date: 2002/07/31 21:53:42 $
 *
 * Copyright (c) 2001 Zooki Technologies. All rights reserved.
 *
 */
package com.zookitec.layout.demo;

import java.awt.*;


public class DemoLabel extends Component {

    private static final Font defaultFont = new Font("SansSerif", Font.BOLD, 12);

    private String label;
    private Dimension preferredSize;

    public DemoLabel(String label) {
        this.label = label;
        setFont(defaultFont);
    }


    public DemoLabel(String label, int width, int height) {
        this.label = label;
        setFont(defaultFont);
        preferredSize = new Dimension(width, height);
    }

    public void paint(Graphics g) {
        Dimension size = getSize();

        g.setColor(Color.lightGray);
        g.fillRect(0, 0, size.width, size.height);

        g.setColor(Color.black);
        FontMetrics fm = getFontMetrics(getFont());
        g.setFont(getFont());
        int x = size.width / 2 - fm.stringWidth(label) / 2;
        int y = size.height / 2 - fm.getHeight() / 2 + fm.getAscent();
        g.drawString(label, x, y);

        g.drawRect(0, 0, size.width - 1, size.height - 1);
        g.setColor(Color.white);
        g.drawLine(1, 1, size.width - 2, 1);
        g.drawLine(1, 1, 1, size.height - 2);
        g.setColor(Color.gray);
        g.drawLine(size.width - 2, 1, size.width - 2, size.height - 2);
        g.drawLine(1, size.height - 2, size.width - 2, size.height - 2);
    }

   /* public void setBounds(int x, int y, int w, int h) {
        super.setBounds(x,y,w,h);
        System.out.println("label " + label + " : " + x + ", " + y + ", " + w + ", " + h);
    }*/

    public Dimension getPreferredSize() {
        if (preferredSize == null) {
            FontMetrics fm = getFontMetrics(getFont());
            return new Dimension(fm.stringWidth(label) + 8, fm.getHeight() + 4);
        } else {
            return preferredSize;
        }
    }

    public String toString() {
        return getClass().getName() + "(" + label + ")";
    }
}




