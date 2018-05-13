package cn.zyp.rpc;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.lang.Nullable;

public class HttpInvokerProxyFactoryBean extends HttpInvokerInterceptor implements FactoryBean<Object> {


    private Class<?> interfaces;
    private Object serviceProxy;

    public HttpInvokerProxyFactoryBean(Class<?> interfaces,String url) {
        this.interfaces = interfaces;
        this.url=url;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        ProxyFactory proxyFactory = new ProxyFactory(interfaces, this);
        this.serviceProxy=proxyFactory.getProxy();
    }

    @Nullable
    public Object getObject() throws Exception {
        return this.serviceProxy;
    }

    @Nullable
    public Class<?> getObjectType() {
        return this.interfaces;
    }

    public Class<?> getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(Class<?> interfaces) {
        this.interfaces = interfaces;
    }
}
