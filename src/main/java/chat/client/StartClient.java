package chat.client;

import chat.model.*;
import chat.view.ViewHandler;
import chat.viewmodel.ViewModelFactory;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

public class StartClient extends Application
{
  @Override public void start(Stage primaryStage) throws Exception
  {
    ChatClient client = new ChatClientImplementation("localhost", 8080, "230.0.0.0", 8888);
    ModelManager.getInstance((ChatClientImplementation) client);
    ViewModelFactory viewModelFactory = new ViewModelFactory();
    ViewHandler viewHandler = new ViewHandler(viewModelFactory);
    viewHandler.start(primaryStage);
  }

  public static void main(String[] args) throws IOException
  {
    launch();
  }
}
