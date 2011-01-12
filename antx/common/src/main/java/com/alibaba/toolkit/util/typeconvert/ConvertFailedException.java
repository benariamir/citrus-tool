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
package com.alibaba.toolkit.util.typeconvert;

import com.alibaba.toolkit.util.exception.ChainedRuntimeException;

/**
 * ��ʾת��ʧ�ܵ��쳣.  ת��ʧ��ʱ, ����ָ��һ�������Ĭ��ֵ.
 *
 * @version $Id: ConvertFailedException.java,v 1.1 2003/07/03 07:26:36 baobao Exp $
 * @author Michael Zhou
 */
public class ConvertFailedException extends ChainedRuntimeException {
    private static final long serialVersionUID = -3145089557163861714L;
    private Object  defaultValue;
    private boolean defaultValueSet = false;

    /**
     * ����һ���յ��쳣.
     */
    public ConvertFailedException() {
        super();
    }

    /**
     * ����һ���쳣, ָ���쳣����ϸ��Ϣ.
     *
     * @param message  ��ϸ��Ϣ
     */
    public ConvertFailedException(String message) {
        super(message);
    }

    /**
     * ����һ���쳣, ָ����������쳣������.
     *
     * @param cause  �쳣������
     */
    public ConvertFailedException(Throwable cause) {
        super(cause);
    }

    /**
     * ����һ���쳣, ָ����������쳣������.
     *
     * @param message  ��ϸ��Ϣ
     * @param cause    �쳣������
     */
    public ConvertFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * ���ý����Ĭ��ֵ.
     *
     * @param defaultValue Ĭ��ֵ
     *
     * @return �쳣����
     */
    public ConvertFailedException setDefaultValue(Object defaultValue) {
        this.defaultValue    = defaultValue;
        this.defaultValueSet = true;
        return this;
    }

    /**
     * ȡ��Ĭ��ֵ.
     *
     * @return Ĭ��ֵ����
     */
    public Object getDefaultValue() {
        return defaultValue;
    }

    /**
     * �Ƿ�������Ĭ��ֵ.
     *
     * @return ���������Ĭ��ֵ, �򷵻�<code>true</code>
     */
    public boolean isDefaultValueSet() {
        return defaultValueSet;
    }
}
