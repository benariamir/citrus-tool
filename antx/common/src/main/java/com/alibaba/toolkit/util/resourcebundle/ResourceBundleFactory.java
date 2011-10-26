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

import com.alibaba.toolkit.util.collection.SoftHashMap;
import com.alibaba.toolkit.util.resourcebundle.xml.XMLResourceBundleFactory;

import java.lang.ref.SoftReference;

import java.text.MessageFormat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * ����<code>ResourceBundle</code>��ʵ���Ĺ���.
 *
 * @version $Id: ResourceBundleFactory.java,v 1.1 2003/07/03 07:26:35 baobao Exp $
 * @author Michael Zhou
 */
public abstract class ResourceBundleFactory {
    /**
     * ʹ��ָ����bundle������, Ĭ�ϵ�locale, Ĭ�ϵ�factory��ȡ��resource bundle. Ĭ�ϵ�factory�Ǵ��̵߳�context class
     * loader��ȡ����Դ�ļ�, ����XML�ĸ�ʽ������Դ�ļ�.
     *
     * @param baseName  bundle�Ļ�����
     *
     * @return resource bundle
     *
     * @throws MissingResourceException  ָ��bundleδ�ҵ�, �򴴽�bundle����
     */
    public static final ResourceBundle getBundle(String baseName) {
        return getBundle(baseName, null, (ResourceBundleFactory) null);
    }

    /**
     * ʹ��ָ����bundle������, ָ����locale, Ĭ�ϵ�factory��ȡ��resource bundle. Ĭ�ϵ�factory�Ǵ��̵߳�context class
     * loader��ȡ����Դ�ļ�, ����XML�ĸ�ʽ������Դ�ļ�.
     *
     * @param baseName  bundle�Ļ�����
     * @param locale    ��������
     *
     * @return resource bundle
     *
     * @throws MissingResourceException  ָ��bundleδ�ҵ�, �򴴽�bundle����
     */
    public static final ResourceBundle getBundle(String baseName, Locale locale) {
        return getBundle(baseName, locale, (ResourceBundleFactory) null);
    }

    /**
     * ʹ��ָ����bundle������, ָ����locale, Ĭ�ϵ�factory��ȡ��resource bundle. Ĭ�ϵ�factory�Ǵ�ָ����class loader��ȡ����Դ�ļ�,
     * ����XML�ĸ�ʽ������Դ�ļ�.
     *
     * @param baseName    bundle�Ļ�����
     * @param locale      ��������
     * @param classLoader class loader
     *
     * @return resource bundle
     *
     * @throws MissingResourceException  ָ��bundleδ�ҵ�, �򴴽�bundle����
     */
    public static final ResourceBundle getBundle(String baseName, Locale locale,
                                                 ClassLoader classLoader) {
        return getBundle(baseName, locale, new XMLResourceBundleFactory(classLoader));
    }

    /**
     * ʹ��ָ����bundle������, ָ����locale, ָ����loader, Ĭ�ϵ�factory��ȡ��resource bundle.
     * Ĭ�ϵ�factory�Ǵ�ָ����loader��ȡ����Դ�ļ�, ����XML�ĸ�ʽ������Դ�ļ�.
     *
     * @param baseName  bundle�Ļ�����
     * @param locale    ��������
     * @param loader    bundle��װ����
     *
     * @return resource bundle
     *
     * @throws MissingResourceException  ָ��bundleδ�ҵ�, �򴴽�bundle����
     */
    public static final ResourceBundle getBundle(String baseName, Locale locale,
                                                 ResourceBundleLoader loader) {
        return getBundle(baseName, locale, new XMLResourceBundleFactory(loader));
    }

