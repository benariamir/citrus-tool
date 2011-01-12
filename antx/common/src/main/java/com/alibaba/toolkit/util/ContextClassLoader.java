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
package com.alibaba.toolkit.util;

import com.alibaba.toolkit.util.collection.ArrayHashSet;

import java.io.IOException;
import java.io.InputStream;

import java.lang.reflect.Method;

import java.net.URL;

import java.util.Enumeration;
import java.util.Set;

/**
 * <p>
 * ���Ҳ�װ�������Դ�ĸ�����.
 * </p>
 *
 * <p>
 * <code>ClassFinder</code>���������Դ��Ч��,
 * �൱��<code>ClassLoader.loadClass</code>������<code>ClassLoader.getResource</code>����.
 * ��<code>ClassFinder</code>�������ȳ��Դ�<code>Thread.getContextClassLoader()</code>����ȡ��<code>ClassLoader</code>�в�װ�������Դ.
 * ���ַ����������ڶ༶<code>ClassLoader</code>�������, �Ҳ��������Դ�����.
 * </p>
 *
 * <p>
 * �������������:
 * </p>
 *
 * <ul>
 * <li>
 * ������<code>A</code>�Ǵ�ϵͳ<code>ClassLoader</code>װ���(classpath)
 * </li>
 * <li>
 * ��<code>B</code>��Web Application�е�һ����, ����servlet�����<code>ClassLoader</code>��̬װ���
 * </li>
 * <li>
 * ��Դ�ļ�<code>C.properties</code>Ҳ��Web Application��, ֻ��servlet����Ķ�̬<code>ClassLoader</code>�����ҵ���
 * </li>
 * <li>
 * ��<code>B</code>���ù�����<code>A</code>�ķ���, ϣ��ͨ����<code>A</code>ȡ����Դ�ļ�<code>C.properties</code>
 * </li>
 * </ul>
 *
 * <p>
 * �����<code>A</code>ʹ��<code>getClass().getClassLoader().getResource(&quot;C.properties&quot;)</code>,
 * �ͻ�ʧ��, ��Ϊϵͳ<code>ClassLoader</code>�����ҵ�����Դ.
 * ����A����ʹ��ClassFinder.getResource(&quot;C.properties&quot;), �Ϳ����ҵ������Դ,
 * ��ΪClassFinder����<code>Thread.currentThead().getContextClassLoader()</code>ȡ����servlet�����<code>ClassLoader</code>,
 * �Ӷ��ҵ��������Դ�ļ�.
 * </p>
 *
 * <p>
 * ע��, <code>Thread.getContextClassLoader()</code>����JDK1.2֮����е�, ���ڵͰ汾��JDK,
 * <code>ClassFinder</code>��Ч����ֱ�ӵ���<code>ClassLoader</code>��ȫ��ͬ.
 * </p>
 *
 * @version $Id: ContextClassLoader.java,v 1.1 2003/07/03 07:26:15 baobao Exp $
 * @author Michael Zhou
 */
public class ContextClassLoader {
    /**
     * JDK1.2����, �������������<code>Thread.getContextClassLoader()</code>����. ���ڵͰ汾��JDK,
     * �˱���Ϊ<code>null</code>.
     */
    private static Method GET_CONTEXT_CLASS_LOADER_METHOD = null;

    static {
        try {
            GET_CONTEXT_CLASS_LOADER_METHOD = Thread.class.getMethod("getContextClassLoader", null);
        } catch (NoSuchMethodException e) {
            // JDK 1.2����.
        }
    }

    /**
     * <p>
     * ��<code>ClassLoader</code>ȡ������resource URL. ������˳�����:
     * </p>
     *
     * <ol>
     * <li>
     * �ڵ�ǰ�̵߳�<code>ClassLoader</code>�в���.
     * </li>
     * <li>
     * ��װ���Լ���<code>ClassLoader</code>�в���.
     * </li>
     * <li>
     * ͨ��<code>ClassLoader.getSystemResource</code>��������.
     * </li>
     * </ol>
     *
     *
     * @param resourceName Ҫ���ҵ���Դ��, ������&quot;/&quot;�ָ��ı�ʶ���ַ���
     *
     * @return resource��URL����, ���û�ҵ�, �򷵻ؿ�����. �����б�֤�������ظ���URL.
     */
    public static URL[] getResources(String resourceName) {
        ClassLoader classLoader = null;
        Set         urlSet = new ArrayHashSet();
        boolean     found  = false;


        // �������Ŵӵ�ǰ�̵߳�ClassLoader�в���.
        found = getResources(urlSet, resourceName, getClassLoader(), false);

        // ���û�ҵ�, ���Ŵ�װ���Լ���ClassLoader�в���.
        if (!found) {
            getResources(urlSet, resourceName, ContextClassLoader.class.getClassLoader(), false);
        }

        // ���ĳ���: ��ϵͳClassLoader�в���(JDK1.2����),
        // ������JDK���ڲ�ClassLoader�в���(JDK1.2����).
        if (!found) {
            getResources(urlSet, resourceName, null, true);
        }

        if (found) {
            return (URL[]) urlSet.toArray(new URL[urlSet.size()]);
        }

        return new URL[0];
    }

