package chat.client;

import chat.model.Message;
import chat.model.User;

import java.beans.PropertyChangeListener;
import java.io.IOException;

public interface ChatClient
{
  boolean connect(User user) throws IOException;
  boolean chat(User user, Message message) throws IOException;
  void addPropertyChangeListener(PropertyChangeListener listener);
  void removePropertyChangeListener(PropertyChangeListener listener);
  void close(User user) throws IOException;
}
