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

package com.alibaba.toolkit.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import java.text.MessageFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

/**
 * ���ַ����йص�С����.
 *
 * @version $Id: StringUtil.java,v 1.1 2003/07/03 07:26:15 baobao Exp $
 * @author Michael Zhou
 */
public class StringUtil {
    private static final OutputStream DUMMY_OUTPUT_STREAM = new ByteArrayOutputStream(0);
    private static final String       SYSTEM_CHARSET =
            new OutputStreamWriter(DUMMY_OUTPUT_STREAM).getEncoding();

    /* ============================================================================ */
    /* �������й�resource bundle�ķ���                                              */
    /* ============================================================================ */

    /**
     * ʹ��<code>MessageFormat</code>��ʽ���ַ���.
     *
     * @param bundle  resource bundle
     * @param key     Ҫ���ҵļ�
     * @param params  ������
     *
     * @return key��Ӧ���ַ���
     *
     * @throws NullPointerException      resource keyΪ<code>null</code>��resource
     *         bundleΪ<code>null</code>
     * @throws MissingResourceException  ָ��resource keyδ�ҵ�
     */
    public static String getMessage(ResourceBundle bundle, Object key, Object[] params) {
        String pattern = bundle.getString(key.toString());

        if ((params == null) || (params.length == 0)) {
            return pattern;
        }

        return MessageFormat.format(pattern, params);
    }

    /**
     * ʹ��<code>MessageFormat</code>��ʽ���ַ���.
     *
     * @param bundle  resource bundle
     * @param key     Ҫ���ҵļ�
     * @param param1  ����1
     *
     * @return key��Ӧ���ַ���
     *
     * @throws NullPointerException      resource keyΪ<code>null</code>��resource
     *         bundleΪ<code>null</code>
     * @throws MissingResourceException  ָ��resource keyδ�ҵ�
     */
    public static String getMessage(ResourceBundle bundle, Object key, Object param1) {
        return getMessage(bundle, key, new Object[] {
            param1
        });
    }

    /**
     * ʹ��<code>MessageFormat</code>��ʽ���ַ���.
     *
     * @param bundle  resource bundle
     * @param key     Ҫ���ҵļ�
     * @param param1  ����1
     * @param param2  ����2
     *
     * @return key��Ӧ���ַ���
     *
     * @throws NullPointerException      resource keyΪ<code>null</code>��resource
     *         bundleΪ<code>null</code>
     * @throws MissingResourceException  ָ��resource keyδ�ҵ�
     */
    public static String getMessage(ResourceBundle bundle, Object key, Object param1, Object param2) {
        return getMessage(bundle, key, new Object[] {
            param1,
            param2
        });
    }

    /**
     * ʹ��<code>MessageFormat</code>��ʽ���ַ���.
     *
     * @param bundle  resource bundle
     * @param key     Ҫ���ҵļ�
     * @param param1  ����1
     * @param param2  ����2
     * @param param3  ����3
     *
     * @return key��Ӧ���ַ���
     *
     * @throws NullPointerException      resource keyΪ<code>null</code>��resource
     *         bundleΪ<code>null</code>
     * @throws MissingResourceException  ָ��resource keyδ�ҵ�
     */
    public static String getMessage(ResourceBundle bundle, Object key, Object param1, Object param2,
                                    Object param3) {
        return getMessage(bundle, key, new Object[] {
            param1,
            param2,
            param3
        });
    }

    /**
     * ʹ��<code>MessageFormat</code>��ʽ���ַ���.
     *
     * @param bundle  resource bundle
     * @param key     Ҫ���ҵļ�
     * @param param1  ����1
     * @param param2  ����2
     * @param param3  ����3
     * @param param4  ����4
     *
     * @return key��Ӧ���ַ���
     *
     * @throws NullPointerException      resource keyΪ<code>null</code>��resource
     *         bundleΪ<code>null</code>
     * @throws MissingResourceException  ָ��resource keyδ�ҵ�
     */
    public static String getMessage(ResourceBundle bundle, Object key, Object param1, Object param2,
                                    Object param3, Object param4) {
        return getMessage(bundle, key,
                          new Object[] {
            param1,
            param2,
            param3,
            param4
        });
    }

    /**
     * ʹ��<code>MessageFormat</code>��ʽ���ַ���.
     *
     * @param bundle  resource bundle
     * @param key     Ҫ���ҵļ�
     * @param param1  ����1
     * @param param2  ����2
     * @param param3  ����3
     * @param param4  ����4
     * @param param5  ����5
     *
     * @return key��Ӧ���ַ���
     *
     * @throws NullPointerException      resource keyΪ<code>null</code>��resource
     *         bundleΪ<code>null</code>
     * @throws MissingResourceException  ָ��resource keyδ�ҵ�
     */
    public static String getMessage(ResourceBundle bundle, Object key, Object param1, Object param2,
                                    Object param3, Object param4, Object param5) {
        return getMessage(bundle, key,
                          new Object[] {
            param1,
            param2,
            param3,
            param4,
            param5
        });
    }

