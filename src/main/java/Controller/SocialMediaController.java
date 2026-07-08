package Controller;

import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */

     AccountService accountService;
     MessageService messageService;
    
    public SocialMediaController(){
        accountService = new AccountService();
        messageService = new MessageService();
    }
    
    
     public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::accountRegistrationHandler);
        app.post("/login", this::accountLoginHandler);
        app.post("/messages", this::createMessage);
        app.get("/messages", this::getAllMessages);
        app.get("/messages/{message_id}", this::getMessageById);
       

        return app;
    }




    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void accountRegistrationHandler(Context context) throws JsonProcessingException{

        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class); 

        try {
            Account result = accountService.userRegistrationSerice(account);
            context.json(result); 
        } catch (IllegalArgumentException e) {
            if ("userName already exist".equals(e.getMessage())) {
                context.status(400);
            } else if ("username empty or blank".equals(e.getMessage())) {
                context.status(400);
            } else if ("password lessthan 4".equals(e.getMessage())) {
                context.status(400);
            }else
            {
                context.status(500);
            }
        }
    }


    private void accountLoginHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);


        try {
            Account result = accountService.getUserByUserNameAndPassword(account.getUsername(), account.getPassword());
            if (result == null) {
                context.status(401);
            }else{
                context.json(result);
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

 private void createMessage(Context context) throws JsonProcessingException{
    ObjectMapper mapper = new ObjectMapper();
    Message message = mapper.readValue(context.body(), Message.class);

try {
 context.json(messageService.createMessage(message));
} catch (IllegalArgumentException e) {
    if ("message null or blank".equals(e.getMessage())) {
        context.status(400);
    }else if("message above 255".equals(e.getMessage())){
        context.status(400);
    }else if("account not found".equals(e.getMessage())){
        context.status(400);
    }else{
        context.status(500);
    }
}

 }


private void getAllMessages(Context context){
    try {
        context.json(messageService.getAllMessages());
        context.status(200);
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
}


private void getMessageById(Context context){
    try {
        int id = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageById(id);
        
        if (message != null) {
         context.json(message);   
        }
        // no message
        else{
            context.json("");
        }
        context.status(200);
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
}


}

