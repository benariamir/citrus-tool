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
package com.alibaba.toolkit.util.resourcebundle;

import com.alibaba.toolkit.util.StringUtil;

import java.text.MessageFormat;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * "��Դ��"�����˺͹���, ���Լ�������صĶ���.  ����ĳ�����Ҫ������ص���Դ, �����ַ���, ��ô��ĳ���Ϳ���װ��һ��resource bundle, ��ָ��������Ҫ��locale����.
 * Resource bundle�ᰴ��һ���Ĺ���ȡ�þ����ܽӽ���Ҫ����Դ.
 *
 * <p>
 * <code>ResourceBundle</code>�Ƕ�<code>java.util.ResourceBundle</code>����չ.
 * ���<code>ResourceBundle</code>�����˺�<code>java.util.ResourceBundle</code>��ͬ����Դ���Ҳ���, ��ʹ����factoryģʽ,
 * ʹ֮��������չ���µ���Դ��ʽ.
 * </p>
 *
 * <p>
 * ������������˳�����, ����language1/country1/variant1���û�ָ����locale, language2/country2/variant2��ϵͳĬ�ϵ�locale:
 *
 * <ul>
 * <li>
 * baseName + "_" + language1 + "_" + country1 + "_" + variant1
 * </li>
 * <li>
 * baseName + "_" + language1 + "_" + country1
 * </li>
 * <li>
 * baseName + "_" + language1
 * </li>
 * <li>
 * baseName + "_" + language2 + "_" + country2 + "_" + variant2
 * </li>
 * <li>
 * baseName + "_" + language2 + "_" + country2
 * </li>
 * <li>
 * baseName + "_" + language2
 * </li>
 * <li>
 * baseName
 * </li>
 * </ul>
 *
 * ����, ����ʱ�������һ��localeԪ��Ϊ�յ����, ����: ����new Locale("langauge1", "", ""),
 * ��baseName_language1_country1_variant1�Լ�baseName_language1_country1������. ���ȫ��localeԪ�ؾ�Ϊ��,
 * ��ֻ����baseName.
 * </p>
 *
 * @version $Id: ResourceBundle.java,v 1.1 2003/07/03 07:26:35 baobao Exp $
 * @author Michael Zhou
 */
public abstract class ResourceBundle extends java.util.ResourceBundle {
    /** ���bundle�Ļ�����. */
    private String baseName;

    /** ���bundle�����������. */
    private Locale locale;

    /**
     * ȡ�����bundle�Ļ�����.
     *
     * @return ������
     */
    public String getBaseName() {
        return baseName;
    }

    /**
     * ȡ�����bundle����ʵlocale, �������û�����<code>getBundle</code>ʱ�ṩ��locale����.
     *
     * @return ���bundle����ʵlocale
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * ȡ�����ڸ�ʽ��message�ַ�����<code>MessageBuilder</code>.
     *
     * @param key  resource bundle key
     *
     * @return <code>MessageBuilder</code>����
     */
    public MessageBuilder getMessageBuilder(Object key) {
        return new MessageBuilder(this, key);
    }

    /**
     * ʹ��<code>MessageFormat</code>��ʽ���ַ���.
     *
     * @param key     Ҫ���ҵļ�
     * @param params  ������
     *
     * @return key��Ӧ���ַ���
     *
     * @throws MissingResourceException  ָ��resource keyδ�ҵ�
     */
    public final String getMessage(Object key, Object[] params) {
        return StringUtil.getMessage(this, key, params);
    }

    /**
     * �ӵ�ǰresource bundle������һ����bundle��, ȡ�ú�ָ������Ӧ���ַ���.  ������ɹ�,
     * ������<code>MissingResourceException</code>�쳣.
     *
     * @param key  Ҫ���ҵļ�
     *
     * @return key��Ӧ���ַ���
     *
     * @throws MissingResourceException  ָ��resource keyδ�ҵ�
     */
    public final String getString(Object key) {
        return (String) getObject(key);
    }

