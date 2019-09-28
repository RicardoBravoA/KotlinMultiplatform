package com.rba.mpp.shared

import kotlinx.coroutines.*

internal actual val ApplicationDispatcher: CoroutineDispatcher = Dispatchers.Default