package ai.kitt.vnest.util.event;

import timber.log.Timber;

public abstract class BaseEvent {
    public BaseEvent() {
        Timber.d(toString(), this);
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}
