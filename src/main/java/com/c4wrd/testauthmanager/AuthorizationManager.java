package com.c4wrd.testauthmanager;

public interface AuthorizationManager {
    boolean isAuthorized(long accountId, String resource, String action);
}
