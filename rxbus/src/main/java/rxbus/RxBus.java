package rxbus;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;


public class RxBus {
    private static class LazyHolder {
        private static final RxBus INSTANCE = new RxBus();
    }

    public static RxBus getInstance() {
        return LazyHolder.INSTANCE;
    }

    private RxBus() {
    }
    private final Subject<Object, Object> _bus = new SerializedSubject<>(PublishSubject.create());//thread safe

    public void sendMission(Object o) {
        _bus.onNext(o);
    }

    public Observable<Object> toObserverable() {
        return _bus;
    }

    public boolean hasObservers() {
        return _bus.hasObservers();
    }


}
