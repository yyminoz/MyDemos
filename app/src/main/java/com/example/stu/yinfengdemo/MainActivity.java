package com.example.stu.yinfengdemo;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    List<String> dataList = new ArrayList<>();
    List<ECG12Bean> ecgList = new ArrayList<>();
    StringBuffer string = new StringBuffer();
    EcgListAdapter adapter;
    int count;

    //    @InjectView(R.id.ecg_recycler)
//    RecyclerView ecgRecycler;
    @InjectView(R.id.ecg_chart12)
    LineChart ecgChart12;
    @InjectView(R.id.mecg_chart12)
    EcgChart mecgChart12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        //     dayinBtn.setOnClickListener(this);
//        adapter = new EcgListAdapter(ecgList, this);
////        ecgRecycler.setAdapter(adapter);
////        ecgRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        // ecg = new StringBuffer();
//        // ecg.append(EcgData.getEcgData())
        initEcgchart(ecgChart12);
//        initEcgBackGrid(ecgChart12.getPaint(Chart.PAINT_GRID_BACKGROUND));
//        ecgChart12.setAutoScaleMinMaxEnabled(true);
        getEcgData();
    }

    private void initEcgBackGrid(Paint paint) {
        //   Canvas canvas=ecgChart12.
        if (paint == null) {
            return;
        }
        for (int i = 0; i < 220000; i++) {
            if (i % 40 == 0) {

            }
        }
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
        Observer<List<ECG12Bean>> observer = new Observer<List<ECG12Bean>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<ECG12Bean> aDouble) {
                //   dataList.add(aDouble);
                count++;
                Log.e("onNext", count + "");
                //   ecgText.setText(string);
                ecgList.clear();
                ecgList.addAll(aDouble);

                updataWave(ecgList);
//                adapter.notifyDataSetChanged();
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
        Observable<List<ECG12Bean>> observable = Observable.create(new ObservableOnSubscribe<List<ECG12Bean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ECG12Bean>> e) throws Exception {

                XmlUtils xmlUtils = new XmlUtils();
                //  InputStream is = xmlUtils.getXml();
                String xml = xmlUtils.getXmls();
                Map<String, Class> attrs = new HashMap<>();
                attrs.put("code_code", String.class);
                if (!TextUtils.isEmpty(xml)) {
                    List<ECG12Bean> list = xmlUtils.parseXml2Bean(xml, "sequence", ECG12Bean.class, attrs);
                    List<ECG12Bean> ecgList = new ArrayList<>();
                    double max = 0;
                    double min = 0;
                    for (ECG12Bean ecg : list) {
                        if (ecg.getDigits() != null) {
                            String str = ecg.getDigits();
                            String[] ecgStr = str.split(" ");
                            Double[] ecgArr = new Double[ecgStr.length];
                            for (int i = 0; i < ecgStr.length; i++) {
                                if (TextUtils.isEmpty(ecgStr[i])) {
                                    break;
                                }
                                Double d = null;
                                d = Double.parseDouble(ecgStr[i]);
                                ecgArr[i] = d;
                                max = (d < max) ? max : d;
                                min = (d < min) ? d : min;
                            }

                            ecg.setEcgArr(ecgArr);
                            ecg.setMax(max);
                            ecg.setMin(min);
//                            ecg.set
                            ecgList.add(ecg);
                        }
                    }
                    e.onNext(ecgList);
                }
            }
        });
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    private void updataWave(List<ECG12Bean> ecgList) {

        //ecgChart12
        feedMultiple();
//        ecgChart12.setData(initEcgchartData(ecgList));
//        ecgChart12.moveViewToX(ecgChart12.getLineData().getEntryCount());
        //ecgChart12.setVisibleXRangeMaximum(120);
    }

    private LineData initEcgchartData(List<ECG12Bean> ecgList) {
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        for (int j = 0; j < ecgList.size(); j++) {
            ArrayList<Entry> valsComp1 = new ArrayList<Entry>();     //坐标点的集合
            Double[] digits = ecgList.get(j).getEcgArr();
            for (int i = 0; i < digits.length; i++) {
                Entry entry;
                Double d = 10.5 - j * 2;
                Log.e("test", "getLineData:40的整数倍 d=" + d);
                if (digits[i] == null || digits[i] == 0) {
                    entry = new Entry(((float) i), d.floatValue());
                    valsComp1.add(entry);
                } else {
                    float y = digits[i].floatValue() * 0.001f + d.floatValue();
                    entry = new Entry(((float) i), y);
                    Log.e("test", "getLineData:40的整数倍 y=" + y);
                    valsComp1.add(entry);
                }

            }
            String code = ecgList.get(j).getCode_code().substring(ecgList.get(j).getCode_code().lastIndexOf("_") + 1);
            LineDataSet ecgSet = setLineDataSet(valsComp1, code);
            dataSets.add(ecgSet);
        }
        LineData lineData = new LineData(dataSets);


        return lineData;
    }

    private XAxis xAxis;
    private YAxis yAxis;

    private void initEcgchart(LineChart lineChart) {

        //General Chart Styling 通用的图表造型，还有些对于特定图表有这特定方法的造型。
        //请参考https://github.com/PhilJay/MPAndroidChart/wiki/Specific-chart-settings
        lineChart.setBackgroundColor(Color.WHITE);// 设置图表背景 参数是个Color对象
//        Description description = new Description();
//        String de = ecg.getCode_code().substring(ecg.getCode_code().lastIndexOf("_") + 1);
//        description.setText(de);
//        description.setPosition(800f, 800f);
//        lineChart.setDescription(description); //图表默认右下方的描述，参数是String对象
        //lineChart.setDescriptionColor(Color.rgb(227, 135, 0));  //上面字的颜色，参数是Color对象
        //     lineChart.setDescriptionPosition(400f,600f);    //上面字的位置，参数是float类型，像素，从图表左上角开始计算
//      lineChart.setDescriptionTypeface();     //上面字的字体，参数是Typeface 对象
        // lineChart.setDescriptionTextSize(16);    //上面字的大小，float类型[6,16]

        //  lineChart.setNoDataTextDescription("没有数据呢(⊙o⊙)");   //没有数据时显示在中央的字符串，参数是String对象

        lineChart.setDrawGridBackground(true);//设置图表内格子背景是否显示，默认是false
        lineChart.setGridBackgroundColor(Color.WHITE);//设置格子背景色,参数是Color类型对象

        lineChart.setDrawBorders(true);     //设置图表内格子外的边框是否显示
        lineChart.setBorderColor(Color.WHITE);   //上面的边框颜色
        lineChart.setBorderWidth(1);       //上面边框的宽度，float类型，dp单位
//      lineChart.setMaxVisibleValueCount();设置图表能显示的最大值，仅当setDrawValues()属性值为true时有用


        //Interaction with the Chart 图表的交互

        //Enabling / disabling interaction

        lineChart.setTouchEnabled(true); // 设置是否可以触摸
        lineChart.setDragEnabled(true);// 是否可以拖拽

        lineChart.setScaleEnabled(true);// 是否可以缩放 x和y轴, 默认是true
        lineChart.setScaleXEnabled(false); //是否可以缩放 仅x轴
//        lineChart.setScaleYEnabled(true); //是否可以缩放 仅y轴
        // lineChart.setLabelFor();
        lineChart.setPinchZoom(true);  //设置x轴和y轴能否同时缩放。默认是否
        lineChart.setDoubleTapToZoomEnabled(true);//设置是否可以通过双击屏幕放大图表。默认是true

        //lineChart.setHighlightEnabled(false);  //If set to true, highlighting/selecting values via touch is possible for all underlying DataSets.
        lineChart.setHighlightPerDragEnabled(false);//能否拖拽高亮线(数据点与坐标的提示线)，默认是true

        lineChart.setAutoScaleMinMaxEnabled(false);


        // Chart fling / deceleration
        lineChart.setDragDecelerationEnabled(true);//拖拽滚动时，手放开是否会持续滚动，默认是true（false是拖到哪是哪，true拖拽之后还会有缓冲）
        lineChart.setDragDecelerationFrictionCoef(0.99f);//与上面那个属性配合，持续滚动时的速度快慢，[0,1) 0代表立即停止。
        xAxis = lineChart.getXAxis();
        xAxis.setEnabled(true);     //是否显示X坐标轴 及 对应的刻度竖线，默认是true
        xAxis.setDrawAxisLine(true); //是否绘制坐标轴的线，即含有坐标的那条线，默认是true
        xAxis.setDrawGridLines(true); //是否显示X坐标轴上的刻度竖线，默认是true
        xAxis.setDrawLabels(true); //是否显示X坐标轴上的刻度，默认是true
        //xAxis.setSpaceMin(200);
        //   xAxis.setTextColor(Color.rgb(145, 13, 64)); //X轴上的刻度的颜色
        xAxis.setTextSize(0.1f); //X轴上的刻度的字的大小 单位dp
//      xAxis.setTypeface(Typeface tf); //X轴上的刻度的字体
        xAxis.setGridColor(ColorTemplate.rgb("fad5cd")); //X轴上的刻度竖线的颜色
//        xAxis.setGridDashedLine();
//      X轴专用

        int count = 300;
        // count=context.get
        xAxis.setLabelCount(count);    //设置坐标相隔多少，参数是int类型
//        xAxis.resetLabelsToSkip();   //将自动计算坐标相隔多少
        //    xAxis.setAvoidFirstLastClipping(false);
        //xAxis.setSpaceBetweenLabels(4);
        xAxis.setGridLineWidth(0.9f);
        //  xAxis.setAxisMaximum(6000f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//把坐标轴放在上下 参数有：TOP, BOTTOM, BOTH_SIDED, TOP_INSIDE or BOTTOM_INSIDE.
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String x;
                Double f1 = value * 0.001;
                if (value % 200 == 0) {

                    x = f1.floatValue() + "s";
                } else {
                    x = "";
                }
                return x;
            }
        });
