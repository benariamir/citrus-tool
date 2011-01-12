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
import com.alibaba.toolkit.util.typeconvert.Converter;

/**
 * Object���͵�ת����.
 *
 * <ul>
 * <li>
 * ���<code>targetType</code>��<code>Object</code>����, ��ֱ�ӷ���.
 * </li>
 * <li>
 * ����, �Ѷ��󴫵ݸ���һ��<code>Converter</code>����.
 * </li>
 * </ul>
 *
 *
 * @version $Id: ObjectConverter.java,v 1.1 2003/07/03 07:26:41 baobao Exp $
 * @author Michael Zhou
 */
public class ObjectConverter implements Converter {
    public Object convert(Object value, ConvertChain chain) {
        Class targetType = chain.getTargetType();

        if (Object.class.equals(targetType)) {
            return value;
        }

        return chain.convert(value);
    }
}
