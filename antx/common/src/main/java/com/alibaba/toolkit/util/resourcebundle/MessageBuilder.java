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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * ͨ����Դ��������Ϣ�Ĺ�����, ֧������ԭ������, ����ʹ��.
 *
 * <p>
 * ʹ�÷���:
 * <pre>
 *   String message = new MessageBuilder(bundle, key)
 *                   .append(param1)
 *                   .append(param2)
 *                   .toString();
 * </pre>
 * </p>
 *
 * <p>
 * �ڹ������ʱ, �����ṩһ��<code>quiet</code>����.  ����˲���Ϊ<code>true</code>, ����resource bundle�Ҳ���,
 * �򲻻��׳�<code>MissingResourceException</code>, ���Ƿ���һ��Ĭ�ϵ��ַ���.
 * </p>
 *
 * @version $Id: MessageBuilder.java,v 1.1 2003/07/03 07:26:35 baobao Exp $
 * @author Michael Zhou
 */
public class MessageBuilder {
    protected final List           params = new ArrayList(5);
    protected final ResourceBundle bundle;
    protected final Object         key;

    /**
     * ����һ��<code>MessageBuilder</code>.
     *
     * @param bundleName  ��Դ��
     * @param key         ��ֵ
     *
     * @throws MissingResourceException  ָ��bundleδ�ҵ�, �򴴽�bundle����
     */
    public MessageBuilder(String bundleName, Object key) {
        this(ResourceBundleFactory.getBundle(bundleName), key);
    }

    /**
     * ����һ��<code>MessageBuilder</code>.
     *
     * @param bundle  ��Դ��
     * @param key     ��ֵ
     */
    public MessageBuilder(ResourceBundle bundle, Object key) {
        this.bundle = bundle;
        this.key    = key;
    }

    /**
     * ����һ������.
     *
     * @param param ����
     *
     * @return <code>MessageBuilder</code>����
     */
    public MessageBuilder append(Object param) {
        params.add(param);
        return this;
    }

    /**
     * ����һ������.
     *
     * @param param ����
     *
     * @return <code>MessageBuilder</code>����
     */
    public MessageBuilder append(boolean param) {
        params.add(new Boolean(param));
        return this;
    }

    /**
     * ����һ������.
     *
     * @param param ����
     *
     * @return <code>MessageBuilder</code>����
     */
    public MessageBuilder append(char param) {
        params.add(new Character(param));
        return this;
    }

    /**
     * ����һ������.
     *
     * @param param ����
     *
     * @return <code>MessageBuilder</code>����
     */
    public MessageBuilder append(double param) {
        params.add(new Double(param));
        return this;
    }

    /**
     * ����һ������.
     *
     * @param param ����
     *
     * @return <code>MessageBuilder</code>����
     */
    public MessageBuilder append(float param) {
        params.add(new Float(param));
        return this;
    }

    /**
     * ����һ������.
     *
     * @param param ����
     *
     * @return <code>MessageBuilder</code>����
     */
    public MessageBuilder append(int param) {
        params.add(new Integer(param));
        return this;
    }

    /**
     * ����һ������.
     *
     * @param param ����
     *
     * @return <code>MessageBuilder</code>����
     */
    public MessageBuilder append(long param) {
        params.add(new Long(param));
        return this;
    }

    /**
     * ���Ӷ������.
     *
     * @param params ������
     *
     * @return <code>MessageBuilder</code>����
     */
    public MessageBuilder append(Object[] params) {
        if (params != null) {
            this.params.addAll(Arrays.asList(params));
        }

        return this;
    }

    /**
     * ȡ����Ϣ�ַ���.
     *
     * @return ��Ϣ�ַ���
     */
    public String toString() {
        return getMessage();
    }

    /**
     * ����Դ����ȡ����Ϣ�ַ���.
     *
     * @return ��Ϣ�ַ���
     *
     * @throws MissingResourceException  ָ��resource keyδ�ҵ�
     */
    protected String getMessage() {
        return StringUtil.getMessage(bundle, key, params.toArray());
    }
}
