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

import com.alibaba.toolkit.util.enumeration.Enum;
import com.alibaba.toolkit.util.enumeration.EnumConverter;
import com.alibaba.toolkit.util.enumeration.FlagSet;
import com.alibaba.toolkit.util.enumeration.FlagSetConverter;
import com.alibaba.toolkit.util.typeconvert.converters.BigDecimalConverter;
import com.alibaba.toolkit.util.typeconvert.converters.BigIntegerConverter;
import com.alibaba.toolkit.util.typeconvert.converters.BooleanConverter;
import com.alibaba.toolkit.util.typeconvert.converters.ByteConverter;
import com.alibaba.toolkit.util.typeconvert.converters.CharacterConverter;
import com.alibaba.toolkit.util.typeconvert.converters.DoubleConverter;
import com.alibaba.toolkit.util.typeconvert.converters.FloatConverter;
import com.alibaba.toolkit.util.typeconvert.converters.IntegerConverter;
import com.alibaba.toolkit.util.typeconvert.converters.LongConverter;
import com.alibaba.toolkit.util.typeconvert.converters.ObjectArrayConverter;
import com.alibaba.toolkit.util.typeconvert.converters.ObjectConverter;
import com.alibaba.toolkit.util.typeconvert.converters.ShortConverter;
import com.alibaba.toolkit.util.typeconvert.converters.SqlDateConverter;
import com.alibaba.toolkit.util.typeconvert.converters.SqlTimeConverter;
import com.alibaba.toolkit.util.typeconvert.converters.SqlTimestampConverter;
import com.alibaba.toolkit.util.typeconvert.converters.StringConverter;

import java.math.BigDecimal;
import java.math.BigInteger;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/**
 * <code>Converter</code>�Ĺ�����.
 *
 * @version $Id: ConvertManager.java,v 1.1 2003/07/03 07:26:36 baobao Exp $
 * @author Michael Zhou
 */
public class ConvertManager {
    private static final Object NO_DEFAULT_VALUE = new Object();
    private Map                 registry = Collections.synchronizedMap(new HashMap());
    private Map                 aliases  = Collections.synchronizedMap(new HashMap());

    /**
     * ����һ��ת����.
     */
    public ConvertManager() {
        register(BigDecimal.class, new BigDecimalConverter());
        register(BigInteger.class, new BigIntegerConverter());
        register(Boolean.class, new BooleanConverter());
        register(Byte.class, new ByteConverter());
        register(Character.class, new CharacterConverter());
        register(Double.class, new DoubleConverter());
        register(Float.class, new FloatConverter());
        register(Integer.class, new IntegerConverter());
        register(Long.class, new LongConverter());
        register(Short.class, new ShortConverter());
        register(Date.class, new SqlDateConverter());
        register(Time.class, new SqlTimeConverter());
        register(Timestamp.class, new SqlTimestampConverter());
        register(String.class, new StringConverter());
        register(Object[].class, new ObjectArrayConverter());
        register(Object.class, new ObjectConverter());
        register(Enum.class, new EnumConverter());
        register(FlagSet.class, new FlagSetConverter());


        // �ǼǱ���.
        registerAlias("string", String.class);
        registerAlias("enum", Enum.class);
    }

