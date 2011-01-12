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

/**
 * ��һ������йص�С����.
 *
 * @version $Id: ObjectUtil.java,v 1.1 2003/07/03 07:26:15 baobao Exp $
 * @author Michael Zhou
 */
public class ObjectUtil {
    /**
     * �Ƚ����������Ƿ����.
     *
     * @param o1  ����1
     * @param o2  ����2
     *
     * @return ������, �򷵻�<code>true</code>
     */
    public static boolean equals(Object o1, Object o2) {
        return (o1 == null) ? (o2 == null)
                            : o1.equals(o2);
    }

    /**
     * ȡ�ö����hashֵ, �������Ϊ<code>null</code>, �򷵻�<code>0</code>
     *
     * @param o ����
     *
     * @return hashֵ
     */
    public static int hashCode(Object o) {
        return (o == null) ? 0
                           : o.hashCode();
    }
}
