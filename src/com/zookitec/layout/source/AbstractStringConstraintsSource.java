/*
 * $Source: e:\\cvshome/explicit3/src/com/zookitec/layout/source/AbstractStringConstraintsSource.java,v $
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

import java.awt.*;
import java.util.*;
import java.lang.reflect.*;
import com.zookitec.layout.*;
import com.zookitec.layout.source.parser.*;

/**
 * An abstract constraints source whose constraints attributes are defined by Java
 * Language strings.

 * <P>Actually, only a small subset of Java Language is supported,
 * sufficient to specify constraints attribute values. There are also some subtle
 * differences:</P>
 * <UL>
 *  <LI>Components are identified using the name specified in the
 *  corresponding ExplicitConstraints constructor rather than the Java identifier</LI>
 *  <LI>Arrays of components or expressions are specified using the syntax
 *  {a, b, c} rather than new Component[]{a,b,c} or new Expression[]{a,b,c}</LI>
 *  </UL>
 */
public abstract class AbstractStringConstraintsSource implements ConstraintsSource {

    /**
     * x attribute name ".x"
     */
    public static final String X = ".x";

    /**
     * y attribute name ".y"
     */
    public static final String Y = ".y";

    /**
     * width attribute name ".width"
     */
    public static final String WIDTH = ".width";

    /**
     * height attribute name ".height"
     */
    public static final String HEIGHT = ".height";

    /**
     * right attribute name ".right"
     */
    public static final String RIGHT = ".right";

    /**
     * bottom attribute name ".bottom"
     */
    public static final String BOTTOM = ".bottom";

    /**
     * originX attribute name ".originY"
     */
    public static final String ORIGIN_X = ".originX";

    /**
     * originY attribute name ".originY"
     */
    public static final String ORIGIN_Y = ".originY";

    /**
     * widthZeroIfInvisible attribute name ".widthZero"
     */
    public static final String WIDTH_ZERO = ".widthZero";

    /**
     * heightZeroIfInvisible attribute name ".heightZero"
     */
    public static final String HEIGHT_ZERO = ".heightZero";


    private Hashtable cache;

    public AbstractStringConstraintsSource() {
        cache = new Hashtable();
    }

    /**
     * Parses, builds and sets the specified variant constraints of named components
     * within the specified layout. If the specified variant constraints for a named
     * component are not defined by this source, the component's constraints are not
     * changed. If the specified variant constraints for a named component are
     * defined by this source, the component's constraints are first set to
     * defaults (as defined by ExplicitConstraints constructor) then the defined
     * attributes are set. This ensures that the constraints are in a consistent (default)
     * state before the attributes for a particular variant are applied.
     *
     * <P>As a consequence of the above behaviour, it is possible to combine different
     * variants into a single layout; each variant would only specify constraints for
     * a subset of the components in the layout.</P>
     *
     * <P>ExplicitConstraints objects are cached after they are first created so
     * that it is not necessary to re-parse the attribute strings each time
     * this method is called for a particular layout and variant. If any attribute strings
     * change, subclasses should call the refresh() method so that they are re-parsed
     * next time defineConstraints is called.</P>
     */
    public void defineConstraints(ExplicitLayout layout, String variant)
                                throws InvalidConstraintsException {
        Enumeration enum = layout.getNamedConstraints();
        ExplicitConstraints constraints, cacheConstraints;
        String name, aname, value;
        while (enum.hasMoreElements()) {
            constraints = (ExplicitConstraints)enum.nextElement();
            name = (variant == null)
                   ? constraints.getName()
                   : constraints.getName() + "." + variant;

            if ((cacheConstraints = getConstraintsFromCache(layout, name)) == null) {
                if (isConstraintsDefined(name)) {
                    constraints.restoreDefaults();

                    if ((value = getAttributeString(aname = name + X)) != null) {
                        constraints.setX(buildExpression(layout, aname, value));
                    }
                    if ((value = getAttributeString(aname = name + Y)) != null) {
                        constraints.setY(buildExpression(layout, aname, value));
                    }
                    if ((value = getAttributeString(aname = name + WIDTH)) != null) {
                        constraints.setWidth(buildExpression(layout, aname, value));
                    } else if ((value = getAttributeString(aname = name + RIGHT)) != null) {
                        constraints.setRight(buildExpression(layout, aname, value));
                    }

                    if ((value = getAttributeString(aname = name + HEIGHT)) != null) {
                        constraints.setHeight(buildExpression(layout, aname, value));
                    } else if ((value = getAttributeString(aname = name + BOTTOM)) != null) {
                        constraints.setBottom(buildExpression(layout, aname, value));
                    }

                    if ((value = getAttributeString(aname = name + ORIGIN_X)) != null) {
                        constraints.setOriginX(parseOrigin(aname, value));
                    }

                    if ((value = getAttributeString(aname = name + ORIGIN_Y)) != null) {
                        constraints.setOriginY(parseOrigin(aname, value));
                    }
                    if ((value = getAttributeString(aname = name + WIDTH_ZERO)) != null) {
                        constraints.setWidthZeroIfInvisible(parseBoolean(aname, value));
                    }
                    if ((value = getAttributeString(aname = name + HEIGHT_ZERO)) != null) {
                        constraints.setHeightZeroIfInvisible(parseBoolean(aname, value));
                    }
                    putConstraintsInCache(layout, name, (ExplicitConstraints)constraints.clone());
                }
            } else {
                constraints.copy(cacheConstraints);
            }
        }
        if (layout.getContainer() != null) {
            layout.getContainer().invalidate();
        }
    }



