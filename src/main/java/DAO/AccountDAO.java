package DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {
  
String userRegistrationQuery = "INSERT INTO account(username,password) VALUES (?,?)";
String getUserByUserNameQuery = "SELECT * FROM account WHERE username = ?";
String getUserByUserNameAndPasswordQuery = "SELECT * FROM account WHERE username = ? AND password = ?";

String getUserByIdQuery = "SELECT * FROM account WHERE account_id = ?";




// resitration for new user
public Account userRegistration(Account account){

try (Connection connection = ConnectionUtil.getConnection()) {
  
PreparedStatement ps = connection.prepareStatement(userRegistrationQuery, java.sql.Statement.RETURN_GENERATED_KEYS);
ps.setString(1, account.getUsername());
ps.setString(2, account.getPassword());


int row = ps.executeUpdate();

if (row == 0) {
  return null;
}

ResultSet rs = ps.getGeneratedKeys();

while (rs.next()) {
  account.setAccount_id(rs.getInt("account_id"));
}

return account;
} catch (Exception e) {
  System.out.println("Registration error : "+e);
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



public Account getUserByUserNameAndPassword(String userName, String password){
 
  Account account = null;
  
   try (Connection connection = ConnectionUtil.getConnection()) {
 
 
   PreparedStatement ps = connection.prepareStatement(getUserByUserNameAndPasswordQuery);
   ps.setString(1, userName);
   ps.setString(2, password);
 
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




 public Account getUserById(int account_id){
 
  Account account = null;
  
   try (Connection connection = ConnectionUtil.getConnection()) {
 
 
   PreparedStatement ps = connection.prepareStatement(getUserByIdQuery);
   ps.setInt(1, account_id);
 
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
