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
package com.alibaba.toolkit.util.typeconvert.converters;

import com.alibaba.toolkit.util.collection.EnumerationIterator;
import com.alibaba.toolkit.util.typeconvert.ConvertChain;
import com.alibaba.toolkit.util.typeconvert.ConvertFailedException;
import com.alibaba.toolkit.util.typeconvert.ConvertManager;
import com.alibaba.toolkit.util.typeconvert.Converter;

import java.lang.reflect.Array;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

/**
 * ������ת���ɶ�������.
 *
 * <ul>
 * <li>
 * ���<code>targetType</code>������������, ���׳�<code>ConvertFailedException</code>.
 * </li>
 * <li>
 * �������Ϊ<code>null</code>, ���׳���Ĭ��ֵ��<code>ConvertFailedException</code>.
 * </li>
 * <li>
 * ��������Ѿ���<code>targetType</code>��, ֱ�ӷ���.
 * </li>
 * <li>
 * �������Ϊ����, ���������, ����ÿ������Ԫ��, ִ��ת��.
 * </li>
 * <li>
 * �������Ϊ<code>Collection</code>, �����֮, ����ÿ������Ԫ��, ִ��ת��.
 * </li>
 * <li>
 * �������Ϊ<code>Iterator</code>��<code>Enumeration</code>, �����֮, ����ÿ��Ԫ��, ִ��ת��.
 * </li>
 * <li>
 * ����, �Ѷ��󴫵ݸ���һ��<code>Converter</code>����.
 * </li>
 * </ul>
 *
 *
 * @version $Id: ObjectArrayConverter.java,v 1.1 2003/07/03 07:26:41 baobao Exp $
 * @author Michael Zhou
 */
public class ObjectArrayConverter implements Converter {
    public Object convert(Object value, ConvertChain chain) {
        Class targetType    = chain.getTargetType();
        Class componentType = targetType.getComponentType();

        if (componentType == null) {
            throw new ConvertFailedException();
        }

        if (value == null) {
            throw new ConvertFailedException().setDefaultValue(Array.newInstance(componentType, 0));
        }

        if (targetType.isInstance(value)) {
            return value;
        }

        if (value.getClass().isArray()) {
            int            length          = Array.getLength(value);
            Object         convertedValues = Array.newInstance(componentType, length);
            ConvertManager manager         = chain.getConvertManager();

            for (int i = 0; i < length; i++) {
                Array.set(convertedValues, i, manager.asType(componentType, Array.get(value, i)));
            }

            return convertedValues;
        }

        if (value instanceof Collection) {
            Collection     values          = (Collection) value;
            Object         convertedValues = Array.newInstance(componentType, values.size());
            ConvertManager manager         = chain.getConvertManager();

            Iterator       iterator = values.iterator();

            for (int i = 0; iterator.hasNext(); i++) {
                Array.set(convertedValues, i, manager.asType(componentType, iterator.next()));
            }

            return convertedValues;
        }

        Iterator iterator = null;

        if (value instanceof Iterator) {
            iterator = (Iterator) value;
        } else if (value instanceof Enumeration) {
            iterator = new EnumerationIterator((Enumeration) value);
        }

        if (iterator != null) {
            List           convertedValueList = new ArrayList();
            ConvertManager manager = chain.getConvertManager();

            while (iterator.hasNext()) {
                convertedValueList.add(manager.asType(componentType, iterator.next()));
            }

            int    length          = convertedValueList.size();
            Object convertedValues = Array.newInstance(componentType, length);

            for (int i = 0; i < length; i++) {
                Array.set(convertedValues, i, convertedValueList.get(i));
            }

            return convertedValues;
        }

        return chain.convert(value);
    }
}
