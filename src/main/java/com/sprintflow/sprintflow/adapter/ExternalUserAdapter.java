package com.sprintflow.sprintflow.adapter;

import org.springframework.stereotype.Component;
import com.sprintflow.sprintflow.model.User;
import java.util.Map;


interface ExternalUserApi {
    Map<String,String> fetchUser(String id);
}

class LdapService implements ExternalUserApi {
    public Map<String,String> fetchUser(String id){
        return Map.of("id", id, "name", "LDAP "+id, "email", id+"@ldap.example");
    }
}

@Component
public class ExternalUserAdapter {
    private final ExternalUserApi ldap = new LdapService();
    public User getUser(String id){
        var map = ldap.fetchUser(id);
        User u = new User(map.get("name"), map.get("email"));
        return u;
    }
}
