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

package com.alibaba.antx.util.scanner;

/**
 * ɨ�����Ļص�������
 *
 * @author Michael Zhou
 *
 */
public interface ScannerHandler {
    /**
     * ����scanner���˷���һ�������ȱ����õġ�
     *
     * @param scanner ��ǰ����ɨ���scanner
     */
    void setScanner(Scanner scanner);

    /**
     * ��ʼɨ�衣
     */
    void startScanning();

    /**
     * ����ɨ�衣
     */
    void endScanning();

    /**
     * ɨ��Ŀ¼��
     */
    void directory();

    /**
     * ɨ���ļ���
     */
    void file();

    /**
     * �Ƿ����ָ��Ŀ¼���ļ����÷������������ɨ���ٶȡ�
     *
     * @return ����ǣ��򷵻�<code>true</code>
     */
    boolean followUp();
}
