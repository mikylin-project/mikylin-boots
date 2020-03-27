package cn.mikylin.boot.dubbo;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

import static org.apache.dubbo.common.constants.CommonConstants.PROVIDER;
import static org.apache.dubbo.common.constants.FilterConstants.CACHE_KEY;

/**
 * dubbo filter
 *
 * @author mikylin
 * @date 20200326
 */
@Activate(group = {/*CONSUMER,*/ PROVIDER})
@Slf4j
public class DubboProviderFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

        Result invoke = invoker.invoke(invocation);

        return invoke;
    }
}
