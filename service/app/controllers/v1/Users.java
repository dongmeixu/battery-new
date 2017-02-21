package controllers.v1;

import controllers.api.API;
import models.User;
import org.bson.types.ObjectId;
import play.data.validation.Required;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static play.modules.jongo.BaseModel.getCollection;


public class Users extends API {


    public static void save(){
        User user = readBody(User.class);
        User userSave = getCollection(User.class).findOne("{userName:#}",user.getUserName()).as(User.class);
        if(userSave==null)
        {
            user.save();
            renderJSON("{\"success\":\"ok\"}");
        }else{
            render("{\"msg\":\"此用戶名已存在！\"}");
        }

    }
    public static void list(){
     List<User> users=  StreamSupport.stream( getCollection(User.class).find().limit(10).as(User.class).spliterator(),false).collect(Collectors.toList());
       renderJSON(users);
    }
    public static void get(@Required String  id){
        User user =  getCollection(User.class).findOne(new ObjectId(id)).as(User.class);
        if (user == null) {
            notFound(id);
        }else {
            renderJSON(user);
        }
    }
}