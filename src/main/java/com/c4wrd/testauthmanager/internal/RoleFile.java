package com.c4wrd.testauthmanager.internal;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class RoleFile {

    private List<Role> roles;

    private Map<String, List<String>> assignments;

}
