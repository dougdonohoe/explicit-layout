/*
 * $Source: e:\\cvshome/explicit3/src/com/zookitec/layout/source/ConstraintsSource.java,v $
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

import com.zookitec.layout.*;

/**
 * This interface is implemented by classes that define ExplicitConstraints.
 *
 * <P>A ConstraintsSource may specify constraints for several different layouts for the same container;
 * each layout variant is identified by a string. For example,
 * you may want to specify different layouts based on the locale, user preferences,
 * container width / height ratio or other application state.</P>
 *
 * <P>A ConstraintsSource may specify constraints for different containers. If you do this,
 * you should ensure that component names are unique within a particular ConstraintsSource.
 * This could be done by prefixing each name with the name of the class than creates
 * the constraints.</P>
 *
 * <P>For example, the component 'someLabel' in class 'SomeContainer' could be given the
 * name 'SomeContainer.someLabel'</P>
 *
 * <P>
 * It is a good idea to use different ConstraintsSource objects for layouts in different
 * packages; this is a modular approach and avoids having to use verbose component names such as
 * 'com.mycompany.somepackage.MyContainer.someLabel' to ensure uniqueness.
 *
 * </P>
 *
 */
public interface ConstraintsSource {

    /**
     * Defines the constraints for the specified layout using this constraints source.
     *
     *
     * @param layout the ExplicitLayout whose constraints are to be defined
     * @param variant identifies the layout variant
     *
     */
    public void defineConstraints(ExplicitLayout layout, String variant)
                                              throws InvalidConstraintsException;


}
