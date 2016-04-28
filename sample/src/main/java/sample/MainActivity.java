package sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import rxbus.BaseActivity;
import rxbus.RxBus;

public class MainActivity extends BaseActivity {

    @Bind(R.id.tv)
    TextView tv;
    @Bind(R.id.btn)
    Button btn;
    @Bind(R.id.vp)
    ViewPager vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        vp.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        btn.setOnClickListener(v -> {  //send mission  to the current fragment
            if (RxBus.getInstance().hasObservers()) RxBus.getInstance().sendMission(vp.getCurrentItem());
        });
    }
    // true when get new mission onNewMission() will be called
    @Override
    protected boolean wantMission() {
        return true;
    }

    @Override
    protected void onNewMission(Object object) {  //get mission
        if (object instanceof String)
            tv.setText("" + object);

    }

    public class FragmentAdapter extends FragmentPagerAdapter {

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return MainFragment.instance(position);
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
