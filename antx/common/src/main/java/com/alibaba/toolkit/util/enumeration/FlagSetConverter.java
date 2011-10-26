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
 */

package com.alibaba.toolkit.util.enumeration;

import com.alibaba.toolkit.util.typeconvert.ConvertChain;
import com.alibaba.toolkit.util.typeconvert.ConvertFailedException;
import com.alibaba.toolkit.util.typeconvert.Converter;

/**
 * ������ת����<code>FlagSet</code>.
 *
 * <ul>
 * <li>
 * ��������Ѿ���<code>targetType</code>��, ֱ�ӷ���.
 * </li>
 * <li>
 * ���Ž�����ת����<code>FlagSet.getUnderlyingType</code>����, ����ɹ�, �򷵻ض�Ӧ��<code>FlagSet</code>.
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
 * @version $Id: FlagSetConverter.java,v 1.1 2003/07/03 07:26:20 baobao Exp $
 * @author Michael Zhou
 */
public class FlagSetConverter implements Converter {
    public Object convert(Object value, ConvertChain chain) {
        Class targetType = chain.getTargetType();

        if (targetType.isInstance(value)) {
            return value;
        }

        if (!FlagSet.class.isAssignableFrom(targetType)) {
            return chain.convert(value);
        }

        FlagSet flagSet = null;

        try {
            flagSet = (FlagSet) targetType.newInstance();
        } catch (Exception e) {
            return new ConvertFailedException();
        }

        try {
            Object flagSetValue = chain.getConvertManager()
                                       .asTypeWithoutDefaultValue(flagSet.getUnderlyingClass(),
                                                                  value);

            flagSet.setValue(flagSetValue);
        } catch (ConvertFailedException e) {
            if (e.isDefaultValueSet()) {
                flagSet.setValue(e.getDefaultValue());
                throw new ConvertFailedException(e).setDefaultValue(flagSet);
            }

            return chain.convert(value);
        }

        return flagSet;
    }
}
