package rxbus;

import android.support.v4.app.Fragment;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by zhangchong on 16/4/28.
 */
public abstract class BaseFragment extends Fragment {
    protected CompositeSubscription subscriptions;


    @Override
    public void onStart() {
        super.onStart();
        if (wantMission()) {
            subscriptions = new CompositeSubscription();
            subscriptions.add(RxBus.getInstance().toObserverable().subscribe(o -> onNewMission(o)));
        }
    }

    protected abstract boolean wantMission();

    protected abstract void onNewMission(Object object);

    @Override
    public void onStop() {
        super.onStop();
        if (wantMission()) subscriptions.unsubscribe();
    }

}