    /**
     * ʹ��ָ����bundle������, ָ����locale, ָ����factory��ȡ��resource bundle.
     *
     * @param baseName  bundle�Ļ�����
     * @param locale    ��������
     * @param factory   bundle����
     *
     * @return resource bundle
     *
     * @throws MissingResourceException  ָ��bundleδ�ҵ�, �򴴽�bundle����
     */
    public static final ResourceBundle getBundle(String baseName, Locale locale,
                                                 ResourceBundleFactory factory) {
        if (locale == null) {
            locale = Locale.getDefault();
        }

        if (factory == null) {
            factory = new XMLResourceBundleFactory();
        }

        return Helper.getBundleImpl(baseName, locale, factory);
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
    public abstract ResourceBundle createBundle(String bundleName)
            throws ResourceBundleCreateException;

    /**
     * �ж�����<code>ResourceBundleFactory</code>�Ƿ��Ч. �⽫��Ϊ<code>ResourceBundle</code>��cache������.
     *
     * @param obj Ҫ�Ƚϵ���һ������
     *
     * @return �����Ч, �򷵻�<code>true</code>
     */
    public abstract boolean equals(Object obj);

    /**
     * ȡ��hashֵ.  ��Ч��<code>ResourceBundleFactory</code>Ӧ�þ�����ͬ��hashֵ.
     *
     * @return hashֵ
     */
    public abstract int hashCode();

    /**
     * ���Һʹ���bundle����.
     */
    private static final class Helper {
        /** ��(factory, bundleName, defaultLocale)ӳ�䵽bundle�����cache. ���ڴ治��ʱ, cache�����ݻ��Զ��ͷ�. */
        private static final Cache cache = new Cache();

        /**
         * ʹ��ָ����bundle������, ָ����locale, ָ����factory��ȡ��resource bundle.
         *
         * @param baseName  bundle�Ļ�����
         * @param locale    ��������
         * @param factory   bundle����
         *
         * @return resource bundle
         *
         * @throws MissingResourceException  ָ��bundleδ�ҵ�, �򴴽�bundle����
         */
        private static ResourceBundle getBundleImpl(String baseName, Locale locale,
                                                    ResourceBundleFactory factory) {
            if (baseName == null) {
                throw new NullPointerException(ResourceBundleConstant.RB_BASE_NAME_IS_NULL);
            }

            // ʹ��factory��Ϊbundleδ�ҵ��ı��, ������factory��GC���յ�ʱ��, cache���Ӧ����Ҳ���Ա�����.
            final Object NOT_FOUND = factory;

            // ��cache��ȡ��bundle.
            String bundleName   = baseName;
            String localeSuffix = locale.toString();

            if (localeSuffix.length() > 0) {
                bundleName += ("_" + localeSuffix);
            } else if (locale.getVariant().length() > 0) {
                // ����: new Locale("", "", "VARIANT").toString == ""
                bundleName += ("___" + locale.getVariant());
            }

            // ȡ��ϵͳlocale, ע��, ���ֵ���ܱ��ı�, ����ÿ��ִ��ʱ������ȡ.
            Locale defaultLocale = Locale.getDefault();

            Object lookup = cache.get(factory, bundleName, defaultLocale);

            if (NOT_FOUND.equals(lookup)) {
                throwResourceBundleException(true, baseName, locale, null);
            } else if (lookup != null) {
                return (ResourceBundle) lookup;
            }

            // ��ʼ���Ҳ�����bundle.
            Object parent = NOT_FOUND;

            try {
                // ����base bundle.
                Object root = findBundle(factory, baseName, defaultLocale, baseName, null,
                                         NOT_FOUND);

                if (root == null) {
                    root = NOT_FOUND;
                    cache.put(factory, baseName, defaultLocale, root);
                }

                // ������Ҫ·��, ����getBundle("baseName", new Locale("zh", "CN", "Variant")),
                // ��Ҫ·��ΪbaseName_zh, baseName_zh_CN, baseName_zh_CN_Varient.
                final List names        = calculateBundleNames(baseName, locale);
                List       bundlesFound = new ArrayList(ResourceBundleConstant.MAX_BUNDLES_SEARCHED);

                // ���base bundle�Ѿ��ҵ�, ������·��Ϊ��.
                boolean foundInMainBranch = (!NOT_FOUND.equals(root) && names.size() == 0);

                if (!foundInMainBranch) {
                    parent = root;

                    for (int i = 0; i < names.size(); i++) {
                        bundleName = (String) names.get(i);
                        lookup     = findBundle(factory, bundleName, defaultLocale, baseName,
                                                parent, NOT_FOUND);
                        bundlesFound.add(lookup);

                        if (lookup != null) {
                            parent            = lookup;
                            foundInMainBranch = true;
                        }
                    }
                }


                // �����·��δ�ҵ�bundle, �����ϵͳĬ��·��, ���統ǰϵͳĬ��localeΪen_US,
                // ������·��Ϊ: baseName_en, baseName_US.
                parent = root;

                if (!foundInMainBranch) {
                    final List fallbackNames = calculateBundleNames(baseName, defaultLocale);

                    for (int i = 0; i < fallbackNames.size(); i++) {
                        bundleName = (String) fallbackNames.get(i);

                        // ���ϵͳĬ��·������·��һ��, ����Ҫ������ȥ��
                        if (names.contains(bundleName)) {
                            break;
                        }

                        lookup = findBundle(factory, bundleName, defaultLocale, baseName, parent,
                                            NOT_FOUND);

                        if (lookup != null) {
                            parent = lookup;
                        } else {
                            // ����bundle���ݸ���bundle, ����:
                            // ��bundle: baseName_en.xml�Ѿ��ҵ�, ��bundle: baseName_en_USδ�ҵ�,
                            // ��cache��:
                            // baseName       => bundle����: baseName.xml
                            // baseName_en    => bundle����: baseName_en.xml
                            // baseName_en_US => bundle����: baseName_en.xml
                            cache.put(factory, bundleName, defaultLocale, parent);
                        }
                    }
                }

                // ����·����, ����bundle���ݸ���bundle, �������������:
                // 1. bundle����·����, ����getBundle("baseName", new Locale("zh", "CN")),
                //    baseName_zh���ҵ�, ��cache��:
                //    baseName       => bundle����: baseName.xml
                //    baseName_zh    => bundle����: baseName_zh.xml
                //    baseName_zh_CN => bundle����: baseName_zh.xml
                //
                // 2. bundle��ϵͳ·����, ��·��δ�ҵ�, ����getBundle("baseName", new Locale("zh", "CN")),
                //    baseName_zh��baseName_zh_CN��δ�ҵ�, ��ϵͳ·����baseName_en���ҵ�, ��cache��:
                //    baseName       => bundle����: baseName.xml
                //    baseName_zh    => bundle����: baseName_en.xml
                //    baseName_zh_CN => bundle����: baseName_en.xml
                //    baseName_en    => bundle����: baseName_en.xml
                //    baseName_en_US => bundle����: baseName_en.xml
                //
                // 3. bundle�Ļ�����δ�ҵ�:
                //    baseName       => NOT_FOUND
                //    baseName_zh    => NOT_FOUND
                //    baseName_zh_CN => NOT_FOUND
                //    baseName_en    => NOT_FOUND
                //    baseName_en_US => NOT_FOUND
                for (int i = 0; i < names.size(); i++) {
                    final String name        = (String) names.get(i);
                    final Object bundleFound = bundlesFound.get(i);

                    if (bundleFound == null) {
                        cache.put(factory, name, defaultLocale, parent);
                    } else {
                        parent = bundleFound;
                    }
                }
            } catch (Exception e) {
                // ������ResourceBundleCreateException������RuntimeException.
                cache.cleanUpConstructionList();
                throwResourceBundleException(false, baseName, locale, e);
            } catch (Error e) {
                cache.cleanUpConstructionList();
                throw e;
            }

            if (NOT_FOUND.equals(parent)) {
                throwResourceBundleException(true, baseName, locale, null);
            }

            return (ResourceBundle) parent;
        }

        /**
         * ��cache�в���bundle, ���factory��װ��bundle.  ����˷�������<code>null</code>, ������߱����Լ�����bundle,
         * ������<code>cache.put</code>����.
         *
         * @param factory       bundle����
         * @param bundleName    bundle����
         * @param defaultLocale ϵͳĬ�ϵ�locale
         * @param baseName      bundle������
         * @param parent        ��bundle, ���ڸ�bundle,  Ϊ<code>null</code>
         * @param NOT_FOUND     ���"δ�ҵ�"״̬�Ķ���
         *
         * @return resource bundle, ����<code>null</code>��ʾbundleδ�ҵ�
         *
         * @throws ResourceBundleCreateException bundle���ҵ�, �����첻�ɹ�
         */
        private static Object findBundle(ResourceBundleFactory factory, String bundleName,
                                         Locale defaultLocale, String baseName, Object parent,
                                         final Object NOT_FOUND)
                throws ResourceBundleCreateException {
            Object result = cache.getWait(factory, bundleName, defaultLocale);

            if (result != null) {
                return result;
            }


            // ���Դ�factory��װ��bundle.
            result = factory.createBundle(bundleName);

            if (result != null) {
                // �ڵ���factoryʱ, �п��ܵݹ�ص�����getBundle����, �������bundle�Ѿ���������.
                // ���������, bundleһ����cache��.  Ϊ��һ����, Ӧ����cache�е�bundle.
                Object otherBundle = cache.get(factory, bundleName, defaultLocale);

                if (otherBundle != null) {
                    result = otherBundle;
                } else {
                    // ����bundle�ĸ�bundle, �������ŵ�cache��.
                    final ResourceBundle bundle = (ResourceBundle) result;

                    if ((!NOT_FOUND.equals(parent)) && (bundle.getParent() == null)) {
                        bundle.setParent((ResourceBundle) parent);
                    }

                    bundle.setBaseName(baseName);
                    bundle.setLocale(baseName, bundleName);
                    cache.put(factory, bundleName, defaultLocale, result);
                }
            }

            return result;
        }

        /**
         * ȡ�ñ�ѡ��bundle��.
         *
         * @param baseName bundle�Ļ�����
         * @param locale   ��������
         *
         * @return ���б�ѡ��bundle��
         */
        private static List calculateBundleNames(String baseName, Locale locale) {
            final List   result         = new ArrayList(ResourceBundleConstant.MAX_BUNDLES_SEARCHED);
            final String language       = locale.getLanguage();
            final int    languageLength = language.length();
            final String country        = locale.getCountry();
            final int    countryLength  = country.length();
            final String variant        = locale.getVariant();
            final int    variantLength  = variant.length();

            // ���locale��("", "", "").
            if ((languageLength + countryLength + variantLength) == 0) {
                return result;
            }

            final StringBuffer buffer = new StringBuffer(baseName);


            // ����baseName_language
            buffer.append('_');
            buffer.append(language);

            if (languageLength > 0) {
                result.add(buffer.toString());
            }

            if ((countryLength + variantLength) == 0) {
                return result;
            }


            // ����baseName_language_country
            buffer.append('_');
            buffer.append(country);

            if (countryLength > 0) {
                result.add(buffer.toString());
            }

            if (variantLength == 0) {
                return result;
            }


            // ����baseName_language_country_variant
            buffer.append('_');
            buffer.append(variant);
            result.add(buffer.toString());

            return result;
        }

        /**
         * ����"resource bundleδ�ҵ�"���쳣.
         *
         * @param missing  ָ��bundleδ�ҵ�, ���Ǵ���bundle����
         * @param baseName δ�ҵ���bundle������
         * @param locale   δ�ҵ���bundle����������
         * @param cause    �쳣����
         */
        private static void throwResourceBundleException(boolean missing, String baseName,
                                                         Locale locale, Throwable cause) {
            String bundleName = baseName + "_" + locale;

            if (missing) {
                throw new ResourceBundleException(ResourceBundleConstant.RB_MISSING_RESOURCE_BUNDLE,
                                                  new Object[] {
                    baseName,
                    locale
                }, cause, bundleName, "");
            } else {
                throw new ResourceBundleException(ResourceBundleConstant.RB_FAILED_LOADING_RESOURCE_BUNDLE,
                                                  new Object[] {
                    baseName,
                    locale
                }, cause, bundleName, "");
            }
        }
    }

    /**
     * ��(factory, bundleName, defaultLocale)ӳ�䵽bundle�����cache��.
     */
    private static final class Cache extends SoftHashMap {
        /** ��̬��key, ������cache�в���bundle. ʹ�þ�̬�����Լ���GC�ĸ���. ʹ��cacheKey���������cache����ͬ��. */
        private static final CacheKey cacheKey = new CacheKey();

        /**
         * ���hash������ͬ������߳�, �Ա�ͬʱװ��ͬһ��bundle. ���hash������cacheKey��thread��ӳ��. ʹ�ô�hash����������cache����ͬ��.
         */
        private final Map underConstruction = new HashMap(
                                                      ResourceBundleConstant.MAX_BUNDLES_SEARCHED,
                                                      ResourceBundleConstant.CACHE_LOAD_FACTOR);

        /**
         * ����һ��cache.
         */
        public Cache() {
            super(ResourceBundleConstant.INITIAL_CACHE_SIZE,
                  ResourceBundleConstant.CACHE_LOAD_FACTOR);
        }

        /**
         * ��cache�в���bundle.
         *
         * @param factory       bundle����
         * @param bundleName    bundle����
         * @param defaultLocale ϵͳlocale
         *
         * @return ��cache��bundle. ���δ�ҵ�, �򷵻�<code>null</code>
         */
        public synchronized Object get(ResourceBundleFactory factory, String bundleName,
                                       Locale defaultLocale) {
            cacheKey.set(factory, bundleName, defaultLocale);

            Object result = get(cacheKey);

            cacheKey.clear();
            return result;
        }

        /**
         * ��cache�в���bundle, ���bundle������, ��������һ���߳����ڹ����bundle, ��ȴ�֮. ����˷�������<code>null</code>,
         * ������߱��븺�����<code>put</code>��<code>cleanUpConstructionList</code>����, �������߳̿��ܵȴ���, ���������.
         *
         * @param factory       bundle����
         * @param bundleName    bundle����
         * @param defaultLocale ϵͳlocale
         *
         * @return ��cache��bundle. ���δ�ҵ�, �򷵻�<code>null</code>
         */
        public synchronized Object getWait(ResourceBundleFactory factory, String bundleName,
                                           Locale defaultLocale) {
            Object result;


            // ���Ȳ���cache���Ƿ��Ѿ������bundle��, �����, ֱ�ӷ���.
            cacheKey.set(factory, bundleName, defaultLocale);
            result = get(cacheKey);

            if (result != null) {
                cacheKey.clear();
                return result;
            }

            // ����ǲ��Ѿ�����һ��thread���ڴ������bundle.
            // ע��, �п��ܵݹ����getBundle����, ����, ��factory�е�����getBundle.
            // ���������, beingBuilt == false
            Thread  builder    = (Thread) underConstruction.get(cacheKey);
            boolean beingBuilt = (builder != null && builder != Thread.currentThread());

            // ����Ѿ�����һ��thread���ڴ������bundle.
            if (beingBuilt) {
                while (beingBuilt) {
                    cacheKey.clear();

                    try {
                        // �ȴ�, ֱ������̴߳������.
                        wait();
                    } catch (InterruptedException e) {
                    }

                    cacheKey.set(factory, bundleName, defaultLocale);
                    beingBuilt = underConstruction.containsKey(cacheKey);
                }


                // �����һ���̰߳����bundle��������, ��ֱ�ӷ��ؼ���
                result = get(cacheKey);

                if (result != null) {
                    cacheKey.clear();
                    return result;
                }
            }


            // ���bundle����cache��, ��׼�������bundle.
            // �����߱�����������put��cleanUpConstructionList����, ���򽫻�����.
            underConstruction.put(cacheKey.clone(), Thread.currentThread());

            cacheKey.clear();

            return null;
        }

        /**
         * ��bundle����cache, ���������еȴ����߳�.
         *
         * @param factory       bundle����
         * @param bundleName    bundle����
         * @param defaultLocale ϵͳlocale
         * @param bundle        ����cache��bundle����
         */
        public synchronized void put(ResourceBundleFactory factory, String bundleName,
                                     Locale defaultLocale, Object bundle) {
            cacheKey.set(factory, bundleName, defaultLocale);

            put(cacheKey.clone(), bundle);

            underConstruction.remove(cacheKey);

            cacheKey.clear();


            // ���������߳�
            notifyAll();
        }

        /**
         * ��"���ڹ���bundle"���̱߳��������ǰ�߳�. ���װ��bundleʧ��, ����Ҫ���ô˷���.
         */
        public synchronized void cleanUpConstructionList() {
            final Collection entries    = underConstruction.values();
            final Thread     thisThread = Thread.currentThread();

            while (entries.remove(thisThread)) {
            }


            // ���������߳�
            notifyAll();
        }
    }

    /**
     * ��bundle��Ӧ��cache key, ��bundle����, bundle����, ϵͳlocale�����ֶ����.
     */
    private static final class CacheKey implements Cloneable {
        private SoftReference factoryRef;
        private String        bundleName;
        private Locale        defaultLocale;
        private int           hashCode;

        /**
         * ����cache key.
         *
         * @param factory       bundle����
         * @param bundleName    bundle����
         * @param defaultLocale ϵͳlocale
         */
        public void set(ResourceBundleFactory factory, String bundleName, Locale defaultLocale) {
            this.bundleName    = bundleName;
            this.hashCode      = bundleName.hashCode();
            this.defaultLocale = defaultLocale;

            if (defaultLocale != null) {
                hashCode ^= defaultLocale.hashCode();
            }

            if (factory == null) {
                this.factoryRef = null;
            } else {
                factoryRef = new SoftReference(factory);
                hashCode ^= factory.hashCode();
            }
        }

        /**
         * ���cache key.
         */
        public void clear() {
            set(null, "", null);
        }

        /**
         * �������key�Ƿ�ƥ��.
         *
         * @param other  ��һ��cache key
         *
         * @return ���ƥ��, �򷵻�<code>true</code>
         */
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }

            try {
                final CacheKey otherKey = (CacheKey) other;

                // hashֵ��ͬ, ����������
                if (hashCode != otherKey.hashCode) {
                    return false;
                }

                // bundle�����Ƿ���ͬ?
                if (!eq(bundleName, otherKey.bundleName)) {
                    return false;
                }

                // locale�Ƿ���ͬ
                if (!eq(defaultLocale, otherKey.defaultLocale)) {
                    return false;
                }

                // factory�Ƿ���ͬ?
                if (factoryRef == null) {
                    return otherKey.factoryRef == null;
                } else {
                    return (otherKey.factoryRef != null)
                           && eq(factoryRef.get(), otherKey.factoryRef.get());
                }
            } catch (NullPointerException e) {
                return false;
            } catch (ClassCastException e) {
                return false;
            }
        }

