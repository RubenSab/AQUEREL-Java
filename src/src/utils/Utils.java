package utils;

import execution_data_structures.node_content_type.NameContent;
import execution_data_structures.node_content_type.NodeContent;
import execution_data_structures.node_content_type.NumberContent;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static Class<?>[] BINARY_NUM_SIGNATURE = {NumberContent.class, NumberContent.class};
    public static Class<?>[] UNARY_NUM_SIGNATURE = {NumberContent.class};
    public static Class<?>[] UNARY_NAME_SIGNATURE = {NameContent.class};
    public static Class<?>[] UNARY_STR_SIGNATURE = {NameContent.class};
    public static Class<?>[] UNARY_GENERIC_SIGNATURE = {NodeContent.class};
    public static Class<?>[] ARG_LESS_SIGNATURE = {};


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

}
