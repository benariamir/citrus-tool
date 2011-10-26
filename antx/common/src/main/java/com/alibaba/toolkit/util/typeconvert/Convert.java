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

package com.alibaba.toolkit.util.typeconvert;

/**
 * ת���������͵Ĺ�����, ֧������primitive���ͺ������ת��.
 *
 * @version $Id: Convert.java,v 1.1 2003/07/03 07:26:36 baobao Exp $
 * @author Michael Zhou
 */
public class Convert {
    private static final ConvertManager defaultConvertManager = new ConvertManager();

    /**
     * ��ָ��ֵת����<code>boolean</code>����.
     *
     * @param value   Ҫת����ֵ
     *
     * @return ת�����ֵ
     */
    public static boolean asBoolean(Object value) {
        return defaultConvertManager.asBoolean(value);
    }

    /**
     * ��ָ��ֵת����<code>boolean</code>����.
     *
     * @param value         Ҫת����ֵ
     * @param defaultValue  Ĭ��ֵ
     *
     * @return ת�����ֵ
     */
    public static boolean asBoolean(Object value, boolean defaultValue) {
        return defaultConvertManager.asBoolean(value, defaultValue);
    }

    /**
     * ��ָ��ֵת����<code>byte</code>����.
     *
     * @param value   Ҫת����ֵ
     *
     * @return ת�����ֵ
     */
    public static byte asByte(Object value) {
        return defaultConvertManager.asByte(value);
    }

    /**
     * ��ָ��ֵת����<code>byte</code>����.
     *
     * @param value         Ҫת����ֵ
     * @param defaultValue  Ĭ��ֵ
     *
     * @return ת�����ֵ
     */
    public static byte asByte(Object value, byte defaultValue) {
        return defaultConvertManager.asByte(value, defaultValue);
    }

    /**
     * ��ָ��ֵת����<code>char</code>����.
     *
     * @param value   Ҫת����ֵ
     *
     * @return ת�����ֵ
     */
    public static char asChar(Object value) {
        return defaultConvertManager.asChar(value);
    }

    /**
     * ��ָ��ֵת����<code>char</code>����.
     *
     * @param value         Ҫת����ֵ
     * @param defaultValue  Ĭ��ֵ
     *
     * @return ת�����ֵ
     */
    public static char asChar(Object value, char defaultValue) {
        return defaultConvertManager.asChar(value, defaultValue);
    }

    /**
     * ��ָ��ֵת����<code>double</code>����.
     *
     * @param value   Ҫת����ֵ
     *
     * @return ת�����ֵ
     */
    public static double asDouble(Object value) {
        return defaultConvertManager.asDouble(value);
    }

    /**
     * ��ָ��ֵת����<code>double</code>����.
     *
     * @param value         Ҫת����ֵ
     * @param defaultValue  Ĭ��ֵ
     *
     * @return ת�����ֵ
     */
    public static double asDouble(Object value, double defaultValue) {
        return defaultConvertManager.asDouble(value, defaultValue);
    }

    /**
     * ��ָ��ֵת����<code>float</code>����.
     *
     * @param value   Ҫת����ֵ
     *
     * @return ת�����ֵ
     */
    public static float asFloat(Object value) {
        return defaultConvertManager.asFloat(value);
    }

    /**
     * ��ָ��ֵת����<code>float</code>����.
     *
     * @param value         Ҫת����ֵ
     * @param defaultValue  Ĭ��ֵ
     *
     * @return ת�����ֵ
     */
    public static float asFloat(Object value, float defaultValue) {
        return defaultConvertManager.asFloat(value, defaultValue);
    }

    /**
     * ��ָ��ֵת����<code>int</code>����.
     *
     * @param value   Ҫת����ֵ
     *
     * @return ת�����ֵ
     */
    public static int asInt(Object value) {
        return defaultConvertManager.asInt(value);
    }

    /**
     * ��ָ��ֵת����<code>int</code>����.
     *
     * @param value         Ҫת����ֵ
     * @param defaultValue  Ĭ��ֵ
     *
     * @return ת�����ֵ
     */
    public static int asInt(Object value, int defaultValue) {
        return defaultConvertManager.asInt(value, defaultValue);
    }

    /**
     * ��ָ��ֵת����<code>long</code>����.
     *
     * @param value   Ҫת����ֵ
     *
     * @return ת�����ֵ
     */
    public static long asLong(Object value) {
        return defaultConvertManager.asLong(value);
    }

    /**
     * ��ָ��ֵת����<code>long</code>����.
     *
     * @param value         Ҫת����ֵ
     * @param defaultValue  Ĭ��ֵ
     *
     * @return ת�����ֵ
     */
    public static long asLong(Object value, long defaultValue) {
        return defaultConvertManager.asLong(value, defaultValue);
    }

    /**
     * ��ָ��ֵת����<code>short</code>����.
     *
     * @param value   Ҫת����ֵ
     *
     * @return ת�����ֵ
     */
    public static short asShort(Object value) {
        return defaultConvertManager.asShort(value);
    }

    /**
     * ��ָ��ֵת����<code>short</code>����.
     *
     * @param value         Ҫת����ֵ
     * @param defaultValue  Ĭ��ֵ
     *
     * @return ת�����ֵ
     */
    public static short asShort(Object value, short defaultValue) {
        return defaultConvertManager.asShort(value, defaultValue);
    }

    /**
     * ��ָ��ֵת����<code>String</code>����.
     *
     * @param value   Ҫת����ֵ
     *
     * @return ת�����ֵ
     */
    public static String asString(Object value) {
        return defaultConvertManager.asString(value);
    }

    /**
     * ��ָ��ֵת����<code>String</code>����.
     *
     * @param value         Ҫת����ֵ
     * @param defaultValue  Ĭ��ֵ
     *
     * @return ת�����ֵ
     */
    public static String asString(Object value, String defaultValue) {
        return defaultConvertManager.asString(value, defaultValue);
    }

    /**
     * ��ָ��ֵת����ָ������.
     *
     * @param targetType Ҫת����Ŀ������
     * @param value      Ҫת����ֵ
     *
     * @return ת�����ֵ
     */
    public static Object asType(Object targetType, Object value) {
        return defaultConvertManager.asType(targetType, value);
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
    public static Object asType(Object targetType, Object value, Object defaultValue) {
        return defaultConvertManager.asType(targetType, value, defaultValue);
    }
}
