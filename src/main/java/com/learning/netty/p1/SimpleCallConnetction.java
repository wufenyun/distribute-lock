/**
 * Package: com.demo.netty.p1
 * Description: 
 */
package com.learning.netty.p1;

/**
 * Description:  
 * Date: 2017年12月26日 下午3:38:28
 * @author wufenyun 
 */
public class SimpleCallConnetction implements CallConnetction{

    private String serverIp;
    private String clientIp;
    private Integer serverPort;
    private Integer clientPort;
    
    public SimpleCallConnetction(String serverIp,Integer serverPort) {
        this(serverIp,serverPort,null,0);
    }
    
    public SimpleCallConnetction(String serverIp,Integer serverPort,String clientIp,Integer clientPort) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        this.clientIp = clientIp;
        this.clientPort = clientPort;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    public Integer getClientPort() {
        return clientPort;
    }

    public void setClientPort(Integer clientPort) {
        this.clientPort = clientPort;
    }

    @Override
    public String getService() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getCallTimeout() {
        // TODO Auto-generated method stub
        return 0;
    }

    

}
