package chat.view;

import chat.model.User;
import chat.viewmodel.HelloViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

public class HelloViewController
{
  @FXML public TextField nameInput;
  @FXML public Button confirmButton;

  private ViewHandler viewHandler;
  private HelloViewModel helloViewModel;
  private Region root;

  @FXML public void onEnterName()
  {
    boolean ok = false;
    if (!nameInput.getText().trim().isEmpty())
    {
       ok = helloViewModel.addUser(new User(nameInput.getText()));
    }

    if (ok)
    {
      viewHandler.openView(ViewHandler.CHAT);
      helloViewModel.reset();
    }
    else
    {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Error");
      alert.setHeaderText("Empty name or name already taken");
      alert.showAndWait();
      helloViewModel.reset();
    }
  }

  public void init(ViewHandler viewHandler, HelloViewModel helloViewModel, Region root)
  {
    this.viewHandler = viewHandler;
    this.helloViewModel = helloViewModel;
    this.root = root;

    this.helloViewModel.bindName(nameInput.textProperty());
  }

  public Region getRoot()
  {
    return root;
  }
}
