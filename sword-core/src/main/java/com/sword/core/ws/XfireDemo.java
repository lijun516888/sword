package com.sword.core.ws;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.codehaus.xfire.transport.http.CommonsHttpMessageSender;

public class XfireDemo {

    private static long TIME_OUT = 1000 * 60 * 3l;
    private static int RETRY_TIMES = 1;
    private static int SO_TIMEOUT = 1000 * 60 * 3;


    public static void main(String[] args) {
        ObjectInterface onLineClient;
        org.codehaus.xfire.service.Service serviceModel = new ObjectServiceFactory().create(ObjectInterface.class);
        XFireProxyFactory serviceFactory = new XFireProxyFactory();
        HttpClientParams params = new HttpClientParams();
        params.setParameter(HttpClientParams.USE_EXPECT_CONTINUE, Boolean.FALSE);
        params.setParameter(HttpClientParams.CONNECTION_MANAGER_TIMEOUT, TIME_OUT); // 单位是毫秒
        params.setParameter(HttpClientParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(RETRY_TIMES, false));
        try {
            onLineClient = (ObjectInterface)serviceFactory.create(serviceModel, "http://61.152.173.76:9080/sts/services/StsInterfaceTest");
            Client c = Client.getInstance(onLineClient);
            c.setProperty(CommonsHttpMessageSender.HTTP_CLIENT_PARAMS, params);
            c.setProperty(CommonsHttpMessageSender.HTTP_TIMEOUT, SO_TIMEOUT);
            String xml = "<xml></xml>";
            String result = onLineClient.interfaceAction(xml);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
