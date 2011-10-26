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
 * Ĭ�ϵ�scanner handlerʵ�֡�
 *
 * @author Michael Zhou
 */
public class DefaultScannerHandler implements ScannerHandler {
    private Scanner scanner;

    /**
     * ȡ��scanner��
     *
     * @return ��ǰ����ɨ���scanner
     */
    public Scanner getScanner() {
        return scanner;
    }

    /**
     * ����scanner���˷���һ�������ȱ����õġ�
     *
     * @param scanner ��ǰ����ɨ���scanner
     */
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * ��ʼɨ�衣
     */
    public void startScanning() {
    }

    /**
     * ����ɨ�衣
     */
    public void endScanning() {
    }

    /**
     * ɨ��Ŀ¼��
     */
    public void directory() {
    }

    /**
     * ɨ���ļ���
     */
    public void file() {
    }

    /**
     * �Ƿ����ָ��Ŀ¼���ļ����÷������������ɨ���ٶȡ�
     *
     * @return ����ǣ��򷵻�<code>true</code>
     */
    public boolean followUp() {
        return true;
    }
}