    /**
     * ��¼һ��ת����.
     *
     * @param type      ת������Ŀ������
     * @param converter ת��������
     */
    public void register(Class type, Converter converter) {
        synchronized (registry) {
            if (Boolean.class.equals(type) || Boolean.TYPE.equals(type)) {
                internalRegister(Boolean.class, converter);
                internalRegister(Boolean.TYPE, converter);
            } else if (Byte.class.equals(type) || Byte.TYPE.equals(type)) {
                internalRegister(Byte.class, converter);
                internalRegister(Byte.TYPE, converter);
            } else if (Character.class.equals(type) || Character.TYPE.equals(type)) {
                internalRegister(Character.class, converter);
                internalRegister(Character.TYPE, converter);
            } else if (Double.class.equals(type) || Double.TYPE.equals(type)) {
                internalRegister(Double.class, converter);
                internalRegister(Double.TYPE, converter);
            } else if (Float.class.equals(type) || Float.TYPE.equals(type)) {
                internalRegister(Float.class, converter);
                internalRegister(Float.TYPE, converter);
            } else if (Integer.class.equals(type) || Integer.TYPE.equals(type)) {
                internalRegister(Integer.class, converter);
                internalRegister(Integer.TYPE, converter);
            } else if (Long.class.equals(type) || Long.TYPE.equals(type)) {
                internalRegister(Long.class, converter);
                internalRegister(Long.TYPE, converter);
            } else if (Short.class.equals(type) || Short.TYPE.equals(type)) {
                internalRegister(Short.class, converter);
                internalRegister(Short.TYPE, converter);
            } else {
                internalRegister(type, converter);
            }
        }
    }

    /**
     * �ǼǱ���.
     *
     * @param alias   ����
     * @param type    Ŀ������
     */
    public void registerAlias(String alias, Class type) {
        synchronized (aliases) {
            if ((alias != null) && (type != null) && !aliases.containsKey(alias)) {
                aliases.put(alias, type);
            }
        }
    }

    /**
     * �ڲ�����: �Ǽ�һ��ת����.  �����primitive����, ��ͬ��.
     *
     * @param type      ת������Ŀ������
     * @param converter ת��������
     */
    private void internalRegister(Class type, Converter converter) {
        LinkedList converters = (LinkedList) registry.get(type);

        if (converters == null) {
            converters = new LinkedList();
            registry.put(type, converters);
        }

        if (!converters.contains(converter)) {
            converters.addFirst(converter);
            registerAlias(type.getName(), type);
        }
    }

    /**
     * ��ָ��ֵת����<code>boolean</code>����.
     *
     * @param value   Ҫת����ֵ
     *
     * @return ת�����ֵ
     */
    public boolean asBoolean(Object value) {
        return ((Boolean) asType(Boolean.class, value)).booleanValue();
    }

    /**
     * ��ָ��ֵת����<code>boolean</code>����.
     *
     * @param value         Ҫת����ֵ
     * @param defaultValue  Ĭ��ֵ
     *
     * @return ת�����ֵ
     */
    public boolean asBoolean(Object value, boolean defaultValue) {
        return ((Boolean) asType(Boolean.class, value, new Boolean(defaultValue))).booleanValue();
    }

    /**
     * ��ָ��ֵת����<code>byte</code>����.
     *
     * @param value   Ҫת����ֵ
     *
     * @return ת�����ֵ
     */
    public byte asByte(Object value) {
        return ((Byte) asType(Byte.class, value)).byteValue();
    }

    /**
     * ��ָ��ֵת����<code>byte</code>����.
     *
     * @param value         Ҫת����ֵ
     * @param defaultValue  Ĭ��ֵ
     *
     * @return ת�����ֵ
     */
    public byte asByte(Object value, byte defaultValue) {
        return ((Byte) asType(Byte.class, value, new Byte(defaultValue))).byteValue();
    }

    /**
     * ��ָ��ֵת����<code>char</code>����.
     *
     * @param value   Ҫת����ֵ
     *
     * @return ת�����ֵ
     */
    public char asChar(Object value) {
        return ((Character) asType(Character.class, value)).charValue();
    }

    /**
     * ��ָ��ֵת����<code>char</code>����.
     *
     * @param value         Ҫת����ֵ
     * @param defaultValue  Ĭ��ֵ
     *
     * @return ת�����ֵ
     */
    public char asChar(Object value, char defaultValue) {
        return ((Character) asType(Character.class, value, new Character(defaultValue))).charValue();
    }

    /**
     * ��ָ��ֵת����<code>double</code>����.
     *
     * @param value   Ҫת����ֵ
     *
     * @return ת�����ֵ
     */
    public double asDouble(Object value) {
        return ((Double) asType(Double.class, value)).doubleValue();
    }

