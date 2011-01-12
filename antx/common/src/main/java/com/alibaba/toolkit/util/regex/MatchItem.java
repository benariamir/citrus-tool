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

import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.PatternMatcherInput;

/**
 * ����һ��ƥ����, ͨ��������, ��������һ���Ĳ���, ���滻, ȡ��ƥ���ַ�����.
 *
 * @version $Id: MatchItem.java,v 1.1 2003/07/03 07:26:34 baobao Exp $
 * @author Michael Zhou
 */
public class MatchItem implements MatchResult {
    public static final int SUBSTITUTION_ONLY           = 0;
    public static final int SUBSTITUTION_WITH_PREMATCH  = 1;
    public static final int SUBSTITUTION_WITH_POSTMATCH = 2;
    private MatchContext    context;
    private MatchPattern    pattern;
    private MatchResult     result;

    /**
     * ����һ��ƥ����.
     *
     * @param context �������ƥ������context
     * @param pattern �������ƥ������pattern
     */
    public MatchItem(MatchContext context, MatchPattern pattern) {
        this.context = context;
        this.pattern = pattern;
    }

    /**
     * ����һ��ƥ����.
     *
     * @param context      �������ƥ������context
     * @param pattern      �������ƥ������pattern
     * @param result       ������ʽ��ƥ����
     */
    public MatchItem(MatchContext context, MatchPattern pattern, MatchResult result) {
        this(context, pattern);
        this.result = result;
    }

    /**
     * ȡ�ò������ƥ������context.
     *
     * @return �������ƥ������context
     */
    public MatchContext getMatchContext() {
        return this.context;
    }

    /**
     * ȡ�ò������ƥ������pattern.
     *
     * @return �������ƥ������pattern
     */
    public MatchPattern getMatchPattern() {
        return this.pattern;
    }

    /**
     * ȡ������������ֵ�ַ���.
     *
     * @return ����������ֵ�ַ���
     */
    public String getInput() {
        return (String) this.context.getInput().getInput();
    }

    /**
     * ʵ��<code>MatchResult</code>�ӿ�, ȡ��ƥ�䳤��.
     *
     * @return ƥ��ĳ���
     */
    public int length() {
        return (result == null) ? 0
                                : result.length();
    }

    /**
     * ʵ��<code>MatchResult</code>�ӿ�, ȡ��group������.
     *
     * @return group������, ����group0, Ҳ��������ƥ��
     */
    public int groups() {
        return (result == null) ? 0
                                : result.groups();
    }

    /**
     * ʵ��<code>MatchResult</code>�ӿ�, ȡ��ָ��group���Ӵ�.
     *
     * @param group  group��, 0��������ƥ��
     *
     * @return ָ��group���Ӵ�
     */
    public String group(int group) {
        return (result == null) ? null
                                : result.group(group);
    }

    /**
     * ʵ��<code>MatchResult</code>�ӿ�, ȡ��ָ��group���������ƥ���λ����.
     *
     * @param group group��, 0��������ƥ��
     *
     * @return ָ��group���������ƥ���λ����, ע�������ƥ����ַ�������Ϊ0, ��λ���ַ�����ĩβ, ��λ���������ַ����ĳ���.
     */
    public int begin(int group) {
        return (result == null) ? (-1)
                                : result.begin(group);
    }

    /**
     * ʵ��<code>MatchResult</code>�ӿ�, ȡ��ָ��groupĩβ���������ƥ���λ����.
     *
     * @param group group��, 0��������ƥ��
     *
     * @return ָ��groupĩβ���������ƥ���λ����, ���ָ��group�����ڻ�δƥ��, �򷵻�-1, ��ƥ����ַ�������Ϊ0, �򷵻���ʼλ����
     */
    public int end(int group) {
        return (result == null) ? (-1)
                                : result.end(group);
    }

    /**
     * ʵ��<code>MatchResult</code>�ӿ�, ȡ��ָ��group����������ַ�����λ����.
     *
     * @param group group��, 0��������ƥ��
     *
     * @return ָ��group����������ַ�����λ����, ���ָ��group�����ڻ�δƥ��, �򷵻�-1
     */
    public int beginOffset(int group) {
        return (result == null) ? (-1)
                                : result.beginOffset(group);
    }

