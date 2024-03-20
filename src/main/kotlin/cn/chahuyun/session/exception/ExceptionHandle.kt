package cn.chahuyun.session.exception

import cn.chahuyun.session.HuYanSession
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

/**
 * 全局异常处理
 *
 * @author Moyuyanli
 * @date 2024/1/15 11:20
 */
class ExceptionHandle : CoroutineExceptionHandler {

    override val key: CoroutineContext.Key<*>
        get() = CoroutineExceptionHandler.Key

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        HuYanSession.INSTANCE.logger.error(exception)
    }
}