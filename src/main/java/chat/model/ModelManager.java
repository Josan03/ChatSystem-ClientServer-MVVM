package chat.model;

import chat.client.ChatClientImplementation;
import chat.server.model.ServerData;
import javafx.application.Platform;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;

public class ModelManager implements PropertyChangeListener
{
  private final MessageList messageList;
  private final UserList userList;
  private final PropertyChangeSupport support;
  private final ChatClientImplementation client;
  private User user;
  private static ModelManager instance;

  private ModelManager(ChatClientImplementation client)
  {
    this.messageList = new MessageList();
    this.userList = new UserList();
    this.client = client;
    support = new PropertyChangeSupport(this);

    this.client.addPropertyChangeListener(this);
  }

  public static ModelManager getInstance(ChatClientImplementation client)
  {
    if (instance == null)
    {
      instance = new ModelManager(client);
    }

    return instance;
  }

  public static ModelManager getInstance()
  {
    return instance;
  }

  public ArrayList<Message> getMessageList()
  {
    return messageList.getMessages();
  }

  public ArrayList<User> getUserList()
  {
    return userList.getUsers();
  }

  public User getUser()
  {
    return user;
  }

  public boolean addUser(User user)
  {
    boolean ok = false;
    try
    {
      ok = client.connect(user);
      if (ok)
      {
        this.user = user;
        return true;
      }
      else return false;
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }
  }

  public boolean addMessage(Message message)
  {
    boolean ok = false;
    try
    {
      ok = client.chat(user, message);
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }
    return ok;
  }

  public void removeUser()
  {
    try
    {
      client.close(user);
      client.removePropertyChangeListener(this);
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }
  }

  public void addPropertyChangeListener(String propertyName,
      PropertyChangeListener listener)
  {
    support.addPropertyChangeListener(propertyName, listener);
  }

  public void removePropertyChangeListener(String propertyName,
      PropertyChangeListener listener)
  {
    support.removePropertyChangeListener(propertyName, listener);
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    Platform.runLater(() -> {
      ServerData serverData = (ServerData) evt.getNewValue();
      if (evt.getPropertyName().equals("data"))
      {
        userList.setUserList(serverData.getUsers());
        support.firePropertyChange("users", null, userList);
        messageList.setMessageList(serverData.getMessages());
        support.firePropertyChange("messages", null, messageList);
      }
      if (evt.getPropertyName().equals("close"))
      {
        userList.setUserList(serverData.getUsers());
        messageList.setMessageList(serverData.getMessages());
        support.firePropertyChange("close", null, userList);
      }
    });
  }
}
