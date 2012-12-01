/*
 * Copyright (c) 2002-2012 Alibaba Group Holding Limited.
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

package com.alibaba.toolkit.util.collection;

/**
 * "断言"接口, 基于指定的输入对象, 返回<code>true</code>或<code>false</code>. 这个接口被用于过滤器,
 * 指定过滤的条件.
 *
 * @author Michael Zhou
 * @version $Id: Predicate.java,v 1.1 2003/07/03 07:26:16 baobao Exp $
 */
public interface Predicate {
    /**
     * 根据指定的输入对象, 返回<code>true</code>或<code>false</code>.
     *
     * @param input 输入对象
     * @return <code>true</code>或<code>false</code>
     */
    public boolean evaluate(Object input);
}
