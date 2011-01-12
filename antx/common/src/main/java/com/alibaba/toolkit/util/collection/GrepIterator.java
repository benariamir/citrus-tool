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

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * ����ָ���Ĺ�������<code>Predicate</code>, ����ָ����<code>Iterator</code>.
 *
 * @version $Id: GrepIterator.java,v 1.1 2003/07/03 07:26:16 baobao Exp $
 * @author Michael Zhou
 */
public class GrepIterator extends FilterIterator {
    private Predicate predicate;
    private Object    nextObject;
    private boolean   nextObjectSet = false;

    /**
     * ����һ��<code>GrepIterator</code>.
     *
     * @param iterator  �����˵�<code>Iterator</code>
     * @param predicate ��������
     */
    public GrepIterator(Iterator iterator, Predicate predicate) {
        super(iterator);
        this.predicate = predicate;
    }

    /**
     * ȡ��"����"����.
     *
     * @return "����"����
     */
    public Predicate getPredicate() {
        return predicate;
    }

    /**
     * �ж��Ƿ�����һ��Ԫ��.
     *
     * @return �������һ��Ԫ��, �򷵻�<code>true</code>
     */
    public boolean hasNext() {
        if (nextObjectSet) {
            return true;
        } else {
            return setNextObject();
        }
    }

    /**
     * ȡ����һ��Ԫ��.
     *
     * @return һ�¸�����������Ԫ��
     */
    public Object next() {
        if (!nextObjectSet && !setNextObject()) {
            throw new NoSuchElementException();
        }

        nextObjectSet = false;
        return nextObject;
    }

    /**
     * ɾ��������ص�Ԫ��, ��֧��.
     */
    public void remove() {
        throw new UnsupportedOperationException();
    }

    /**
     * ������һ�����õ�Ԫ��.
     *
     * @return ���û����һ��Ԫ����, �򷵻�<code>false</code>, ���򷵻�<code>true</code>
     */
    private boolean setNextObject() {
        Iterator  iterator  = getIterator();
        Predicate predicate = getPredicate();

        while (iterator.hasNext()) {
            Object object = iterator.next();

            if (predicate.evaluate(object)) {
                nextObject    = object;
                nextObjectSet = true;
                return true;
            }
        }

        return false;
    }
}

