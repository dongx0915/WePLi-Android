package common

import kotlinx.coroutines.flow.Flow

typealias FlowResult<T> = Flow<Result<T>>