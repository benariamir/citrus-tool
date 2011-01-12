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
import com.alibaba.toolkit.util.typeconvert.Converter;
import com.alibaba.toolkit.util.typeconvert.Convertible;

import java.io.Serializable;

import java.text.MessageFormat;

import java.util.Iterator;

/**
 * ����һ������<code>Flags</code>���ɵ�λ��.
 *
 * @version $Id: FlagSet.java,v 1.1 2003/07/03 07:26:20 baobao Exp $
 * @author Michael Zhou
 */
public abstract class FlagSet implements Flags, Cloneable, Comparable, Serializable,
                                                        Convertible {
    private static final long   serialVersionUID = -5507969553098965333L;
    private final Class         enumClass;
    protected transient boolean immutable;

    /**
     * ����һ��λ��.
     *
     * @param enumClass λ����������ڲ�ö����
     */
    public FlagSet(Class enumClass) {
        this.enumClass = enumClass;

        if (!Enum.class.isAssignableFrom(enumClass)) {
            throw new IllegalArgumentException(MessageFormat.format(EnumConstants.ILLEGAL_CLASS,
                                                                    new Object[] {
                enumClass.getName(),
                Enum.class.getName()
            }));
        }

        if (!Flags.class.isAssignableFrom(enumClass)) {
            throw new IllegalArgumentException(MessageFormat.format(EnumConstants.ILLEGAL_INTERFACE,
                                                                    new Object[] {
                enumClass.getName(),
                Flags.class.getName()
            }));
        }
    }

    /**
     * ȡ���ڲ�ö������.
     *
     * @return �ڲ�ö������
     */
    public Class getEnumClass() {
        return enumClass;
    }

    /**
     * ȡ��λ����ֵ������.
     *
     * @return λ����ֵ������
     */
    public Class getUnderlyingClass() {
        return Enum.getUnderlyingClass(enumClass);
    }

    /**
     * ����λ����ֵ, ֵ��������<code>getUnderlyingClass()</code>ȷ��.
     *
     * @param value λ����ֵ
     */
    public abstract void setValue(Object value);

    /**
     * ȡ��λ����ֵ, ֵ��������<code>getUnderlyingClass()</code>ȷ��.
     *
     * @return λ����ֵ
     */
    public abstract Object getValue();

    /**
     * ʵ��<code>Number</code>��, ȡ��<code>byte</code>ֵ.
     *
     * @return <code>byte</code>ֵ
     */
    public byte byteValue() {
        return (byte) intValue();
    }

    /**
     * ʵ��<code>Number</code>��, ȡ��<code>short</code>ֵ.
     *
     * @return <code>short</code>ֵ
     */
    public short shortValue() {
        return (short) intValue();
    }

    /**
     * ʵ��<code>Convertible</code>�ӿ�, ȡ�ý���ǰλ��ת����ָ��<code>targetType</code>��<code>Converter</code>.
     * ת���Ĺ�������:
     *
     * <ul>
     * <li>
     * ���<code>targetType</code>���ַ���, �򷵻�<code>FlagSet.toString()</code>.
     * </li>
     * <li>
     * ����λ����ֵ���ݵ�ת������.
     * </li>
     * </ul>
     *
     *
     * @param targetType Ŀ������
     *
     * @return ����ǰλ��ת����ָ��<code>targetType</code>��<code>Converter</code>
     */
    public Converter getConverter(Class targetType) {
        return new Converter() {
            public Object convert(Object value, ConvertChain chain) {
                FlagSet flagSet    = (FlagSet) value;
                Class   targetType = chain.getTargetType();

                if (String.class.equals(targetType)) {
                    return flagSet.toString();
                }

                return chain.convert(flagSet.getValue());
            }
        };
    }

    /**
     * ����λ������.
     *
     * @return ����Ʒ
     */
    public Object clone() {
        FlagSet flagSet = null;

        try {
            flagSet = (FlagSet) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(MessageFormat.format(EnumConstants.CLONE_NOT_SUPPORTED,
                                                         new Object[] {
                getClass().getName()
            }));
        }

        flagSet.immutable = false;
        return flagSet;
    }

    /**
     * ����һ��λ���Ƚϴ�С, ���ǰ�λ����ֵ�Ƚ�.
     *
     * @param other Ҫ�Ƚϵ�λ��
     *
     * @return �������<code>0</code>, ��ʾֵ���, ����<code>0</code>��ʾ��ǰ��λ����ֵ��<code>otherFlags</code>��,
     *         С��<code>0</code>��ʾ��ǰ��λ����ֵ��<code>otherFlags</code>С
     */
    public int compareTo(Object other) {
        if (!getClass().equals(other.getClass())) {
            throw new IllegalArgumentException(MessageFormat.format(
                                                       EnumConstants.COMPARE_TYPE_MISMATCH,
                                                       new Object[] {
                getClass().getName(),
                other.getClass().getName()
            }));
        }

        FlagSet otherFlagSet = (FlagSet) other;

        if (!enumClass.equals(otherFlagSet.enumClass)) {
            throw new IllegalArgumentException(MessageFormat.format(
                                                       EnumConstants.COMPARE_UNDERLYING_CLASS_MISMATCH,
                                                       new Object[] {
                enumClass.getName(),
                otherFlagSet.enumClass.getName()
            }));
        }

        return ((Comparable) getValue()).compareTo(otherFlagSet.getValue());
    }

    /**
     * �Ƚ�����λ���Ƿ����, ��: ������ͬ, �ڲ�����ͬ, ����ֵ��ͬ.
     *
     * @param obj  Ҫ�ȽϵĶ���
     *
     * @return ������, �򷵻�<code>true</code>
     */
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if ((obj == null) || !getClass().equals(obj.getClass())
                || !enumClass.equals(((FlagSet) obj).enumClass)) {
            return false;
        }

        return getValue().equals(((FlagSet) obj).getValue());
    }

    /**
     * ȡ��λ����hashֵ.  �������λ����ͬ, �����ǵ�hashֵһ����ͬ.
     *
     * @return hashֵ
     */
    public int hashCode() {
        return getClass().hashCode() ^ enumClass.hashCode() ^ getValue().hashCode();
    }

    /**
     * ȡ��λ�����ַ�����ʾ.
     *
     * @return λ�����ַ�����ʾ
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer("{");
        String       sep = "";

        for (Iterator i = Enum.iterator(enumClass); i.hasNext();) {
            Flags flags = (Flags) i.next();

            if (test(flags)) {
                buffer.append(sep);
                sep = ", ";
                buffer.append(flags);
            }
        }

        buffer.append("}");
        return buffer.toString();
    }

    /**
     * ���óɲ��ɱ��λ��.
     *
     * @return λ������
     */
    public Flags setImmutable() {
        this.immutable = true;
        return this;
    }

    /**
     * �����ǰλ����ȫ��λ.
     *
     * @return ��ǰλ��
     */
    public abstract Flags clear();

    /**
     * �����ǰλ����ָ��λ, ��Ч��<code>andNot</code>����.
     *
     * @param flags  ��־λ
     *
     * @return ��ǰλ��
     */
    public Flags clear(Flags flags) {
        checkImmutable();
        return andNot(flags);
    }

    /**
     * ���õ�ǰλ����ָ��λ, ��Ч��<code>or</code>����.
     *
     * @param flags  ��־λ
     *
     * @return ��ǰλ��
     */
    public Flags set(Flags flags) {
        checkImmutable();
        return or(flags);
    }

    /**
     * ���Ե�ǰλ����ָ��λ, ��Ч��<code>and(flags) != 0</code>.
     *
     * @param flags  ��־λ
     *
     * @return ���ָ��λ����λ, �򷵻�<code>true</code>
     */
    public abstract boolean test(Flags flags);

    /**
     * ����ǲ��ɱ�λ��, �򴴽�һ���µ�λ��, ���򷵻ر���.
     *
     * @return λ���������Ʒ
     */
    protected FlagSet getFlagSetForModification() {
        if (immutable) {
            return (FlagSet) this.clone();
        } else {
            return this;
        }
    }

    /**
     * ����ǲ��ɱ��λ��, ������<code>UnsupportedOperationException</code>.
     */
    protected void checkImmutable() {
        if (immutable) {
            throw new UnsupportedOperationException(EnumConstants.FLAG_SET_IS_IMMUTABLE);
        }
    }

    /**
     * ȷ��<code>flags</code>�ǿ�, ������<code>Enum</code>��<code>FlagSet</code>��.
     *
     * @param flags Ҫ�жϵĶ���
     */
    protected void checkFlags(Flags flags) {
        if (flags == null) {
            throw new NullPointerException(EnumConstants.FLAGS_IS_NULL);
        }

        Class flagsClass = flags.getClass();

        if (!enumClass.equals(flagsClass) && !getClass().equals(flagsClass)) {
            throw new IllegalArgumentException(MessageFormat.format(
                                                       EnumConstants.ILLEGAL_FLAGS_OBJECT,
                                                       new Object[] {
                enumClass.getName(),
                getClass().getName()
            }));
        }
    }
}
