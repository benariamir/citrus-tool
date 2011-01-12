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

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;

/**
 * ��ʾһ������ƥ���pattern.
 *
 * @version $Id: MatchPattern.java,v 1.1 2003/07/03 07:26:34 baobao Exp $
 * @author Michael Zhou
 */
public class MatchPattern {
    private Pattern pattern;

    /**
     * �����µ�pattern.
     */
    public MatchPattern() {
    }

    /**
     * �����µ�pattern.
     *
     * @param pattern ����ƥ��������ʽ��pattern
     */
    public MatchPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    /**
     * �����µ�pattern.
     *
     * @param compiler pattern������
     * @param pattern  ����ƥ��������ʽ��pattern
     *
     * @throws MalformedPatternException ���pattern���Ϸ�
     */
    public MatchPattern(PatternCompiler compiler, String pattern)
            throws MalformedPatternException {
        this.pattern = compiler.compile(pattern);
    }

    /**
     * �����µ�pattern.
     *
     * @param compiler pattern������
     * @param pattern  ����ƥ��������ʽ��pattern
     * @param options  ������ѡ��
     *
     * @throws MalformedPatternException ���pattern���Ϸ�
     */
    public MatchPattern(PatternCompiler compiler, String pattern, int options)
            throws MalformedPatternException {
        this.pattern = compiler.compile(pattern, options);
    }

    /**
     * ����pattern.
     *
     * @param pattern ����ƥ��������ʽ��pattern
     */
    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    /**
     * ȡ������ƥ��������ʽ��pattern.
     *
     * @return ƥ��������ʽ��pattern
     */
    public Pattern getPattern() {
        return pattern;
    }

    /**
     * ƥ��pattern, ����ɹ�, �򷵻�<code>true</code>.  �����߿���ͨ��<code>context.getMatchItem()</code>��ȡ��ƥ����.
     *
     * @param context  ƥ��������
     *
     * @return ���ƥ��ɹ�, �򷵻�<code>true</code>
     */
    public boolean matches(MatchContext context) {
        PatternMatcher matcher = context.getMatcher();

        if (matcher.contains(context.getInputReset(), pattern)) {
            MatchItem item = context.createMatchItem(this, matcher.getMatch());

            context.setLastMatchItem(item);
            return true;
        }

        return false;
    }
}
