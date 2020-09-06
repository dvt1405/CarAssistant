package ai.kitt.vnest.util.common;

public class Command {
    public static final String[] AC_AUTO_OFF = {"điều hòa thủ công", "tắt điều hòa tự động", "dừng điều hòa tự động", "tắt làm mát tự động", "dừng làm mát tự động", "tắt nhiệt độ tự động", "dừng nhiệt độ tự động", "chuyển chế độ điều hòa thủ công"};
    public static final String[] AC_AUTO_ON = {"điều hòa tự động", "làm mát tự động", "nhiệt độ tự động", "máy lạnh tự động", "điều hòa không khí tự động", "chuyển chế độ điều hòa tự động"};
    public static final String[] AC_CHANGE = {"chỉnh điều hòa", "chỉnh nhiệt độ", "tăng nhiệt độ", "giảm nhiệt độ", "hạ nhiệt độ", "tăng điều hòa", "giảm điều hòa", "tăng", "giảm", "chỉnh"};
    public static final String[] AC_DOWN = {"giảm nhiệt độ", "hạ nhiệt độ", "giảm điều hòa", "làm mát", "làm lạnh", "nóng quá", "nóng bức quá", "oi quá"};
    public static final String[] AC_DUAL_MODE = {"điều hòa 2 vùng", "điều hòa hai vùng", "điều hòa toàn xe", "điều hòa toàn thân"};
    public static final String[] AC_OFF = {"tắt điều hòa", "dừng điều hòa", "tắt máy lạnh", "dừng máy lạnh", "tắt làm lạnh", "tắt làm mát"};
    public static final String[] AC_ON = {"điều hòa", "mở điều hòa", "bật điều hòa", "làm mát", "làm lanh", "điều hòa không khí", "điều hòa nhiệt độ"};
    public static final String[] AC_SINGLE_MODE = {"điều hòa độc lập", "điều hòa vùng độc lập", "điều hòa hai vùng độc lập", "điều hòa 2 vùng độc lập", "điều hòa một vùng", "điều hòa 1 vùng"};
    public static final String[] AC_UP = {"tăng nhiệt độ", "tăng điều hòa", "làm nóng", "làm ấm", "lạnh quá"};
    public static final String[] AIR_ION_MODE = {"ion hóa", "ion hóa không khí", "lọc không khí", "làm sạch không khí", "làm tươi không khí", "khử mùi", "làm sạch mùi", "làm sạch khoang"};
    public static final String[] AIR_QUALITY = {"thông tin không khí", "chất lượng không khí"};
    public static final String[] DEFROST_OFF;
    public static final String[] DEFROST_ON = {"sấy kính", "sưởi kính"};
    public static final String[] FACE_DEFROST = {"sấy mặt", "sưởi mặt", "thổi mặt"};
    public static final String[] FOOT_DEFROST = {"sấy chân", "sưởi chân", "thổi chân"};
    public static final String[] FRONT_DEFROST_OFF;
    public static final String[] FRONT_DEFROST_ON = {"bật sấy kính", "sấy kính trước", "sấy kính lái", "bật sấy kính trước", "bật sấy kính lái"};
    public static final String[] MAP_OPEN = {"xem bản đồ", "mở bản đồ", "mở định vị"};
    public static final String[] MODE_CHANGE = {"tăng", "giảm", "làm", "chỉnh"};
    public static final String[] MODE_OFF = {"tắt", "dừng", "đóng", "stop", Config.CMD_MUSIC_PAUSE};
    public static final String[] MODE_ON = {"mở", "bật", Constant.ACTION_OPEN, "turn turnOn"};
    public static final String[] MUSIC_NEXT_SONG = {"bài tiếp", "phát bài tiếp", "chơi bài tiếp", "bài kế tiếp", "phát bài kế tiếp", "chơi bài kế tiếp", "qua bài", "bỏ bài"};
    public static final String[] MUSIC_ON = {"mở", "bật", "chơi", "nghe", "tìm", "phát"};
    public static final String[] MUSIC_OPEN = {"mở nhạc", "chơi nhạc", "bật nhạc"};
    public static final String[] MUSIC_PREV_SONG = {"bài trước", "bài vừa phát", "bài trước đó", "phát bài trước", "bật bài trước"};
    public static final String[] MUSIC_STOP = {"tắt nhạc", "dừng nhạc", "dừng chơi nhạc"};
    public static final String[] OPEN_CD_PLAYER = {"phát cd", "chơi cd", "mở cd", "phát dvd", "chơi dvd", "mở dvd"};
    public static final String[] RADIO_NEXT_CHANNEL = {"mở kênh tiếp theo", "sang kênh tiếp theo", "chuyển kênh tiếp theo"};
    public static final String[] RADIO_ON = {"bật radio", "tìm radio"};
    public static final String[] RADIO_PREV_CHANNEL = {"chuyển về kênh trước", "chuyển về kênh cũ"};
    public static final String[] REAR_DEFROST_OFF = {"tắt sấy gương", "dừng sấy gương", "tắt sấy kính sau", "tắt sấy kính hậu", "dừng sấy kính sau", "dừng sấy kính hậu"};
    public static final String[] REAR_DEFROST_ON = {"sấy gương", "bật sấy gương", "sấy kính sau", "bật sấy kính sau", "sấy kính hậu", "bật sấy kính hậu"};
    public static final String[] SPEAKER_OFF = {"tắt loa", "tắt tiếng", "tắt âm lượng", "tắt âm thanh"};
    public static final String[] SPEAKER_ON = {"bật loa", "bật tiếng", "mở loa", "mở tiếng", "mở âm lượng", "bật âm lượng"};
    public static final String[] VOLUME_LOW = {"giảm âm lượng", "giảm loa", "giảm tiếng", "giảm âm thanh"};
    public static final String[] VOLUME_RAISE = {"tăng âm lượng", "tăng loa", "tăng tiếng", "tăng âm thanh"};
    public static final String[] WINDOW_CLOSE = {"đóng cửa", "đóng cửa nóc", "đóng cửa sổ nóc", "đóng cửa sổ trời"};
    public static final String[] WINDOW_OPEN = {"mở cửa", "mở cửa nóc", "mở cửa sổ nóc", "mở cửa sổ trời"};

    static {
        String str = "dừng sấy kính";
        String str2 = "tắt sấy kính";
        DEFROST_OFF = new String[]{str2, str};
        FRONT_DEFROST_OFF = new String[]{str2, str};
    }
}
