package aeminium.webserver.withlock;
import java.io.*;
import java.net.*;
import top.liebes.anno.Perm;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Server {
  public static ReentrantReadWriteLock clientLock=new ReentrantReadWriteLock();
  public static String ROOT="/Users/asad22/Research/test/";
  public static Socket client=null;
  @Perm(requires="unique(client) in alive",ensures="unique(client) in alive") public static void main(  String argv[]) throws Exception {
    System.out.println(" Server is Running  ");
    ServerSocket mysocket=new ServerSocket(5559);
    while (true) {
      clientLock.writeLock().lock();
      client=mysocket.accept();
      BufferedReader reader=new BufferedReader(new InputStreamReader(client.getInputStream()));
      BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
      writer.write("*** Welcome to the Calculation Server (Addition Only) ***\r\n");
      writer.write("*** Please type in the first number and press Enter : \n");
      writer.flush();
      String data1=reader.readLine().trim();
      writer.write("*** Please type in the second number and press Enter : \n");
      writer.flush();
      String data2=reader.readLine().trim();
      int num1=Integer.parseInt(data1);
      int num2=Integer.parseInt(data2);
      int result=num1 + num2;
      System.out.println("Addition operation done ");
      writer.write("\r\n=== Result is  : " + result);
      writer.flush();
      client.close();
      clientLock.writeLock().unlock();
    }
  }
  public static ReentrantReadWriteLock getClientLock(){
    Cloner cloner=new Cloner();
    clientLock=cloner.deepClone(clientLock);
    Cloner cloner=new Cloner();
    clientLock=cloner.deepClone(clientLock);
    return clientLock;
  }
  public static String getROOT(){
    Cloner cloner=new Cloner();
    ROOT=cloner.deepClone(ROOT);
    Cloner cloner=new Cloner();
    ROOT=cloner.deepClone(ROOT);
    return ROOT;
  }
  public static Socket getClient(){
    Cloner cloner=new Cloner();
    client=cloner.deepClone(client);
    Cloner cloner=new Cloner();
    client=cloner.deepClone(client);
    return client;
  }
}
