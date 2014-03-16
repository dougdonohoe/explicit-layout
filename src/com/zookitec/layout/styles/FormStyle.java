/*
 * $Source: e:\\cvshome/explicit3/src/com/zookitec/layout/styles/FormStyle.java,v $
 * $Revision: 1.3 $
 * $Date: 2003/05/05 23:27:53 $
 *
 * Copyright (c) 2002 Zooki Technologies. All rights reserved.
 *
 * http://www.zookitec.com
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *  
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *  
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *  
 *  Bug fixes, suggestions and comments should be sent to: alex@zookitec.com
 */
 


package com.zookitec.layout.styles;

import java.awt.*;
import com.zookitec.layout.*;

/**
 * Provides convenience methods for laying out labels and fields of a form.
 * See demo application for example use.
 * To Do : Improve documentation for this class.
 */
public class FormStyle {

    private static final int DEFAULT_GAP = 5;


    private FormStyle() {
    }

    public static void addComponents(Container container,
                                     Component [] labels, Component [] fields) {
        addComponents(container, labels, fields,
                      ContainerEF.left(container), ContainerEF.top(container), null,
                      DEFAULT_GAP, DEFAULT_GAP);
    }

    /**
     * Use fields preferred widths
     */
    public static void addComponents(Container container,
                                     Component [] labels, Component [] fields,
                                     Expression left, Expression top,
                                     int xGap, int yGap) {
        addComponents(container, labels, fields, left, top, null, xGap, yGap, false, null);

    }

    public static void addComponents(Container container,
                                     Component [] labels, Component [] fields,
                                     Expression left, Expression top, Expression maxRight,
                                     int xGap, int yGap) {
        addComponents(container, labels, fields, left, top, maxRight, xGap, yGap, false, null);

    }

    /**
     *
     * @param equalizeWidths true if all fields have width set to maximum preferred field width
     */
    public static void addComponents(Container container,
                                     Component [] labels, Component [] fields,
                                     Expression left, Expression top,
                                     int xGap, int yGap,
                                     boolean equalizeWidths) {
        addComponents(container, labels, fields, left, top, null, xGap, yGap, equalizeWidths, null);
    }

    public static void addComponents(Container container,
                                     Component [] labels, Component [] fields,
                                     Expression left, Expression top, Expression maxRight,
                                     int xGap, int yGap,
                                     boolean equalizeWidths) {
        addComponents(container, labels, fields, left, top, maxRight, xGap, yGap, equalizeWidths, null);
    }

    /**
     * @param fillTo expression for each field's right hand x coordinate
     */
    public static void addComponents(Container container,
                                     Component [] labels, Component [] fields,
                                     Expression left, Expression top,
                                     int xGap, int yGap,
                                     Expression fillTo) {
        addComponents(container, labels, fields, left, top, null, xGap, yGap, false, fillTo);
    }

    private static void addComponents(Container container,
                                     Component [] labels, Component [] fields,
                                     Expression left, Expression top, Expression maxRight,
                                     int xGap, int yGap,
                                     boolean equalizeWidths, Expression fillTo) {
        ExplicitLayout layout = (ExplicitLayout)container.getLayout();
        if (labels.length != fields.length) {
            throw new IllegalArgumentException("labels.length != fields.length");
        }
        if (labels.length == 0) {
            return;
        }

        Expression labelWidth = GroupEF.preferredWidthMax(labels);
        Expression fieldX = left.add(labelWidth).add(xGap);

        Expression fieldWidth = (fillTo != null) ? fillTo.subtract(fieldX) :
                                (equalizeWidths) ? GroupEF.preferredWidthMax(fields) : null;
        Expression maxWidth = (maxRight != null) ? maxRight.subtract(fieldX) : null;

        container.add(labels[0], new ExplicitConstraints(labels[0], left, top,
                                                        labelWidth,
                                                        MathEF.max(ComponentEF.preferredHeight(labels[0]), ComponentEF.preferredHeight(fields[0]))));

        for (int i = 1; i < labels.length; i++) {
            container.add(labels[i], new ExplicitConstraints(labels[i],
            left, ComponentEF.bottom(labels[i-1]).add(new GapExpression(yGap, fields[i-1])),
                             labelWidth, MathEF.max(ComponentEF.preferredHeight(labels[i]),
                                                    ComponentEF.height(fields[i])) ));

        }

        for (int i = 0; i < fields.length; i++) {
            Expression thisFieldWidth = (fieldWidth == null) ?
                                ComponentEF.preferredWidth(fields[i]) :  fieldWidth;
            container.add(fields[i], new ExplicitConstraints(fields[i],
                fieldX, ComponentEF.top(labels[i]),
                (maxWidth == null) ? thisFieldWidth :  MathEF.min(maxWidth, thisFieldWidth),
                null));
        }
    }


    private static class GapExpression extends Expression {

        private Component component;
        private int gap;

        /**
         * @param gap size of gap in pixels
         * @param component gap = 0 if component not visible
         */
        public GapExpression(int gap, Component component) {
            this.component = component;
            this.gap = gap;
        }

        protected double computeValue(ExplicitLayout layout) {
            return (component.isVisible()) ? gap : 0;
        }
    }

}
