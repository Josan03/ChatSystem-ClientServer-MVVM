package chat.view;

import chat.viewmodel.ViewModelFactory;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class ViewHandler
{
  public static final String CHAT = "chat";
  public static final String HELLO = "hello";
  private final Scene currentScene;
  private final ViewFactory viewFactory;
  private Stage primaryStage;

  public ViewHandler(ViewModelFactory viewModelFactory)
  {
    this.viewFactory = new ViewFactory(this, viewModelFactory);
    this.currentScene = new Scene(new Region());
  }

  public void start(Stage primaryStage)
  {
    this.primaryStage = primaryStage;
    openView(HELLO);
  }

  public void openView(String id)
  {
    Region root = switch (id)
        {
          case CHAT -> viewFactory.loadChatViewController();
          case HELLO -> viewFactory.loadHelloViewController();
          default -> throw new IllegalArgumentException("Unknown view: " + id);
        };
    currentScene.setRoot(root);
    if (root.getUserData() == null)
    {
      primaryStage.setTitle("");
    }
    else
    {
      primaryStage.setTitle(root.getUserData().toString());
    }

    primaryStage.setScene(currentScene);
    primaryStage.sizeToScene();
    primaryStage.show();
  }

  public void closeView()
  {
    primaryStage.close();
  }
}
