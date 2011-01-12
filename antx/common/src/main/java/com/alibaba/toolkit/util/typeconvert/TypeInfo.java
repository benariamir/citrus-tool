/*
 * Copyright 2010 Alibaba Group Holding Limited.
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.alibaba.toolkit.util.typeconvert;

import com.alibaba.toolkit.util.collection.ArrayHashSet;

import java.lang.reflect.Array;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ����һ�������Ϣ, ��������, �ӿ�, �����ά����.
 *
 * @version $Id: TypeInfo.java,v 1.1 2003/07/03 07:26:36 baobao Exp $
 * @author Michael Zhou
 */
public class TypeInfo {
    private static Map typeMap       = Collections.synchronizedMap(new HashMap());
    private Class      type;
    private Class      componentType;
    private int        dimension;
    private List       superclasses = new ArrayList(2);
    private List       interfaces   = new ArrayList(2);

    /**
     * ȡ��ָ�����<code>TypeInfo</code>.
     *
     * @param type ָ����.
     *
     * @return <code>TypeInfo</code>����.
     */
    public static TypeInfo getTypeInfo(Class type) {
        if (type == null) {
            throw new NullPointerException();
        }

        TypeInfo typeInfo;
        synchronized (typeMap) {
            typeInfo = (TypeInfo) typeMap.get(type);

            if (typeInfo == null) {
                typeInfo = new TypeInfo(type);
                typeMap.put(type, typeInfo);
            }
        }

        return typeInfo;
    }

    /**
     * ����<code>TypeInfo</code>.
     *
     * @param type ����ָ�����<code>TypeInfo</code>
     */
    private TypeInfo(Class type) {
        this.type = type;

        // �����array, ����componentType��dimension
        if (type.isArray()) {
            this.componentType = type;

            do {
                componentType = componentType.getComponentType();
                dimension++;
            } while (componentType.isArray());
        }

        // ȡ������superclass
        if (dimension > 0) {
            Class superComponentType = componentType.getSuperclass();

            // �����primitive, interface, �����������ΪObject.
            if ((superComponentType == null) && !Object.class.equals(componentType)) {
                superComponentType = Object.class;
            }

            if (superComponentType != null) {
                Class superclass = getArrayClass(superComponentType, dimension);

                superclasses.add(superclass);
                superclasses.addAll(getTypeInfo(superclass).superclasses);
            } else {
                for (int i = dimension - 1; i >= 0; i--) {
                    superclasses.add(getArrayClass(Object.class, i));
                }
            }
        } else {
            Class superclass = type.getSuperclass();

            if (superclass != null) {
                superclasses.add(superclass);
                superclasses.addAll(getTypeInfo(superclass).superclasses);
            }
        }

        // ȡ������interface
        if (dimension == 0) {
            Class[] typeInterfaces = type.getInterfaces();
            Set     set = new ArrayHashSet();

            for (int i = 0; i < typeInterfaces.length; i++) {
                Class typeInterface = typeInterfaces[i];

                set.add(typeInterface);
                set.addAll(getTypeInfo(typeInterface).interfaces);
            }

            for (Iterator i = superclasses.iterator(); i.hasNext();) {
                Class typeInterface = (Class) i.next();

                set.addAll(getTypeInfo(typeInterface).interfaces);
            }

            interfaces.addAll(set);
        }
    }

    /**
     * ȡ��<code>TypeInfo</code>�������java��.
     *
     * @return <code>TypeInfo</code>�������java��
     */
    public Class getType() {
        return type;
    }

    /**
     * ȡ������Ԫ�ص�����.
     *
     * @return ���������, �򷵻�����Ԫ�ص�����, ���򷵻�<code>null</code>
     */
    public Class getComponentType() {
        return componentType;
    }

    /**
     * ȡ�������ά��.
     *
     * @return �����ά��. �����������, �򷵻�<code>0</code>
     */
    public int getDimension() {
        return dimension;
    }

    /**
     * ȡ�����еĸ���.
     *
     * @return ���еĸ���
     */
    public List getSuperclasses() {
        return Collections.unmodifiableList(superclasses);
    }

    /**
     * ȡ�����еĽӿ�.
     *
     * @return ���еĽӿ�
     */
    public List getInterfaces() {
        return Collections.unmodifiableList(interfaces);
    }

    /**
     * ȡ��ָ��ά����<code>Array</code>��.
     *
     * @param componentType  ����Ļ���
     * @param dimension      ά��
     *
     * @return ���ά��Ϊ0, �򷵻ػ��౾��, ���򷵻�������
     */
    public static Class getArrayClass(Class componentType, int dimension) {
        if (dimension == 0) {
            return componentType;
        }

        return Array.newInstance(componentType, new int[dimension]).getClass();
    }
}
