package Service;

import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Message;

public class MessageService {
  
  MessageDAO messageDAO = null;


public MessageService(){
messageDAO = new MessageDAO();
}



public Message createMessage(Message message){

  AccountDAO accountDAO = new AccountDAO();

if (message.getMessage_text().isEmpty() || message.getMessage_text().isBlank()) {
  throw new IllegalArgumentException("message null or blank");
}

if (message.getMessage_text().length() > 256) {
  throw new IllegalArgumentException("message above 255");
}

if (accountDAO.getUserById(message.getPosted_by()) == null) {
  throw new IllegalArgumentException("account not found");
}

return messageDAO.createMessage(message);

}



public List<Message> getAllMessages(){

return messageDAO.getAllMessages();


}

}
