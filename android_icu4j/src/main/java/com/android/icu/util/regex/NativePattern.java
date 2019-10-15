/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.icu.util.regex;

import libcore.util.NativeAllocationRegistry;

/**
 * Provide an entry point to use ICU4C icu::RegexPattern.
 */
@libcore.api.IntraCoreApi
public class NativePattern {

    private static final NativeAllocationRegistry REGISTRY = NativeAllocationRegistry
        .createMalloced(NativePattern.class.getClassLoader(), getNativeFinalizer());

    @dalvik.annotation.optimization.ReachabilitySensitive
    final long address;

    @libcore.api.IntraCoreApi
    public static NativePattern create(String pattern, int flags) {
        return new NativePattern(pattern, flags);
    }

    private NativePattern(String pattern, int flags) {
        address = compileImpl(pattern, flags);
        REGISTRY.registerNativeAllocation(this, address);
    }

    /**
     * @return native address of the native allocation.
     */
    private static native long compileImpl(String pattern, int flags);

    /**
     * @return address of a native function of type <code>void f(void* nativePtr)</code>
     *         used to free this kind of native allocation
     */
    private static native long getNativeFinalizer();

}