    /**
     * ʵ��<code>MatchResult</code>�ӿ�, ȡ��ָ��groupĩβ����������ַ�����λ����.
     *
     * @param group group��, 0��������ƥ��
     *
     * @return ָ��groupĩβ����������ַ�����λ����, ���ָ��group�����ڻ�δƥ��, �򷵻�-1, ��ƥ����ַ�������Ϊ0, �򷵻���ʼλ����
     */
    public int endOffset(int group) {
        return (result == null) ? (-1)
                                : result.endOffset(group);
    }

    /**
     * ʵ��<code>MatchResult</code>�ӿ�, ȡ������ƥ����ַ���, �൱��<code>group(0)</code>.
     *
     * @return ����ƥ����ַ���
     */
    public String toString() {
        return (result == null) ? ""
                                : result.toString();
    }

    /**
     * ��ƥ���ַ���ǰ���Ӵ�, �ӵ�ָ��<code>StringBuffer</code>��.
     *
     * @param buffer  Ҫ��ӵ�<code>StringBuffer</code>
     */
    public void appendPreMatch(StringBuffer buffer) {
        PatternMatcherInput input       = context.getInput();
        char[]              inputBuffer = input.getBuffer();
        int                 beginOffset = input.getBeginOffset();

        buffer.append(inputBuffer, beginOffset, beginOffset(0) - beginOffset);
    }

    /**
     * ��ƥ���ַ�������Ӵ�, �ӵ�ָ��<code>StringBuffer</code>��.
     *
     * @param buffer  Ҫ��ӵ�<code>StringBuffer</code>
     */
    public void appendPostMatch(StringBuffer buffer) {
        PatternMatcherInput input       = context.getInput();
        char[]              inputBuffer = input.getBuffer();
        int                 beginOffset = endOffset(0);

        buffer.append(inputBuffer, beginOffset, input.length() - beginOffset);
    }

    /**
     * ��ƥ���ַ���, �ӵ�ָ��<code>StringBuffer</code>��.
     *
     * @param buffer  Ҫ��ӵ�<code>StringBuffer</code>
     */
    public void appendMatch(StringBuffer buffer) {
        PatternMatcherInput input       = context.getInput();
        char[]              inputBuffer = input.getBuffer();
        int                 beginOffset = beginOffset(0);

        buffer.append(inputBuffer, beginOffset, endOffset(0));
    }

    /**
     * ���滻�ַ������뵽ָ��<code>StringBuffer</code>��.
     *
     * @param buffer        Ҫ��ӵ�<code>StringBuffer</code>
     * @param substitution  �滻���ʽ
     */
    public void appendSubstitution(StringBuffer buffer, String substitution) {
        context.getSubstitution(substitution)
               .appendSubstitution(buffer, this, 1, context.getInput(), context.getMatcher(),
                                   pattern.getPattern());
    }

    /**
     * �滻ƥ����ַ���.
     *
     * @param substitution �滻�ַ���
     *
     * @return ���滻���ַ���
     */
    public String substitute(String substitution) {
        return substitute(substitution, SUBSTITUTION_ONLY);
    }

    /**
     * �滻ƥ����ַ���.
     *
     * @param substitution �滻�ַ���
     * @param options      �滻ѡ��, ����Ϊ<code>SUBSTITUTION_ONLY</code>,
     *        <code>SUBSTITUTION_WITH_PREMATCH</code>��<code>SUBSTITUTION_WITH_POSTMATCH</code>�����ǵ����
     *
     * @return ���滻���ַ���
     */
    public String substitute(String substitution, int options) {
        StringBuffer buffer = new StringBuffer();

        if ((options & SUBSTITUTION_WITH_PREMATCH) != 0) {
            appendPreMatch(buffer);
        }

        appendSubstitution(buffer, substitution);

        if ((options & SUBSTITUTION_WITH_POSTMATCH) != 0) {
            appendPostMatch(buffer);
        }

        return buffer.toString();
    }
}
