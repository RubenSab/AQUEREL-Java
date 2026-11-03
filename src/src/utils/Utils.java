package utils;

import execution_data_structures.node_content_type.NameContent;
import execution_data_structures.node_content_type.NodeContent;
import execution_data_structures.node_content_type.NumberContent;
import execution_data_structures.node_content_type.StringContent;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static final Class<?>[] BINARY_NUM_SIGNATURE = {NumberContent.class, NumberContent.class};
    public static final Class<?>[] UNARY_NUM_SIGNATURE = {NumberContent.class};
    public static final Class<?>[] UNARY_NAME_SIGNATURE = {NameContent.class};
    public static final Class<?>[] UNARY_STR_SIGNATURE = {StringContent.class};
    public static final Class<?>[] UNARY_GENERIC_SIGNATURE = {NodeContent.class};
    public static final Class<?>[] ARG_LESS_SIGNATURE = {};


    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static List<String> findallMatches(String regex, String input) {
        List<String> allMatches = new ArrayList<>();
        Matcher m = Pattern.compile(regex).matcher(input);
        while (m.find()) {
            allMatches.add(m.group());
        }
        return allMatches;
    }

    public static String unescapeJava(String s) { // AI
        StringBuilder sb = new StringBuilder(s.length());
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c != '\\' || i == s.length() - 1) { sb.append(c); continue; }
            char n = s.charAt(++i);
            switch (n) {
                case 'n'  -> sb.append('\n');
                case 'r'  -> sb.append('\r');
                case 't'  -> sb.append('\t');
                case 'b'  -> sb.append('\b');
                case 'f'  -> sb.append('\f');
                case '\\' -> sb.append('\\');
                case '\'' -> sb.append('\'');
                case '"'  -> sb.append('"');
                case 'u'  -> { sb.append((char) Integer.parseInt(s.substring(i + 1, i + 5), 16)); i += 4; }
                default   -> sb.append(n);
            }
        }
        return sb.toString();
    }
}
