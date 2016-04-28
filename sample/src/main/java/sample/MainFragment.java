package sample;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import rxbus.BaseFragment;
import rxbus.RxBus;

/**
 * Created by zhangchong on 16/4/28.
 */
public class MainFragment extends BaseFragment {
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_pic)
    ImageView ivPic;
    private String[] picUrl = {"http://img4.imgtn.bdimg.com/it/u=2172041622,3819068822&fm=21&gp=0.jpg"
            , "http://pic15.nipic.com/20110713/4725838_111825711000_2.jpg"
            , "http://bbs.ncnews.com.cn/data/attachment/forum/201108/31/162401tiorhwtmpgm3w1ru.jpg"
            , "http://g.hiphotos.baidu.com/zhidao/pic/item/18d8bc3eb13533fa24dfa175aad3fd1f41345b7b.jpg"
    };
    private int mPosition;  //tag the position

    public static MainFragment instance(int mPosition) {
        MainFragment fragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("mPosition", mPosition);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mPosition = bundle.getInt("mPosition");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        String url = picUrl[mPosition];
        Glide.with(this).load(url).centerCrop().thumbnail(0.2f).into(ivPic);
        tvTitle.setText("FragmentPage:" + mPosition);
        ivPic.setOnClickListener(v -> startAni(ivPic, () -> {
            if (RxBus.getInstance().hasObservers())
                RxBus.getInstance().sendMission("aniStart......");
        }, () -> {
            if (RxBus.getInstance().hasObservers())
                RxBus.getInstance().sendMission("......aniEnd");
        }));
    }

    @Override
    protected boolean wantMission() {
        return true;
    }

    @Override
    protected void onNewMission(Object object) {
        if (object instanceof Integer && (Integer) object == mPosition) {
            startAni(ivPic, () -> {
                if (RxBus.getInstance().hasObservers())
                    RxBus.getInstance().sendMission("aniStart......");
            }, () -> {
                if (RxBus.getInstance().hasObservers())
                    RxBus.getInstance().sendMission("......aniEnd");
            });
        }

    }

    private void startAni(ImageView view, @Nullable final Runnable aniStart, @Nullable final Runnable aniEnd) {
        final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator bounceAnimX = ObjectAnimator.ofFloat(view, "scaleX", 0.5f, 1f);
        bounceAnimX.setDuration(300);
        bounceAnimX.setInterpolator(OVERSHOOT_INTERPOLATOR);

        ObjectAnimator bounceAnimY = ObjectAnimator.ofFloat(view, "scaleY", 0.5f, 1f);
        bounceAnimY.setDuration(300);
        bounceAnimY.setInterpolator(OVERSHOOT_INTERPOLATOR);
        bounceAnimY.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (aniStart != null) aniStart.run();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (aniEnd != null) aniEnd.run();
            }
        });

        animatorSet.play(bounceAnimX).with(bounceAnimY);
        animatorSet.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
