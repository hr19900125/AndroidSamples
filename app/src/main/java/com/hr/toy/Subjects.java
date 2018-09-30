package com.hr.toy;

import com.hr.toy.router.Subject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Subjects extends ArrayList<Subject> {

    private Map<Integer, Subject> SubMap = new HashMap<>();

    public void putSubject(int id, Subject subject) {
        SubMap.put(id, subject);
    }

    public Subject getSubject(int id) {
        return SubMap.get(id);
    }
}
