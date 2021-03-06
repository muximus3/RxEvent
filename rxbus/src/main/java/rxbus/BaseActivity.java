package rxbus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by zhangchong on 16/4/28.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected CompositeSubscription subscriptions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
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
