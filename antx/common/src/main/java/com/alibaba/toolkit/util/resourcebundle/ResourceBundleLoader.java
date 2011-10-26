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

import java.io.InputStream;

/**
 * װ��resource bundle������.
 *
 * @version $Id: ResourceBundleLoader.java,v 1.1 2003/07/03 07:26:35 baobao Exp $
 * @author Michael Zhou
 */
public interface ResourceBundleLoader {
    /**
     * ����ָ����bundle�ļ�����, ȡ��������.
     *
     * <p>
     * ע��, �˴��Ĳ���Ϊ�ļ���, ������bundle������, <code>ResourceBundleFactory</code>�����ڵ��ô˷���ǰ, ��bundle������ת�����ļ���.
     * ����, bundle��ΪbaseName_langauge_country, ���ļ���������baseName_language_country.xml.
     * </p>
     *
     * @param bundleFilename Ҫ���ҵ�bundle�ļ���
     *
     * @return bundle��������, ���ָ��bundle�ļ�������, �򷵻�<code>null</code>
     *
     * @throws ResourceBundleCreateException ����ļ�����, ����ȡ������ʧ��
     */
    InputStream openStream(String bundleFilename) throws ResourceBundleCreateException;

    /**
     * �ж�����<code>ResourceBundleLoader</code>�Ƿ��Ч. �⽫��Ϊ<code>ResourceBundle</code>��cache������.
     *
     * @param obj Ҫ�Ƚϵ���һ������
     *
     * @return �����Ч, �򷵻�<code>true</code>
     */
    boolean equals(Object obj);

    /**
     * ȡ��hashֵ.  ��Ч��<code>ResourceBundleLoader</code>Ӧ�þ�����ͬ��hashֵ.
     *
     * @return hashֵ
     */
    int hashCode();
}
