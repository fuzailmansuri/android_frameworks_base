/*
 * Copyright (C) 2026 Nayan Chavare
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package com.android.server.spoof;

import android.content.Context;

/**
 * Minimal system_server-side registration point for the spoof backends.
 * Provides the Context that AxSpoofManager needs and the config getters
 * that ActivityManagerService binds to IActivityManager.
 */
public final class SpoofServiceFactory {
    private static final Object sLock = new Object();
    private static volatile Context sContext;
    private static volatile IAxSpoofManager sSpoofManager;

    private SpoofServiceFactory() {}

    public static void init(Context context) {
        sContext = context;
    }

    public static Context getContext() {
        return sContext;
    }

    public static IAxSpoofManager getSpoofManager() {
        if (sSpoofManager == null) {
            synchronized (sLock) {
                if (sSpoofManager == null) {
                    sSpoofManager = new AxSpoofManager();
                }
            }
        }
        return sSpoofManager;
    }

    public static void systemReady() {
        getSpoofManager().systemReady();
    }
}
