package cn.zyp.rpc;

import org.aopalliance.intercept.MethodInvocation;

import java.io.Serializable;

/**
 * 将调用方法的信息封装成RemoteInvocation
 */
public class RemoteInvocation implements Serializable{
    private String methodName;
    private Object[] arguments;
    private Class<?>[] parameterTypes;

    public RemoteInvocation(MethodInvocation methodInvocation) {
        this.methodName = methodInvocation.getMethod().getName();
        this.arguments = methodInvocation.getArguments();
        this.parameterTypes = methodInvocation.getMethod().getParameterTypes();
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }
}
