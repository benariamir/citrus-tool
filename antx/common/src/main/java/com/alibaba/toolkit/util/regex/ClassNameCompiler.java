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

/**
 * ����ཫһ������ͨ���������, �����Perl5��������ʽ.  ��ʽ��������:
 *
 * <ul>
 * <li>
 * �Ϸ���<em>�����ַ�</em>����: ��ĸ/����/�»���/'$';
 * </li>
 * <li>
 * �Ϸ���<em>�����ָ���</em>ΪС����".";
 * </li>
 * <li>
 * "��"����0������<em>�����ַ�</em>;
 * </li>
 * <li>
 * "��"����1��<em>�����ַ�</em>;
 * </li>
 * <li>
 * "����"����0������<em>�����ַ�</em>��<em>�����ָ���</em>;
 * </li>
 * <li>
 * ������������3��"��";
 * </li>
 * <li>
 * ������������2��<em>�����ָ���</em>;
 * </li>
 * <li>
 * "����"��ǰ��ֻ����<em>�����ָ���</em>.
 * </li>
 * </ul>
 *
 * ת�����������ʽ, ��ÿһ��ͨ�������<em>���ñ���</em>, ����Ϊ<code>$1</code>, <code>$2</code>, ...
 *
 * @version $Id: ClassNameCompiler.java,v 1.1 2003/07/03 07:26:34 baobao Exp $
 * @author Michael Zhou
 */
public class ClassNameCompiler extends Perl5CompilerWrapper {
    /** ǿ�ƴ�ͷƥ�� */
    public static final int MATCH_PREFIX = 0x1;

    // ˽�г���
    private static final char   DOT                          = '.';
    private static final char   UNDERSCORE                   = '_';
    private static final char   DOLLAR                       = '$';
    private static final char   STAR                         = '*';
    private static final char   QUESTION                     = '?';
    private static final String REGEX_MATCH_PREFIX           = "^";
    private static final String REGEX_WORD_BOUNDARY          = "\\b";
    private static final String REGEX_DOT                    = "\\.";
    private static final String REGEX_DOT_NO_DUP             = "\\.(?!\\.)";
    private static final String REGEX_CLASS_NAME_CHAR        = "[\\w\\$]";
    private static final String REGEX_CLASS_NAME_SINGLE_CHAR = "(" + REGEX_CLASS_NAME_CHAR + ")";
    private static final String REGEX_CLASS_NAME             = "(" + REGEX_CLASS_NAME_CHAR + "*)";
    private static final String REGEX_CLASS_NAME_FULL        =
            "(" + REGEX_CLASS_NAME_CHAR + "+(?:" + REGEX_DOT_NO_DUP + REGEX_CLASS_NAME_CHAR
            + "*)*(?=" + REGEX_DOT + "|$)|)" + REGEX_DOT + "?";

    // ��һ��token��״̬
    private static final int LAST_TOKEN_START       = 0;
    private static final int LAST_TOKEN_DOT         = 1;
    private static final int LAST_TOKEN_CLASS_NAME  = 2;
    private static final int LAST_TOKEN_STAR        = 3;
    private static final int LAST_TOKEN_DOUBLE_STAR = 4;
    private static final int LAST_TOKEN_QUESTION    = 5;

    /**
     * ������ͨ���������, �����perl5������ʽ.
     *
     * @param pattern  Ҫ���������
     * @param options  λ��־
     *
     * @return Perl5������ʽ�ַ���
     *
     * @throws MalformedPatternException  ��������ַ�����ʽ����ȷ
     */
    public String toPerl5Regex(char[] pattern, int options)
            throws MalformedPatternException {
        int          lastToken = LAST_TOKEN_START;
        StringBuffer buffer = new StringBuffer(pattern.length * 2);

        boolean      matchPrefix = (options & MATCH_PREFIX) != 0;

        if (matchPrefix) {
            buffer.append(REGEX_MATCH_PREFIX);
        }

        for (int i = 0; i < pattern.length; i++) {
            char ch = pattern[i];

            switch (ch) {
                case DOT:

                    // dot���治����dot, dot������Ϊ�ַ����Ŀ�ʼ
                    if ((lastToken == LAST_TOKEN_DOT) || (lastToken == LAST_TOKEN_START)) {
                        throw new MalformedPatternException(getDefaultErrorMessage(pattern, i));
                    }

                    // ��Ϊ**�Ѿ�������dot, ���Բ���Ҫ�����ƥ��dot
                    if (lastToken != LAST_TOKEN_DOUBLE_STAR) {
                        buffer.append(REGEX_DOT_NO_DUP);
                    }

                    lastToken = LAST_TOKEN_DOT;
                    break;

                case STAR:

                    int j = i + 1;

                    if ((j < pattern.length) && (pattern[j] == STAR)) {
                        i = j;

                        // **ǰ��ֻ����dot
                        if ((lastToken != LAST_TOKEN_START) && (lastToken != LAST_TOKEN_DOT)) {
                            throw new MalformedPatternException(getDefaultErrorMessage(pattern, i));
                        }

                        lastToken = LAST_TOKEN_DOUBLE_STAR;
                        buffer.append(REGEX_CLASS_NAME_FULL);
                    } else {
                        // *ǰ�治����*��**
                        if ((lastToken == LAST_TOKEN_STAR) || (lastToken == LAST_TOKEN_DOUBLE_STAR)) {
                            throw new MalformedPatternException(getDefaultErrorMessage(pattern, i));
                        }

                        lastToken = LAST_TOKEN_STAR;
                        buffer.append(REGEX_CLASS_NAME);
                    }

                    break;

                case QUESTION:
                    lastToken = LAST_TOKEN_QUESTION;
                    buffer.append(REGEX_CLASS_NAME_SINGLE_CHAR);
                    break;

                default:

                    // **��ֻ����dot
                    if (lastToken == LAST_TOKEN_DOUBLE_STAR) {
                        throw new MalformedPatternException(getDefaultErrorMessage(pattern, i));
                    }

                    if (Character.isLetterOrDigit(ch) || (ch == UNDERSCORE)) {
                        // ����word�߽�, ��������ƥ��
                        if (lastToken == LAST_TOKEN_START) {
                            buffer.append(REGEX_WORD_BOUNDARY).append(ch); // ǰ�߽�
                        } else if ((i + 1) == pattern.length) {
                            buffer.append(ch).append(REGEX_WORD_BOUNDARY); // ��߽�
                        } else {
                            buffer.append(ch);
                        }
                    } else if (ch == DOLLAR) {
                        buffer.append(ESCAPE_CHAR).append(DOLLAR);
                    } else {
                        throw new MalformedPatternException(getDefaultErrorMessage(pattern, i));
                    }

                    lastToken = LAST_TOKEN_CLASS_NAME;
            }
        }

        return buffer.toString();
    }
}
