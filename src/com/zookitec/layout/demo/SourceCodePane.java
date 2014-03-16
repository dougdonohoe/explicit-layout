/*
 * $Source: e:\\cvshome/explicit3/src/com/zookitec/layout/demo/SourceCodePane.java,v $
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
import java.net.*;
import java.io.*;


public class SourceCodePane extends JPanel {

    private static final Font TEXT_FONT = new Font("Monospaced", Font.PLAIN, 12);

    private JTextArea textArea;

    public SourceCodePane() {
        setLayout(new BorderLayout());
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(TEXT_FONT);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);
    }


    private static String readString(URL url) {
        StringBuffer buffer = new StringBuffer();
        if (url == null) {
            buffer.append("Source code missing!");
        } else {
            char [] data = new char[1024];
            int count;
            try {
                InputStreamReader isr = new InputStreamReader(url.openStream());
                while ((count = isr.read(data)) != -1) {
                    buffer.append(data, 0, count);
                }
                isr.close();
                isr.close();
            } catch (IOException e) {
                buffer.append("Error reading source code: " + e);
            }
        }
        return buffer.toString();
    }

    public void setURL(URL url) {
        String text = readString(url);
        textArea.setText(text);
        textArea.select(1,1);
        validate();
        repaint();
    }


}

