/*
 * $Source: e:\\cvshome/explicit3/src/com/zookitec/layout/source/InvalidConstraintsException.java,v $
 * $Revision: 1.3 $
 * $Date: 2003/04/27 21:32:04 $
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

package com.zookitec.layout.source;

/**
 * An exception typically thrown by a ConstraintsSource when a constraints
 * attribute definition is invalid. <BR>
 * 
 */
public class InvalidConstraintsException extends RuntimeException {

    private String name;
    private String value;
    private String error;

    /**
     * Constructs an InvalidConstraintsException.
     *
     * @param name the name of the attribute containing the error.
     * @param value the value of the attribute containing the error.
     * @param error the error message.
     */
    public InvalidConstraintsException(String name, String value, String error) {
        super(createMessage(name, value, error));
        this.name = name;
        this.value = value;
        this.error = error;
    }

    private static String createMessage(String name, String value, String error) {
        StringBuffer sb = new StringBuffer();
        sb.append("Invalid constraints attibute ").append(name).append("=").
        append(value).append(" : ").append(error);
        return sb.toString();
    }

    /**
     * Gets the name of the attribute containing the error.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the value of the attribute containing the error.
     */
    public String getValue() {
        return value;
    }

    /**
     * Gets the error message.
     */
    public String getError() {
        return error;
    }



}
