package chat.viewmodel;

import chat.model.ModelManager;

public class ViewModelFactory
{
  private final ChatViewModel chatViewModel;
  private final HelloViewModel helloViewModel;

  public ViewModelFactory()
  {
    this.chatViewModel = new ChatViewModel(ModelManager.getInstance());
    this.helloViewModel = new HelloViewModel(ModelManager.getInstance());
  }

  public ChatViewModel getChatViewModel()
  {
    return chatViewModel;
  }

  public HelloViewModel getHelloViewModel()
  {
    return helloViewModel;
  }
}
