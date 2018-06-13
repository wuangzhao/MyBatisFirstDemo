package cn.com.mybatis.test;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

/**
 * 对目标对象进行拦截
 */
@Intercepts({
        @Signature(
                type = Executor.class,//接口类型
                method = "query",//拦截的方法名
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}//所需的参数信息
        )
})
public class QueryPlugin implements Interceptor {
    /**
     * 对一个目标方法拦截的抽象方法
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        return invocation.proceed();
    }

    /**
     * 将拦截器插入目标对象
     * @param o
     * @return
     */
    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    /**
     * 将全局配置文件中的参数注入到插件类中
     * @param properties
     */
    @Override
    public void setProperties(Properties properties) {

    }
}
