package chat.model;

import java.util.ArrayList;

public class MessageList
{
  private ArrayList<Message> messages;

  public MessageList()
  {
    this.messages = new ArrayList<Message>();
  }

  public void setMessageList(ArrayList<Message> messageList)
  {
    this.messages = messageList;
  }

  public ArrayList<Message> getMessages()
  {
    return messages;
  }
}
