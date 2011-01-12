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

/**
 * ��һ��<code>Iterator</code>�е�ֵת������һ��ֵ�Ĺ�����.
 *
 * @version $Id: TransformIterator.java,v 1.1 2003/07/03 07:26:16 baobao Exp $
 * @author Michael Zhou
 */
public class TransformIterator extends FilterIterator {
    private Transformer transformer;

    /**
     * ����һ��������.
     *
     * @param iterator    �����˵�<code>Iterator</code>
     * @param transformer ת����
     */
    public TransformIterator(Iterator iterator, Transformer transformer) {
        super(iterator);
        this.transformer = transformer;
    }

    /**
     * ȡ��ת����.
     *
     * @return ת��������
     */
    public Transformer getTransformer() {
        return transformer;
    }

    /**
     * ȡ����һ������.
     *
     * @return ��һ������ת���Ķ���
     */
    public Object next() {
        return transform(super.next());
    }

    /**
     * ת������.
     *
     * @param input �������
     *
     * @return ת����Ķ���
     */
    private Object transform(Object input) {
        Transformer transformer = getTransformer();

        if (transformer != null) {
            return transformer.transform(input);
        }

        return input;
    }
}
