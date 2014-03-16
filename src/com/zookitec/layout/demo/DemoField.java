/*
 * $Source: e:\\cvshome/explicit3/src/com/zookitec/layout/demo/DemoField.java,v $
 * $Revision: 1.1.1.1 $
 * $Date: 2002/07/31 21:53:42 $
 *
 * Copyright (c) 2001 Zooki Technologies. All rights reserved.
 *
 */
package com.zookitec.layout.demo;

import java.awt.*;


public class DemoField extends Component {

    private static final Font defaultFont = new Font("SansSerif", Font.PLAIN, 12);

    private String label;
    private Dimension preferredSize;

    public DemoField(String label) {
        this(label, 100, 100);
    }

    public DemoField(String label, int width, int height) {
        this.label = label;
        setFont(defaultFont);
        preferredSize = new Dimension(width, height);
    }

    public void paint(Graphics g) {
        Dimension size = getSize();

        //draw border
        g.setColor(Color.white);
        g.fillRect(0, 0, size.width, size.height);


        g.setColor(Color.black);
        FontMetrics fm = getFontMetrics(getFont());
        g.setFont(getFont());
        g.drawString(label, 5, 2 + fm.getAscent());

        g.drawLine(1, 1, size.width - 2, 1);
        g.drawLine(1, 1, 1, size.height - 2);

        g.setColor(Color.gray);
        g.drawLine(0, 0, size.width - 1, 0);
        g.drawLine(0, 0, 0, size.height - 1);
        g.setColor(Color.lightGray);
        g.drawLine(size.width - 2, 1, size.width - 2, size.height - 2);
        g.drawLine(1, size.height - 2, size.width - 2, size.height - 2);
        g.setColor(Color.white);
        g.drawLine(size.width - 1, 0, size.width - 1, size.height - 1);
        g.drawLine(0, size.height - 1, size.width - 1, size.height - 1);

    }

    public Dimension getPreferredSize() {
        return preferredSize;
    }
}


