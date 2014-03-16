/*
 * $Source: e:\\cvshome/explicit3/src/com/zookitec/layout/demo/PropertiesConstraintsEditor.java,v $
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
import javax.swing.table.*;
import java.io.*;

import com.zookitec.layout.*;
import com.zookitec.layout.source.*;

/**
 */
public class PropertiesConstraintsEditor extends JPanel {

    private static final int GAP = 5;
    private static final Insets insets = new Insets(GAP,GAP,GAP,GAP);

    private ExplicitLayout layout = new ExplicitLayout();

    private JTable propertiesTable;
    private JScrollPane scrollPane;
    private PropertiesTableModel tableModel;
    private JButton reloadButton = new JButton("Reload");
    private JButton refreshButton = new JButton("Layout");
    private JLabel variantLabel = new JLabel(" Variant");
    private JTextField variantField = new JTextField(10);



    private PropertiesConstraintsSource pcs;

    private Container container;

    /**
     * @param pcs the properties constraints source whose proprties to edit
     * @param container the container being layed out by pcs
     */
    public PropertiesConstraintsEditor(PropertiesConstraintsSource pcs, Container container) {
        if (!(container.getLayout() instanceof ExplicitLayout)) {
            throw new IllegalArgumentException("Container must use ExplicitLayout");
        }
        this.container = container;
        this.pcs = pcs;
        tableModel = new PropertiesTableModel(pcs.getProperties());
        propertiesTable = new JTable(tableModel);
        propertiesTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);


        scrollPane = new JScrollPane(propertiesTable);

        propertiesTable.getColumnModel().getColumn(0).setHeaderValue("Key");
        propertiesTable.getColumnModel().getColumn(1).setHeaderValue("Value");


        setLayout(layout);

        add(scrollPane, new ExplicitConstraints(scrollPane,
                        ContainerEF.left(this), ContainerEF.top(this),
                        ContainerEF.width(this), ContainerEF.height(this).subtract(GAP).
                        subtract(ComponentEF.preferredHeight(reloadButton)),
                        0.0, 0.0, true, true));

        add(reloadButton, new ExplicitConstraints(reloadButton,
                        ContainerEF.right(this), ContainerEF.bottom(this),
                        null, null, 1.0, 1.0, true, true));

        add(refreshButton, new ExplicitConstraints(refreshButton,
                        ComponentEF.left(reloadButton).subtract(GAP), ContainerEF.bottom(this),
                        null, null, 1.0, 1.0, true, true));

        add(variantLabel, new ExplicitConstraints(variantLabel,
                        ContainerEF.left(this), ContainerEF.bottom(this),
                        null, null, 0.0, 1.0, true, true));

        add(variantField, new ExplicitConstraints(variantField,
                        ComponentEF.right(variantLabel).add(GAP), ContainerEF.bottom(this),
                        MathEF.min(ComponentEF.preferredWidth(variantField),
                                   ComponentEF.left(refreshButton).subtract(ComponentEF.right(variantLabel)).subtract(2*GAP)),
                        null, 0.0, 1.0, true, true));


        reloadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reload();
            }
        });
        reloadButton.setToolTipText("Reload properties file");

        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refresh();
            }
        });
        refreshButton.setToolTipText("Update layout using specified variant properties");

        variantField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refresh();
            }
        });

        variantField.setToolTipText("Specify layout variant e.g. middle or right");

    }

    public void reload() {
        try {
            pcs.reload();
            tableModel.propertiesChanged();
        } catch (IOException e) {
            ErrorFactory.showErrorMessage(this, "Failed To Reload Properties",
                         e.toString(), ErrorFactory.SUPPORT_FOOTER);
        }
        updateLayout();

    }

    public void refresh() {
        CellEditor editor = propertiesTable.getCellEditor();
        if (editor != null) {
            editor.stopCellEditing();
        }
        pcs.refresh();
        updateLayout();
    }

    private void updateLayout() {
        try {
            String variant = variantField.getText();
            if (variant.length() < 1) {
                variant = null;
            }

            pcs.defineConstraints((ExplicitLayout)container.getLayout(), variant);
            container.validate();
            container.repaint();
        } catch (InvalidConstraintsException e) {
            ErrorFactory.showErrorMessage(this, "Invalid Constraints Attribute",
                                    e.getMessage(), "Please edit the value of " + e.getName() + " and try again.");
        }
    }



    public Insets getInsets() {
        return insets;
    }

}

