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
package com.alibaba.toolkit.util.resourcebundle;

import com.alibaba.toolkit.util.enumeration.Enum;
import com.alibaba.toolkit.util.exception.ChainedThrowable;
import com.alibaba.toolkit.util.exception.ChainedThrowableDelegate;
import com.alibaba.toolkit.util.exception.ErrorCode;

import java.io.PrintStream;
import java.io.PrintWriter;

import java.text.MessageFormat;

/**
 * ��ʾ<code>ResourceBundle</code>δ�ҵ�, �򴴽�ʧ�ܵ��쳣.
 *
 * @version $Id: ResourceBundleException.java,v 1.1 2003/07/03 07:26:35 baobao Exp $
 * @author Michael Zhou
 */
public class ResourceBundleException extends java.util.MissingResourceException
        implements ChainedThrowable, ErrorCode {
    private static final long serialVersionUID = -2272722732501708511L;
    private final ChainedThrowable delegate  = new ChainedThrowableDelegate(this);
    private Throwable              cause;
    private Enum                   errorCode;

    /**
     * ����һ���쳣, ָ����������쳣������.
     *
     * @param messageId   ��ϸ��ϢID
     * @param params      ��ϸ��Ϣ����
     * @param cause       �쳣������
     * @param bundleName  bundle����
     * @param key         resource key
     */
    public ResourceBundleException(String messageId, Object[] params, Throwable cause,
                                   String bundleName, Object key) {
        super(MessageFormat.format(messageId,
                                   (params == null) ? new Object[0]
                                                    : params), bundleName, String.valueOf(key));
        this.cause = cause;
    }

    /**
     * ȡ��bundle��.
     *
     * @return bundle��
     */
    public String getBundleName() {
        return super.getClassName();
    }

    /**
     * ȡ����������쳣������.
     *
     * @return �쳣������.
     */
    public Throwable getCause() {
        return cause;
    }

    /**
     * ȡ�ô�����.
     *
     * @return ������
     */
    public Enum getErrorCode() {
        return errorCode;
    }

    /**
     * ��ӡ����ջ����׼����.
     */
    public void printStackTrace() {
        delegate.printStackTrace();
    }

    /**
     * ��ӡ����ջ��ָ�������.
     *
     * @param stream  ����ֽ���.
     */
    public void printStackTrace(PrintStream stream) {
        delegate.printStackTrace(stream);
    }

    /**
     * ��ӡ����ջ��ָ�������.
     *
     * @param writer  ����ַ���.
     */
    public void printStackTrace(PrintWriter writer) {
        delegate.printStackTrace(writer);
    }

    /**
     * ��ӡ�쳣�ĵ���ջ, �����������쳣����Ϣ.
     *
     * @param writer  ��ӡ�������
     */
    public void printCurrentStackTrace(PrintWriter writer) {
        super.printStackTrace(writer);
    }
}
