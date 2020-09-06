package ai.kitt.vnest.util.common;

public class Regex {
    public static final String DATE = "((ngày|mùng|ngày mùng)\\s)(1[0-9]|2[0-9]|3[0-1]|0?[1-9])";
    public static final String DAY = "((thứ)\\s([2-7]|hai|ba|tư|năm|sáu|bảy))|(chủ nhật)";
    public static final String EMAIL = "[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})";
    public static final String FROM_TO = "từ\\s(.+)\\s(đến|tới|qua|vào)\\s(.+)";
    public static final String FULL_DATE = "((ngày|mùng|ngày mùng)\\s)?(1[0-9]|2[0-9]|3[0-1]|0?[1-9])\\s?(tháng|-|\\/)\\s?(1[0-2]|0?[1-9]|tư)(\\s?(năm|-|\\/)\\s?(\\d{4}))?";
    public static final String MONTH = "tháng\\s(1[0-2]|0?[1-9]|này|tư|giêng|chạp)";
    public static final String NUMBER = "([0-9]+)(\\s(phần trăm|kilômét|mét|nghìn|ngàn|triệu|lần))?";
    public static final String PHONE = "([\\s\\d]){3,}";
    public static final String STREET_NUMBER = "(\\d+\\w?)";
    public static final String TIME = "(1[0-9]|2[0-3]|[0-9]|sáu|bảy|chín)\\s?(giờ|g|h|rưỡi|r|:)\\s?(((kém\\s?)?[0-5]?[0-9](\\s?phút)?)|rưỡi|r)?";

    /* renamed from: TO */
    public static final String f92TO = "(đến|tới|qua|vào)\\s(.+)";
    public static final String URL = "(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?$";
    public static final String WEEK = "(tuần)\\s(này|sau|tới)?";
    public static final String YEAR = "(năm\\s)?(19|20)(\\d){2}";
}