//      Y轴专用
        yAxis = lineChart.getAxisLeft();
        yAxis.setStartAtZero(false);    //设置Y轴坐标是否从0开始
        yAxis.setAxisMaxValue(14f);
        // yAxis.resetAxisMaximum();    //重新设置Y轴坐标最大为多少，自动调整
        yAxis.setAxisMinValue(0.5f);
        //yAxis.mAxisMinimum=-13.5f;
        yAxis.setSpaceMin(0.5f);
        yAxis.setGranularityEnabled(true);
        //设置Y轴的值之间的最小间隔。这可以用来避免价值复制当放大到一个地步，小数设置轴不再数允许区分两轴线之间的值。
        yAxis.setGranularity(0.5f);
        //yAxis.resetAxisMinValue();    //重新设置Y轴坐标，自动调整
        // yAxis.setInverted(false);    //Y轴坐标反转,默认是false,即下小上大
//        yAxis.setSpaceTop(10);    //Y轴坐标距顶有多少距离，即留白
//        yAxis.setSpaceBottom(10);    //Y轴坐标距底有多少距离，即留白
        //yAxis.setShowOnlyMinMax(false);    //参数如果为true Y轴坐标只显示最大值和最小值
        yAxis.setLabelCount(54, false);    //第一个参数是Y轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布
        // yAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);  //参数是INSIDE_CHART(Y轴坐标在内部) 或 OUTSIDE_CHART(在外部（默认是这个）)
        yAxis.setGridColor(ColorTemplate.rgb("fad5cd")); //X轴上的刻度竖线的颜色
        lineChart.animateX(1000); // 立即执行的动画,x轴
        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);
        // setComp1.setCircleRadius();
        yAxis.setDrawZeroLine(true);
        yAxis.setDrawLabels(true);
