package chat.model;

public class User
{
  private String name;

  public User(String name)
  {
    this.name = name;
  }

  public String getName()
  {
    return name;
  }

  public boolean equals(Object obj)
  {
    if (obj == null || getClass() != obj.getClass())
    {
      return false;
    }

    User other = (User) obj;
    return name.equals(other.name);
  }

  public String toString()
  {
    return name;
  }
}

