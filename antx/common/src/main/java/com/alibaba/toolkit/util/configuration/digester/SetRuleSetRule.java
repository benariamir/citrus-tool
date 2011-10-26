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

import org.apache.commons.digester.Rule;
import org.apache.commons.digester.RuleSet;

import org.xml.sax.Attributes;

/**
 * ������������ص�<code>RuleSet</code>�Ĺ���.
 *
 * @version $Id: SetRuleSetRule.java,v 1.2 2003/08/07 08:08:59 zyh Exp $
 * @author Michael Zhou
 */
public class SetRuleSetRule extends Rule {
    private Class          ruleSetFactoryClass;
    private RuleSetFactory ruleSetFactory;

    /**
     * ʹ��ָ������Ϊȡ��<code>RuleSet</code>�Ĺ���.
     *
     * @param ruleSetFactoryClass  ������
     */
    public SetRuleSetRule(Class ruleSetFactoryClass) {
        this.ruleSetFactoryClass = ruleSetFactoryClass;
    }

    /**
     * ʹ��ָ��<code>RuleSet</code>����.
     *
     * @param ruleSetFactory ��������
     */
    public SetRuleSetRule(RuleSetFactory ruleSetFactory) {
        this.ruleSetFactory = ruleSetFactory;
    }

    /**
     * ��ʼ����, ������������ص�<code>RuleSet</code>.
     *
     * @param attributes XML����ֵ
     *
     * @throws Exception ���ʧ��
     */
    public void begin(String namespace, String name, Attributes attributes) throws Exception {
        ContextSensitiveRules rules   = (ContextSensitiveRules) digester.getRules();
        String                context = rules.getContext();

        if (!rules.isInitialized(context)) {
            rules.setInitializing(context);

            RuleSet ruleSet = getFactory().getRuleSet(attributes);

            digester.addRuleSet(ruleSet);
            rules.setInitialized(context);

            if (digester.getLogger().isDebugEnabled()) {
                digester.getLogger()
                        .debug("[SetRuleSetRule]{" + digester.getMatch() + "} New " + ruleSet);
            }
        }
    }

    /**
     * ȡ��rule���ַ�����ʾ.
     *
     * @return �ַ�����ʾ
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer("SetRuleSetRule[");

        if (ruleSetFactoryClass != null) {
            buffer.append("ruleSetFactory=").append(ruleSetFactoryClass);
        } else if (ruleSetFactory != null) {
            buffer.append("ruleSetFactory=").append(ruleSetFactory);
        }

        buffer.append("]");
        return buffer.toString();
    }

    /**
     * ȡ�ù���.
     *
     * @return ȡ��<code>RuleSet</code>�Ĺ���
     *
     * @throws Exception ���ʧ��
     */
    protected RuleSetFactory getFactory() throws Exception {
        if ((ruleSetFactory == null) && (ruleSetFactoryClass != null)) {
            ruleSetFactory = (RuleSetFactory) ruleSetFactoryClass.newInstance();
        }

        ruleSetFactory.setDigester(digester);

        return ruleSetFactory;
    }
}
