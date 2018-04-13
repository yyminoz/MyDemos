package com.example.stu.yinfengdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

/**
 * Created by yf on 2018/3/26.
 */

public class EcgChart extends LineChart {
    Paint gridPant;
    Canvas canvas;
    int width;
    int height;

    public EcgChart(Context context) {
        super(context);
        initEcgchart();
//        initGrid(25000, 50);
    }

    public EcgChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        initEcgchart();
//        initGrid(25000, 50);
    }

    public EcgChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initEcgchart();
//        initGrid(25000, 50);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
    }

    public void initGrid(int xCount, int yCount) {
        if (gridPant == null) {
            Log.e("test", "initGrid: null");
            return;
        }
        //竖线
        for (int i = 40; i < xCount; i++) {
            if (i % 40 == 0) {
                canvas.drawLine(i, 0, i, 50, gridPant);
            }
        }
        for (float i = 0; i < yCount; i++) {
            if (i % 0.1 == 0) {
                canvas.drawLine(0, i, 0, 24000, gridPant);
            }
        }
    }

    private void initEcgchart() {

        //General Chart Styling 通用的图表造型，还有些对于特定图表有这特定方法的造型。
        //请参考https://github.com/PhilJay/MPAndroidChart/wiki/Specific-chart-settings
        setBackgroundColor(Color.WHITE);// 设置图表背景 参数是个Color对象
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

        setDrawGridBackground(true);//设置图表内格子背景是否显示，默认是false
        setGridBackgroundColor(Color.WHITE);//设置格子背景色,参数是Color类型对象

        setDrawBorders(true);     //设置图表内格子外的边框是否显示
        setBorderColor(Color.WHITE);   //上面的边框颜色
        setBorderWidth(1);       //上面边框的宽度，float类型，dp单位
//      lineChart.setMaxVisibleValueCount();设置图表能显示的最大值，仅当setDrawValues()属性值为true时有用


        //Interaction with the Chart 图表的交互

        //Enabling / disabling interaction

        setTouchEnabled(true); // 设置是否可以触摸
        setDragEnabled(true);// 是否可以拖拽

        setScaleEnabled(true);// 是否可以缩放 x和y轴, 默认是true
        setScaleXEnabled(false); //是否可以缩放 仅x轴
//        lineChart.setScaleYEnabled(true); //是否可以缩放 仅y轴
        // lineChart.setLabelFor();
        setPinchZoom(true);  //设置x轴和y轴能否同时缩放。默认是否
        setDoubleTapToZoomEnabled(true);//设置是否可以通过双击屏幕放大图表。默认是true

        //lineChart.setHighlightEnabled(false);  //If set to true, highlighting/selecting values via touch is possible for all underlying DataSets.
        setHighlightPerDragEnabled(false);//能否拖拽高亮线(数据点与坐标的提示线)，默认是true

        setAutoScaleMinMaxEnabled(false);


        // Chart fling / deceleration
        setDragDecelerationEnabled(true);//拖拽滚动时，手放开是否会持续滚动，默认是true（false是拖到哪是哪，true拖拽之后还会有缓冲）
        setDragDecelerationFrictionCoef(0.99f);//与上面那个属性配合，持续滚动时的速度快慢，[0,1) 0代表立即停止。
        XAxis xAxis = getXAxis();
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
        YAxis yAxis = getAxisLeft();
        yAxis.setStartAtZero(false);    //设置Y轴坐标是否从0开始
        yAxis.setAxisMaxValue(25.5f);
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
        animateX(1000); // 立即执行的动画,x轴
        YAxis rightAxis = getAxisRight();
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
        Legend mLegend = getLegend(); // 设置比例图标示，就是那个一组y的value的
        gridPant = new Paint();
        width = (int) xAxis.getAxisLineWidth();
        height = (int) yAxis.getAxisLineWidth();
        // mLegend.setEnabled(false);
        mLegend.setForm(Legend.LegendForm.SQUARE);// 样式
        mLegend.setFormSize(0.5f);// 字体
        mLegend.setTextColor(Color.WHITE);// 颜色

        getDescription().setEnabled(false);
        LineData data = new LineData();
        setData(data);
    }
}
