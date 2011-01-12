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
package com.alibaba.toolkit.util.exception;

import com.alibaba.toolkit.util.enumeration.Enum;
import com.alibaba.toolkit.util.resourcebundle.MessageBuilder;

import java.util.ResourceBundle;

/**
 * ����Я�����������쳣�Ĵ�����Ϣ.
 *
 * @version $Id: ErrorCodeMessageBuilder.java,v 1.1 2003/07/03 07:26:22 baobao Exp $
 * @author Michael Zhou
 */
public class ErrorCodeMessageBuilder extends MessageBuilder {
    protected static final String STRING_ERROR_CODE_PREFIX = "ERR-";
    protected static final String STRING_ERROR_CODE_SUFFIX = ": ";

    /**
     * ����һ��<code>ErrorCodeMessageBuilder</code>.
     *
     * @param bundleName  ������Ϣ����Դ������
     * @param errorCode   �������
     *
     * @throws MissingResourceException  ָ��bundleδ�ҵ�, �򴴽�bundle����
     */
    public ErrorCodeMessageBuilder(String bundleName, Enum errorCode) {
        super(bundleName, errorCode);
    }

    /**
     * ����һ��<code>ErrorCodeMessageBuilder</code>.
     *
     * @param bundle     ������Ϣ����Դ��
     * @param errorCode  �������
     */
    public ErrorCodeMessageBuilder(ResourceBundle bundle, Enum errorCode) {
        super(bundle, errorCode);
    }

    /**
     * ȡ�ô�����Ϣ.
     *
     * @param message ������Ϣ
     *
     * @return ������Ϣ
     */
    public String toString(String message) {
        return new StringBuffer(STRING_ERROR_CODE_PREFIX).append(((Enum) key).toHexString())
                                                         .append(STRING_ERROR_CODE_SUFFIX)
                                                         .append(getMessage()).toString();
    }
}
