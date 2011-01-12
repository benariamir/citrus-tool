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

/**
 * ������ת������һ����ʽ��ת����.
 *
 * @version $Id: Converter.java,v 1.1 2003/07/03 07:26:36 baobao Exp $
 * @author Michael Zhou
 */
public interface Converter {
    /**
     * ת��ָ����ֵ��ָ��������.
     *
     * @param value       Ҫת����ֵ
     * @param chain       ת����, ���һ��ת��������ת��ĳ������, ������������е���һ��ת����
     *
     * @return ת�����ֵ
     */
    Object convert(Object value, ConvertChain chain);
}
