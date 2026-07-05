package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
  public AccountDAO accountDAO;


public AccountService(){
  accountDAO = new AccountDAO();
}



public Account userRegistrationSerice(Account account){

if (account.getUsername().isEmpty() || account.getUsername().isBlank()) {
  throw new IllegalArgumentException("username empty or blank");
}

if (account.getPassword().length() < 4) {
  throw new IllegalArgumentException("password lessthan 4");
}

if (getUserByUserName(account.getUsername()) != null) {
  throw new IllegalArgumentException("userName already exist");
}

return accountDAO.userRegistration(account);

}


private Account getUserByUserName(String userName){
  return accountDAO.getUserByUserName(userName);
}


}
