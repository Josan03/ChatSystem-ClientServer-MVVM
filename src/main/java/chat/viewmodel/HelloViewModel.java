package chat.viewmodel;

import chat.model.ModelManager;
import chat.model.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class HelloViewModel
{
  private final ModelManager modelManager;
  private final SimpleStringProperty name;
  public HelloViewModel(ModelManager instance)
  {
    this.modelManager = instance;
    this.name = new SimpleStringProperty("");
  }

  public void bindName(StringProperty property)
  {
    property.bindBidirectional(name);
  }

  public boolean addUser(User user)
  {
    return modelManager.addUser(user);
  }

  public void reset()
  {
    name.set("");
  }
}
