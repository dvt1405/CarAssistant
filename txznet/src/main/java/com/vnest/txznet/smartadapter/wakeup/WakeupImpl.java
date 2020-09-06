package com.vnest.txznet.smartadapter.wakeup;


import com.vnest.txznet.App;
import com.vnest.txznet.smartadapter.api.HandleCenter;

import kun.kt.opencam.air.AirControl;

public class WakeupImpl extends WakeupCommand {
    public static void onWakeup(String str) {
        boolean compare = compare(str, WakeupCommand.wakeup_key_play_movie);
        String str2 = App.SYU_VIDEO_PKG;
        if (compare) {
            HandleCenter.startApp(str2);
        } else if (compare(str, WakeupCommand.wakeup_key_turn_off_movie)) {
            HandleCenter.closeApp(str2);
        } else {
            boolean compare2 = compare(str, WakeupCommand.wakeup_key_play_radio);
            String str3 = App.SYU_RADIO_PKG;
            if (compare2) {
                HandleCenter.startApp(str3);
            } else if (compare(str, WakeupCommand.wakeup_key_turn_off_radio)) {
                HandleCenter.closeApp(str3);
            } else {
                boolean compare3 = compare(str, WakeupCommand.wakeup_key_play_music);
                String str4 = App.SYU_MUSIC_PKG;
                if (compare3) {
                    HandleCenter.startApp(str4);
                } else if (compare(str, WakeupCommand.wakeup_key_turn_off_music)) {
                    HandleCenter.closeApp(str4);
                } else {
                    boolean compare4 = compare(str, WakeupCommand.wakeup_key_turn_on_bluetooth);
                    String str5 = App.SYU_BT_PKG;
                    if (compare4) {
                        HandleCenter.startApp(str5);
                    } else if (compare(str, WakeupCommand.wakeup_key_turn_off_bluetooth)) {
                        HandleCenter.closeApp(str5);
                    } else if (compare(str, WakeupCommand.wakeup_key_turn_on_navigation)) {
                        HandleCenter.startNavi();
                    } else if (compare(str, WakeupCommand.wakeup_key_turn_off_navigation)) {
                        HandleCenter.closeNavi();
                    } else {
                        boolean compare5 = compare(str, WakeupCommand.wakeup_key_turn_on_tv);
                        String str6 = App.YOUTUBE_PKG;
                        if (compare5) {
                            HandleCenter.startApp(str6);
                        } else if (compare(str, WakeupCommand.wakeup_key_turn_off_tv)) {
                            HandleCenter.closeApp(str6);
                        } else if (compare(str, WakeupCommand.wakeup_key_back_to_menu)) {
                            HandleCenter.backHome();
                        } else if (compare(str, WakeupCommand.wakeup_key_continue_playing)) {
                            HandleCenter.play();
                        } else if (compare(str, WakeupCommand.wakeup_key_pause_playback)) {
                            HandleCenter.pause();
                        } else if (compare(str, WakeupCommand.wakeup_key_previous_track)) {
                            HandleCenter.prev();
                        } else if (compare(str, WakeupCommand.wakeup_key_next_track)) {
                            HandleCenter.next();
                        } else if (compare(str, WakeupCommand.wakeup_key_volume_up)) {
                            HandleCenter.volumeUp();
                        } else if (compare(str, WakeupCommand.wakeup_key_volume_down)) {
                            HandleCenter.volumeDown();
                        } else if (compare(str, WakeupCommand.wakeup_key_turn_on_screen)) {
                            HandleCenter.screenOn();
                        } else if (compare(str, WakeupCommand.wakeup_key_turn_off_screen)) {
                            HandleCenter.screenOff();
                        } else if (compare(str, WakeupCommand.wakeup_key_turn_on_air_conditioning)) {
                            AirControl.getInstance().airPower(true);
                        } else if (compare(str, WakeupCommand.wakeup_key_turn_off_air_conditioning)) {
                            AirControl.getInstance().airPower(false);
                        } else if (compare(str, WakeupCommand.wakeup_key_set_sixteen_degrees)) {
                            AirControl.getInstance().setAirTempValue(4098, 16);
                        } else if (compare(str, WakeupCommand.wakeup_key_set_seventeen_degrees)) {
                            AirControl.getInstance().setAirTempValue(4098, 17);
                        } else if (compare(str, WakeupCommand.wakeup_key_set_eighteen_degrees)) {
                            AirControl.getInstance().setAirTempValue(4098, 18);
                        } else if (compare(str, WakeupCommand.wakeup_key_set_nineteen_degrees)) {
                            AirControl.getInstance().setAirTempValue(4098, 19);
                        } else if (compare(str, WakeupCommand.wakeup_key_set_twenty_degrees)) {
                            AirControl.getInstance().setAirTempValue(4098, 20);
                        } else if (compare(str, WakeupCommand.wakeup_key_set_twenty_one_degrees)) {
                            AirControl.getInstance().setAirTempValue(4098, 21);
                        } else if (compare(str, WakeupCommand.wakeup_key_set_twenty_two_degrees)) {
                            AirControl.getInstance().setAirTempValue(4098, 22);
                        } else if (compare(str, WakeupCommand.wakeup_key_set_twenty_three_degrees)) {
                            AirControl.getInstance().setAirTempValue(4098, 23);
                        } else if (compare(str, WakeupCommand.wakeup_key_set_twenty_four_degrees)) {
                            AirControl.getInstance().setAirTempValue(4098, 24);
                        } else if (compare(str, WakeupCommand.wakeup_key_set_twenty_five_degrees)) {
                            AirControl.getInstance().setAirTempValue(4098, 25);
                        } else if (compare(str, WakeupCommand.wakeup_key_set_twenty_six_degrees)) {
                            AirControl.getInstance().setAirTempValue(4098, 26);
                        } else if (compare(str, WakeupCommand.wakeup_key_set_twenty_seven_degrees)) {
                            AirControl.getInstance().setAirTempValue(4098, 27);
                        } else if (compare(str, WakeupCommand.wakeup_key_set_twenty_eight_degrees)) {
                            AirControl.getInstance().setAirTempValue(4098, 28);
                        } else if (compare(str, WakeupCommand.wakeup_key_set_twenty_nine_degrees)) {
                            AirControl.getInstance().setAirTempValue(4098, 29);
                        } else if (compare(str, WakeupCommand.wakeup_key_set_thirty_degrees)) {
                            AirControl.getInstance().setAirTempValue(4098, 30);
                        } else if (compare(str, WakeupCommand.wakeup_key_open_sunroof)) {
                            HandleCenter.sendCmd(68, 10);
                        } else if (compare(str, WakeupCommand.wakeup_key_close_sunroof)) {
                            HandleCenter.sendCmd(68, 9);
                        } else if (compare(str, WakeupCommand.wakeup_key_open_all_windows)) {
                            HandleCenter.sendCmd(68, 12);
                        } else if (compare(str, WakeupCommand.wakeup_key_close_all_windows)) {
                            HandleCenter.sendCmd(68, 11);
                        } else if (compare(str, WakeupCommand.wakeup_key_open_front_left_window)) {
                            HandleCenter.sendCmd(68, 2);
                        } else if (compare(str, WakeupCommand.wakeup_key_open_front_right_window)) {
                            HandleCenter.sendCmd(68, 4);
                        } else if (compare(str, WakeupCommand.wakeup_key_open_rear_left_window)) {
                            HandleCenter.sendCmd(68, 6);
                        } else if (compare(str, WakeupCommand.wakeup_key_open_rear_right_window)) {
                            HandleCenter.sendCmd(68, 8);
                        } else if (compare(str, WakeupCommand.wakeup_key_open_panorama)) {
                            HandleCenter.sendCmd(67, 0);
                        } else if (compare(str, WakeupCommand.wakeup_key_open_front_camera)) {
                            HandleCenter.sendCmd(67, 2);
                        } else if (compare(str, WakeupCommand.wakeup_key_open_rear_camera)) {
                            HandleCenter.sendCmd(67, 3);
                        } else if (compare(str, WakeupCommand.wakeup_key_open_left_camera)) {
                            HandleCenter.sendCmd(67, 4);
                        } else if (compare(str, WakeupCommand.wakeup_key_open_right_camera)) {
                            HandleCenter.sendCmd(67, 5);
                        } else {
                            boolean compare6 = compare(str, WakeupCommand.wakeup_key_open_camera);
                            String str7 = App.TEYES_REAR_CAMERA_PKG;
                            if (compare6) {
                                HandleCenter.startApp(str7);
                            } else if (compare(str, WakeupCommand.wakeup_key_close_camera)) {
                                HandleCenter.closeApp(str7);
                            } else if (compare(str, WakeupCommand.wakeup_key_open_dvr_teyes)) {
                                HandleCenter.startApp(App.TEYES_FRONT_CAMERA_PKG);
                            } else if (compare(str, WakeupCommand.wakeup_key_answer)) {
                                HandleCenter.answer();
                            } else if (compare(str, WakeupCommand.wakeup_key_hang_up)) {
                                HandleCenter.hangup();
                            }
                        }
                    }
                }
            }
        }
    }

    static boolean compare(String str, String str2) {
        String[] wakeupText = WakeupCommand.getWakeupText(str2);
        if (!(wakeupText == null || wakeupText.length == 0)) {
            for (String trim : wakeupText) {
                if (str.equalsIgnoreCase(trim.trim())) {
                    return true;
                }
            }
        }
        return false;
    }
}