    /**
     * ��ָ��ֵת����<code>double</code>����.
     *
     * @param value         Ҫת����ֵ
     * @param defaultValue  Ĭ��ֵ
     *
     * @return ת�����ֵ
     */
    public double asDouble(Object value, double defaultValue) {
        return ((Double) asType(Double.class, value, new Double(defaultValue))).doubleValue();
    }

    /**
     * ��ָ��ֵת����<code>float</code>����.
     *
     * @param value   Ҫת����ֵ
     *
     * @return ת�����ֵ
     */
    public float asFloat(Object value) {
        return ((Float) asType(Float.class, value)).floatValue();
    }

    /**
     * ��ָ��ֵת����<code>float</code>����.
     *
     * @param value         Ҫת����ֵ
     * @param defaultValue  Ĭ��ֵ
     *
     * @return ת�����ֵ
     */
    public float asFloat(Object value, float defaultValue) {
        return ((Float) asType(Float.class, value, new Float(defaultValue))).floatValue();
    }

    /**
     * ��ָ��ֵת����<code>int</code>����.
     *
     * @param value   Ҫת����ֵ
     *
     * @return ת�����ֵ
     */
    public int asInt(Object value) {
        return ((Integer) asType(Integer.class, value)).intValue();
    }

    /**
     * ��ָ��ֵת����<code>int</code>����.
     *
     * @param value         Ҫת����ֵ
     * @param defaultValue  Ĭ��ֵ
     *
     * @return ת�����ֵ
     */
    public int asInt(Object value, int defaultValue) {
        return ((Integer) asType(Integer.class, value, new Integer(defaultValue))).intValue();
    }

    /**
     * ��ָ��ֵת����<code>long</code>����.
     *
     * @param value   Ҫת����ֵ
     *
     * @return ת�����ֵ
     */
    public long asLong(Object value) {
        return ((Long) asType(Long.class, value)).longValue();
    }

    /**
     * ��ָ��ֵת����<code>long</code>����.
     *
     * @param value         Ҫת����ֵ
     * @param defaultValue  Ĭ��ֵ
     *
     * @return ת�����ֵ
     */
    public long asLong(Object value, long defaultValue) {
        return ((Long) asType(Long.class, value, new Long(defaultValue))).longValue();
    }

    /**
     * ��ָ��ֵת����<code>short</code>����.
     *
     * @param value   Ҫת����ֵ
     *
     * @return ת�����ֵ
     */
    public short asShort(Object value) {
        return ((Short) asType(Short.class, value)).shortValue();
    }

    /**
     * ��ָ��ֵת����<code>short</code>����.
     *
     * @param value         Ҫת����ֵ
     * @param defaultValue  Ĭ��ֵ
     *
     * @return ת�����ֵ
     */
    public short asShort(Object value, short defaultValue) {
        return ((Short) asType(Short.class, value, new Short(defaultValue))).shortValue();
    }

    /**
     * ��ָ��ֵת����<code>String</code>����.
     *
     * @param value   Ҫת����ֵ
     *
     * @return ת�����ֵ
     */
    public String asString(Object value) {
        return (String) asType(String.class, value);
    }

    /**
     * ��ָ��ֵת����<code>String</code>����.
     *
     * @param value         Ҫת����ֵ
     * @param defaultValue  Ĭ��ֵ
     *
     * @return ת�����ֵ
     */
    public String asString(Object value, String defaultValue) {
        return (String) asType(String.class, value, defaultValue);
    }

    /**
     * ��ָ��ֵת����ָ������.
     *
     * @param targetType Ҫת����Ŀ������
     * @param value      Ҫת����ֵ
     *
     * @return ת�����ֵ
     */
    public Object asType(Object targetType, Object value) {
        return asType(targetType, value, NO_DEFAULT_VALUE);
    }

