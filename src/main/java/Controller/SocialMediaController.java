package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Service.AccountService;
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
    
    public SocialMediaController(){
        accountService = new AccountService();
    }
    
    
     public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this:: accountRegistrationHandler);
       

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
           if ("username empty or blank".equals(e.getMessage())) {
                context.status(400);
            } else if ("password lessthan 4".equals(e.getMessage())) {
                context.status(400);
            }else
            {
                context.status(500);
            }
        }
    }


    // private void userRegistrationHandler(Context context) throws JsonProcessingException{
    //     ObjectMapper mapper = new ObjectMapper();
    //     Account account = mapper.readValue(context.body(), Account.class);


    // }


}