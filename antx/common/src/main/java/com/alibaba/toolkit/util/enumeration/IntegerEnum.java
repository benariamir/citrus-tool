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

/**
 * ���Ͱ�ȫ��ö������, ����һ������.
 *
 * @version $Id: IntegerEnum.java,v 1.1 2003/07/03 07:26:20 baobao Exp $
 * @author Michael Zhou
 */
public abstract class IntegerEnum extends Enum {
    private static final long serialVersionUID = 343392921439669443L;

    /**
     * ����һ��ö����.
     *
     * @param name ö����������
     */
    protected IntegerEnum(String name) {
        super(name);
    }

    /**
     * ����һ��ö����.
     *
     * @param name  ö����������
     * @param value ö����������ֵ
     */
    protected IntegerEnum(String name, int value) {
        super(name, new Integer(value));
    }

    /**
     * ����һ��ö�����͵�<code>EnumType</code>.
     *
     * @return ö�����͵�<code>EnumType</code>
     */
    protected static Object createEnumType() {
        return new EnumType() {
            protected Class getUnderlyingClass() {
                return Integer.class;
            }

            protected Object getNextValue(Object value, boolean flagMode) {
                if (value == null) {
                    return flagMode ? new Integer(1)
                                    : new Integer(0); // Ĭ����ʼֵ
                }

                int intValue = ((Integer) value).intValue();

                if (flagMode) {
                    return new Integer(intValue << 1); // λģʽ
                } else {
                    return new Integer(intValue + 1);
                }
            }

            protected boolean isZero(Object value) {
                return ((Integer) value).intValue() == 0;
            }

            protected boolean isPowerOfTwo(Object value) {
                int intValue = ((Integer) value).intValue();

                if (intValue == 0) {
                    return false;
                }

                while ((intValue & 1) == 0) {
                    intValue = intValue >>> 1;
                }

                return intValue == 1;
            }
        };
    }

    /**
     * ʵ��<code>Number</code>��, ȡ������ֵ.
     *
     * @return ����ֵ
     */
    public int intValue() {
        return ((Integer) getValue()).intValue();
    }

    /**
     * ʵ��<code>Number</code>��, ȡ�ó�����ֵ.
     *
     * @return ������ֵ
     */
    public long longValue() {
        return ((Integer) getValue()).longValue();
    }

    /**
     * ʵ��<code>Number</code>��, ȡ��<code>double</code>ֵ.
     *
     * @return <code>double</code>ֵ
     */
    public double doubleValue() {
        return ((Integer) getValue()).doubleValue();
    }

    /**
     * ʵ��<code>Number</code>��, ȡ��<code>float</code>ֵ.
     *
     * @return <code>float</code>ֵ
     */
    public float floatValue() {
        return ((Integer) getValue()).floatValue();
    }

    /**
     * ʵ��<code>IntegralNumber</code>��, ת����ʮ�����������ַ���.
     *
     * @return ʮ�����������ַ���
     */
    public String toHexString() {
        return Integer.toHexString(((Integer) getValue()).intValue());
    }

    /**
     * ʵ��<code>IntegralNumber</code>��, ת���ɰ˽��������ַ���.
     *
     * @return �˽��������ַ���
     */
    public String toOctalString() {
        return Integer.toOctalString(((Integer) getValue()).intValue());
    }

    /**
     * ʵ��<code>IntegralNumber</code>��, ת���ɶ����������ַ���.
     *
     * @return �����������ַ���
     */
    public String toBinaryString() {
        return Integer.toBinaryString(((Integer) getValue()).intValue());
    }
}