    /**
     * ��ָ��ֵת����ָ������.
     *
     * @param targetType   Ҫת����Ŀ������
     * @param value        Ҫת����ֵ
     * @param defaultValue Ĭ��ֵ
     *
     * @return ת�����ֵ
     */
    public Object asType(Object targetType, Object value, Object defaultValue) {
        try {
            return new ChainImpl(this, getTargetType(targetType)).convert(value);
        } catch (ConvertFailedException e) {
            if (e.isDefaultValueSet()) {
                return (defaultValue == NO_DEFAULT_VALUE) ? e.getDefaultValue()
                                                          : defaultValue;
            }

            throw e;
        }
    }

    /**
     * ��ָ��ֵת����ָ������.  ��ʹת��ʧ��, Ҳ���᷵��Ĭ��ֵ, ���׳�һ���쳣.
     *
     * @param targetType   Ҫת����Ŀ������
     * @param value        Ҫת����ֵ
     *
     * @return ת�����ֵ
     */
    public Object asTypeWithoutDefaultValue(Object targetType, Object value) {
        return new ChainImpl(this, getTargetType(targetType)).convert(value);
    }

    /**
     * ȡ��target type�����.
     *
     * @param targetType  target type������
     *
     * @return target type�����
     */
    private Class getTargetType(Object targetType) {
        if (targetType instanceof Class) {
            return (Class) targetType;
        }

        if (targetType instanceof String) {
            Class type = (Class) aliases.get(targetType);

            if (type != null) {
                return type;
            }
        }

        throw new IllegalArgumentException("Invalid targetType " + targetType);
    }

    /**
     * ת������. ���γ���: Convertible.getConverter(targetType), targetType��Ӧ��ת����,
     * targetType�Ļ���(������Object��)��Ӧ��ת����, targetType�Ľӿڶ�Ӧ��ת����, Object������Ӧ��ת����.
     */
    private class ChainImpl implements ConvertChain {
        private static final int     STATE_START              = 0;
        private static final int     STATE_TARGET_TYPE        = STATE_START + 1;
        private static final int     STATE_BASE_TYPE          = STATE_TARGET_TYPE + 1;
        private static final int     STATE_INTERFACE          = STATE_BASE_TYPE + 1;
        private static final int     STATE_OBJECT             = STATE_INTERFACE + 1;
        private static final int     STATE_END                = STATE_OBJECT + 1;
        private final ConvertManager manager;
        private final TypeInfo       targetTypeInfo;
        private int                  state                    = STATE_START;
        private Convertible          previousConvertibleValue;
        private Iterator             converterIterator;
        private Iterator             superclassIterator;
        private Iterator             interfaceIterator;

        /**
         * ����ת����.
         *
         * @param manager            ����������<code>ConvertManager</code>
         * @param targetType         ת����Ŀ������
         */
        ChainImpl(ConvertManager manager, Class targetType) {
            this.manager        = manager;
            this.targetTypeInfo = TypeInfo.getTypeInfo(targetType);
        }

        /**
         * ȡ�ô���������<code>ConvertManager</code>.
         *
         * @return ����������<code>ConvertManager</code>
         */
        public ConvertManager getConvertManager() {
            return manager;
        }

        /**
         * ȡ��ת����Ŀ������.
         *
         * @return Ŀ������
         */
        public Class getTargetType() {
            return targetTypeInfo.getType();
        }

