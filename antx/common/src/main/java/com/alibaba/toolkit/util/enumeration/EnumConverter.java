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
package com.alibaba.toolkit.util.enumeration;

import com.alibaba.toolkit.util.typeconvert.ConvertChain;
import com.alibaba.toolkit.util.typeconvert.ConvertFailedException;
import com.alibaba.toolkit.util.typeconvert.Converter;

/**
 * ������ת����<code>Enum</code>.
 *
 * <ul>
 * <li>
 * ��������Ѿ���<code>targetType</code>��, ֱ�ӷ���.
 * </li>
 * <li>
 * ����������ַ���, �����Ű���ת�����ַ��������������<code>Enum</code>.  ����ɹ�, �򷵻�.
 * </li>
 * <li>
 * ���Ž�����ת����<code>Enum.getUnderlyingClass</code>����, ����ɹ�, �򷵻ض�Ӧ��<code>Enum</code>.
 * </li>
 * <li>
 * �����Ĭ��ֵ, ���׳���Ĭ��ֵ��<code>ConvertFailedException</code>
 * </li>
 * <li>
 * ����, �Ѷ��󴫵ݸ���һ��<code>Converter</code>����.
 * </li>
 * </ul>
 *
 *
 * @version $Id: EnumConverter.java,v 1.1 2003/07/03 07:26:20 baobao Exp $
 * @author Michael Zhou
 */
public class EnumConverter implements Converter {
    public Object convert(Object value, ConvertChain chain) {
        Class targetType = chain.getTargetType();

        if (targetType.isInstance(value)) {
            return value;
        }

        if (value instanceof String) {
            Enum enumObj = Enum.getEnumByName(targetType, ((String) value).trim());

            if (enumObj != null) {
                return enumObj;
            }
        }

        Enum enumObj = null;

        try {
            Object enumValue = chain.getConvertManager()
                                    .asTypeWithoutDefaultValue(Enum.getUnderlyingClass(targetType),
                                                               value);

            enumObj = Enum.getEnumByValue(targetType, enumValue);
        } catch (ConvertFailedException e) {
            if (e.isDefaultValueSet()) {
                enumObj = Enum.getEnumByValue(targetType, e.getDefaultValue());

                if (enumObj != null) {
                    throw new ConvertFailedException(e).setDefaultValue(enumObj);
                }
            }

            return chain.convert(value);
        }

        if (enumObj == null) {
            throw new ConvertFailedException();
        }

        return enumObj;
    }
}