        /**
         * �Ƚ����������Ƿ����.
         *
         * @param o1 ����1
         * @param o2 ����2
         *
         * @return ������, �򷵻�<code>true</code>
         */
        private boolean eq(Object o1, Object o2) {
            return (o1 == null) ? (o2 == null)
                                : o1.equals(o2);
        }

        /**
         * ȡ��hashֵ, ������������Ч, ��hashֵҲ���.
         *
         * @return hashֵ
         */
        public int hashCode() {
            return hashCode;
        }

        /**
         * ���ƶ���.
         *
         * @return cache key�ĸ���
         */
        public Object clone() {
            try {
                return super.clone();
            } catch (CloneNotSupportedException e) {
                throw new InternalError(MessageFormat.format(
                                                ResourceBundleConstant.RB_CLONE_NOT_SUPPORTED,
                                                new Object[] {
                    CacheKey.class.getName()
                }));
            }
        }

        /**
         * ȡ���ַ���ֵ��ʾ.
         *
         * @return �ַ�����ʾ
         */
        public String toString() {
            return new StringBuffer("CacheKey[factory=").append((factoryRef == null)
                                                                    ? "null"
                                                                    : factoryRef.get())
                                                        .append(", bundleName=").append(bundleName)
                                                        .append(", defaultLocale=")
                                                        .append(defaultLocale).append("]")
                                                        .toString();
        }
    }
}
