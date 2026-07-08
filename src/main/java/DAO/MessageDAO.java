package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
  

String createMessageQuery = "INSERT INTO message(posted_by,message_text,time_posted_epoch) VALUES (?,?,?)";


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



}
