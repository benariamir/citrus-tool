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

import com.alibaba.toolkit.util.typeconvert.ConvertChain;
import com.alibaba.toolkit.util.typeconvert.ConvertFailedException;
import com.alibaba.toolkit.util.typeconvert.Converter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * ������ת���ɲ���ֵ.
 *
 * <ul>
 * <li>
 * �������Ϊ<code>null</code>, ���׳���Ĭ��ֵ��<code>ConvertFailedException</code>.
 * </li>
 * <li>
 * ��������Ѿ���<code>Boolean</code>��, ֱ�ӷ���.
 * </li>
 * <li>
 * ���������<code>Number</code>����, �Ҳ�Ϊ<code>0</code>, �򷵻�<code>true</code>, ���򷵻�<code>false</code>.
 * </li>
 * <li>
 * ����������ַ���, ���ַ�����������һ������, ����ֵ��Ϊ<code>0</code>, �򷵻�<code>true</code>, ���򷵻�<code>false</code>.
 * </li>
 * <li>
 * �������Ϊ���ַ���, ���׳���Ĭ��ֵ��<code>ConvertFailedException</code>.
 * </li>
 * <li>
 * �������Ϊ����ֵ֮һ���ַ���(��Сд������): <code>"false", "null", "nul", "nil", "off", "no", "n"</code>
 * ��Ϊ<code>false</code>.
 * </li>
 * <li>
 * �������Ϊ����ֵ֮һ���ַ���(��Сд������): <code>"true", "on", "yes", "y"</code> ��Ϊ<code>true</code>.
 * </li>
 * <li>
 * ����������ַ���, �Ҳ�����������������, ���׳�<code>ConvertFailedException</code>.
 * </li>
 * <li>
 * ����, �Ѷ��󴫵ݸ���һ��<code>Converter</code>����.
 * </li>
 * </ul>
 *
 *
 * @version $Id: BooleanConverter.java,v 1.1 2003/07/03 07:26:37 baobao Exp $
 * @author Michael Zhou
 */
public class BooleanConverter implements Converter {
    protected static final Boolean DEFAULT_VALUE = Boolean.FALSE;
    protected static final Set     TRUE_STRINGS = new HashSet(Arrays.asList(
                                                                      new String[] {
        "true",
        "on",
        "yes",
        "y"
    }));
    protected static final Set FALSE_STRINGS = new HashSet(Arrays.asList(
                                                                   new String[] {
        "false",
        "null",
        "nul",
        "nil",
        "off",
        "no",
        "n"
    }));

    public Object convert(Object value, ConvertChain chain) {
        if (value == null) {
            throw new ConvertFailedException().setDefaultValue(DEFAULT_VALUE);
        }

        if (value instanceof Boolean) {
            return value;
        }

        if (value instanceof Number) {
            return (Math.abs(((Number) value).doubleValue()) < Float.MIN_VALUE)
                       ? Boolean.FALSE
                       : Boolean.TRUE;
        }

        if (value instanceof String) {
            String strValue = ((String) value).trim();

            try {
                return (Integer.decode(strValue).intValue() == 0) ? Boolean.FALSE
                                                                  : Boolean.TRUE;
            } catch (NumberFormatException e) {
                if (strValue.length() == 0) {
                    throw new ConvertFailedException().setDefaultValue(DEFAULT_VALUE);
                }

                strValue = strValue.toLowerCase();

                if (TRUE_STRINGS.contains(strValue)) {
                    return Boolean.TRUE;
                }

                if (FALSE_STRINGS.contains(strValue)) {
                    return Boolean.FALSE;
                }

                throw new ConvertFailedException(e);
            }
        }

        return chain.convert(value);
    }
}
