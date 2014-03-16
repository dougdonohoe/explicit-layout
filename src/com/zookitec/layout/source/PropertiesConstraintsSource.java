/*
 * $Source: e:\\cvshome/explicit3/src/com/zookitec/layout/source/PropertiesConstraintsSource.java,v $
 * $Revision: 1.2 $
 * $Date: 2003/04/27 21:25:15 $
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


import java.io.*;
import java.util.*;
import java.net.*;
import com.zookitec.layout.*;


/**
 * Extends AbstractStringConstraintsSource to get constraints attribute strings from a
 * properties file.
 * <P><I>This class is available in ExplicitLayout 2.0 Professional only.</I></P>
 *
 * <P>See Properties example in the demonstration application for more details</P>
 * <P>Here is an example properties file:</P>
 * <P>
 * gap=5<BR>
 *<BR>
 * field1.x=ContainerEF.left()<BR>
 * field1.y=ContainerEF.top()<BR>
 * field1.width=ContainerEF.widthFraction(0.5)<BR>
 * field1.height=MathEF.subtract(MathEF.subtract(ContainerEF.height(), ComponentEF.preferredHeight(button1)), &lt;gap&gt;)<BR>
 *<BR>
 * field2.x=ContainerEF.right()<BR>
 * field2.y=ContainerEF.top()<BR>
 * field2.width=ContainerEF.widthFraction(0.5)<BR>
 * field2.height=ComponentEF.height(field1)<BR>
 * field2.originX=1.0<BR>
 *<BR>
 * button1.x=ContainerEF.left()<BR>
 * button1.y=MathEF.add(ComponentEF.bottom(field1), &lt;gap&gt;)<BR>
 *<BR>
 * button2.x=MathEF.add(ComponentEF.right(button1), &lt;gap&gt;)<BR>
 * button2.y=ComponentEF.top(button1)<BR>
 *<BR>
 * button3.x=MathEF.add(ComponentEF.right(button2), &lt;gap&gt;)<BR>
 * button3.y=ComponentEF.top(button1)<BR>
 *<BR>
 * button4.x=MathEF.add(ComponentEF.right(button3), &lt;gap&gt;)<BR>
 * button4.y=ComponentEF.top(button1)<BR>
 *
 * </P>
 *
 */
public class PropertiesConstraintsSource extends AbstractStringConstraintsSource {

    private URL url;
    private Properties properties;

    /**
     * Constructs a PropertiesConstraintsSource from the specified file.
     *
     * @param file a properties file
     */
    public PropertiesConstraintsSource(File file) throws IOException {
        this(file.toURL());
    }

    /**
     * Constructs a PropertiesConstraintsSource from the specified url.
     *
     * @param url the url a properties file
     */
    public PropertiesConstraintsSource(URL url) throws IOException  {
        this.url = url;
        properties = new Properties();
        loadProperties();
    }

    /**
     * Constructs a PropertiesConstraintsSource from the specified Properties object.
     *
     * @param properties a properties object
     */
    public PropertiesConstraintsSource(Properties properties) {
        this.properties = properties;
    }

    /**
     * Gets the value of the attribute with the specified name. The property
     * value may contain variables in the form &lt;<I>variable_name</I>&gt;.
     * In this case the variable is replaced by the value of the property with
     * the specified name. If such a property does not exist, the variable is
     * removed. All variables are recursively replaced or removed from the property value
     * to get the final attribute string.
     *
     * @return the value of the attribute with the specified name; null if unspecified.
     */
    public String getAttributeString(String name) throws InvalidConstraintsException {
        String value = properties.getProperty(name);
        if (value != null) {
            //replace varaiables
            StringBuffer buffer = new StringBuffer(value.length());
            StringBuffer key = new StringBuffer(value.length());
            int length = value.length();
            char c;
            boolean invar = false;
            for (int i = 0; i < length; i++) {
                c = value.charAt(i);
                if (invar) {
                    if (c == '>') {
                        String v = getAttributeString(key.toString());
                        if (v != null) {
                            buffer.append(v);
                        }
                        key.delete(0, key.length());
                        invar = false;
                    } else {
                        key.append(c);
                    }
                } else {
                    if (c == '<') {
                       invar = true;
                    } else {
                       buffer.append(c);
                    }
                }
            }
            return buffer.toString();
        } else {
            return null;
        }
    }


    /**
     * Determines if the properties file contains an attibute with the specified name.
     */
    public boolean isAttributeDefined(String name) {
        return properties.containsKey(name);

    }



    /**
     * Gets the properties object that is providing the attributes for this constraints source.
     */
    public Properties getProperties() {
        return properties;
    }


    private void loadProperties() throws IOException  {
        properties.clear();
        InputStream in = url.openStream();
        properties.load(in);
        in.close();
    }




    /**
     * Clears all cached expressions and if a file or url is specified, reloads the properties.
     */
    public void reload() throws IOException {
        refresh();
        if (url != null) {
            loadProperties();
        }
    }

}
