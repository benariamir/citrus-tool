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
package com.alibaba.toolkit.util.enumeration;

/**
 * ���<code>Enum</code>Ϊλ������ģʽ, Ҳ����˵<code>Enum</code>ֵ���ǵ���, ���Ǳ���(����).
 *
 * @version $Id: Flags.java,v 1.1 2003/07/03 07:26:20 baobao Exp $
 * @author Michael Zhou
 */
public interface Flags extends IntegralNumber {
    /**
     * ���óɲ��ɱ��λ��.
     *
     * @return λ������
     */
    Flags setImmutable();

    /**
     * �Ե�ǰλ��ִ���߼������.
     *
     * @param flags  ��־λ
     *
     * @return ��ǰλ��
     */
    Flags and(Flags flags);

    /**
     * �Ե�ǰλ��ִ���߼��ǲ���.
     *
     * @param flags  ��־λ
     *
     * @return ��ǰλ��
     */
    Flags andNot(Flags flags);

    /**
     * �Ե�ǰλ��ִ���߼������.
     *
     * @param flags  ��־λ
     *
     * @return ��ǰλ��
     */
    Flags or(Flags flags);

    /**
     * �Ե�ǰλ��ִ���߼�������.
     *
     * @param flags  ��־λ
     *
     * @return ��ǰλ��
     */
    Flags xor(Flags flags);

    /**
     * �����ǰλ����ȫ��λ.
     *
     * @return ��ǰλ��
     */
    Flags clear();

    /**
     * �����ǰλ����ָ��λ, ��Ч��<code>andNot</code>����.
     *
     * @param flags  ��־λ
     *
     * @return ��ǰλ��
     */
    Flags clear(Flags flags);

    /**
     * ���õ�ǰλ����ָ��λ, ��Ч��<code>or</code>����.
     *
     * @param flags  ��־λ
     *
     * @return ��ǰλ��
     */
    Flags set(Flags flags);

    /**
     * ���Ե�ǰλ����ָ��λ, ��Ч��<code>and(flags) != 0</code>.
     *
     * @param flags  ��־λ
     *
     * @return ���ָ��λ����λ, �򷵻�<code>true</code>
     */
    boolean test(Flags flags);

    /**
     * ���Ե�ǰλ����ָ��λ, ��Ч��<code>and(flags) == flags</code>.
     *
     * @param flags  ��־λ
     *
     * @return ���ָ��λ����λ, �򷵻�<code>true</code>
     */
    boolean testAll(Flags flags);

    /**
     * ȡ�ñ�־��ֵ.
     *
     * @return ��־��ֵ
     */
    Object getValue();
}
