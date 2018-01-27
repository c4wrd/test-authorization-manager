package com.c4wrd.testauthmanager;

import com.c4wrd.testauthmanager.internal.Role;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Test Authorization Manager that allows for a
 * map of user id to com.c4wrd.testauthmanager.role assignments
 */
public class TestAuthorizationManager implements AuthorizationManager {

    private Map<Long, Collection<Role>> assignments;

    public TestAuthorizationManager(Map<Long, Collection<Role>> assignments) {
        this.assignments = assignments;
    }

    public boolean isAuthorized(long accountId, String resource, String action) {

        if ( this.assignments.containsKey(accountId) ) {
            for ( Role role : this.assignments.get(accountId) ) {
                if ( role.isAuthorized(resource, action ) ) {
                    return true;
                }
            }
        }

        return false;
    }
}
