package chat.model;

import java.util.ArrayList;

public class UserList
{
  private ArrayList<User> users;

  public UserList()
  {
    this.users = new ArrayList<User>();
  }

  public void removeUser(User user)
  {
    users.remove(user);
  }

  public void setUserList(ArrayList<User> userList)
  {
    this.users = userList;
  }

  public ArrayList<User> getUsers()
  {
    return users;
  }
}
