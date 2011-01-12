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

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * ���Ͱ�ȫ��ö������, ����һ����������.
 *
 * @version $Id: BigIntegerEnum.java,v 1.1 2003/07/03 07:26:20 baobao Exp $
 * @author Michael Zhou
 */
public abstract class BigIntegerEnum extends Enum {
    static final long serialVersionUID = 3407019802348379119L;

    /**
     * ����һ��ö����.
     *
     * @param name ö����������
     */
    protected BigIntegerEnum(String name) {
        super(name);
    }

    /**
     * ����һ��ö����.
     *
     * @param name  ö����������
     * @param value ö�����ĳ�������ֵ
     */
    protected BigIntegerEnum(String name, int value) {
        super(name, new BigInteger(String.valueOf(value)));
    }

    /**
     * ����һ��ö����.
     *
     * @param name  ö����������
     * @param value ö�����ĳ�������ֵ
     */
    protected BigIntegerEnum(String name, long value) {
        super(name, new BigInteger(String.valueOf(value)));
    }

    /**
     * ����һ��ö����.
     *
     * @param name  ö����������
     * @param value ö�����ĳ�������ֵ
     */
    protected BigIntegerEnum(String name, String value) {
        super(name, new BigInteger(value));
    }

    /**
     * ����һ��ö����.
     *
     * @param name  ö����������
     * @param value ö�����ĳ�������ֵ
     */
    protected BigIntegerEnum(String name, BigInteger value) {
        super(name, value);
    }

    /**
     * ����һ��ö����.
     *
     * @param name  ö����������
     * @param value ö�����ĳ�������ֵ
     */
    protected BigIntegerEnum(String name, BigDecimal value) {
        super(name, value.toBigInteger());
    }

    /**
     * ����һ��ö�����͵�<code>EnumType</code>.
     *
     * @return ö�����͵�<code>EnumType</code>
     */
    protected static Object createEnumType() {
        return new EnumType() {
            protected Class getUnderlyingClass() {
                return BigInteger.class;
            }

            protected Object getNextValue(Object value, boolean flagMode) {
                if (value == null) {
                    return flagMode ? BigInteger.ONE
                                    : BigInteger.ZERO; // Ĭ����ʼֵ
                }

                if (flagMode) {
                    return ((BigInteger) value).shiftLeft(1); // λģʽ
                } else {
                    return ((BigInteger) value).add(BigInteger.ONE);
                }
            }

            protected boolean isZero(Object value) {
                return ((BigInteger) value).equals(BigInteger.ZERO);
            }

            protected boolean isPowerOfTwo(Object value) {
                BigInteger bintValue = (BigInteger) value;
                int        bitIndex = bintValue.getLowestSetBit();

                if (bitIndex < 0) {
                    return false;
                }

                return bintValue.clearBit(bitIndex).equals(BigInteger.ZERO);
            }
        };
    }

    /**
     * ʵ��<code>Number</code>��, ȡ������ֵ.
     *
     * @return ����ֵ
     */
    public int intValue() {
        return ((BigInteger) getValue()).intValue();
    }

    /**
     * ʵ��<code>Number</code>��, ȡ�ó�����ֵ.
     *
     * @return ������ֵ
     */
    public long longValue() {
        return ((BigInteger) getValue()).longValue();
    }

    /**
     * ʵ��<code>Number</code>��, ȡ��<code>double</code>ֵ.
     *
     * @return <code>double</code>ֵ
     */
    public double doubleValue() {
        return ((BigInteger) getValue()).doubleValue();
    }

    /**
     * ʵ��<code>Number</code>��, ȡ��<code>float</code>ֵ.
     *
     * @return <code>float</code>ֵ
     */
    public float floatValue() {
        return ((BigInteger) getValue()).floatValue();
    }

    /**
     * ʵ��<code>IntegralNumber</code>��, ת����ʮ�����������ַ���.
     *
     * @return ʮ�����������ַ���
     */
    public String toHexString() {
        return ((BigInteger) getValue()).toString(RADIX_HEX);
    }

    /**
     * ʵ��<code>IntegralNumber</code>��, ת���ɰ˽��������ַ���.
     *
     * @return �˽��������ַ���
     */
    public String toOctalString() {
        return ((BigInteger) getValue()).toString(RADIX_OCT);
    }

    /**
     * ʵ��<code>IntegralNumber</code>��, ת���ɶ����������ַ���.
     *
     * @return �����������ַ���
     */
    public String toBinaryString() {
        return ((BigInteger) getValue()).toString(RADIX_BIN);
    }
}
