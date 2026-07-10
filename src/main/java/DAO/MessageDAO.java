package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
  

String createMessageQuery = "INSERT INTO message(posted_by,message_text,time_posted_epoch) VALUES (?,?,?)";
String getAllMessagesQuery = "SELECT * FROM message";
String getMessageByIdQuery = "SELECT * FROM message WHERE message_id = ?";
String deleteMessageByIdQuery = "DELETE FROM message WHERE message_id = ?";
String updateMessageByIdQuery = "UPDATE message SET message_text = ? WHERE message_id = ?";
String getMessageByUserIdQuery = "SELECT * FROM message WHERE posted_by = ?";

public Message createMessage(Message message){

try (Connection connection = ConnectionUtil.getConnection()) {
  
PreparedStatement ps = connection.prepareStatement(createMessageQuery, Statement.RETURN_GENERATED_KEYS);

ps.setInt(1, message.getPosted_by());
ps.setString(2, message.getMessage_text());
ps.setLong(3, message.getTime_posted_epoch());

int rows = ps.executeUpdate();

if (rows == 0) return null;



ResultSet rs = ps.getGeneratedKeys();
while (rs.next()) {
  message.setMessage_id(rs.getInt("message_id"));
} 

} catch (SQLException e) {
  System.out.println("error in creating message : "+e.getMessage());
}

return message;
}



public List<Message> getAllMessages(){

  List<Message> result = new ArrayList<>();

  try (Connection connection = ConnectionUtil.getConnection()) {
    PreparedStatement ps = connection.prepareStatement(getAllMessagesQuery);

    ResultSet rs = ps.executeQuery();

    while (rs.next()) {
      result.add(new Message(
       rs.getInt("message_id"),  
       rs.getInt("posted_by"),
       rs.getString("message_text"),
       rs.getLong("time_posted_epoch")
      ));
    }
    
  } catch (SQLException e) {
    System.out.println("There is an error retrieving messages : "+e.getMessage());
  }

  return result;

}



public Message getMessageById(int message_id){

  Message message = null;

try (Connection connection = ConnectionUtil.getConnection()) {

  PreparedStatement ps = connection.prepareStatement(getMessageByIdQuery);
  ps.setInt(1, message_id);

  ResultSet rs = ps.executeQuery();


  while (rs.next()) {
    message = new Message();
    message.setMessage_id(rs.getInt("message_id"));  
    message.setPosted_by(rs.getInt("posted_by"));
    message.setMessage_text(rs.getString("message_text"));
    message.setTime_posted_epoch(rs.getLong("time_posted_epoch"));
  }
  
} catch (SQLException e) {
  System.out.println("There is an error retrieving selected message : "+e.getMessage());
}

return message;

}


public Message deleteMessageById(int id){

  Message message = getMessageById(id);

  if (message == null) return null;

try (Connection connection = ConnectionUtil.getConnection()) {

  PreparedStatement ps = connection.prepareStatement(deleteMessageByIdQuery);
  ps.setInt(1, id);

  int rows = ps.executeUpdate();

  if (rows == 0) {
    return null;
  }

    return message;

  
} catch (SQLException e) {
  System.out.println("There is an error deleting message : "+e.getMessage());
}


return null;
}



public Message updateMessageById(int id, String message_text){

try (Connection connection = ConnectionUtil.getConnection()) {
  
  PreparedStatement ps = connection.prepareStatement(updateMessageByIdQuery);
  ps.setString(1, message_text);
  ps.setInt(2, id);
  int rows = ps.executeUpdate();

  if (rows == 0) {
    return null;
  }

return getMessageById(id);

} catch (SQLException e) {
  System.out.println("There is an error updating this message : "+e.getMessage());
}
return null;
}



public List<Message> getMessagesByUserId(int id){

List<Message> messages = null;
try (Connection connection = ConnectionUtil.getConnection()) {
  
PreparedStatement ps = connection.prepareStatement(getAllMessagesQuery);
ps.setInt(1, id);

ResultSet rs = ps.executeQuery();
messages = new ArrayList<>();
while (rs.next()) {
  Message message = new Message();
  message.setMessage_id(rs.getInt("message_id"));
  message.setMessage_text(rs.getString("message_text"));
  message.setPosted_by(rs.getInt("posted_by"));
  message.setTime_posted_epoch(rs.getLong("time_posted_epoch"));
  messages.add(message);
}
} catch (SQLException e) {
  System.out.println("there is an error retrieving messages from user : "+e.getMessage());
}

return messages;
}


}