    /**
     * Clear all cached expressions.
     */
    public void refresh() {
        cache.clear();
    }


    private ExplicitConstraints getConstraintsFromCache(ExplicitLayout layout, String name) {
        Hashtable table = (Hashtable)cache.get(layout);
        return (table == null) ? null : (ExplicitConstraints)table.get(name);
    }

    private void putConstraintsInCache(ExplicitLayout layout, String name,
                                            ExplicitConstraints constraints) {
        Hashtable table = (Hashtable)cache.get(layout);
        if (table == null) {
            table = new Hashtable();
            cache.put(layout, table);
        }
        table.put(name, constraints);
    }


    private boolean parseBoolean(String name, String valueString) throws InvalidConstraintsException {
        if (valueString.equalsIgnoreCase("true")) {
            return true;
        } else if (valueString.equalsIgnoreCase("false")) {
            return false;
        } else {
            throw new InvalidConstraintsException(name, valueString, "must be 'true' or 'false'");
        }
    }


    private double parseOrigin(String name, String valueString) throws InvalidConstraintsException {
        double value;
        try {
            value = Float.parseFloat(valueString);
            if (value < 0.0 || value > 1.0) {
                throw new InvalidConstraintsException(name, valueString, "out of range 0 .. 1");
            }
        } catch (NumberFormatException e) {
            try {
                //Check for constant value
                Field field = ExplicitConstraints.class.getField(valueString);
                value = field.getDouble(null);
            } catch (Exception e2) {
                throw new InvalidConstraintsException(name, valueString, "unable to parse value : " + e2.getMessage());
            }
        }
        return value;
    }


    private Class getClass(String qualifiedMethod) throws ClassNotFoundException {
        int lastDot = qualifiedMethod.lastIndexOf('.');
        Class efClass;
        if (lastDot == -1) {
            throw new ClassNotFoundException("");
        }
        String className = qualifiedMethod.substring(0, lastDot);
        try {
            //try qualifying it with com.zookitec.layout package
            efClass = Class.forName("com.zookitec.layout." + className);
        } catch (ClassNotFoundException e) {
            efClass = Class.forName(className);
        }


        return efClass;
    }


    private Expression buildExpression(ExplicitLayout layout, String name, String value) throws InvalidConstraintsException {
        SimpleNode root;
        try {
            root = ExpressionParser.parse(value);
            return buildExpression(layout, name, value, (SimpleNode)root.jjtGetChild(0));
        } catch (ParseException e) {
            throw new InvalidConstraintsException(name, value, e.getMessage());
        } catch (TokenMgrError e) {
           throw new InvalidConstraintsException(name, value, e.getMessage());
        }
    }


