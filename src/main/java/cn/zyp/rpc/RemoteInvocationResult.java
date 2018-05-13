package cn.zyp.rpc;

import java.io.Serializable;

public class RemoteInvocationResult implements Serializable {
    private Object result;
    private Throwable tx;

    public RemoteInvocationResult(Object result) {
        this.result = result;
    }

    public RemoteInvocationResult(Object result, Throwable tx) {
        this.result = result;
        this.tx = tx;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Throwable getTx() {
        return tx;
    }

    public void setTx(Throwable tx) {
        this.tx = tx;
    }
}
