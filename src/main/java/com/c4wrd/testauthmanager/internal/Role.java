package com.c4wrd.testauthmanager.internal;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class Role {

    private String name;

    @Singular
    private Map<String, List<String>> assignments;

    public boolean isAuthorized(String resource, String action) {
        return this.isAuthorizedRecursive(resource, action);
    }

    private boolean isAuthorizedRecursive(String resource, String action) {

        // base case, either the resource will be null or empty
        if (resource == null || resource.isEmpty()) {
            return false;
        }

        // first we will check the full resource path
        if ( this.assignments.containsKey(resource) ) {
            for (String allowedAction : this.assignments.get(resource)) {
                if (allowedAction.equals("*") || allowedAction.equalsIgnoreCase(action)) {
                    return true;
                }
            }
        }

        // check each resource path when split by ":", so if they have
        // root:*:view, then root:user:view will work correctly
        return resource.contains(":") && this.isAuthorizedRecursive(resource.substring(0, resource.lastIndexOf(":")), action);
    }

}
