package chat.client;

import chat.model.Message;
import chat.model.User;

public class Data
{
  private final String request;
  private final User user;
  private final Message message;

  public Data(String request)
  {
    this.request = request;
    this.user = null;
    this.message = null;
  }

  public Data(String request, User user)
  {
    this.request = request;
    this.user = user;
    message = null;
  }

  public Data(String request, User user, Message message)
  {
    this.request = request;
    this.message = message;
    this.user = user;
  }

  public User getUser()
  {
    return user;
  }

  public Message getMessage()
  {
    return message;
  }
  public String getRequest()
  {
    return request;
  }

  public String toString()
  {
    return user + ":\n" + message;
  }
}
