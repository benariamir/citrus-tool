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

import java.text.MessageFormat;

/**
 * �����������͵�<code>FlagSet</code>.
 *
 * @version $Id: IntegerFlagSet.java,v 1.1 2003/07/03 07:26:21 baobao Exp $
 * @author Michael Zhou
 */
public abstract class IntegerFlagSet extends FlagSet {
    private static final long serialVersionUID = 397018141070555601L;
    private int               value;

    /**
     * ����һ������λ��.
     *
     * @param enumClass λ����������ڲ�ö����
     */
    public IntegerFlagSet(Class enumClass) {
        super(enumClass);

        if (!IntegerEnum.class.isAssignableFrom(enumClass)) {
            throw new IllegalArgumentException(MessageFormat.format(EnumConstants.ILLEGAL_CLASS,
                                                                    new Object[] {
                enumClass.getName(),
                IntegerEnum.class.getName()
            }));
        }
    }

    /**
     * ����λ����ֵ, ֵ��������<code>getUnderlyingClass()</code>ȷ��.
     *
     * @param value λ����ֵ
     */
    public void setValue(Object value) {
        checkImmutable();

        if (value == null) {
            throw new NullPointerException(EnumConstants.FLAT_SET_VALUE_IS_NULL);
        }

        this.value = ((Integer) value).intValue();
    }

    /**
     * ȡ��λ����ֵ, ֵ��������<code>getUnderlyingClass()</code>ȷ��.
     *
     * @return λ����ֵ
     */
    public Object getValue() {
        return new Integer(value);
    }

    /**
     * �����ǰλ����ȫ��λ.
     *
     * @return ��ǰλ��
     */
    public Flags clear() {
        checkImmutable();
        value = 0;
        return this;
    }

    /**
     * ���Ե�ǰλ����ָ��λ, ��Ч��<code>and(flags) != 0</code>.
     *
     * @param flags  ��־λ
     *
     * @return ���ָ��λ����λ, �򷵻�<code>true</code>
     */
    public boolean test(Flags flags) {
        return (value & getFlagsValue(flags)) != 0;
    }

    /**
     * ���Ե�ǰλ����ָ��λ, ��Ч��<code>and(flags) == flags</code>.
     *
     * @param flags  ��־λ
     *
     * @return ���ָ��λ����λ, �򷵻�<code>true</code>
     */
    public boolean testAll(Flags flags) {
        int testValue = getFlagsValue(flags);

        return (value & testValue) == testValue;
    }

    /**
     * �Ե�ǰλ��ִ���߼������.
     *
     * @param flags  ��־λ
     *
     * @return ��ǰλ��
     */
    public Flags and(Flags flags) {
        IntegerFlagSet flagSet = (IntegerFlagSet) getFlagSetForModification();

        flagSet.value &= getFlagsValue(flags);
        return flagSet;
    }

    /**
     * �Ե�ǰλ��ִ���߼��ǲ���.
     *
     * @param flags  ��־λ
     *
     * @return ��ǰλ��
     */
    public Flags andNot(Flags flags) {
        IntegerFlagSet flagSet = (IntegerFlagSet) getFlagSetForModification();

        flagSet.value &= ~getFlagsValue(flags);
        return flagSet;
    }

    /**
     * �Ե�ǰλ��ִ���߼������.
     *
     * @param flags  ��־λ
     *
     * @return ��ǰλ��
     */
    public Flags or(Flags flags) {
        IntegerFlagSet flagSet = (IntegerFlagSet) getFlagSetForModification();

        flagSet.value |= getFlagsValue(flags);
        return flagSet;
    }

    /**
     * �Ե�ǰλ��ִ���߼�������.
     *
     * @param flags  ��־λ
     *
     * @return ��ǰλ��
     */
    public Flags xor(Flags flags) {
        IntegerFlagSet flagSet = (IntegerFlagSet) getFlagSetForModification();

        flagSet.value ^= getFlagsValue(flags);
        return flagSet;
    }

    /**
     * ȡ��λ����ֵ.
     *
     * @param flags λ��
     *
     * @return λ����ֵ
     */
    private int getFlagsValue(Flags flags) {
        checkFlags(flags);
        return (flags instanceof IntegerEnum)
                   ? ((IntegerEnum) flags).intValue()
                   : ((IntegerFlagSet) flags).value;
    }

    /**
     * ʵ��<code>Number</code>��, ȡ������ֵ.
     *
     * @return ����ֵ
     */
    public int intValue() {
        return value;
    }

    /**
     * ʵ��<code>Number</code>��, ȡ�ó�����ֵ.
     *
     * @return ������ֵ
     */
    public long longValue() {
        return (long) value;
    }

    /**
     * ʵ��<code>Number</code>��, ȡ��<code>double</code>ֵ.
     *
     * @return <code>double</code>ֵ
     */
    public double doubleValue() {
        return (double) value;
    }

    /**
     * ʵ��<code>Number</code>��, ȡ��<code>float</code>ֵ.
     *
     * @return <code>float</code>ֵ
     */
    public float floatValue() {
        return (float) value;
    }

    /**
     * ʵ��<code>IntegralNumber</code>��, ת����ʮ�����������ַ���.
     *
     * @return ʮ�����������ַ���
     */
    public String toHexString() {
        return Integer.toHexString(value);
    }

    /**
     * ʵ��<code>IntegralNumber</code>��, ת���ɰ˽��������ַ���.
     *
     * @return �˽��������ַ���
     */
    public String toOctalString() {
        return Integer.toOctalString(value);
    }

    /**
     * ʵ��<code>IntegralNumber</code>��, ת���ɶ����������ַ���.
     *
     * @return �����������ַ���
     */
    public String toBinaryString() {
        return Integer.toBinaryString(value);
    }
}
