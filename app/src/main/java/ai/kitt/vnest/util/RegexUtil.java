package ai.kitt.vnest.util;

public class RegexUtil {
    public static boolean equalsIgnoreCase(String str, String... strArr) {
        for (String equalsIgnoreCase : strArr) {
            if (str.equalsIgnoreCase(equalsIgnoreCase)) {
                return true;
            }
        }
        return false;
    }

    public static boolean endWidth(String str, String... strArr) {
        for (String endsWith : strArr) {
            if (str.endsWith(endsWith)) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(String str, String... strArr) {
        for (String contains : strArr) {
            if (str.contains(contains)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsIgnoreCase(String str, String... strArr) {
        String lowerCase = str.toLowerCase();
        for (String lowerCase2 : strArr) {
            if (lowerCase.contains(lowerCase2.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public static boolean startsWidth(String str, String... strArr) {
        String lowerCase = str.toLowerCase();
        for (String lowerCase2 : strArr) {
            if (lowerCase.startsWith(lowerCase2.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