    /**
     * ��ָ��class loader�в���ָ�����Ƶ�resource, �������ҵ���resource��URL����ָ���ļ�����.
     *
     * @param urlSet          ���resource URL�ļ���
     * @param resourceName    ��Դ��
     * @param classLoader     ��װ����
     * @param sysClassLoader  �Ƿ���system class loaderװ����Դ
     *
     * @return ����ҵ�, �򷵻�<code>true</code>
     */
    private static boolean getResources(Set urlSet, String resourceName, ClassLoader classLoader,
                                        boolean sysClassLoader) {
        Enumeration i = null;

        try {
            if (classLoader != null) {
                i = classLoader.getResources(resourceName);
            } else if (sysClassLoader) {
                i = ClassLoader.getSystemResources(resourceName);
            }
        } catch (IOException e) {
        }

        if ((i != null) && i.hasMoreElements()) {
            while (i.hasMoreElements()) {
                urlSet.add(i.nextElement());
            }

            return true;
        }

        return false;
    }

    /**
     * <p>
     * ��<code>ClassLoader</code>ȡ��resource URL. ������˳�����:
     * </p>
     *
     * <ol>
     * <li>
     * �ڵ�ǰ�̵߳�<code>ClassLoader</code>�в���.
     * </li>
     * <li>
     * ��װ���Լ���<code>ClassLoader</code>�в���.
     * </li>
     * <li>
     * ͨ��<code>ClassLoader.getSystemResource</code>��������.
     * </li>
     * </ol>
     *
     *
     * @param resourceName Ҫ���ҵ���Դ��, ������&quot;/&quot;�ָ��ı�ʶ���ַ���
     *
     * @return resource��URL
     */
    public static URL getResource(String resourceName) {
        ClassLoader classLoader = null;
        URL         url = null;


        // �������Ŵӵ�ǰ�̵߳�ClassLoader�в���.
        classLoader = getClassLoader();

        if (classLoader != null) {
            url = classLoader.getResource(resourceName);

            if (url != null) {
                return url;
            }
        }


        // ���û�ҵ�, ���Ŵ�װ���Լ���ClassLoader�в���.
        classLoader = ContextClassLoader.class.getClassLoader();

        if (classLoader != null) {
            url = classLoader.getResource(resourceName);

            if (url != null) {
                return url;
            }
        }

        // ���ĳ���: ��ϵͳClassLoader�в���(JDK1.2����),
        // ������JDK���ڲ�ClassLoader�в���(JDK1.2����).
        return ClassLoader.getSystemResource(resourceName);
    }

    /**
     * ��<code>ClassLoader</code>ȡ��resource��������.
     * �൱��<code>getResource(resourceName).openStream()</code>.
     *
     * @param resourceName Ҫ���ҵ���Դ��, ������"/"�ָ��ı�ʶ���ַ���
     *
     * @return resource��������
     */
    public static InputStream getResourceAsStream(String resourceName) {
        URL url = getResource(resourceName);

        try {
            if (url != null) {
                return url.openStream();
            }
        } catch (IOException e) {
            // ��URLʧ��.
        }

        return null;
    }

    /**
     * �ӵ�ǰ�̵߳�<code>ClassLoader</code>װ����.  ����JDK1.2����, ���൱��<code>Class.forName</code>.
     *
     * @param className Ҫװ�������
     *
     * @return ��װ�����
     *
     * @throws ClassNotFoundException �����û�ҵ�
     */
    public static Class loadClass(String className)
            throws ClassNotFoundException {
        return loadClass(className, true, null);
    }

    /**
     * ��ָ����<code>ClassLoader</code>��װ����.  ���δָ��<code>ClassLoader</code>,
     * ��ӵ�ǰ�̵߳�<code>ClassLoader</code>��װ��.
     *
     * @param className   Ҫװ�������
     * @param initialize  �Ƿ�Ҫ��ʼ����
     * @param classLoader ��ָ����<code>ClassLoader</code>��װ����
     *
     * @return ��װ�����
     *
     * @throws ClassNotFoundException �����û�ҵ�
     */
    public static Class loadClass(String className, boolean initialize, ClassLoader classLoader)
            throws ClassNotFoundException {
        if (classLoader == null) {
            classLoader = getClassLoader();
        }

        return Class.forName(className, initialize, classLoader);
    }

    /**
     * ȡ�õ�ǰ�̵߳�<code>ClassLoader</code>. ���������ҪJDK1.2����߰汾��JDK��֧��.
     *
     * @return ���JDK��1.2��ǰ�汾, ����null. ���򷵻ص�ǰ�̵߳�<code>ClassLoader</code>.
     */
    public static ClassLoader getClassLoader() {
        if (GET_CONTEXT_CLASS_LOADER_METHOD != null) {
            try {
                return (ClassLoader) GET_CONTEXT_CLASS_LOADER_METHOD.invoke(Thread.currentThread(),
                                                                            null);
            } catch (Throwable e) {
                return null;
            }
        }

        return null;
    }
}
