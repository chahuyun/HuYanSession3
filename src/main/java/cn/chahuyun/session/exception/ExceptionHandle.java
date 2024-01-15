package cn.chahuyun.session.exception;

import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineExceptionHandler;
import org.jetbrains.annotations.NotNull;

/**
 * 全局异常处理
 *
 * @author Moyuyanli
 * @date 2024/1/15 11:20
 */
public class ExceptionHandle implements CoroutineExceptionHandler {
    @Override
    public void handleException(@NotNull CoroutineContext coroutineContext, @NotNull Throwable throwable) {

    }
}
