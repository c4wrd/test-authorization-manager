package com.c4wrd.testauthmanager;

import com.esotericsoftware.yamlbeans.YamlException;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class TestAuthorizationManagerProviderTest {

    @Test
    void fromFile() throws FileNotFoundException, YamlException {
        TestAuthorizationManager manager = TestAuthorizationManagerProvider.fromFile("src/main/resources/roles.yaml");

        System.out.println(manager.isAuthorized(2L, "root:company:_vertical", "edit"));
    }
}