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
 * �������������ֽӿ�.
 *
 * @version $Id: IntegralNumber.java,v 1.1 2003/07/03 07:26:21 baobao Exp $
 * @author Michael Zhou
 */
public interface IntegralNumber {
    int RADIX_HEX = 16;
    int RADIX_OCT = 8;
    int RADIX_BIN = 2;

    /**
     * ȡ������ֵ
     *
     * @return ����ֵ
     */
    int intValue();

    /**
     * ȡ�ó�����ֵ
     *
     * @return ������ֵ
     */
    long longValue();

    /**
     * ȡ�ø���ֵ
     *
     * @return ����ֵ
     */
    float floatValue();

    /**
     * ȡ��ȡ��doubleֵ
     *
     * @return doubleֵ
     */
    double doubleValue();

    /**
     * ȡ��byteֵ
     *
     * @return byteֵ
     */
    byte byteValue();

    /**
     * ȡ��shortֵ
     *
     * @return shortֵ
     */
    short shortValue();

    /**
     * ת����ʮ�����������ַ���.
     *
     * @return ʮ�����������ַ���
     */
    String toHexString();

    /**
     * ת���ɰ˽��������ַ���.
     *
     * @return �˽��������ַ���
     */
    String toOctalString();

    /**
     * ת���ɶ����������ַ���.
     *
     * @return �����������ַ���
     */
    String toBinaryString();
}
