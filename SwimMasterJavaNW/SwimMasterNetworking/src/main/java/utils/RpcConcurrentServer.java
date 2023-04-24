package utils;


import rpcprotocol.RpcClientWorker;
import sm.services.ISwimMasterServices;

import java.net.Socket;


public class RpcConcurrentServer extends AbsConcurrentServer {
    private ISwimMasterServices smServer;
    public RpcConcurrentServer(int port, ISwimMasterServices smServer) {
        super(port);
        this.smServer = smServer;
        System.out.println("SwimMaster - RpcConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
       // ChatClientRpcWorker worker=new ChatClientRpcWorker(chatServer, client);
        RpcClientWorker worker=new RpcClientWorker(smServer, client);

        Thread tw=new Thread(worker);
        return tw;
    }

    @Override
    public void stop(){
        System.out.println("Stopping services ...");
    }
}
