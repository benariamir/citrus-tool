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
package com.alibaba.toolkit.util.regex;

import com.alibaba.toolkit.util.collection.Predicate;

import java.util.Collection;
import java.util.Collections;

import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.PatternMatcherInput;
import org.apache.oro.text.regex.Perl5Matcher;
import org.apache.oro.text.regex.Perl5Substitution;
import org.apache.oro.text.regex.Substitution;

/**
 * ����һ��ƥ���context.  һ��context�б�����һ�λ���ƥ������п��ظ�ʹ�õĶ���, �Լ����һ��ƥ��Ľ��.
 *
 * @version $Id: MatchContext.java,v 1.1 2003/07/03 07:26:34 baobao Exp $
 * @author Michael Zhou
 */
public class MatchContext {
    private PatternMatcher      matcher;
    private PatternMatcherInput input;
    private Substitution        substitution;
    private MatchItem           lastMatchItem;

    /**
     * ����һ���µ�context.
     *
     * @param input Ҫƥ����ַ���
     */
    public MatchContext(String input) {
        init(input);
    }

    /**
     * ����һ���µ�context.
     *
     * @param input Ҫƥ����ַ���
     */
    public void init(String input) {
        if (this.matcher == null) {
            this.matcher = createPatternMatcher();
        }

        if (this.input == null) {
            this.input = new PatternMatcherInput(input);
        } else {
            this.input.setInput(input);
        }
    }

    /**
     * ȡ������ƥ���patterns.
     *
     * @return ����patterns
     */
    public Collection getPatterns() {
        return Collections.EMPTY_LIST;
    }

    /**
     * ȡ�����ڹ���ƥ���������, Ĭ�Ϸ���<code>null</code>.
     *
     * @return ����ƥ���������
     */
    public Predicate getPredicate() {
        return null;
    }

    /**
     * ȡ��ƥ�������ֵ.
     *
     * @return ƥ�������ֵ
     */
    public PatternMatcherInput getInput() {
        return input;
    }

    /**
     * ȡ�ò���λƥ�������ֵ. ÿ��ƥ�䶯����ɺ�, ������ô˷����ſɽ��еڶ���ƥ��.
     *
     * @return input ƥ�������ֵ
     */
    public PatternMatcherInput getInputReset() {
        input.setCurrentOffset(input.getBeginOffset());
        return input;
    }

    /**
     * ȡ��patternƥ����.
     *
     * @return patternƥ����
     */
    public PatternMatcher getMatcher() {
        return matcher;
    }

    /**
     * �������һ��ƥ��Ľ��.
     *
     * @param item ���һ��ƥ��Ľ��
     */
    public void setLastMatchItem(MatchItem item) {
        this.lastMatchItem = item;
    }

    /**
     * ȡ�����һ��ƥ��Ľ��.
     *
     * @return ���һ��ƥ��Ľ��
     */
    public MatchItem getLastMatchItem() {
        return lastMatchItem;
    }

    /**
     * ȡ���滻����.
     *
     * @param substitution �滻�ַ���
     *
     * @return �滻����
     */
    public Substitution getSubstitution(String substitution) {
        this.substitution = createSubstitution(this.substitution, substitution);

        return this.substitution;
    }

    /**
     * ����������ʽ��ƥ����, Ĭ��Ϊ<code>Perl5Matcher</code>, ������Ըı����ʵ��.
     *
     * @return ������ʽ��ƥ����
     */
    protected PatternMatcher createPatternMatcher() {
        return new Perl5Matcher();
    }

    /**
     * �����滻��, Ĭ��Ϊ<code>Perl5Substitution</code>, ������Ըı����ʵ��.
     *
     * @param reuse         �����õ��滻��
     * @param substitution  �滻�ַ���
     *
     * @return �滻��
     */
    protected Substitution createSubstitution(Substitution reuse, String substitution) {
        if (reuse == null) {
            return new Perl5Substitution(substitution);
        }

        ((Perl5Substitution) reuse).setSubstitution(substitution);
        return reuse;
    }

    /**
     * ����ƥ����, Ĭ��Ϊ<code>MatchItem</code>, ������Ըı����ʵ��.
     *
     * @param pattern  ����ƥ���pattern
     * @param result   ������ʽ��ƥ����
     *
     * @return ƥ������
     */
    protected MatchItem createMatchItem(MatchPattern pattern, MatchResult result) {
        return new MatchItem(this, pattern, result);
    }
}
