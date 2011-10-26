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
 * ����scanner�Ļ��ࡣ
 *
 * @author Michael Zhou
 */
public abstract class AbstractScanner implements Scanner {
    private ScannerHandler handler;
    private String         path;

/**
     * ����һ��scanner��
     *
     * @param �ص�����
     */
    public AbstractScanner(ScannerHandler handler) {
        this.handler = handler;
    }

    /**
     * ȡ��scanner handler��
     *
     * @return scanner handler
     */
    public ScannerHandler getScannerHandler() {
        return handler;
    }

    /**
     * ȡ�õ�ǰ����ɨ����ļ�·����
     *
     * @return �ļ�·��
     */
    public String getPath() {
        return (path == null) ? ""
                              : path;
    }

    /**
     * ���õ�ǰ����ɨ����ļ�·����
     *
     * @param path �ļ�·��
     *
     * @return ԭ·��
     */
    protected String setPath(String path) {
        String old = getPath();

        if (path != null) {
            path = path.replace('\\', '/');
        }

        this.path = path;

        return old;
    }

    /**
     * ת�����ַ�����
     *
     * @return �ַ�����ʾ
     */
    public String toString() {
        return "Scanner[URL=" + getBaseURL() + "]";
    }
}
