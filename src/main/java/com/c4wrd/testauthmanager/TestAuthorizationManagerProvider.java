package com.c4wrd.testauthmanager;

import com.c4wrd.testauthmanager.internal.RoleFile;
import com.c4wrd.testauthmanager.internal.Role;
import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import lombok.extern.log4j.Log4j2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
public class TestAuthorizationManagerProvider {

    public static TestAuthorizationManager fromFile(String fileName) throws FileNotFoundException, YamlException {
        YamlReader reader = new YamlReader(new FileReader(fileName));
        RoleFile roleFile = reader.read(RoleFile.class);
        Map<String, Role> roleDeclarations = roleFile.getRoles()
                .stream()
                .collect(Collectors.toMap(Role::getName, it -> it));

        Multimap<Long, Role> assignments = LinkedListMultimap.create();

        for ( String accountIdStr : roleFile.getAssignments().keySet() ) {
            long accountId = Long.parseLong(accountIdStr);

            for ( String roleName : roleFile.getAssignments().get(accountIdStr) ) {
                if ( roleDeclarations.containsKey(roleName) ) {
                    assignments.put(accountId, roleDeclarations.get(roleName));
                } else {
                    throw new RuntimeException(String.format("The role '%s' was assigned to user:%d, but the role did not exist", roleName, accountId));
                }
            }
        }

        return fromMap(Multimaps.asMap(assignments));
    }

    public static TestAuthorizationManager fromMap(Map<Long, Collection<Role>> assignments) {
        return new TestAuthorizationManager(assignments);
    }

}
