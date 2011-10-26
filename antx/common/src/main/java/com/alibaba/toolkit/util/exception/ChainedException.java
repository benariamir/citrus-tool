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

package com.alibaba.toolkit.util.exception;

import com.alibaba.toolkit.util.enumeration.Enum;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * ��Ƕ�׵��쳣.
 *
 * @author Michael Zhou
 * @version $Id: ChainedException.java,v 1.2 2003/08/07 08:09:00 zyh Exp $
 */
public class ChainedException extends Exception implements ChainedThrowable, ErrorCode {
    private static final long serialVersionUID = -5440570868302479741L;
    private final ChainedThrowable delegate  = new ChainedThrowableDelegate(this);
    private Throwable              cause;
    private Enum                   errorCode;

    /**
     * ����һ���յ��쳣.
     */
    public ChainedException() {
        super();
    }

    /**
     * ����һ���쳣, ָ���쳣����ϸ��Ϣ.
     *
     * @param message ��ϸ��Ϣ
     */
    public ChainedException(String message) {
        super(message);
    }

    /**
     * ����һ���쳣, ָ���쳣����ϸ��Ϣ.
     *
     * @param message ��ϸ��Ϣ
     * @param errorCode ������
     */
    public ChainedException(String message, Enum errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * ����һ���쳣, ָ����������쳣������.
     *
     * @param cause �쳣������
     */
    public ChainedException(Throwable cause) {
        super((cause == null) ? null
                              : cause.getMessage());
        this.cause = cause;
    }

    /**
     * ����һ���쳣, ָ����������쳣������.
     *
     * @param message ��ϸ��Ϣ
     * @param cause �쳣������
     */
    public ChainedException(String message, Throwable cause) {
        super(message);
        this.cause = cause;
    }

    /**
     * ����һ���쳣, ָ����������쳣������.
     *
     * @param message ��ϸ��Ϣ
     * @param cause �쳣������
     * @param errorCode ������
     */
    public ChainedException(String message, Throwable cause, Enum errorCode) {
        super(message);
        this.cause         = cause;
        this.errorCode     = errorCode;
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
     * @param stream ����ֽ���.
     */
    public void printStackTrace(PrintStream stream) {
        delegate.printStackTrace(stream);
    }

    /**
     * ��ӡ����ջ��ָ�������.
     *
     * @param writer ����ַ���.
     */
    public void printStackTrace(PrintWriter writer) {
        delegate.printStackTrace(writer);
    }

    /**
     * ��ӡ�쳣�ĵ���ջ, �����������쳣����Ϣ.
     *
     * @param writer ��ӡ�������
     */
    public void printCurrentStackTrace(PrintWriter writer) {
        super.printStackTrace(writer);
    }
}
