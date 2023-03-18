package chat.model;

public class Message
{
  private final String message;
  private final User user;
  private final boolean announcement;

  public Message(User user, String message)
  {
    this.user = user;
    this.message = message;
    announcement = false;
  }

  public Message(String message)
  {
    this.message = message;
    this.user = null;
    announcement = true;
  }

  public String getMessage()
  {
    return message;
  }

  public String toString()
  {
    if (announcement)
    {
      return message;
    }
    else
    {
      return user + ":\n" + message;
    }
  }
}
