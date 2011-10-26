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

package com.alibaba.toolkit.util.resourcebundle;

import com.alibaba.toolkit.util.exception.ChainedException;

import java.text.MessageFormat;

/**
 * ��ʾ����<code>ResourceBundle</code>ʧ�ܵ��쳣.
 *
 * @version $Id: ResourceBundleCreateException.java,v 1.1 2003/07/03 07:26:35 baobao Exp $
 * @author Michael Zhou
 */
public class ResourceBundleCreateException extends ChainedException {
    private static final long serialVersionUID = -1816609850584933734L;

    /**
     * ����һ���쳣, ָ����������쳣������.
     *
     * @param messageId  ��ϸ��ϢID
     * @param params     ��ϸ��Ϣ����
     * @param cause      �쳣������
     */
    public ResourceBundleCreateException(String messageId, Object[] params, Throwable cause) {
        super(MessageFormat.format(messageId,
                                   (params == null) ? new Object[0]
                                                    : params), cause);
    }
}
