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

package com.alibaba.antx.util;

public final class Assert {
    public static Object assertNotNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("[Assertion failed] - the argument is required; it must not be null");
        }

        return object;
    }

    public static Object assertNull(Object object) {
        if (object != null) {
            throw new IllegalArgumentException("[Assertion failed] - the object argument must be null");
        }

        return object;
    }

    public static void assertTrue(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException("[Assertion failed] - the expression must be true");
        }
    }
}
