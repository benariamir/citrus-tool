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
 * ��������<code>Convertible</code>�İ�װ��.
 *
 * @version $Id: ConvertibleWrapper.java,v 1.1 2003/07/03 07:26:36 baobao Exp $
 * @author Michael Zhou
 */
public abstract class ConvertibleWrapper implements Convertible {
    private Object wrappedObject;

    /**
     * ������װ��.
     *
     * @param wrappedObject  ����װ�Ķ���
     */
    public ConvertibleWrapper(Object wrappedObject) {
        this.wrappedObject = wrappedObject;
    }

    /**
     * ȡ�ñ���װ�Ķ���.
     *
     * @return ����װ�Ķ���
     */
    public Object getWrappedObject() {
        return wrappedObject;
    }

    /**
     * ȡ��<code>Converter</code>.
     *
     * @param targetType  Ŀ������
     *
     * @return ת����<code>Converter</code>
     */
    public Converter getConverter(Class targetType) {
        return new Converter() {
            public Object convert(Object value, ConvertChain chain) {
                Class targetType = chain.getTargetType();

                value = preConvert(wrappedObject, targetType);
                return postConvert(targetType, chain.convert(value));
            }
        };
    }

    /**
     * Ԥת��.
     *
     * @param wrappedObject ����װ�Ķ���
     * @param targetType    Ŀ������
     *
     * @return Ԥת����Ķ���
     */
    protected Object preConvert(Object wrappedObject, Class targetType) {
        return wrappedObject;
    }

    /**
     * ��ת��.
     *
     * @param targetType     Ŀ������
     * @param convertedValue ת���Ľ��
     *
     * @return ���������ת�����
     */
    protected Object postConvert(Class targetType, Object convertedValue) {
        return convertedValue;
    }
}
