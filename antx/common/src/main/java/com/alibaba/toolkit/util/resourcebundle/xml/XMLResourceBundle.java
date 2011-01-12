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
package com.alibaba.toolkit.util.resourcebundle.xml;

import com.alibaba.toolkit.util.ContextClassLoader;
import com.alibaba.toolkit.util.collection.ArrayHashMap;
import com.alibaba.toolkit.util.collection.ListMap;
import com.alibaba.toolkit.util.enumeration.Enum;
import com.alibaba.toolkit.util.resourcebundle.ResourceBundle;
import com.alibaba.toolkit.util.resourcebundle.ResourceBundleConstant;
import com.alibaba.toolkit.util.resourcebundle.ResourceBundleCreateException;
import com.alibaba.toolkit.util.resourcebundle.ResourceBundleEnumeration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Node;

/**
 * XML��ʽ��<code>ResourceBundle</code>.
 *
 * @version $Id: XMLResourceBundle.java,v 1.1 2003/07/03 07:26:35 baobao Exp $
 * @author Michael Zhou
 */
public class XMLResourceBundle extends ResourceBundle {
    protected ListMap values = new ArrayHashMap();

    /**
     * ��XML�ĵ��д���<code>ResourceBundle</code>.
     *
     * @param doc XML�ĵ�
     *
     * @throws ResourceBundleCreateException ��������
     */
    public XMLResourceBundle(Document doc) throws ResourceBundleCreateException {
        // ����group.
        for (Iterator i = doc.selectNodes(ResourceBundleConstant.XPATH_GROUPS).iterator();
             i.hasNext();) {
            Node groupNode = (Node) i.next();

            initGroup(groupNode);
        }

        // ����û��group��resource.
        for (Iterator i = doc.selectNodes(ResourceBundleConstant.XPATH_UNGROUPED_RESOURCES)
                             .iterator(); i.hasNext();) {
            Node resourceNode = (Node) i.next();

            initResource(resourceNode, null);
        }
    }

    /**
     * ����XML Node��ʼ��һ��resource��.
     *
     * @param groupNode  ����resource��Ϣ��XML Node
     *
     * @throws ResourceBundleCreateException  ��������
     */
    protected void initGroup(Node groupNode) throws ResourceBundleCreateException {
        String enumTypeName = (String) groupNode.selectObject(
                                      ResourceBundleConstant.XPATH_GROUP_ENUM);
        Class  enumType = null;

        if (enumTypeName.length() > 0) {
            try {
                enumType = ContextClassLoader.loadClass(enumTypeName);
            } catch (ClassNotFoundException e) {
                throw new ResourceBundleCreateException(ResourceBundleConstant.RB_ENUM_CLASS_NOT_FOUND,
                                                        new Object[] {
                    enumTypeName,
                    ContextClassLoader.getClassLoader()
                }, e);
            }
        }

        for (Iterator i = groupNode.selectNodes(ResourceBundleConstant.XPATH_RESOURCES).iterator();
             i.hasNext();) {
            Node resourceNode = (Node) i.next();

            initResource(resourceNode, enumType);
        }
    }

    /**
     * ����XML Node��ʼ��һ��resource��.
     *
     * @param resourceNode  ����resource��Ϣ��XML Node
     * @param enumType      <code>Enum</code>��
     *
     * @throws ResourceBundleCreateException  ��������
     */
    protected void initResource(Node resourceNode, Class enumType)
            throws ResourceBundleCreateException {
        String id = (String) resourceNode.selectObject(ResourceBundleConstant.XPATH_RESOURCE_ID);

        // ���ָ����enum����, ���Դ�enumֵ��Ϊresource key.
        if (enumType != null) {
            Enum enumObj = Enum.getEnumByName(enumType, (String) id);

            if (enumObj == null) {
                throw new ResourceBundleCreateException(ResourceBundleConstant.RB_ENUM_ID_NOT_FOUND,
                                                        new Object[] {
                    id,
                    enumType.getName()
                }, null);
            }

            id = enumObj.toString();
        }

        Object value = null;
        String type = resourceNode.getName();

        if (ResourceBundleConstant.RB_RESOURCE_TYPE_MESSAGE.equals(type)) {
            value = getMessageResource(id, resourceNode);
        } else if (ResourceBundleConstant.RB_RESOURCE_TYPE_MAP.equals(type)) {
            value = getMapResource(id, resourceNode);
        } else if (ResourceBundleConstant.RB_RESOURCE_TYPE_LIST.equals(type)) {
            value = getListResource(id, resourceNode);
        }

        if (values.containsKey(id)) {
            throw new ResourceBundleCreateException(ResourceBundleConstant.RB_DUPLICATED_RESOURCE_KEY,
                                                    new Object[] {
                id
            }, null);
        }

        values.put(id, value);
    }

