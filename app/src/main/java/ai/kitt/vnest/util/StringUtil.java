package ai.kitt.vnest.util;

import android.text.TextUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class StringUtil {
    private static final Pattern URL_PATTERN = Pattern.compile("((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)", 2);
    private static final Pattern urlPattern = Pattern.compile("(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)", 42);

    public static List<String> tokenize(String str, String str2) {
        StringTokenizer stringTokenizer = new StringTokenizer(str, str2);
        ArrayList arrayList = new ArrayList();
        while (stringTokenizer.hasMoreTokens()) {
            arrayList.add(stringTokenizer.nextToken());
        }
        return arrayList;
    }

    public static List<String> tokenize(String str) {
        return tokenize(str, " \t\r\n");
    }

    public static String normalize(String str) {
        StringTokenizer stringTokenizer = new StringTokenizer(str, " \t\r\n");
        StringBuilder sb = new StringBuilder();
        while (stringTokenizer.hasMoreTokens()) {
            sb.append(stringTokenizer.nextToken());
            sb.append(StringUtils.SPACE);
        }
        return sb.toString().trim();
    }

    public static String capitalize(String str) {
        if (!(str == null || str.length() == 0)) {
            if (str.length() == 1) {
                return str.toUpperCase();
            }
            StringBuilder sb = new StringBuilder();
            sb.append(str.substring(0, 1).toUpperCase());
            sb.append(str.substring(1));
            str = sb.toString();
        }
        return str;
    }

    public static String removeUrl(String str) {
        return str.replaceAll("(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)", "");
    }

    public static String removeAcronym(String str) {
        String[] split;
        StringBuilder sb = new StringBuilder();
        String str2 = StringUtils.SPACE;
        for (String str3 : str.split(str2)) {
//            Iterator it = AutoAIApp.getAcronymWords().iterator();
//            while (true) {
//                if (!it.hasNext()) {
//                    break;
//                }
//                Acronym acronym = (Acronym) it.next();
//                if (str3.equalsIgnoreCase(acronym.getKey())) {
//                    str3 = acronym.getVal();
//                    break;
//                }
//            }
            sb.append(str3);
            sb.append(str2);
        }
        return sb.toString().trim();
    }

    public static String capsentence(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str.substring(0, 1).toUpperCase());
        sb.append(str.substring(1));
        return sb.toString();
    }

    public static String capword(String str) {
        return WordUtils.capitalizeFully(str);
    }

    public static String join(String str, String... strArr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strArr.length; i++) {
            if (i == strArr.length - 1) {
                sb.append(strArr[i]);
            } else {
                sb.append(strArr[i]);
                sb.append(str);
            }
        }
        return sb.toString();
    }

    public static String[] split(String str, int i) {
        ArrayList arrayList = new ArrayList();
        int length = str.length();
        int i2 = 0;
        while (i2 < length) {
            int i3 = i2 + i;
            arrayList.add(str.substring(i2, Math.min(length, i3)));
            i2 = i3;
        }
        return (String[]) arrayList.toArray(new String[0]);
    }

    public static String fromByteArray(byte... bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte append : bArr) {
            sb.append(append);
            sb.append(StringUtils.SPACE);
        }
        return sb.toString().trim();
    }

    public static String stripAccents(String str) {
        if (str == null) {
            return null;
        }
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[\\p{InCombiningDiacriticalMarks}]", "").replace("đ", "d");
    }

    public static String join(List<String> list, int i, int i2) {
        return join(list, i, i2, StringUtils.SPACE);
    }

    public static String join(List<String> list, int i, int i2, String str) {
        if (i < 0) {
            i = 0;
        }
        if (i2 >= list.size()) {
            i2 = list.size() - 1;
        }
        if (i > i2) {
            return "";
        }
        String str2 = (String) list.get(i);
        while (true) {
            i++;
            if (i > i2) {
                return str2;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append(str);
            sb.append((String) list.get(i));
            str2 = sb.toString();
        }
    }

    public static String join(List<String> list) {
        return join(list, StringUtils.SPACE);
    }

    public static String join(List<String> list, String str) {
        StringBuilder sb = new StringBuilder();
        for (String append : list) {
            sb.append(append);
            sb.append(str);
        }
        return sb.toString().trim();
    }
}
