package com.vnest.txznet.smartadapter.wakeup;

import java.util.HashMap;

public class WakeupCommand {
    static HashMap<String, String> mWakeup = new HashMap<>();
    public static final String wakeup_key_answer = "wakeup_key_answer";
    public static final String wakeup_key_back_to_menu = "wakeup_key_back_to_menu";
    public static final String wakeup_key_close_all_windows = "wakeup_key_close_all_windows";
    public static final String wakeup_key_close_camera = "wakeup_key_close_camera";
    public static final String wakeup_key_close_sunroof = "wakeup_key_close_sunroof";
    public static final String wakeup_key_continue_playing = "wakeup_key_continue_playing";
    public static final String wakeup_key_hang_up = "wakeup_key_hang_up";
    public static final String wakeup_key_next_track = "wakeup_key_next_track";
    public static final String wakeup_key_open_all_windows = "wakeup_key_open_all_windows";
    public static final String wakeup_key_open_camera = "wakeup_key_open_camera";
    public static final String wakeup_key_open_dvr_teyes = "wakeup_key_open_dvr_teyes";
    public static final String wakeup_key_open_front_camera = "wakeup_key_open_front_camera";
    public static final String wakeup_key_open_front_left_window = "wakeup_key_open_front_left_window";
    public static final String wakeup_key_open_front_right_window = "wakeup_key_open_front_right_window";
    public static final String wakeup_key_open_left_camera = "wakeup_key_open_left_camera";
    public static final String wakeup_key_open_panorama = "wakeup_key_open_panorama";
    public static final String wakeup_key_open_rear_camera = "wakeup_key_open_rear_camera";
    public static final String wakeup_key_open_rear_left_window = "wakeup_key_open_rear_left_window";
    public static final String wakeup_key_open_rear_right_window = "wakeup_key_open_rear_right_window";
    public static final String wakeup_key_open_right_camera = "wakeup_key_open_right_camera";
    public static final String wakeup_key_open_sunroof = "wakeup_key_open_sunroof";
    public static final String wakeup_key_pause_playback = "wakeup_key_pause_playback";
    public static final String wakeup_key_play_movie = "wakeup_key_play_movie";
    public static final String wakeup_key_play_music = "wakeup_key_play_music";
    public static final String wakeup_key_play_radio = "wakeup_key_play_radio";
    public static final String wakeup_key_previous_track = "wakeup_key_previous_track";
    public static final String wakeup_key_set_eighteen_degrees = "wakeup_key_set_eighteen_degrees";
    public static final String wakeup_key_set_nineteen_degrees = "wakeup_key_set_nineteen_degrees";
    public static final String wakeup_key_set_seventeen_degrees = "wakeup_key_set_seventeen_degrees";
    public static final String wakeup_key_set_sixteen_degrees = "wakeup_key_set_sixteen_degrees";
    public static final String wakeup_key_set_thirty_degrees = "wakeup_key_set_thirty_degrees";
    public static final String wakeup_key_set_twenty_degrees = "wakeup_key_set_twenty_degrees";
    public static final String wakeup_key_set_twenty_eight_degrees = "wakeup_key_set_twenty_eight_degrees";
    public static final String wakeup_key_set_twenty_five_degrees = "wakeup_key_set_twenty_five_degrees";
    public static final String wakeup_key_set_twenty_four_degrees = "wakeup_key_set_twenty_four_degrees";
    public static final String wakeup_key_set_twenty_nine_degrees = "wakeup_key_set_twenty_nine_degrees";
    public static final String wakeup_key_set_twenty_one_degrees = "wakeup_key_set_twenty_one_degrees";
    public static final String wakeup_key_set_twenty_seven_degrees = "wakeup_key_set_twenty_seven_degrees";
    public static final String wakeup_key_set_twenty_six_degrees = "wakeup_key_set_twenty_six_degrees";
    public static final String wakeup_key_set_twenty_three_degrees = "wakeup_key_set_twenty_three_degrees";
    public static final String wakeup_key_set_twenty_two_degrees = "wakeup_key_set_twenty_two_degrees";
    public static final String wakeup_key_turn_off_air_conditioning = "wakeup_key_turn_off_air_conditioning";
    public static final String wakeup_key_turn_off_bluetooth = "wakeup_key_turn_off_bluetooth";
    public static final String wakeup_key_turn_off_movie = "wakeup_key_turn_off_movie";
    public static final String wakeup_key_turn_off_music = "wakeup_key_turn_off_music";
    public static final String wakeup_key_turn_off_navigation = "wakeup_key_turn_off_navigation";
    public static final String wakeup_key_turn_off_radio = "wakeup_key_turn_off_radio";
    public static final String wakeup_key_turn_off_screen = "wakeup_key_turn_off_screen";
    public static final String wakeup_key_turn_off_tv = "wakeup_key_turn_off_tv";
    public static final String wakeup_key_turn_on_air_conditioning = "wakeup_key_turn_on_air_conditioning";
    public static final String wakeup_key_turn_on_bluetooth = "wakeup_key_turn_on_bluetooth";
    public static final String wakeup_key_turn_on_navigation = "wakeup_key_turn_on_navigation";
    public static final String wakeup_key_turn_on_screen = "wakeup_key_turn_on_screen";
    public static final String wakeup_key_turn_on_tv = "wakeup_key_turn_on_tv";
    public static final String wakeup_key_volume_down = "wakeup_key_volume_down";
    public static final String wakeup_key_volume_up = "wakeup_key_volume_up";

    public static String[] getWakeupText(String str) {
        if (mWakeup.containsKey(str)) {
            return ((String) mWakeup.get(str)).split("\\+");
        }
        return null;
    }
}
