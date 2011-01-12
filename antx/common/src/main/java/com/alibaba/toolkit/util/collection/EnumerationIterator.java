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
package com.alibaba.toolkit.util.collection;

import java.util.Enumeration;
import java.util.Iterator;

/**
 * ��<code>Enumeration</code>ת����<code>Iterator</code>��������.
 *
 * @version $Id: EnumerationIterator.java,v 1.1 2003/07/03 07:26:16 baobao Exp $
 * @author Michael Zhou
 */
public class EnumerationIterator implements Iterator {
    private Enumeration enumeration;
    private Object      lastReturned;

    /**
     * ����һ��<code>EnumerationIterator</code>.
     *
     * @param enumeration  �������<code>Enumeration</code>
     */
    public EnumerationIterator(Enumeration enumeration) {
        this.enumeration = enumeration;
    }

    /**
     * ȡ�ñ������<code>Enumeration</code>.
     *
     * @return �������<code>Enumeration</code>
     */
    public Enumeration getEnumeration() {
        return enumeration;
    }

    /**
     * �Ƿ�����һ��Ԫ��.
     *
     * @return �������һ��Ԫ��, �򷵻�<code>true</code>
     */
    public boolean hasNext() {
        return enumeration.hasMoreElements();
    }

    /**
     * ȡ����һ��Ԫ��.
     *
     * @return ��һ��Ԫ��
     */
    public Object next() {
        return (lastReturned = enumeration.nextElement());
    }

    /**
     * ɾ��������ص�Ԫ��, ��֧��.
     */
    public void remove() {
        throw new UnsupportedOperationException("remove() method is not supported");
    }
}