    private Expression buildExpression(ExplicitLayout layout, String name, String valueString, SimpleNode root) throws InvalidConstraintsException {
        int count = root.jjtGetNumChildren();
        Expression expr;
        SimpleNode exprMethodNode;
        SimpleNode paramNode;
        String methodName;
        //parser should ensure at lease one method call
        expr = buildMethodCall(layout, name, valueString, (SimpleNode)root.jjtGetChild(0));
        for (int i = 1; i < count; i++) {
            exprMethodNode = (SimpleNode)root.jjtGetChild(i);
            if (exprMethodNode.jjtGetNumChildren() == 2) { //method name, parameter
                methodName = ((SimpleNode)exprMethodNode.jjtGetChild(0)).getValue();
                paramNode = (SimpleNode)exprMethodNode.jjtGetChild(1).jjtGetChild(0);
                switch (paramNode.getID()) {
                    case ExpressionParser.JJTNUMBER :
                        double value = Double.parseDouble(paramNode.getValue());
                        if ("add".equals(methodName)) {
                            expr = expr.add(value);
                        } else if ("subtract".equals(methodName)) {
                            expr = expr.subtract(value);
                        } else if ("multiply".equals(methodName)) {
                            expr = expr.multiply(value);
                        } else if ("divide".equals(methodName)) {
                            expr = expr.divide(value);
                        } else {
                            throw new InvalidConstraintsException(name, valueString, "Unexpected Expression method " + methodName + "(double)");
                        }
                    break;
                    case ExpressionParser.JJTEXPRESSION :
                        Expression paramExpr = buildExpression(layout, name, valueString, paramNode);
                        if ("add".equals(methodName)) {
                            expr = expr.add(paramExpr);
                        } else if ("subtract".equals(methodName)) {
                            expr = expr.subtract(paramExpr);
                        } else if ("multiply".equals(methodName)) {
                            expr = expr.multiply(paramExpr);
                        } else if ("divide".equals(methodName)) {
                            expr = expr.divide(paramExpr);
                        } else {
                            throw new InvalidConstraintsException(name, valueString, "Unexpected Expression method " + methodName + "(Expression)");
                        }

                    break;
                    default :
                        throw new InvalidConstraintsException(name, valueString, "Unexpected parameter type " + paramNode);
                }
            } else {
                if (exprMethodNode.jjtGetNumChildren() > 0) {
                    methodName = ((SimpleNode)exprMethodNode.jjtGetChild(0)).getValue();
                } else {
                    methodName = "";
                }
                throw new InvalidConstraintsException(name, valueString, "Unexpected Expression method, " + methodName);
            }
        }
        return expr;


    }





