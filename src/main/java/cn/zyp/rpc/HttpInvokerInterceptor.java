package cn.zyp.rpc;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.InitializingBean;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpInvokerInterceptor implements MethodInterceptor, InitializingBean {

    protected String url;

    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        //与服务建立连接  发出请求
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        RemoteInvocation remoteInvocation = new RemoteInvocation(methodInvocation);
        System.out.println(remoteInvocation.getMethodName()+"--"+remoteInvocation.getArguments());

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(bos);

        try {
            objectOutputStream.writeObject(remoteInvocation);
            objectOutputStream.flush();
        } finally {
            objectOutputStream.close();
        }

        httpURLConnection.getOutputStream().write(bos.toByteArray());

        //接收请求
        InputStream inputStream = httpURLConnection.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        try {
            RemoteInvocationResult result = (RemoteInvocationResult) objectInputStream.readObject();
            return result.getResult();
        } finally {
            objectInputStream.close();
        }

    }

    public void afterPropertiesSet() throws Exception {

    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
