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
import org.apache.commons.digester.Rules;
import org.apache.commons.digester.RulesBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * ��������ص�<code>Rules</code>��װ��.
 *
 * @author Michael Zhou
 * @version $Id: ContextSensitiveRules.java,v 1.2 2003/08/07 08:08:59 zyh Exp $
 */
public class ContextSensitiveRules implements Rules {
    private static final String CONTEXT_INITIALIZING = "INITIALIZING";
    private static final String CONTEXT_INITIALIZED = "INITIALIZED";
    protected Rules             rules;
    private String              context             = "";
    private StringBuffer        contextBuffer       = new StringBuffer();
    private Map                 contextStatus       = new HashMap();

    /**
     * ����Ĭ�ϵ�<code>Rules</code>.
     */
    public ContextSensitiveRules() {
        this(new RulesBase());
    }

    /**
     * ����ָ��<code>Rules</code>�İ�װ.
     *
     * @param rules ����װ��<code>Rules</code>
     */
    public ContextSensitiveRules(Rules rules) {
        this.rules = rules;
    }

    /**
     * ѹ��ָ����������.
     *
     * @param context Ҫѹ����������ַ���
     */
    public void pushContext(String context) {
        contextBuffer.append('/').append(context);
        this.context = contextBuffer.toString();
    }

    /**
     * �������µ�������.
     *
     * @return ���µ�������
     */
    public String popContext() {
        int    index      = context.lastIndexOf("/");
        String topContext = null;

        if (index >= 0) {
            topContext = contextBuffer.substring(index + 1, contextBuffer.length());
            contextBuffer.setLength(index);
        }

        this.context = contextBuffer.toString();
        return topContext;
    }

    /**
     * ���ָ����context�Ƿ񱻳�ʼ��.
     *
     * @param context Ҫ����context
     *
     * @return �������ʼ��, �򷵻�<code>true</code>
     */
    public boolean isInitialized(String context) {
        return CONTEXT_INITIALIZED.equals(contextStatus.get(context));
    }

    /**
     * ����ָ��contextΪ��ʼ���е�״̬.
     *
     * @param context Ҫ���õ�context
     */
    public void setInitializing(String context) {
        contextStatus.put(context, CONTEXT_INITIALIZING);
    }

    /**
     * ����ָ��contextΪ�ѳ�ʼ����״̬.
     *
     * @param context Ҫ���õ�context
     */
    public void setInitialized(String context) {
        contextStatus.put(context, CONTEXT_INITIALIZED);
    }

    /**
     * ȡ�õ�ǰ��������.
     *
     * @return ��ǰ��������
     */
    public String getContext() {
        return context;
    }

    /**
     * ȡ��digester.
     *
     * @return digester
     */
    public Digester getDigester() {
        return rules.getDigester();
    }

    /**
     * ����digester.
     *
     * @param digester digester
     */
    public void setDigester(Digester digester) {
        rules.setDigester(digester);
    }

    /**
     * ȡ�����ֿռ�.
     *
     * @return ���ֿռ�
     */
    public String getNamespaceURI() {
        return rules.getNamespaceURI();
    }

    /**
     * �������ֿռ�.
     *
     * @param namespaceURI ���ֿռ�
     */
    public void setNamespaceURI(String namespaceURI) {
        rules.setNamespaceURI(namespaceURI);
    }

    /**
     * �Ǽǹ���.
     *
     * @param pattern ƥ��ģ��
     * @param rule Ҫ�ǼǵĹ���
     */
    public void add(String pattern, Rule rule) {
        if ((context.length() > 0) && !(rule instanceof SetContextRule)
                    && CONTEXT_INITIALIZING.equals(contextStatus.get(context))) {
            rule = new ContextSensitiveRule(rule, context);
        }

        rules.add(pattern, rule);
    }

    /**
     * ������й���.
     */
    public void clear() {
        rules.clear();
    }

    /**
     * ƥ��ָ��ģ��.
     *
     * @param pattern ƥ��ģ��
     *
     * @return ƥ��Ĺ���
     *
     * @deprecated
     */
    public List match(String pattern) {
        return match(null, pattern);
    }

    /**
     * ƥ��ָ��ģ��.
     *
     * @param namespaceURI ���ֿռ�
     * @param pattern ƥ��ģ��
     *
     * @return ƥ��Ĺ���
     */
    public List match(String namespaceURI, String pattern) {
        List list    = rules.match(namespaceURI, pattern);
        List sublist = new ArrayList(list.size());

        for (Iterator i = list.iterator(); i.hasNext();) {
            Rule rule = (Rule) i.next();

            if (!(rule instanceof ContextSensitiveRule)
                        || ((ContextSensitiveRule) rule).isContextMatched(context)) {
                sublist.add(rule);
            }
        }

        return sublist;
    }

    /**
     * ȡ�����й���.
     *
     * @return ���й���
     */
    public List rules() {
        return rules.rules();
    }

    /**
     * ȡ��<code>Rules</code>���ַ�����ʾ.
     *
     * @return <code>Rules</code>���ַ�����ʾ
     */
    public String toString() {
        return rules.toString();
    }
}
