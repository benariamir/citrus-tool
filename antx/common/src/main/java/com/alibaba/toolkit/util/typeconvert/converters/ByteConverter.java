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

/**
 * ������ת�����ֽ�.
 *
 * <ul>
 * <li>
 * �������Ϊ<code>null</code>, ���׳���Ĭ��ֵ��<code>ConvertFailedException</code>.
 * </li>
 * <li>
 * ��������Ѿ���<code>Byte</code>��, ֱ�ӷ���.
 * </li>
 * <li>
 * ���������<code>Number</code>����, �򷵻������ֽ�ֵ.
 * </li>
 * <li>
 * ��������ǿ��ַ���, ���׳���Ĭ��ֵ��<code>ConvertFailedException</code>.
 * </li>
 * <li>
 * ����������ַ���, �����Ű���ת�����ֽ�.  ������ɹ�, ���׳�<code>ConvertFailedException</code>.
 * </li>
 * <li>
 * ����, �Ѷ��󴫵ݸ���һ��<code>Converter</code>����.
 * </li>
 * </ul>
 *
 *
 * @version $Id: ByteConverter.java,v 1.1 2003/07/03 07:26:37 baobao Exp $
 * @author Michael Zhou
 */
public class ByteConverter implements Converter {
    protected static final Byte DEFAULT_VALUE = new Byte((byte) 0);

    public Object convert(Object value, ConvertChain chain) {
        if (value == null) {
            throw new ConvertFailedException().setDefaultValue(DEFAULT_VALUE);
        }

        if (value instanceof Byte) {
            return value;
        }

        if (value instanceof Number) {
            return new Byte(((Number) value).byteValue());
        }

        if (value instanceof String) {
            String strValue = ((String) value).trim();

            try {
                return Byte.decode(strValue);
            } catch (NumberFormatException e) {
                if (strValue.length() > 0) {
                    throw new ConvertFailedException(e);
                }

                throw new ConvertFailedException().setDefaultValue(DEFAULT_VALUE);
            }
        }

        return chain.convert(value);
    }
}
