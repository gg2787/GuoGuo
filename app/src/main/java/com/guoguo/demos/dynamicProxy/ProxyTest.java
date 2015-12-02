package com.guoguo.demos.dynamicProxy;

/**
 * Created by Administrator on 2015/12/2.
 */
public class ProxyTest {
    public static void doProxyTest() {
        MyProxyDemoImpl myProxyDemo = new MyProxyDemoImpl();
        DynamicProxy dynamicProxy = new DynamicProxy();

        IMyProxyDemoInterface myProxy = (IMyProxyDemoInterface)dynamicProxy.bind(myProxyDemo);
        myProxy.myFunction();
    }
}
