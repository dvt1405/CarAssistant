package ai.kitt.vnest.util;

public interface AirConditionerImpl {
    void down();

    void getStatus();

    boolean isDown();

    boolean process(String str);

    void setAuto(boolean z);

    void setDual(boolean z);

    void setTemperature(int i);

    void turnOff();

    void turnOn();

    /* renamed from: up */
    void mo12646up();
}