    /**
     * �ӵ�ǰresource bundle������һ����bundle��, ȡ�ú�ָ������Ӧ���ַ���.  ������ɹ�,
     * ������<code>MissingResourceException</code>�쳣.
     *
     * @param key  Ҫ���ҵļ�
     *
     * @return key��Ӧ���ַ���
     *
     * @throws MissingResourceException  ָ��resource keyδ�ҵ�
     */
    public final String[] getStringArray(Object key) {
        return (String[]) getObject(key);
    }

    /**
     * �ӵ�ǰresource bundle������һ����bundle��, ȡ�ú�ָ������Ӧ��<code>Map</code>.  ������ɹ�,
     * ������<code>MissingResourceException</code>�쳣.
     *
     * @param key  Ҫ���ҵļ�
     *
     * @return key��Ӧ��<code>Map</code>
     *
     * @throws MissingResourceException  ָ��resource keyδ�ҵ�
     */
    public final Map getMap(Object key) {
        return (Map) getObject(key);
    }

    /**
     * �ӵ�ǰresource bundle������һ����bundle��, ȡ�ú�ָ������Ӧ��<code>List</code>.  ������ɹ�,
     * ������<code>MissingResourceException</code>�쳣.
     *
     * @param key  Ҫ���ҵļ�
     *
     * @return key��Ӧ��<code>List</code>
     *
     * @throws MissingResourceException  ָ��resource keyδ�ҵ�
     */
    public final List getList(Object key) {
        return (List) getObject(key);
    }

    /**
     * �ӵ�ǰresource bundle������һ����bundle��, ȡ�ú�ָ������Ӧ�Ķ���.  ������ɹ�,
     * ������<code>MissingResourceException</code>�쳣.
     *
     * @param key  Ҫ���ҵļ�
     *
     * @return key��Ӧ�Ķ���
     *
     * @throws MissingResourceException  ָ��resource keyδ�ҵ�
     */
    public final Object getObject(Object key) {
        return super.getObject(key.toString());
    }

    /**
     * �������bundle�Ļ�����.
     *
     * @param baseName ������
     */
    protected final void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    /**
     * ����bundle��locale.  ����û�����fr_FR, ���ҵ�����en_US, �����bundle��locale�������ó�en_US������fr_FR.
     *
     * @param baseName   bundle��������
     * @param bundleName bundle������, ����locale����չ
     */
    protected final void setLocale(String baseName, String bundleName) {
        if (baseName.length() == bundleName.length()) {
            locale = new Locale("", "");
        } else if (baseName.length() < bundleName.length()) {
            int    pos  = baseName.length();
            String temp = bundleName.substring(pos + 1);

            pos = temp.indexOf('_');

            if (pos == -1) {
                locale = new Locale(temp, "", "");
                return;
            }

            String language = temp.substring(0, pos);

            temp = temp.substring(pos + 1);
            pos  = temp.indexOf('_');

            if (pos == -1) {
                locale = new Locale(language, temp, "");
                return;
            }

            String country = temp.substring(0, pos);

            temp = temp.substring(pos + 1);

            locale = new Locale(language, country, temp);
        } else {
            // ����������bundle��
            throw new IllegalArgumentException(MessageFormat.format(
                                                       ResourceBundleConstant.RB_BASE_NAME_LONGER_THAN_BUNDLE_NAME,
                                                       new Object[] {
                baseName,
                bundleName
            }));
        }
    }

    /**
     * ���ø�bundle. ����ڵ�ǰbundle���Ҳ���ָ���Ķ���, �ͻᵽ��bundle��ȥ����.
     *
     * @param parent ��bundle
     */
    protected final void setParent(ResourceBundle parent) {
        this.parent = parent;
    }

    /**
     * ȡ�ø�bundle.
     *
     * @return ��bundle����, ���������, �򷵻�<code>null</code>
     */
    protected final java.util.ResourceBundle getParent() {
        return parent;
    }
}