//        yAxis.setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                return null;
//            }
//        });
        // get the legend (only possible after setting data)
        Legend mLegend = lineChart.getLegend(); // 设置比例图标示，就是那个一组y的value的

        // mLegend.setEnabled(false);
        mLegend.setForm(Legend.LegendForm.SQUARE);// 样式
        mLegend.setFormSize(0.5f);// 字体
        mLegend.setTextColor(Color.WHITE);// 颜色

        lineChart.getDescription().setEnabled(false);
        LineData data = new LineData();
        lineChart.setData(data);
    }

    private void feedMultiple() {
        Thread thread;

        final Runnable runnable = new Runnable() {

            @Override
            public void run() {
                addEntry();
            }
        };

        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                // for (int i = 0; i < 1000; i++) {

                // Don't generate garbage runnables inside the loop.
                runOnUiThread(runnable);

                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                //  }
            }
        });

        thread.start();
    }

    private void addEntry() {
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();

        LineData lineData = new LineData(dataSets);


        LineData data = ecgChart12.getData();
//        LineData data=mecgChart12.getData();
//        data = addXais(data);

        if (data != null) {

            // set.addEntry(...); // can be called as well
            for (int j = 0; j < ecgList.size(); j++) {
                ILineDataSet set = data.getDataSetByIndex(j);
                if (set == null) {
                    set = createSet();
                    data.addDataSet(set);
                }

                ArrayList<Entry> valsComp1 = new ArrayList<Entry>();     //坐标点的集合
                Double[] digits = ecgList.get(j).getEcgArr();
                for (int i = 0; i < digits.length; i++) {
                    Entry entry;
                    Double d = 13.0 - j * 1;
//                    if (i % 40 == 0) {
//                        Entry
//                    }
//                    Log.e("test", "getLineData:40的整数倍 d=" + d);
                    if (digits[i] == null || digits[i] == 0) {
                        entry = new Entry(((float) i), d.floatValue());
                        //   valsComp1.add(entry);
                        data.addEntry(entry, j);
                    } else {
                        float y = digits[i].floatValue() * 0.001f + d.floatValue();
                        entry = new Entry(((float) i), y);
//                        Log.e("test", "getLineData:40的整数倍 y=" + y);
                        valsComp1.add(entry);
                        data.addEntry(entry, j);
                    }
//                    if(i%200==0){
//                        entry = new Entry(((float) i), d.floatValue());
//                    }
                }
//                String code = ecgList.get(j).getCode_code().substring(ecgList.get(j).getCode_code().lastIndexOf("_") + 1);
//                LineDataSet ecgSet = setLineDataSet(valsComp1, code);
//                dataSets.add(ecgSet);
            }

//            ecgChart12.initGrid(data.getEntryCount(),50);
//            for (int j = 0; j < 600; j++) {
//                if (j % 40 == 0) {
//                    ILineDataSet set = data.getDataSetByIndex(j);
//                    if (set == null) {
//                        set = createSet();
//                        data.addDataSet(set);
//                    }
//                    for (int i = 0; i < 13; i++) {
//                        Entry entry = new Entry(j, i);
//                        data.addEntry(entry, j);
//                    }
//                }
//            }
            data.notifyDataChanged();


//            // let the chart know it's data has changed
//            ecgChart12.notifyDataSetChanged();
//
//            // limit the number of visible entries
//            ecgChart12.setVisibleXRangeMaximum(6000);
//
//            ecgChart12.moveViewToX(data.getEntryCount());
//             let the chart know it's data has changed
            ecgChart12.notifyDataSetChanged();

            // limit the number of visible entries
            ecgChart12.setVisibleXRangeMaximum(6000);

            ecgChart12.moveViewToX(data.getEntryCount());

        }
    }

    private LineData addXais(LineData data) {


        return data;
    }

    private LineDataSet createSet() {
        LineDataSet set = new LineDataSet(null, "");
        set.setDrawCircles(false);
        set.setColor(ColorTemplate.rgb("FFF50A0A"));
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setDrawCircleHole(false);
        set.setFormLineWidth(0.5f);
        set.setLineWidth(0.5f);
        //  set.setCircleRadius(1f);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        //  set.setColor(ColorTemplate.getHoloBlue());
        set.setCircleColor(Color.WHITE);

        //   set.setLineWidth(2f);
        //  set.setFillAlpha(65);
        //   set.setFillColor(ColorTemplate.getHoloBlue());
        set.setDrawValues(false);
        return set;
    }

    @NonNull
    private LineDataSet setLineDataSet(ArrayList<Entry> valsComp1, String label) {
        LineDataSet ecgSet = new LineDataSet(valsComp1, label);
        ecgSet.setDrawCircles(false);
        ecgSet.setDrawValues(false);
        ecgSet.setColor(ColorTemplate.rgb("FFF50A0A"));
        ecgSet.setFillColor(ColorTemplate.getHoloBlue());
        ecgSet.setHighLightColor(Color.rgb(244, 117, 117));
        ecgSet.setDrawCircleHole(false);
        ecgSet.setFormLineWidth(0.5f);
        ecgSet.setLineWidth(0.5f);
        ecgSet.setCircleRadius(1f);
        ecgSet.setLabel(label);

        return ecgSet;
    }

}
