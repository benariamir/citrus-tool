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

package com.alibaba.toolkit.util.resourcebundle.xml;

import com.alibaba.toolkit.util.resourcebundle.AbstractResourceBundleFactory;
import com.alibaba.toolkit.util.resourcebundle.ResourceBundle;
import com.alibaba.toolkit.util.resourcebundle.ResourceBundleConstant;
import com.alibaba.toolkit.util.resourcebundle.ResourceBundleCreateException;
import com.alibaba.toolkit.util.resourcebundle.ResourceBundleLoader;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;

import org.dom4j.io.SAXReader;

/**
 * ��XML�ļ��д���<code>ResourceBundle</code>��ʵ���Ĺ���.
 *
 * @version $Id: XMLResourceBundleFactory.java,v 1.1 2003/07/03 07:26:35 baobao Exp $
 * @author Michael Zhou
 */
public class XMLResourceBundleFactory extends AbstractResourceBundleFactory {
    /**
     * ����factory, ʹ�õ�ǰ�̵߳�context class loader��Ϊbundleװ����.
     */
    public XMLResourceBundleFactory() {
        super();
    }

    /**
     * ����factory, ʹ��ָ����class loader��Ϊbundleװ����.
     *
     * @param classLoader װ��bundle��class loader
     */
    public XMLResourceBundleFactory(ClassLoader classLoader) {
        super(classLoader);
    }

    /**
     * ����factory, ʹ��ָ����loader��Ϊbundleװ��
     *
     * @param loader bundleװ����
     */
    public XMLResourceBundleFactory(ResourceBundleLoader loader) {
        super(loader);
    }

    /**
     * ����bundle������ȡ��resource���ļ�����.
     *
     * @param bundleName  bundle������
     *
     * @return resource������
     */
    protected String getFilename(String bundleName) {
        return super.getFilename(bundleName) + ResourceBundleConstant.RB_RESOURCE_EXT_XML;
    }

    /**
     * ��XML��ʽ����������, ������<code>ResourceBundle</code>.
     *
     * @param stream    ������
     * @param systemId  ��־���������ַ���
     *
     * @return resource bundle
     *
     * @throws ResourceBundleCreateException �������ʧ��
     */
    protected ResourceBundle parse(InputStream stream, String systemId)
            throws ResourceBundleCreateException {
        try {
            SAXReader reader = new SAXReader();
            Document  doc = reader.read(stream, systemId);

            return new XMLResourceBundle(doc);
        } catch (DocumentException e) {
            throw new ResourceBundleCreateException(ResourceBundleConstant.RB_FAILED_READING_XML_DOCUMENT,
                                                    new Object[] {
                systemId
            }, e);
        }
    }
}
