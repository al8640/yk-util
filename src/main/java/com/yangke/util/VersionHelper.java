package com.yangke.util;

import java.util.regex.Pattern;

/**
 * @author ke.yang1
 * @description
 * @date 2022/6/10 4:00 下午
 */
public class VersionHelper {
    public static int compare(String v1, String v2) {
        String s1 = normalisedVersion(v1);
        String s2 = normalisedVersion(v2);
        return s1.compareTo(s2);
    }

    private static String normalisedVersion(String version) {
        return normalisedVersion(version, ".", 4);
    }

    private static String normalisedVersion(String version, String sep, int maxWidth) {
        String[] split = Pattern.compile(sep, Pattern.LITERAL).split(version);
        StringBuilder sb = new StringBuilder();
        for (String s : split) {
            int i = Integer.parseInt(s);
            sb.append(String.format("%" + maxWidth + 's', i));
        }
        return sb.toString();
    }
}
