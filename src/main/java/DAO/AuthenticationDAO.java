package DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;

import Model.Account;
import Util.ConnectionUtil;

public class AuthenticationDAO {
  
String userRegistrationQuery = "INSERT INTO account(username,password) VALUES (?,?)";




// resitration for new user
public void userRegistration(Account account){

try (Connection connection = ConnectionUtil.getConnection()) {
  
PreparedStatement ps = connection.prepareStatement(userRegistrationQuery);
ps.setString(1, account.username);
ps.setString(2, account.password);


ps.executeUpdate();
} catch (Exception e) {
  System.out.println("Registration error :"+e);
}
}






}