    /**
     * ����ַ����Ƿ�Ϊ<code>null</code>����ַ���.
     *
     * @param str Ҫ�����ַ���
     *
     * @return ���Ϊ��, �򷵻�<code>true</code>
     */
    public static boolean isEmpty(String str) {
        return (str == null) || (str.length() == 0);
    }

    /**
     * ȡ��ϵͳ�ַ�������.
     *
     * @return ϵͳ�ַ�������
     */
    public static String getSystemCharset() {
        return SYSTEM_CHARSET;
    }

    /**
     * ȡ��������ַ�������, ���ָ���ַ���������, ���׳�<code>UnsupportedEncodingException</code>.
     *
     * @param charset �ַ�������
     *
     * @return ������ַ�������
     *
     * @throws UnsupportedEncodingException ���ָ���ַ���������
     */
    public static String getCanonicalCharset(String charset)
            throws UnsupportedEncodingException {
        return new OutputStreamWriter(DUMMY_OUTPUT_STREAM, charset).getEncoding();
    }

    /**
     * ȡ��������ַ�������, ���ָ���ַ���������, �򷵻�<code>null</code>.
     *
     * @param charset �ַ�������
     *
     * @return ������ַ�������, ���ָ���ַ���������, �򷵻�<code>null</code>
     */
    public static String getCanonicalCharsetQuiet(String charset) {
        try {
            return getCanonicalCharset(charset);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * չ���ַ���, ��"$��"��"��"�еı���ת����<code>System.getProperties()</code>�е�ֵ.
     *
     * @param value       Ҫת����ֵ
     *
     * @return չ�����ֵ
     */
    public static String expendProperty(String value) {
        return expendProperty(value, System.getProperties());
    }

    /**
     * չ���ַ���, ��"$��"��"��"�еı���ת����ָ��properties�е�ֵ.
     *
     * @param value       Ҫת����ֵ
     * @param properties  ���õı���
     *
     * @return չ�����ֵ
     */
    public static String expendProperty(String value, Map properties) {
        if (value == null) {
            return null;
        }

        int i = value.indexOf("${", 0);

        if (i == -1) {
            return value;
        }

        StringBuffer buffer = new StringBuffer(value.length());
        int          length = value.length();
        int          j      = 0;

        while (i < length) {
            if (i > j) {
                buffer.append(value.substring(j, i));
                j = i;
            }

            int k;

            for (k = i + 2; (k < length) && (value.charAt(k) != '}'); k++) {
            }

            if (k == length) {
                buffer.append(value.substring(i, k));
                break;
            }

            String propertyName = value.substring(i + 2, k);

            if (propertyName.equals("/")) {
                buffer.append(File.separatorChar);
            } else {
                Object propertyValue = properties.get(propertyName);

                if (propertyValue != null) {
                    buffer.append(propertyValue);
                } else {
                    buffer.append("${").append(propertyName).append("}");
                }
            }

            j = k + 1;
            i = value.indexOf("${", j);

            if (i == -1) {
                if (j < length) {
                    buffer.append(value.substring(j, length));
                }

                break;
            }
        }

        return buffer.toString();
    }

    // add by roy

    /**
     * ���ݷָ���ָ��ַ����� Return List,after split Split a String by a splitter(such as ",","hai",...)
     *
     * @param sStr       ��Ҫ���ָ���ַ�����
     * @param sSplitter  �ָ����
     *
     * @return һ�����зָ�õ��ַ�����List������ָ�ʧ�ܽ�����null,����ַ�����û�а���ָ���ķָ����
     *         ������ֻ��һ��Ԫ�ص��ַ������飬���Ԫ�ؾ��Ǹ��ַ��������������ַ���ֻ���зָ����������null��
     */
    public static List splitStr(String sStr, String sSplitter) {
        if ((sStr == null) || (sStr.length() <= 0) || (sSplitter == null)
                || (sSplitter.length() <= 0)) {
            return null;
        }

        StringTokenizer st     = new StringTokenizer(sStr, sSplitter);
        List            result = new ArrayList();

        while (st.hasMoreTokens()) {
            result.add(st.nextToken().trim());
        }

        return result;
    }

    // add by roy
    /**
     * ����һ��String�Ƿ���һ�����֣����磺7897897->true,789t67 -> false����
     * ע�⣺�����һ�����������᷵��false. Return true if the string represents a number
     *
     * @param str  ����У����ַ�����
     *
     * @return �����һ����׼�����֣�������true�����򷵻�false��
     */
    public static boolean isNum(String str) {
        if ((str == null) || (str.length() <= 0)) {
            return false;
        }

        char[] ch = str.toCharArray();

        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(ch[i])) {
                return false;
            }
        }

        return true;
    }

}
