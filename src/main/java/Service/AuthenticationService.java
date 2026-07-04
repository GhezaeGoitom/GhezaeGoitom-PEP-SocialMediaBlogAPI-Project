package Service;

import DAO.AuthenticationDAO;
import Model.Account;

public class AuthenticationService {
  public AuthenticationDAO authDAO;


public AuthenticationService(){
  authDAO = new AuthenticationDAO();
}



public void userRegistrationSerice(Account account){
authDAO.userRegistration(account);
}




}