    /**
     * ����XML Node����message resource��.
     *
     * @param id            resource ID
     * @param resourceNode  ����resource��Ϣ��XML Node
     *
     * @return resource��ֵ
     *
     * @throws ResourceBundleCreateException  ��������
     */
    protected Object getMessageResource(String id, Node resourceNode)
            throws ResourceBundleCreateException {
        return resourceNode.selectObject(ResourceBundleConstant.XPATH_RESOURCE_MESSAGE_DATA);
    }

    /**
     * ����XML Node����map resource��.
     *
     * @param id            resource ID
     * @param resourceNode  ����resource��Ϣ��XML Node
     *
     * @return resource��ֵ
     *
     * @throws ResourceBundleCreateException  ��������
     */
    protected Object getMapResource(String id, Node resourceNode)
            throws ResourceBundleCreateException {
        ListMap map = new ArrayHashMap();

        for (Iterator i = resourceNode.selectNodes(ResourceBundleConstant.XPATH_RESOURCES)
                                      .iterator(); i.hasNext();) {
            Node   mapItemNode = (Node) i.next();
            Object mapKey = mapItemNode.selectObject(ResourceBundleConstant.XPATH_RESOURCE_ID);

            if (map.containsKey(id)) {
                throw new ResourceBundleCreateException(ResourceBundleConstant.RB_DUPLICATED_MAP_RESOURCE_KEY,
                                                        new Object[] {
                    mapKey,
                    id
                }, null);
            }

            String mapItemType = mapItemNode.getName();
            Object value = null;

            if (ResourceBundleConstant.RB_RESOURCE_TYPE_MESSAGE.equals(mapItemType)) {
                value = getMessageResource(id, mapItemNode);
            } else if (ResourceBundleConstant.RB_RESOURCE_TYPE_MAP.equals(mapItemType)) {
                value = getMapResource(id, mapItemNode);
            } else if (ResourceBundleConstant.RB_RESOURCE_TYPE_LIST.equals(mapItemType)) {
                value = getListResource(id, mapItemNode);
            }

            map.put(mapKey, value);
        }

        return Collections.unmodifiableMap(map);
    }

    /**
     * ����XML Node����list resource��.
     *
     * @param id            resource ID
     * @param resourceNode  ����resource��Ϣ��XML Node
     *
     * @return resource��ֵ
     *
     * @throws ResourceBundleCreateException  ��������
     */
    protected Object getListResource(String id, Node resourceNode)
            throws ResourceBundleCreateException {
        List list = new ArrayList();

        for (Iterator i = resourceNode.selectNodes(ResourceBundleConstant.XPATH_RESOURCES)
                                      .iterator(); i.hasNext();) {
            Node   listItemNode = (Node) i.next();
            String listItemType = listItemNode.getName();
            Object value        = null;

            if (ResourceBundleConstant.RB_RESOURCE_TYPE_MESSAGE.equals(listItemType)) {
                value = getMessageResource(id, listItemNode);
            } else if (ResourceBundleConstant.RB_RESOURCE_TYPE_MAP.equals(listItemType)) {
                value = getMapResource(id, listItemNode);
            } else if (ResourceBundleConstant.RB_RESOURCE_TYPE_LIST.equals(listItemType)) {
                value = getListResource(id, listItemNode);
            }

            list.add(value);
        }

        return Collections.unmodifiableList(list);
    }

    /**
     * ����ָ���ļ�, ��resource bundle��ȡ����Ӧ�Ķ���. �������<code>null</code>��ʾ��Ӧ�Ķ��󲻴���.
     *
     * @param key  Ҫ���ҵļ�
     *
     * @return key��Ӧ�Ķ���, ��<code>null</code>��ʾ�����ڸö���
     */
    protected Object handleGetObject(String key) {
        return values.get(key);
    }

    /**
     * ȡ������keys.
     *
     * @return ����keys
     */
    public Enumeration getKeys() {
        java.util.ResourceBundle parent = getParent();

        return new ResourceBundleEnumeration(values.keySet(),
                                             (parent != null) ? parent.getKeys()
                                                              : null);
    }
}
