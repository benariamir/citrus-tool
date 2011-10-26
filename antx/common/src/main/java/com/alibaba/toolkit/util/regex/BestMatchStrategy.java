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

import com.alibaba.toolkit.util.collection.Predicate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * ���ƥ�����, ������ͼƥ�����һ��. ����ж��������ͬ��ƥ�䳤��, �򷵻ص�һ��ƥ����.
 *
 * @version $Id: BestMatchStrategy.java,v 1.1 2003/07/03 07:26:34 baobao Exp $
 * @author Michael Zhou
 */
public class BestMatchStrategy implements MatchStrategy {
    /** ��ƥ�䳤�ȴӴ�С����ıȽ���. */
    private static final Comparator MATCH_LENGTH_COMPARATOR =
            new Comparator() {
        public int compare(Object item1, Object item2) {
            return ((MatchItem) item2).length() - ((MatchItem) item1).length();
        }
    };

    /**
     * ��ͼƥ��ָ��������ֵ. ����ɹ�, �򷵻�<code>true</code>.  �����߿���ͨ��<code>context.getMatchItem()</code>��ȡ��ƥ����.
     *
     * @param context  ƥ��������
     *
     * @return ���ƥ��ɹ�, �򷵻�<code>true</code>
     */
    public boolean matches(MatchContext context) {
        Predicate predicate = context.getPredicate();

        // ���û��predicate, ��ѡ��ʹ�ø���Ч�Ĳ���
        if (predicate == null) {
            return matchWithoutPredicate(context);
        }

        Collection patterns      = context.getPatterns();
        List       matchItemList = new ArrayList(patterns.size());

        for (Iterator i = patterns.iterator(); i.hasNext();) {
            MatchPattern pattern = (MatchPattern) i.next();

            if (pattern.matches(context)) {
                matchItemList.add(context.getLastMatchItem());
            }
        }

        // ��ƥ��, ��ֱ�ӷ���null
        if (matchItemList.size() == 0) {
            return false;
        }


        // ��ƥ�䳤���ɴ�С����(�ȶ�)
        Collections.sort(matchItemList, MATCH_LENGTH_COMPARATOR);

        // ͨ��ָ����predicate��������ƥ����
        for (Iterator i = matchItemList.iterator(); i.hasNext();) {
            MatchItem item = (MatchItem) i.next();

            if (predicate.evaluate(item)) {
                context.setLastMatchItem(item);
                return true;
            }
        }

        return false;
    }

    /**
     * ��ͼƥ��ָ��������ֵ, ���ж�predicate, ���нϸߵ�Ч��.
     *
     * @param context  ƥ��������
     *
     * @return ���ƥ��ɹ�, �򷵻�<code>true</code>
     */
    private boolean matchWithoutPredicate(MatchContext context) {
        MatchItem bestMatchItem   = null;
        int       bestMatchLength = -1;

        for (Iterator i = context.getPatterns().iterator(); i.hasNext();) {
            MatchPattern pattern = (MatchPattern) i.next();

            if (pattern.matches(context)) {
                MatchItem matchItem   = context.getLastMatchItem();
                int       matchLength = matchItem.length();

                if (matchLength > bestMatchLength) {
                    bestMatchItem   = matchItem;
                    bestMatchLength = matchLength;
                }
            }
        }

        if (bestMatchItem != null) {
            context.setLastMatchItem(bestMatchItem);
            return true;
        }

        return false;
    }
}
