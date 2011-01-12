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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * �����<code>ResourceBundleFactory</code>, ʵ����ͨ�õĴ���<code>ResourceBundle</code>ʵ���ķ���.
 *
 * <p>
 * ���ʵ��ͨ��<code>ResourceBundleLoader</code>װ��bundle�������ļ�,
 * Ȼ��ִ��<code>parse</code>����ȡ��<code>ResourceBundle</code>ʵ��.
 * </p>
 *
 * <p>
 * ��չ�����ͨ���ṩ�ʵ���<code>ResourceBundleLoader</code>���ı�bundle��װ�뷽ʽ, ������ļ�ϵͳ�����ݿ���װ��.
 * ͨ������<code>parse</code>����, ���Ըı��ȡ�ļ��ĸ�ʽ, ����<code>XMLResourceBundleFactory</code>��XML�ĸ�ʽ�������ļ�.
 * </p>
 *
 * @version $Id
 * @author Michael Zhou
 */
public abstract class AbstractResourceBundleFactory
        extends ResourceBundleFactory {
    private final ResourceBundleLoader loader;

    /**
     * ����factory, ʹ�õ�ǰ�̵߳�context class loader��Ϊbundleװ����.
     */
    public AbstractResourceBundleFactory() {
        this(new ClassLoaderResourceBundleLoader());
    }

    /**
     * ����factory, ʹ��ָ����class loader��Ϊbundleװ����.
     *
     * @param classLoader װ��bundle��class loader
     */
    public AbstractResourceBundleFactory(ClassLoader classLoader) {
        this(new ClassLoaderResourceBundleLoader(classLoader));
    }

    /**
     * ����factory, ʹ��ָ����loader��Ϊbundleװ��
     *
     * @param loader bundleװ����
     */
    public AbstractResourceBundleFactory(ResourceBundleLoader loader) {
        this.loader = loader;
    }

    /**
     * ȡ��<code>ResourceBundleLoader</code>.
     *
     * @return loader
     */
    public ResourceBundleLoader getLoader() {
        return loader;
    }

    /**
     * ����<code>ResourceBundle</code>��ʵ��.
     *
     * @param bundleName  Ҫ������bundle����
     *
     * @return �´�����<code>ResourceBundle</code>ʵ��, ���ָ��bundle������, �򷵻�<code>null</code>
     *
     * @throws ResourceBundleCreateException ָ��bundle�ļ�����, ������bundleʵ��ʧ��, �����ļ���ʽ����
     */
    public ResourceBundle createBundle(String bundleName)
            throws ResourceBundleCreateException {
        InputStream stream   = null;
        String      filename = getFilename(bundleName);

        if (loader != null) {
            stream = loader.openStream(filename);
        }

        // ����ļ�������, �򷵻�null.
        if (stream == null) {
            return null;
        }

        try {
            
            // @TODO: �˴������URL��Ϊsystem ID
            return parse(new BufferedInputStream(stream), filename);
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * ����bundle������ȡ��resource���ļ�����.
     *
     * @param bundleName  bundle������
     *
     * @return resource������
     */
    protected String getFilename(String bundleName) {
        return bundleName.replace('.', '/');
    }

    /**
     * ����������, ���д���<code>ResourceBundle</code>.
     *
     * @param stream    ������
     * @param systemId  ��׼���������ַ���(һ�����ļ���)
     *
     * @return resource bundle
     *
     * @throws ResourceBundleCreateException �������ʧ��
     */
    protected abstract ResourceBundle parse(InputStream stream, String systemId)
            throws ResourceBundleCreateException;

    /**
     * �Ƚ�����factory�Ƿ��Ч.  ���ڵ�Ч��factory, ������ͬ��bundle��, ����<code>createBundle</code>����,
     * ���Եõ���Ч��bundleʵ��.
     *
     * @param other Ҫ�Ƚϵ�factory
     *
     * @return �����Ч, �򷵻�<code>true</code>
     */
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        try {
            AbstractResourceBundleFactory otherFactory = (AbstractResourceBundleFactory) other;

            return (loader == null)
                       ? (otherFactory.loader == null)
                       : loader.equals(otherFactory.loader);
        } catch (NullPointerException npe) {
            return false;
        } catch (ClassCastException cce) {
            return false;
        }
    }

    /**
     * ȡ��factory��hashֵ, �������factory��Ч, �����ǵ�hashֵҲ���.
     *
     * @return factory��hashֵ
     */
    public int hashCode() {
        return (loader == null) ? 0
                                : loader.hashCode();
    }
}
