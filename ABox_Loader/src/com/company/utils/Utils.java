package com.company.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {
    private static List<String> formations = new ArrayList<String>();
    private static Map<String, String> group_URI = new HashMap<String, String>();
    public static String cleanURI(String id) {
        return id.toLowerCase().trim().replace(" ", "_").replace("ø", "o").replace("æ", "a").replace("å",
                "a").replace("(informal)", "").replace(")", "").replace("√ö","o").replace("√ò","o").replace("v√•","").replace("√•","").replace("r√∏","");
    }
    public static void addFormation(String formation) {
        formations.add(formation);
    }

    public static boolean hasGroup (String group) {
        return group_URI.containsKey(group);
    }
    public static String getGroupURI (String group) {
        return group_URI.get(group);
    }
}
