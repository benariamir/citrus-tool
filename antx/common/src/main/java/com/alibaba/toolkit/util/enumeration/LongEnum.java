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
 * ���Ͱ�ȫ��ö������, ����һ��������.
 *
 * @version $Id: LongEnum.java,v 1.1 2003/07/03 07:26:21 baobao Exp $
 * @author Michael Zhou
 */
public abstract class LongEnum extends Enum {
    private static final long serialVersionUID = 8152633183977823349L;

    /**
     * ����һ��ö����.
     *
     * @param name ö����������
     */
    protected LongEnum(String name) {
        super(name);
    }

    /**
     * ����һ��ö����.
     *
     * @param name  ö����������
     * @param value ö�����ĳ�����ֵ
     */
    protected LongEnum(String name, long value) {
        super(name, new Long(value));
    }

    /**
     * ����һ��ö�����͵�<code>EnumType</code>.
     *
     * @return ö�����͵�<code>EnumType</code>
     */
    protected static Object createEnumType() {
        return new EnumType() {
            protected Class getUnderlyingClass() {
                return Long.class;
            }

            protected Object getNextValue(Object value, boolean flagMode) {
                if (value == null) {
                    return flagMode ? new Long(1)
                                    : new Long(0); // Ĭ����ʼֵ
                }

                long longValue = ((Long) value).longValue();

                if (flagMode) {
                    return new Long(longValue << 1); // λģʽ
                } else {
                    return new Long(longValue + 1);
                }
            }

            protected boolean isZero(Object value) {
                return ((Long) value).longValue() == 0L;
            }

            protected boolean isPowerOfTwo(Object value) {
                long longValue = ((Long) value).longValue();

                if (longValue == 0L) {
                    return false;
                }

                while ((longValue & 1L) == 0L) {
                    longValue = longValue >>> 1;
                }

                return longValue == 1L;
            }
        };
    }

    /**
     * ʵ��<code>Number</code>��, ȡ������ֵ.
     *
     * @return ����ֵ
     */
    public int intValue() {
        return ((Long) getValue()).intValue();
    }

    /**
     * ʵ��<code>Number</code>��, ȡ�ó�����ֵ.
     *
     * @return ������ֵ
     */
    public long longValue() {
        return ((Long) getValue()).longValue();
    }

    /**
     * ʵ��<code>Number</code>��, ȡ��<code>double</code>ֵ.
     *
     * @return <code>double</code>ֵ
     */
    public double doubleValue() {
        return ((Long) getValue()).doubleValue();
    }

    /**
     * ʵ��<code>Number</code>��, ȡ��<code>float</code>ֵ.
     *
     * @return <code>float</code>ֵ
     */
    public float floatValue() {
        return ((Long) getValue()).floatValue();
    }

    /**
     * ʵ��<code>IntegralNumber</code>��, ת����ʮ�����������ַ���.
     *
     * @return ʮ�����������ַ���
     */
    public String toHexString() {
        return Long.toHexString(((Long) getValue()).intValue());
    }

    /**
     * ʵ��<code>IntegralNumber</code>��, ת���ɰ˽��������ַ���.
     *
     * @return �˽��������ַ���
     */
    public String toOctalString() {
        return Long.toOctalString(((Long) getValue()).intValue());
    }

    /**
     * ʵ��<code>IntegralNumber</code>��, ת���ɶ����������ַ���.
     *
     * @return �����������ַ���
     */
    public String toBinaryString() {
        return Long.toBinaryString(((Long) getValue()).intValue());
    }
}
