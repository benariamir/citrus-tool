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
import org.apache.commons.digester.Rules;

/**
 * ���Ǵ�commons-digester��չ��<code>Digester</code>, ������һЩ����, �Ա�ʵ����������صĹ���.
 *
 * @version $Id: ContextSensitiveDigester.java,v 1.1 2003/07/03 07:26:16 baobao Exp $
 * @author Michael Zhou
 */
public class ContextSensitiveDigester extends Digester {
    /**
     * ���Ǹ���ķ���, ������<code>rules</code>��װ��<code>ContextSensitiveRules</code>����.
     *
     * @param rules <code>Rules</code>����
     */
    public void setRules(Rules rules) {
        if (!(rules instanceof ContextSensitiveRules)) {
            rules = new ContextSensitiveRules(rules);
        }

        this.rules = rules;
        this.rules.setDigester(this);
    }

    /**
     * ���Ǹ��෽��, ȷ��ȡ�õĶ���Ϊ<code>ContextSensitiveRules</code>����.
     *
     * @return <code>Rules</code>����
     */
    public Rules getRules() {
        if (rules == null) {
            setRules(new ContextSensitiveRules());
        }

        return rules;
    }

    /**
     * ����<code>SetContextRule</code>, ʹ��ָ��attribute��ֵ��Ϊ��ǰcontext��ֵ.
     *
     * @param pattern       ��ǰ��ƥ��
     * @param attributeName XML������
     */
    public void addSetContextRule(String pattern, String attributeName) {
        addRule(pattern, new SetContextRule(attributeName));
    }

    /**
     * ����<code>SetContextRule</code>, ʹ��ָ������Ϊȡ��context�Ĺ���.
     *
     * @param pattern              ��ǰ��ƥ��
     * @param contextFactoryClass  ������
     */
    public void addSetContextRule(String pattern, Class contextFactoryClass) {
        addRule(pattern, new SetContextRule(contextFactoryClass));
    }

    /**
     * ����<code>SetContextRule</code>, ʹ��ָ��context����ȡ�õ�ǰcontext��ֵ.
     *
     * @param pattern        ��ǰ��ƥ��
     * @param contextFactory ��������
     */
    public void addSetContextRule(String pattern, ContextFactory contextFactory) {
        addRule(pattern, new SetContextRule(contextFactory));
    }

    /**
     * ����<code>SetRuleSetRule</code>, ʹ��ָ������Ϊȡ��<code>RuleSet</code>�Ĺ���.
     *
     * @param pattern              ��ǰ��ƥ��
     * @param ruleSetFactoryClass  ������
     */
    public void addSetRuleSetRule(String pattern, Class ruleSetFactoryClass) {
        addRule(pattern, new SetRuleSetRule(ruleSetFactoryClass));
    }

    /**
     * ����<code>SetRuleSetRule</code>, ʹ��ָ��<code>RuleSet</code>����.
     *
     * @param pattern        ��ǰ��ƥ��
     * @param ruleSetFactory ��������
     */
    public void addSetRuleSetRule(String pattern, RuleSetFactory ruleSetFactory) {
        addRule(pattern, new SetRuleSetRule(ruleSetFactory));
    }
}