    private Expression buildMethodCall(ExplicitLayout layout, String name, String valueString, SimpleNode root) throws InvalidConstraintsException {
        Class efClass;
        String methodName;
        SimpleNode node;
        int id;
        String qualifiedMethod = ((SimpleNode)root.jjtGetChild(0)).getValue();
        Object [] args = new Object[root.jjtGetNumChildren() - 1];
        Class [] argsClass = new Class[args.length];
        Method method;
        ExplicitConstraints constraints;

        try {
            efClass = getClass(qualifiedMethod);
            methodName = qualifiedMethod.substring(qualifiedMethod.lastIndexOf('.') + 1);
        } catch (ClassNotFoundException e) {
            throw new InvalidConstraintsException(name, valueString, "Unknown class in method call '" + qualifiedMethod + "'");
        }

        for (int i = 0; i < args.length; i++) {
            String value;
            node = (SimpleNode)root.jjtGetChild(i + 1).jjtGetChild(0);
            id = node.getID();
            value = node.getValue();
            switch (id) {
                case ExpressionParser.JJTEXPRESSION :
                    args[i] = buildExpression(layout, name, valueString, node);
                    argsClass[i] = Expression.class;
                break;
                case ExpressionParser.JJTCOMPONENTNAME :
                    if ("this".equals(value)) {
                        args[i] = layout.getContainer();
                        argsClass[i] = Container.class;
                    } else if ((constraints = layout.getConstraints(value)) != null) {
                        args[i] = constraints.getComponent();
                        argsClass[i] = Component.class;
                    } else {
                        throw new InvalidConstraintsException(name, valueString, "Unknown component name '" + value + "'");
                    }
                break;
                case ExpressionParser.JJTARRAY :
                    int itemCount = node.jjtGetNumChildren();
                    int typeID;
                    String compName;
                    if (itemCount > 0) {
                        typeID = ((SimpleNode)node.jjtGetChild(0)).getID();
                        if (typeID == ExpressionParser.JJTCOMPONENTNAME) {
                            Component [] components = new Component[itemCount];
                            for (int c = 0; c < itemCount; c++) {
                                compName = ((SimpleNode)node.jjtGetChild(c)).getValue();
                                if ((constraints = layout.getConstraints(compName)) != null) {
                                    components[c] = constraints.getComponent();
                                } else {
                                    throw new InvalidConstraintsException(name, valueString, "Unknown component name '" + compName + "'");
                                }
                            }
                            args[i] = components;
                            argsClass[i] = components.getClass();
                        } else if (typeID == ExpressionParser.JJTEXPRESSION) {
                            Expression [] expressions = new Expression[itemCount];
                            for (int e = 0; e < itemCount; i++) {
                                expressions[e] = buildExpression(layout, name, valueString,  (SimpleNode)node.jjtGetChild(e));
                            }
                            args[i] = expressions;
                            argsClass[i] = Expression.class;
                        } else {
                            throw new IllegalStateException("Unexpected array element type id : " + typeID);
                        }
                    } else {
                        //parser should not allow this
                        throw new IllegalStateException("Unexpected zero length array");
                    }
                break;
                case ExpressionParser.JJTNUMBER :
                    args[i] = Double.valueOf(value);
                    argsClass[i] = Double.TYPE;
                break;
                default:
                    //parser should not allow this
                    throw new IllegalStateException("Unexpected parameter type id : " + id);
            }
        }

        try {
            method = efClass.getMethod(methodName, argsClass);
        } catch (NoSuchMethodException e) {
            String argsString = "";
            for (int i = 0; i < argsClass.length; i++) {
                argsString += argsClass[i].getName() + ", ";
            }
            throw new InvalidConstraintsException(name, valueString, "Unknown method '" + qualifiedMethod + "(" + argsString + ")'");
        }
        try {
            return (Expression)method.invoke(null, args);
        } catch (InvocationTargetException e) {
            e.getTargetException().printStackTrace();
            throw new InvalidConstraintsException(name, valueString, "Failed to invoke method " + method + " : " + e.getTargetException());
        } catch (Exception e) {
            throw new InvalidConstraintsException(name, valueString, "Failed to invoke method " + method + " : " + e);
        }
    }


    private boolean isConstraintsDefined(String name) {
        return isAttributeDefined(name + X)
               || isAttributeDefined(name + Y)
               || isAttributeDefined(name + WIDTH)
               || isAttributeDefined(name + HEIGHT)
               || isAttributeDefined(name + ORIGIN_X)
               || isAttributeDefined(name + ORIGIN_Y)
               || isAttributeDefined(name + WIDTH_ZERO)
               || isAttributeDefined(name + HEIGHT_ZERO);
    }


    /**
     * Gets the Java Language string for the attibute with the specified name.
     * Subclasses must override this to get attribute strings from there
     * own data source.<BR>The attribute name has the format:<BR>
     * <I>constraints_name</I>.<I>variant</I>.<I>attribute_name</I> or<BR>
     * <I>constraints_name</I>.<I>attribute_name</I> for the default variant.<BR>
     * e.g. somelabel.myvariant.width
     *
     * @param name the attribute name
     *
     * @return the value of the attribute with the specified name; null if unspecified.
     */
    public abstract String getAttributeString(String name) throws InvalidConstraintsException;


    /**
     * Determines if the attribute with the specified name is defined in this constraints source.
     * Subclasses must override this. The current implementation of defineConstraints
     * determines if any attributes are defined for a particular constraints name and variant
     * before calling getAttributeString for each attribute. This will typically
     * be more efficient that calling getAttibuteString twice for each attribute.
     *
     * @param name the attribute name
     *
     * @return true if the named attribute is defined; false otherwise
     *
     */
    public abstract boolean isAttributeDefined(String name);



}
