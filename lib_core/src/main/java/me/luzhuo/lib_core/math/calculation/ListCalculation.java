/* Copyright 2021 Luzhuo. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.luzhuo.lib_core.math.calculation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Description: 列表相关计算
 * 差集 / 交集 / 并集
 * @Author: Luzhuo
 * @Creation Date: 2021/12/5 21:50
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
public class ListCalculation {
    /**
     * 求两个集合的差集
     * A差B, A在B中没有的元素
     * B差A, B在A中没有的元素
     * @param a List A
     * @param b List B
     * @return 新的List
     */
    @Nullable
    public List difference(@Nullable List a, @Nullable List b) {
        if (a == null || a.size() <= 0) return a;
        if (b == null || b.size() <= 0) return a;

        List tempA = cloneList(a);
        List tempB = cloneList(b);

        tempA.removeAll(tempB);
        return tempA;
    }

    @Nullable
    public List chaji(@Nullable List a, @Nullable List b) {
        return difference(a, b);
    }

    @Nullable
    public Set difference(@Nullable Set a, @Nullable Set b) {
        if (a == null || a.size() <= 0) return a;
        if (b == null || b.size() <= 0) return a;

        Set tempA = cloneSet(a);
        Set tempB = cloneSet(b);
        tempA.removeAll(tempB);
        return tempA;
    }

    @Nullable
    public Set chaji(@Nullable Set a, @Nullable Set b) {
        return difference(a, b);
    }

    /**
     * 求两个集合的交集
     * 相同的元素
     * @param a List A
     * @param b List B
     * @return 新的List
     */
    @Nullable
    public List intersection(@Nullable List a, @Nullable List b) {
        if (a == null || a.size() <= 0) return a;
        if (b == null || b.size() <= 0) return a;

        List tempA = cloneList(a);
        List tempB = cloneList(b);
        tempA.retainAll(tempB);
        return tempA;
    }

    @Nullable
    public List jiaoji(@Nullable List a, @Nullable List b) {
        return intersection(a, b);
    }

    @Nullable
    public Set intersection(@Nullable Set a, @Nullable Set b) {
        if (a == null || a.size() <= 0) return a;
        if (b == null || b.size() <= 0) return a;

        Set tempA = cloneSet(a);
        Set tempB = cloneSet(b);
        tempA.retainAll(tempB);
        return tempA;
    }

    @Nullable
    public Set jiaoji(@Nullable Set a, @Nullable Set b) {
        return intersection(a, b);
    }

    /**
     * 求两个集合的并集
     * 全部元素 (不重复)
     * @param a List A
     * @param b List B
     * @return 新的List
     */
    @Nullable
    public List union(@Nullable List a, @Nullable List b) {
        if (a == null || a.size() <= 0) return a;
        if (b == null || b.size() <= 0) return a;

        List tempA = cloneList(a);
        List tempB = cloneList(b);
        tempB.removeAll(tempA);
        tempA.addAll(tempB);
        return tempA;
    }

    @Nullable
    public List binji(@Nullable List a, @Nullable List b) {
        return union(a, b);
    }

    @Nullable
    public Set union(@Nullable Set a, @Nullable Set b) {
        if (a == null || a.size() <= 0) return a;
        if (b == null || b.size() <= 0) return a;

        Set tempA = cloneSet(a);
        Set tempB = cloneSet(b);
        tempB.removeAll(tempA);
        tempA.addAll(tempB);
        return tempA;
    }

    @Nullable
    public Set binji(@Nullable Set a, @Nullable Set b) {
        return union(a, b);
    }

    /**
     * 浅clone一份
     */
    private List cloneList(List list) {
        final List temp = new ArrayList();
        temp.addAll(list);
        return temp;
    }

    private Set cloneSet(Set set) {
        final Set temp = new HashSet();
        temp.addAll(set);
        return temp;
    }
}
