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

import com.alibaba.toolkit.util.ContextClassLoader;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

/**
 * ͨ��<code>ClassLoader</code>װ��resource bundle������.
 *
 * @version $Id: ClassLoaderResourceBundleLoader.java,v 1.1 2003/07/03 07:26:34 baobao Exp $
 * @author Michael Zhou
 */
public class ClassLoaderResourceBundleLoader implements ResourceBundleLoader {
    private ClassLoader classLoader;

    /**
     * ������loader, ʹ�õ�ǰ�̵߳�context class loader.
     */
    public ClassLoaderResourceBundleLoader() {
        this(null);
    }

    /**
     * ������loader, ʹ��ָ����class loader.
     *
     * @param classLoader ָ����class loader
     */
    public ClassLoaderResourceBundleLoader(ClassLoader classLoader) {
        if (classLoader == null) {
            this.classLoader = ContextClassLoader.getClassLoader();

            if (this.classLoader == null) {
                this.classLoader = ClassLoader.getSystemClassLoader();
            }
        } else {
            this.classLoader = classLoader;
        }
    }

    /**
     * ����ָ����bundle����, ȡ��������.
     *
     * @param bundleFilename Ҫ���ҵ�bundle��
     *
     * @return bundle��������, ���ָ��bundle�ļ�������, �򷵻�<code>null</code>
     *
     * @throws ResourceBundleCreateException ����ļ�����, ����ȡ������ʧ��
     */
    public InputStream openStream(final String bundleFilename)
            throws ResourceBundleCreateException {
        try {
            return (InputStream) AccessController.doPrivileged(new PrivilegedExceptionAction() {
                public Object run() throws ResourceBundleCreateException {
                    URL url = classLoader.getResource(bundleFilename);

                    // �����Դ������, �򷵻�null
                    if (url == null) {
                        return null;
                    }

                    try {
                        return url.openStream();
                    } catch (IOException e) {
                        throw new ResourceBundleCreateException(ResourceBundleConstant.RB_FAILED_OPENING_STREAM,
                                                                new Object[] {
                            bundleFilename
                        }, e);
                    }
                }
            });
        } catch (PrivilegedActionException e) {
            throw (ResourceBundleCreateException) e.getException();
        }
    }

    /**
     * �ж�����<code>ResourceBundleLoader</code>�Ƿ��Ч. �⽫��Ϊ<code>ResourceBundle</code>��cache������.
     * ������ͬ��context class loader��<code>ResourceBundleLoader</code>�ǵ�Ч��.
     *
     * @param obj Ҫ�Ƚϵ���һ������
     *
     * @return �����Ч, �򷵻�<code>true</code>
     */
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof ClassLoaderResourceBundleLoader)) {
            return false;
        }

        return ((ClassLoaderResourceBundleLoader) obj).classLoader == classLoader;
    }

    /**
     * ȡ��hashֵ.  ��Ч��<code>ResourceBundleLoader</code>Ӧ�þ�����ͬ��hashֵ.
     *
     * @return hashֵ
     */
    public int hashCode() {
        return (classLoader == null) ? 0
                                     : classLoader.hashCode();
    }
}
