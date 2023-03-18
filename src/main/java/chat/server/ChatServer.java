package chat.server;

import chat.model.Message;
import chat.model.User;
import chat.server.logs.FileLog;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer
{
  public static void main(String[] args) throws IOException
  {
    ArrayList<User> users = new ArrayList<User>();
    ArrayList<Message> messages = new ArrayList<Message>();
    ServerSocket serverSocket = new ServerSocket(8080);
    UDPBroadcaster broadcaster = new UDPBroadcaster("230.0.0.0", 8888);
    while (true)
    {
      System.out.println("Server is ready for connection");
      FileLog logs = FileLog.getInstance(new File("C:/Users/crist/IdeaProjects/ViaUniversity/Semester2/Assignment2_ChatSystem/src/main/java/chat/server/logs/Logs.txt"));
      logs.log("Server is ready for connection");
      Socket socket = serverSocket.accept();
      logs.log(socket.getInetAddress().toString() + " has connected");
      ChatCommunicator communicator = new ChatCommunicator(socket, broadcaster, users, messages);
      Thread communicatorThread = new Thread(communicator);
      communicatorThread.start();
    }
  }
}
