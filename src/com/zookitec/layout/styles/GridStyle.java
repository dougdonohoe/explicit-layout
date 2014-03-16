/*
 * $Source: e:\\cvshome/explicit3/src/com/zookitec/layout/styles/GridStyle.java,v $
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

import com.zookitec.layout.*;
import java.awt.*;

/**
 * Provides convenience methods for laying out components on a grid.
 * See demo application for example use.
 * To Do : Improve documentation for this class.
 */
public class GridStyle {

    private static final Dimension UNIT_SIZE = new Dimension(1,1);

    public static final int ALIGN_TOP = 0;
    public static final int ALIGN_LEFT = 0;
    public static final int ALIGN_CENTER = 1;
    public static final int ALIGN_RIGHT = 2;
    public static final int ALIGN_BOTTOM = 2;

    private static final Insets ZERO_INSETS = new Insets(0,0,0,0);
    private static final Options DEFAULT_OPTIONS = new Options();

    private GridStyle() {
    }

    public static void addComponents(Container container,
                                     Component [] components,
                                     int rows, int columns) {
        addComponents(container, components, rows, columns, 0, 0);
    }

    public static void addComponents(Container container,
                                     Component [] components,
                                     int rows, int columns,
                                     int xGap, int yGap) {
        addComponents(container, components, rows, columns, xGap, yGap,
                      ContainerEF.left(container), ContainerEF.top(container),
                      ContainerEF.right(container), ContainerEF.bottom(container),
                      null);
    }

    public static void addComponents(Container container,
                                     Component [] components,
                                     int rows, int columns,
                                     int xGap, int yGap,
                                     Expression left, Expression top,
                                     Expression right, Expression bottom) {
        addComponents(container, components, rows, columns, xGap, yGap,
                      left, top, right, bottom, null);
    }




    public static void addComponents(Container container,
                                     Component [] components,
                                     int rows, int columns,
                                     int xGap, int yGap,
                                     Expression left, Expression top,
                                     Expression right, Expression bottom,
                                     Options [] options) {
        if (components.length != rows * columns) {
            throw new IllegalArgumentException("components.length != rows * columns");
        }
        Expression cellWidth = right.subtract(left).add(xGap).divide(columns);
        Expression cellHeight = bottom.subtract(top).add(yGap).divide(rows);
        cellWidth.setRoundingMode(Expression.ROUND_FLOOR);
        cellHeight.setRoundingMode(Expression.ROUND_FLOOR);
        int i = 0;
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                if (components[i] != null) {
                    Expression ex,ey,width,height;
                    double originX = 0.0, originY = 0.0;
                    Options option = (options == null || options[i] == null) ? DEFAULT_OPTIONS : options[i];
                    Insets insets = (option.insets == null) ? ZERO_INSETS : option.insets;
                    ex = left.add(ContainerEF.gridX(container, x, cellWidth)).add(insets.left);
                    ey = top.add(ContainerEF.gridY(container, y, cellHeight)).add(insets.top);
                    width = cellWidth.multiply(option.cellsWide).subtract(xGap + insets.left + insets.right);
                    height = cellHeight.multiply(option.cellsHigh).subtract(yGap + insets.top + insets.bottom);

                    if (!option.fillWidth) {
                        originX = 0.0;
                        if (option.xAlign == ALIGN_CENTER) {
                            originX = 0.5;
                            ex = ex.add(width.multiply(0.5));
                        } else if (option.xAlign == ALIGN_RIGHT) {
                            originX = 1.0;
                            ex = ex.add(width);
                        }
                        width = MathEF.min(ComponentEF.preferredWidth(components[i]), width);
                    }
                    if (!option.fillHeight) {
                        originY = 0.0;
                        if (option.yAlign == ALIGN_CENTER) {
                            originY = 0.5;
                            ey = ey.add(height.multiply(0.5));
                        } else if (option.yAlign == ALIGN_BOTTOM) {
                            originY = 1.0;
                            ey = ey.add(height);
                        }
                        height = MathEF.min(ComponentEF.preferredHeight(components[i]), height);
                    }
                    container.add(components[i], new ExplicitConstraints(components[i],
                                  ex, ey, width, height, originX, originY, true, true));
                }
                i++;
            }
        }

    }



    /**
     * Used to specify options for a component on the grid.
     */
    public static class Options {
        public int cellsWide = 1;
        public int cellsHigh = 1;
        public Insets insets;
        public boolean fillWidth = true;
        public boolean fillHeight = true;
        public int xAlign = ALIGN_LEFT;
        public int yAlign = ALIGN_TOP;

        public Options() {
        }

        public Options(int cellsWide, int cellsHigh) {
            this.cellsWide = cellsWide;
            this.cellsHigh = cellsHigh;
        }

        public Options(int cellsWide, int cellsHigh, Insets insets) {
            this.cellsWide = cellsWide;
            this.cellsHigh = cellsHigh;
            this.insets = insets;
        }

        public Options(int cellsWide, int cellsHigh, Insets insets,
                       boolean fillWidth, boolean fillHeight,
                       int xAlign, int yAlign) {
            this.cellsWide = cellsWide;
            this.cellsHigh = cellsHigh;
            this.insets = insets;
            this.fillWidth = fillWidth;
            this.fillHeight = fillHeight;
            this.xAlign = xAlign;
            this.yAlign = yAlign;
        }
    }
}
