package chat.server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import chat.client.Data;
import chat.server.model.ServerData;
import chat.model.Message;
import chat.model.User;
import chat.server.logs.FileLog;
import com.google.gson.Gson;

public class ChatCommunicator implements Runnable
{
  private final Socket socket;
  private final UDPBroadcaster broadcaster;
  private final Gson gson;
  private final ArrayList<User> users;
  private final ArrayList<Message> messages;
  private final FileLog logs;

  public ChatCommunicator(Socket socket, UDPBroadcaster broadcaster, ArrayList<User> users, ArrayList<Message> messages)
  {
    this.socket = socket;
    this.broadcaster = broadcaster;
    this.gson = new Gson();
    this.users = users;
    this.messages = messages;
    logs = FileLog.getInstance(new File("C:/Users/crist/IdeaProjects/ViaUniversity/Semester2/Assignment2_ChatSystem/src/main/java/chat/server/logs/Logs.txt"));
  }

  @Override public void run()
  {
    try
    {
      communicate();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  private void communicate() throws IOException
  {
    try
    {
      InputStream inputStream = socket.getInputStream();
      OutputStream outputStream = socket.getOutputStream();

      BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
      PrintWriter out = new PrintWriter(outputStream);

      loop: while (true)
      {
        String request = in.readLine();
        Data data = gson.fromJson(request, Data.class);
        switch (data.getRequest())
        {
          case "connect" ->
          {
            boolean ok = !data.getUser().getName().trim().isEmpty();
            for (User user : users)
            {
              if (data.getUser().getName().equals(user.getName()))
              {
                ok = false;
                break;
              }
            }

            if (ok)
            {
              Message message = new Message(data.getUser().getName() + " has joined the chat");
              users.add(data.getUser());
              messages.add(message);
              logs.log(message.getMessage());
            }
            out.println(ok);
            out.flush();
            ServerData serverData = new ServerData("add", users, messages);
            String json = gson.toJson(serverData);
            broadcaster.broadcast(json);
          }
          case "chat" ->
          {
            boolean ok = !data.getMessage().getMessage().trim().isEmpty();
            System.out.println(data.getUser());
            messages.add(data.getMessage());
            logs.log(data.getUser().getName() + ": " + data.getMessage().getMessage());
            out.println(ok);
            out.flush();
            ServerData serverData = new ServerData("add", users, messages);
            String json = gson.toJson(serverData);
            broadcaster.broadcast(json);
          }
          case "exit" ->
          {
            Message message = new Message(data.getUser().getName() + " has left the chat");
            messages.add(message);
            users.remove(data.getUser());
            logs.log(message.getMessage());
            ServerData serverData = new ServerData("remove", users, messages);
            String json = gson.toJson(serverData);
            broadcaster.broadcast(json);
            break loop;
          }
        }
      }
    }
    finally
    {
      synchronized (broadcaster)
      {
        socket.close();
      }
    }
  }
}
