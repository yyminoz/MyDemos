package com.example.stu.yinfengdemo;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.contec.aecgdemo.AECGParse;
import com.contec.aecgdemo.ReviewWave;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {


    @InjectView(R.id.reviewWave)
    ReviewWave reviewWave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.inject(this);
        getEcgData();

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            default:
                break;
        }
    }

    private void getEcgData() {
        Observer<short[]> observer = new Observer<short[]>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(short[] aDouble) {
                //   dataList.add(aDouble);

                showEcgWave(aDouble);
            }

            @Override
            public void onError(Throwable e) {
                //  ecgText.setText(e.getMessage());
            }

            @Override
            public void onComplete() {
                //  ecgText.setText(string.toString());
                //   ecgRecycler.getAdapter().notifyDataSetChanged();
            }
        };
        Observable<short[]> observable = Observable.create(new ObservableOnSubscribe<short[]>() {
            @Override
            public void subscribe(ObservableEmitter<short[]> e) throws Exception {

                XmlUtils xmlUtils = new XmlUtils();
                //  InputStream is = xmlUtils.getXml();
              //  String xml = xmlUtils.getXmls();
                InputStream is = xmlUtils.getInputStream();
                // FileOutputStream outputStream =new FileOutputStream();
                // File file=new File()
                short[] data = xmlUtils.parseAECGRecord(is);

                e.onNext(data);

            }
        });
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    private void showEcgWave(short[] data) {

        //为波形回顾View控件设置数据
        reviewWave.setWaveDatas(data);
        //设置波形样式12x1 6x2等
        reviewWave.setLeadDisplayStyle(ReviewWave.LEAD_DISPLAY_12X1);
        //设置走速
        reviewWave.setSpeed(25);
        //设置增益
        float[] gain = {10, 10};
        reviewWave.setGain(gain);
        //设置字体大小
        reviewWave.setLabelTextSize(30);
        //开始绘图
        reviewWave.startThread();
    }


}
