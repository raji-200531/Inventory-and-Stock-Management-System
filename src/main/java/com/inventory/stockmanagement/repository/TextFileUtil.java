package com.inventory.stockmanagement.repository;

import java.util.ArrayList;
import java.util.List;

public final class TextFileUtil {
    private TextFileUtil() {}

    public static String escape(String value) {
        if (value == null) return "";
        return value.replace("\\", "\\\\").replace("|", "\\p").replace("\n", "\\n").replace("\r", "\\r");
    }

    public static String unescape(String value) {
        if (value == null) return "";
        StringBuilder out = new StringBuilder();
        boolean esc = false;
        for (char c : value.toCharArray()) {
            if (esc) {
                if (c == 'p') out.append('|');
                else if (c == 'n') out.append('\n');
                else if (c == 'r') out.append('\r');
                else out.append(c);
                esc = false;
            } else if (c == '\\') {
                esc = true;
            } else {
                out.append(c);
            }
        }
        if (esc) out.append('\\');
        return out.toString();
    }

    public static String join(String... values) {
        List<String> list = new ArrayList<>();
        for (String value : values) list.add(escape(value));
        return String.join("|", list);
    }

    public static String[] split(String line) {
        List<String> parts = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        boolean esc = false;
        for (char c : line.toCharArray()) {
            if (esc) {
                cur.append('\\').append(c);
                esc = false;
            } else if (c == '\\') {
                esc = true;
            } else if (c == '|') {
                parts.add(unescape(cur.toString()));
                cur.setLength(0);
            } else {
                cur.append(c);
            }
        }
        if (esc) cur.append('\\');
        parts.add(unescape(cur.toString()));
        return parts.toArray(new String[0]);
    }
}