        /**
         * �����ƽ������е���һ��ת����, ת��ָ����ֵ��ָ��������.
         *
         * @param value       Ҫת����ֵ
         *
         * @return ת�����ֵ
         */
        public Object convert(Object value) {
            Class targetType = targetTypeInfo.getType();

            // ���ȴ���ʵ��Convertible�ӿڵ�valueֵ,
            // ����ֹ��ͬһ��convertible value����������converter
            if (value instanceof Convertible && !value.equals(previousConvertibleValue)) {
                Converter converter = ((Convertible) value).getConverter(targetType);

                if (converter != null) {
                    previousConvertibleValue = (Convertible) value;
                    return converter.convert(value, this);
                }
            }

            // ��ʼ״̬
            if (state == STATE_START) {
                state++; // ����STATE_TARGET_TYPE״̬
                converterIterator = getConverterIterator(targetType);
            }

            // ����targetType��Ӧ��ת����
            if (state == STATE_TARGET_TYPE) {
                if (!hasNext(converterIterator)) {
                    state++; // ����STATE_BASE_TYPE״̬
                    superclassIterator = targetTypeInfo.getSuperclasses().iterator();
                    converterIterator  = getSuperclassConverterIterator();
                }
            }

            // ����targetType�Ļ����Ӧ��ת����
            if (state == STATE_BASE_TYPE) {
                if (!hasNext(converterIterator)) {
                    converterIterator = getSuperclassConverterIterator();

                    if (!hasNext(converterIterator)) {
                        state++; // ����STATE_INTERFACE״̬
                        interfaceIterator = targetTypeInfo.getInterfaces().iterator();
                        converterIterator = getInterfaceConverterIterator();
                    }
                }
            }

            // ����targetType�Ľӿڶ�Ӧ��ת����
            if (state == STATE_INTERFACE) {
                if (!hasNext(converterIterator)) {
                    converterIterator = getInterfaceConverterIterator();

                    if (!hasNext(converterIterator)) {
                        state++; // ����STATE_OBJECT״̬

                        if (!Object.class.equals(targetTypeInfo.getType())) {
                            converterIterator = getConverterIterator(Object.class);
                        }
                    }
                }
            }

            // ����Object���Ӧ��ת����
            if (state == STATE_OBJECT) {
                if (!hasNext(converterIterator)) {
                    state++; // ����STATE_END״̬
                }
            }

            // ��ʼת��
            if (state != STATE_END) {
                Converter converter = (Converter) converterIterator.next();

                return converter.convert(value, this);
            }

            // ����Ҳ���converter, ��ʧ��
            throw new ConvertFailedException();
        }

        /**
         * ȡ��ָ�����͵�����ת����.
         *
         * @param type  Ҫ����ת����������
         *
         * @return ת������<code>Iterator</code>, ���������, �򷵻�<code>null</code>
         */
        private Iterator getConverterIterator(Class type) {
            LinkedList converters = (LinkedList) registry.get(type);

            if ((converters != null) && (converters.size() > 0)) {
                return converters.iterator();
            }

            return null;
        }

        /**
         * ȡ��targetType����(������Object��)��ת����.
         *
         * @return �����ת������<code>Iterator</code>, ���������, �򷵻�<code>null</code>
         */
        private Iterator getSuperclassConverterIterator() {
            Iterator iterator = null;

            while (superclassIterator.hasNext()) {
                Class superclass = (Class) superclassIterator.next();

                if (!superclass.equals(Object.class)) {
                    iterator = getConverterIterator(superclass);

                    if (hasNext(iterator)) {
                        return iterator;
                    }
                }
            }

            return null;
        }

        /**
         * ȡ��targetType�ӿڵ�ת����.
         *
         * @return �ӿڵ�ת������<code>Iterator</code>, ���������, �򷵻�<code>null</code>
         */
        private Iterator getInterfaceConverterIterator() {
            Iterator iterator = null;

            while (interfaceIterator.hasNext()) {
                iterator = getConverterIterator((Class) interfaceIterator.next());

                if (hasNext(iterator)) {
                    return iterator;
                }
            }

            return null;
        }

        /**
         * ���iterator�Ƿ������һ��Ԫ��.
         *
         * @param iterator Ҫ����iterator
         *
         * @return ���iteratorΪ<code>null</code>, ��iterator�Ѿ��ߵ���, �򷵻�<code>false</code>
         */
        private boolean hasNext(Iterator iterator) {
            return (iterator != null) && iterator.hasNext();
        }
    }
}
