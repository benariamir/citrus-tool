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

package com.alibaba.toolkit.util.configuration.digester;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.Rule;

import org.xml.sax.Attributes;

/**
 * ��������صĹ���İ�װ��.
 *
 * @version $Id: ContextSensitiveRule.java,v 1.1 2003/07/03 07:26:16 baobao Exp $
 * @author Michael Zhou
 */
public class ContextSensitiveRule extends Rule {
    protected Rule   rule;
    protected String context;

    /**
     * ��װָ���Ĺ���, ��ָ���������Ķ�Ӧ.
     *
     * @param rule     ����
     * @param context  �������ַ���
     */
    public ContextSensitiveRule(Rule rule, String context) {
        this.rule    = rule;
        this.context = context;
    }

    /**
     * �жϵ�ǰ�������Ƿ�ƥ��.
     *
     * @param context  ��ƥ���������
     *
     * @return ���ƥ��, �򷵻�<code>true</code>
     */
    public boolean isContextMatched(String context) {
        return this.context.equals(context);
    }

    /**
     * ȡ��digester.
     *
     * @return digester
     */
    public Digester getDigester() {
        return rule.getDigester();
    }

    /**
     * ����digester.
     *
     * @param digester digester
     */
    public void setDigester(Digester digester) {
        rule.setDigester(digester);
    }

    /**
     * ȡ�����ֿռ�.
     *
     * @return ���ֿռ�
     */
    public String getNamespaceURI() {
        return rule.getNamespaceURI();
    }

    /**
     * �������ֿռ�.
     *
     * @param namespaceURI ���ֿռ�
     */
    public void setNamespaceURI(String namespaceURI) {
        rule.setNamespaceURI(namespaceURI);
    }

    /**
     * ƥ�俪ʼ.
     *
     * @param namespace  ���ֿռ�
     * @param name       XMLԪ��local��
     * @param attributes XML����
     *
     * @throws Exception ���ʧ��
     */
    public void begin(String namespace, String name, Attributes attributes)
            throws Exception {
        rule.begin(namespace, name, attributes);
    }

    /**
     * ƥ�����岿��.
     *
     * @param namespace  ���ֿռ�
     * @param name       XMLԪ��local��
     * @param text       XMLԪ��ֵ
     *
     * @throws Exception ���ʧ��
     */
    public void body(String namespace, String name, String text)
            throws Exception {
        rule.body(namespace, name, text);
    }

    /**
     * ƥ�����.
     *
     * @param namespace  ���ֿռ�
     * @param name       XMLԪ��local��
     *
     * @throws Exception ���ʧ��
     */
    public void end(String namespace, String name)
            throws Exception {
        rule.end(namespace, name);
    }

    /**
     * �������.
     *
     * @throws Exception ���ʧ��
     */
    public void finish() throws Exception {
        rule.finish();
    }

    /**
     * ȡ�ù�����ַ�����ʾ.
     *
     * @return ������ַ�����ʾ
     */
    public String toString() {
        return rule.toString();
    }
}
