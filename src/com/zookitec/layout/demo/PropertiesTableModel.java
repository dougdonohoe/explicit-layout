/*
 * $Source: e:\\cvshome/explicit3/src/com/zookitec/layout/demo/PropertiesTableModel.java,v $
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
import java.util.*;

/**
 */
public class PropertiesTableModel extends AbstractTableModel {

    private Properties properties;
    private ArrayList keys;

    public PropertiesTableModel(Properties properties) {
        keys = new ArrayList();
        setProperties(properties);
    }


    public void setProperties(Properties properties) {
        this.properties = properties;
        propertiesChanged();
    }

    public void propertiesChanged() {
        keys.clear();
        keys.addAll(properties.keySet());
        Collections.sort(keys);
        fireTableDataChanged();
    }

    private Properties getProperties() {
        return properties;
    }

    public int getColumnCount() {
        return 2;
    }


    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex < keys.size()) {
            String key = (String)keys.get(rowIndex);
            if (columnIndex == 0) {
                return key;
            } else if (columnIndex == 1) {
                return properties.get(key);
            }
        }
        return "";
    }

    public int getRowCount() {
        return keys.size() + 1;
    }


    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        String oldKey, oldValue;
        oldKey = (String)getValueAt(rowIndex, 0);
        oldValue = (String)getValueAt(rowIndex, 1);
        if (rowIndex < keys.size() + 1) {
            if (columnIndex == 0) {
                properties.remove(oldKey);
                if (((String)aValue).length() > 0) {
                    properties.setProperty((String)aValue, oldValue);
                }
            } else if (columnIndex == 1) {
                properties.setProperty(oldKey, (String)aValue);
            }
            propertiesChanged();
        }
    }


    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

}

