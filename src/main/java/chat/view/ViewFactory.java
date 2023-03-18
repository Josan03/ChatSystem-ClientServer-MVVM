package chat.view;

import chat.viewmodel.ViewModelFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

import java.io.IOError;
import java.io.IOException;

public class ViewFactory
{
  private final ViewHandler viewHandler;
  private final ViewModelFactory viewModelFactory;
  private ChatViewController chatViewController;
  private HelloViewController helloViewController;

  public ViewFactory(ViewHandler viewHandler, ViewModelFactory viewModelFactory)
  {
    this.viewHandler = viewHandler;
    this.viewModelFactory = viewModelFactory;
    this.chatViewController = null;
    this.helloViewController = null;
  }

  public Region loadChatViewController()
  {
    if (chatViewController == null)
    {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/chat/ChatWindow.fxml"));
      try
      {
        Region root = loader.load();
        chatViewController = loader.getController();
        chatViewController.init(viewHandler, viewModelFactory.getChatViewModel(), root);
      }
      catch (IOException e)
      {
        throw new IOError(e);
      }
    }
    return chatViewController.getRoot();
  }

  public Region loadHelloViewController()
  {
    if (helloViewController == null)
    {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/chat/HelloWindow.fxml"));
      try
      {
        Region root = loader.load();
        helloViewController = loader.getController();
        helloViewController.init(viewHandler, viewModelFactory.getHelloViewModel(), root);
      }
      catch (IOException e)
      {
        throw new IOError(e);
      }
    }
    return helloViewController.getRoot();
  }
}
