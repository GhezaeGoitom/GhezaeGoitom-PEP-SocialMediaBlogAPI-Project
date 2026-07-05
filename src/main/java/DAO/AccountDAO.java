package DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {
  
String userRegistrationQuery = "INSERT INTO account(username,password) VALUES (?,?)";
String getUserByUserNameQuery = "SELECT * FROM account WHERE account_id = ?";




// resitration for new user
public Account userRegistration(Account account){

try (Connection connection = ConnectionUtil.getConnection()) {
  
PreparedStatement ps = connection.prepareStatement(userRegistrationQuery);
ps.setString(1, account.username);
ps.setString(2, account.password);


ps.executeUpdate();

return account;
} catch (Exception e) {
  System.out.println("Registration error :"+e);
}
return null;
}


//get user by username

public Account getUserByUserName(String userName){
 
 Account account = null;
 
  try (Connection connection = ConnectionUtil.getConnection()) {


  PreparedStatement ps = connection.prepareStatement(getUserByUserNameQuery);
  ps.setString(1, userName);

  ResultSet rs = ps.executeQuery();

  while (rs.next()) {
    account = new Account();
    account.setAccount_id(rs.getInt("account_id"));
    account.setUsername(rs.getString("username"));
    account.setPassword(rs.getString("password"));
  }
   
 } catch (SQLException e) {
  System.out.println("There is an error retrieving user : " + e.getMessage());
 }

 return account;
}



}
