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
package com.alibaba.toolkit.util.configuration.digester;

import org.apache.commons.digester.Rule;

import org.xml.sax.Attributes;

/**
 * �ڱ�ƥ��Ԫ�ؿ�ʼʱ, ����������, ��Ԫ�ؽ���ʱ, ��������������.
 *
 * @version $Id: SetContextRule.java,v 1.2 2003/08/07 08:08:59 zyh Exp $
 * @author Michael Zhou
 */
public class SetContextRule extends Rule {
    protected String         attributeName;
    protected Class          contextFactoryClass;
    protected ContextFactory contextFactory;

    /**
     * ʹ��ָ��attribute��ֵ��Ϊ��ǰcontext��ֵ.
     *
     * @param attributeName XML������
     */
    public SetContextRule(String attributeName) {
        this.attributeName = attributeName;
    }

    /**
     * ʹ��ָ������Ϊȡ��context�Ĺ���.
     *
     * @param contextFactoryClass  ������
     */
    public SetContextRule(Class contextFactoryClass) {
        this.contextFactoryClass = contextFactoryClass;
    }

    /**
     * ʹ��ָ��context����ȡ�õ�ǰcontext��ֵ.
     *
     * @param contextFactory ��������
     */
    public SetContextRule(ContextFactory contextFactory) {
        this.contextFactory = contextFactory;
    }

    /**
     * ��ʼ����, ѹ���µ�context.
     *
     * @param attributes XML����ֵ
     *
     * @throws Exception ���ʧ��
     */
    public void begin(String namespace, String name, Attributes attributes) throws Exception {
        String context = null;

        if (attributeName != null) {
            context = attributes.getValue(attributeName);
        }

        if ((context == null) && (getFactory() != null)) {
            context = getFactory().getContext(attributes);
        }

        if (context != null) {
            if (digester.getLogger().isDebugEnabled()) {
                digester.getLogger()
                        .debug("[SetContextRule]{" + digester.getMatch() + "} New " + context);
            }

            ContextSensitiveRules rules = (ContextSensitiveRules) digester.getRules();

            rules.pushContext(context);
        }
    }

    /**
     * ��������, ���������context
     *
     * @throws Exception ���ʧ��
     */
    public void end(String namespace, String name) throws Exception {
        ContextSensitiveRules rules   = (ContextSensitiveRules) digester.getRules();
        String                context = rules.popContext();

        if (context != null) {
            if (digester.getLogger().isDebugEnabled()) {
                digester.getLogger()
                        .debug("[SetContextRule]{" + digester.getMatch() + "} Pop " + context);
            }
        }
    }

    /**
     * ȡ��rule���ַ�����ʾ.
     *
     * @return �ַ�����ʾ
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer("SetContextRule[");

        if (attributeName != null) {
            buffer.append("attributeName=").append(attributeName);
        } else if (contextFactoryClass != null) {
            buffer.append("contextFactory=").append(contextFactoryClass);
        } else if (contextFactory != null) {
            buffer.append("contextFactory=").append(contextFactory);
        }

        buffer.append("]");
        return buffer.toString();
    }

    /**
     * ȡ�ù���.
     *
     * @return ȡ�õ�ǰcontext�Ĺ���
     *
     * @throws Exception ���ʧ��
     */
    protected ContextFactory getFactory() throws Exception {
        if ((contextFactory == null) && (contextFactoryClass != null)) {
            contextFactory = (ContextFactory) contextFactoryClass.newInstance();
        }

        contextFactory.setDigester(digester);

        return contextFactory;
    }
}
