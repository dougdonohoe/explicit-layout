/*
 * $Source: e:\\cvshome/explicit3/src/com/zookitec/layout/demo/ErrorFactory.java,v $
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

/**
 */
public class ErrorFactory {

    public static final String SUPPORT_FOOTER = "If you need help, please e-mail details of the problem to support@zookitec.com";

    public static void showErrorMessage(Component parent, String title, String message, String footer) {
        JTextArea text = new JTextArea(message);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setEditable(false);

        text.append("\n\n");
        text.append(footer);

        JOptionPane optionPane = new JOptionPane(new JScrollPane(text), JOptionPane.ERROR_MESSAGE,
                                            JOptionPane.DEFAULT_OPTION);

        JDialog dialog = optionPane.createDialog(parent, title);
        dialog.setSize(400,260);
        dialog.show();
    }


}

