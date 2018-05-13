package cn.zyp.rpc;

import org.springframework.web.HttpRequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;

/**
 * HttpRequestHandler
 */
public class HttpInvokerServiceExporter implements HttpRequestHandler {
    private Object target;
    private Class<?> classz;

    public void handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        ObjectInputStream objectInputStream = new ObjectInputStream(httpServletRequest.getInputStream());
        try {
            RemoteInvocation remoteInvocation = (RemoteInvocation) objectInputStream.readObject();
            System.out.println(remoteInvocation.getMethodName() + "--" + remoteInvocation.getArguments());
            Method method = target.getClass().getInterfaces()[0].getMethod(remoteInvocation.getMethodName(), remoteInvocation.getParameterTypes());
            if (method == null) {
                throw new RuntimeException("not support this method");
            }

            Object result = null;
            RemoteInvocationResult remoteInvocationResult = null;
            try {
                result = method.invoke(target, remoteInvocation.getArguments());
                remoteInvocationResult = new RemoteInvocationResult(result);
                System.out.println(remoteInvocationResult.getResult());
            } catch (Exception e) {
                e.printStackTrace();
                remoteInvocationResult.setTx(e);
            }
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(httpServletResponse.getOutputStream());
            objectOutputStream.writeObject(remoteInvocationResult);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Class<?> getClassz() {
        return classz;
    }

    public void setClassz(Class<?> classz) {
        this.classz = classz;
    }
}
