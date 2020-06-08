package ann.util;

import ann.User;

public class Util {
  public void test(){
    System.out.println("start");
    System.out.println(User.obj);
    System.out.println(User.getObj());
    User user=new User();
    System.out.println(user.name);
    System.out.println(user.getName());
  }
}
