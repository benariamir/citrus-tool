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

package com.alibaba.toolkit.util.regex;

/**
 * ƥ�����.
 *
 * @version $Id: MatchStrategy.java,v 1.1 2003/07/03 07:26:34 baobao Exp $
 * @author Michael Zhou
 */
public interface MatchStrategy {
    /** ���ƥ�����, ������ͼƥ�����һ��. ����ж��������ͬ��ƥ�䳤��, �򷵻ص�һ��ƥ����. */
    MatchStrategy BEST_MATCH_STRATEGY = new BestMatchStrategy();

    /**
     * ��ͼƥ��ָ��������ֵ, ����ɹ�, �򷵻�<code>true</code>.  �����߿���ͨ��<code>context.getMatchItem()</code>��ȡ��ƥ����.
     *
     * @param context  ƥ��������
     *
     * @return ���ƥ��ɹ�, �򷵻�<code>true</code>
     */
    boolean matches(MatchContext context);
}
