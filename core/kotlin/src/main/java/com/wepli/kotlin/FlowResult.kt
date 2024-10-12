package com.wepli.kotlin

import kotlinx.coroutines.flow.Flow

typealias FlowResult<T> = Flow<Result<T>>