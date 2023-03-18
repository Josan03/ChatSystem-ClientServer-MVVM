package chat.client;

import chat.model.Message;
import chat.model.User;
import com.google.gson.Gson;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClientImplementation implements ChatClient
{
  private final Socket socket;
  private final PrintWriter out;
  private final BufferedReader in;
  private final Gson gson;
  private final PropertyChangeSupport support;
  private final MessageListener listener;

  public ChatClientImplementation(String host, int port, String groupAddress, int groupPort)
      throws IOException
  {
    this.socket = new Socket(host, port);
    this.out = new PrintWriter(socket.getOutputStream());
    this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    this.gson = new Gson();
    support = new PropertyChangeSupport(this);

    listener = new MessageListener(this, groupAddress, groupPort);
    Thread thread = new Thread(listener);
    thread.start();
  }

  @Override public boolean connect(User user) throws IOException
  {
    Data data = new Data("connect", user);
    String json = gson.toJson(data);
    out.println(json);
    out.flush();
    String resultJson = in.readLine();
    return gson.fromJson(resultJson, boolean.class);
  }

  @Override public boolean chat(User user, Message message) throws IOException
  {
    Data data = new Data("chat", user,  message);
    String json = gson.toJson(data);
    out.println(json);
    out.flush();
    String resultJson = in.readLine();
    return gson.fromJson(resultJson, boolean.class);
  }

  @Override public void close(User user) throws IOException
  {
    Data data = new Data("exit", user);
    String json = gson.toJson(data);
    out.println(json);
    out.flush();
    listener.close();
    socket.close();
  }

  @Override public void addPropertyChangeListener(
      PropertyChangeListener listener)
  {
    support.addPropertyChangeListener(listener);
  }

  @Override public void removePropertyChangeListener(
      PropertyChangeListener listener)
  {
    support.removePropertyChangeListener(listener);
  }

  public void receiveBroadcast(String message)
  {
    ServerData serverData = gson.fromJson(message, ServerData.class);
    if (serverData.getResponse().equals("add"))
    {
      support.firePropertyChange("data", null, serverData);
    }
    else if (serverData.getResponse().equals("remove"))
    {
      support.firePropertyChange("close", null, serverData);
    }
  }
}
