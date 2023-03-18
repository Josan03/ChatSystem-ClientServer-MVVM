package chat.view;

import chat.model.Message;
import chat.model.User;
import chat.viewmodel.ChatViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ChatViewController implements PropertyChangeListener
{
  @FXML ListView<User> userList;
  @FXML ListView<Message> chatList;
  @FXML Button exitButton;
  @FXML Button messageSend;
  @FXML TextArea messageInput;

  private ViewHandler viewHandler;
  private ChatViewModel chatViewModel;
  private Region root;

  @FXML public void onExit()
  {
    chatViewModel.removeUser();
    viewHandler.closeView();
  }

  @FXML public void onSend()
  {

    if (!messageInput.getText().trim().isEmpty())
    {
      chatViewModel.addMessage(chatViewModel.getUser(), messageInput.getText());
      messageInput.setText("");
    }
    else
    {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Error");
      alert.setHeaderText("Empty message");
      alert.showAndWait();
    }
  }

  public void init(ViewHandler viewHandler, ChatViewModel chatViewModel, Region root)
  {
    this.viewHandler = viewHandler;
    this.chatViewModel = chatViewModel;
    this.root = root;
    this.chatViewModel.bindMessages(chatList.itemsProperty());
    this.chatViewModel.bindUsers(userList.itemsProperty());
    this.chatViewModel.bindMessage(messageInput.textProperty());

    this.chatViewModel.addPropertyChangeListener("update", this);
    this.chatViewModel.update();
  }

  public Region getRoot()
  {
    return root;
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    chatViewModel.update();
  }
}
