package com.example.stu.yinfengdemo;

import android.app.AutomaticZenRule;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stu on 2018/3/15.
 */

public class EcgListAdapter extends RecyclerView.Adapter<EcgListAdapter.EcgViewHoler> {
    List<ECG12Bean> ecgList = new ArrayList<>();

    private Context context;
    LayoutInflater inflater;
    private XAxis xAxis;
    private YAxis yAxis;
    private int maxLen;

    public EcgListAdapter(List<ECG12Bean> ecgList, Context context) {
        this.ecgList = ecgList;
        this.context = context;
        // this.maxLen = maxlen;
        //inflater=context.
    }

    @Override
    public EcgViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ecg12, parent, false);
        EcgViewHoler viewHolder = (EcgViewHoler) view.getTag();
        if (viewHolder == null) {
            viewHolder = new EcgViewHoler(view);
            view.setTag(viewHolder);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EcgViewHoler holder, int position) {
        ECG12Bean ecg12Bean = ecgList.get(position);
        String code = ecg12Bean.getCode_code().substring(ecg12Bean.getCode_code().lastIndexOf("_") + 1);
        holder.ecgTitle.setText(code);
        // holder.ecgChart.setData(getLineData(ecg12Bean));
        initEcgchart(holder.ecgChart, ecg12Bean);
        //   holder.ecgChart.invalidate();

    }

    @Override
    public int getItemCount() {
        return (ecgList == null) ? 0 : ecgList.size();
    }

    class EcgViewHoler extends RecyclerView.ViewHolder {
        TextView ecgTitle;
        LineChart ecgChart;

        public EcgViewHoler(View itemView) {
            super(itemView);
            ecgTitle = itemView.findViewById(R.id.ecg_title);
            ecgChart = itemView.findViewById(R.id.ecg_chart);
            //initEcgchart();

        }


    }

    private void initEcgchart(LineChart lineChart, ECG12Bean ecg) {

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
        lineChart.setScaleXEnabled(true); //是否可以缩放 仅x轴
        lineChart.setScaleYEnabled(true); //是否可以缩放 仅y轴

        lineChart.setPinchZoom(true);  //设置x轴和y轴能否同时缩放。默认是否
        lineChart.setDoubleTapToZoomEnabled(true);//设置是否可以通过双击屏幕放大图表。默认是true

        //lineChart.setHighlightEnabled(false);  //If set to true, highlighting/selecting values via touch is possible for all underlying DataSets.
        lineChart.setHighlightPerDragEnabled(false);//能否拖拽高亮线(数据点与坐标的提示线)，默认是true

        lineChart.setAutoScaleMinMaxEnabled(false);


        // Chart fling / deceleration
        lineChart.setDragDecelerationEnabled(true);//拖拽滚动时，手放开是否会持续滚动，默认是true（false是拖到哪是哪，true拖拽之后还会有缓冲）
        lineChart.setDragDecelerationFrictionCoef(0.99f);//与上面那个属性配合，持续滚动时的速度快慢，[0,1) 0代表立即停止。


        //Highlighting programmatically

//        highlightValues(Highlight[] highs)
//               Highlights the values at the given indices in the given DataSets. Provide null or an empty array to undo all highlighting.
//        highlightValue(int xIndex, int dataSetIndex)
//               Highlights the value at the given x-index in the given DataSet. Provide -1 as the x-index or dataSetIndex to undo all highlighting.
//        getHighlighted()
//               Returns an Highlight[] array that contains information about all highlighted entries, their x-index and dataset-index.


        //其他请参考https://github.com/PhilJay/MPAndroidChart/wiki/Interaction-with-the-Chart
        //如手势相关方法，选择回调方法


//        The Axis 坐标轴相关的,XY轴通用
        xAxis = lineChart.getXAxis();
        xAxis.setEnabled(true);     //是否显示X坐标轴 及 对应的刻度竖线，默认是true
        xAxis.setDrawAxisLine(true); //是否绘制坐标轴的线，即含有坐标的那条线，默认是true
        xAxis.setDrawGridLines(true); //是否显示X坐标轴上的刻度竖线，默认是true
        xAxis.setDrawLabels(true); //是否显示X坐标轴上的刻度，默认是true
        //   xAxis.setSpaceMin(4);
        //   xAxis.setTextColor(Color.rgb(145, 13, 64)); //X轴上的刻度的颜色
        xAxis.setTextSize(0.1f); //X轴上的刻度的字的大小 单位dp
//      xAxis.setTypeface(Typeface tf); //X轴上的刻度的字体
        xAxis.setGridColor(ColorTemplate.rgb("fad5cd")); //X轴上的刻度竖线的颜色
//      X轴专用

        int count = 300;
        // count=context.get
        xAxis.setLabelCount(count);    //设置坐标相隔多少，参数是int类型
//        xAxis.resetLabelsToSkip();   //将自动计算坐标相隔多少
        //    xAxis.setAvoidFirstLastClipping(false);
        //xAxis.setSpaceBetweenLabels(4);
        xAxis.setGridLineWidth(0.9f);
        xAxis.setAxisMaximum(6000f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);//把坐标轴放在上下 参数有：TOP, BOTTOM, BOTH_SIDED, TOP_INSIDE or BOTTOM_INSIDE.

//      Y轴专用
        yAxis = lineChart.getAxisLeft();
        yAxis.setStartAtZero(false);    //设置Y轴坐标是否从0开始
//         int cout=(ecg.getMax()-ecg.getMin())/
//        yAxis.setAxisMaxValue((ecg.getMin() < -1.0) ? -1.5f
//                : (ecg.getMin() > -1.0 && ecg.getMin() < 0) ? -1.0f : 0f);    //设置Y轴坐标最大为多少
//        // yAxis.resetAxisMaximum();    //重新设置Y轴坐标最大为多少，自动调整
//        yAxis.setAxisMinValue((ecg.getMax() > 1.0) ? 1.5f
//                : (ecg.getMax() <1.0 && ecg.getMin() > 0) ? 1.0f : 0f);
        yAxis.setAxisMaxValue(4.5f);
        // yAxis.resetAxisMaximum();    //重新设置Y轴坐标最大为多少，自动调整
        yAxis.setAxisMinValue(-4.5f);    //
        //yAxis.resetAxisMinValue();    //重新设置Y轴坐标，自动调整
        // yAxis.setInverted(false);    //Y轴坐标反转,默认是false,即下小上大
//        yAxis.setSpaceTop(10);    //Y轴坐标距顶有多少距离，即留白
//        yAxis.setSpaceBottom(10);    //Y轴坐标距底有多少距离，即留白
        //yAxis.setShowOnlyMinMax(false);    //参数如果为true Y轴坐标只显示最大值和最小值
        yAxis.setLabelCount(26, false);    //第一个参数是Y轴坐标的个数，第二个参数是 是否不均匀分布，true是不均匀分布
        // yAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);  //参数是INSIDE_CHART(Y轴坐标在内部) 或 OUTSIDE_CHART(在外部（默认是这个）)
        yAxis.setGridColor(ColorTemplate.rgb("fad5cd")); //X轴上的刻度竖线的颜色
        lineChart.animateX(1000); // 立即执行的动画,x轴
        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);
        // setComp1.setCircleRadius();
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        Double[] digits = ecg.getEcgArr();
        ArrayList<Entry> valsComp1 = new ArrayList<Entry>();     //坐标点的集合
        for (int i = 0; i < digits.length; i++) {
            Entry entry;
            if (digits[i] == null || digits[i] == 0) {
                entry = new Entry(((float) i), 0);
                valsComp1.add(entry);
                break;
            }
            Log.e("test", "getLineData:40的整数倍 i=" + i);
            entry = new Entry(((float) i), digits[i].floatValue() * 0.001f);
            valsComp1.add(entry);
        }
        ArrayList<Entry> valsComp2 = new ArrayList<Entry>();     //坐标点的集合
        for (int i = 0; i < digits.length; i++) {
            Entry entry;
            if (digits[i] == null || digits[i] == 0) {
                entry = new Entry(((float) i),0);
                valsComp1.add(entry);
            } else {
                float y = digits[i].floatValue() * 0.001f ;
                entry = new Entry(((float) i), y);
                Log.e("test", "getLineData:40的整数倍 y=" + y);
                valsComp1.add(entry);
            }
        }
        LineDataSet ecgSet = setLineDataSet(valsComp1);
    //    LineDataSet ecgSet2 = setLineDataSet(valsComp2);
        dataSets.add(ecgSet);
     //   dataSets.add(ecgSet2);
        ArrayList<Integer> xVals = new ArrayList<Integer>();
        for (int i = 0; i < 6000; i++) {
            // x轴显示的数据，这里默认使用数字下标显示
            xVals.add(i);
        }
        LineData lineData = new LineData(dataSets);


        lineChart.setData(lineData);
        // get the legend (only possible after setting data)
        Legend mLegend = lineChart.getLegend(); // 设置比例图标示，就是那个一组y的value的

        // mLegend.setEnabled(false);
        mLegend.setForm(Legend.LegendForm.SQUARE);// 样式
        mLegend.setFormSize(0.5f);// 字体
        mLegend.setTextColor(Color.WHITE);// 颜色

        lineChart.getDescription().setEnabled(false);
    }

    @NonNull
    private LineDataSet setLineDataSet(ArrayList<Entry> valsComp1) {
        LineDataSet ecgSet = new LineDataSet(valsComp1, null);
        ecgSet.setDrawCircles(false);
        ecgSet.setDrawValues(false);
        ecgSet.setColor(ColorTemplate.rgb("FFF50A0A"));
        ecgSet.setFillColor(ColorTemplate.getHoloBlue());
        ecgSet.setHighLightColor(Color.rgb(244, 117, 117));
        ecgSet.setDrawCircleHole(false);
        ecgSet.setFormLineWidth(0.5f);
        ecgSet.setLineWidth(0.5f);
        ecgSet.setCircleRadius(1f);
        return ecgSet;
    }

    private LineData getLineData(ECG12Bean ecg) {
        Double[] digits = ecg.getEcgArr();
        ArrayList<Entry> valsComp1 = new ArrayList<Entry>();     //坐标点的集合
        for (int i = 0; i < digits.length; i++) {
            Entry entry;
          //  Double d = 10.5 - j * 2;
            if (digits[i] == null || digits[i] == 0) {
                entry = new Entry(((float) i),0);
                valsComp1.add(entry);
            } else {
                float y = digits[i].floatValue() * 0.001f ;
                entry = new Entry(((float) i), y);
                Log.e("test", "getLineData:40的整数倍 y=" + y);
                valsComp1.add(entry);
            }
            Log.e("test", "getLineData:40的整数倍 i=" + i);
            entry = new Entry(((float) i), digits[i].floatValue() * 0.001f);
            valsComp1.add(entry);
        }

//
//
//            // create a dataset and give it a type
//        LineDataSet set1 = new LineDataSet(yVals, "DataSet Line");
//
//       // set1.setDrawCubic(true);  //设置曲线为圆滑的线
//        set1.setCubicIntensity(0.2f);
//        set1.setDrawFilled(false);  //设置包括的范围区域填充颜色
//        set1.setDrawCircles(true);  //设置有圆点
//        set1.setLineWidth(2f);    //设置线的宽度
//        set1.setCircleSize(5f);   //设置小圆的大小
//        set1.setHighLightColor(Color.rgb(244, 117, 117));
//        set1.setColor(Color.rgb(104, 241, 175));    //设置曲线的颜色
//
//        // create a data object with the datasets
//        LineData data = new LineData(xVals, set1);
//
//        // set data
//        mChart.setData(data);

        LineDataSet setComp1 = new LineDataSet(valsComp1, ecg.getCode_code());    //坐标线，LineDataSet(坐标点的集合, 线的描述或名称);
        //setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);     //以左边坐标轴为准 还是以右边坐标轴为基准
        setComp1.setDrawCircles(false);
        setComp1.setDrawValues(false);
        setComp1.setColor(ColorTemplate.rgb("FFF50A0A"));
        setComp1.setFillColor(ColorTemplate.getHoloBlue());
        setComp1.setHighLightColor(Color.rgb(244, 117, 117));
        setComp1.setDrawCircleHole(false);
        setComp1.setFormLineWidth(0.5f);
        setComp1.setLineWidth(0.5f);
        setComp1.setCircleRadius(1f);
        // setComp1.setCircleRadius();
        setComp1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        LineData data = new LineData(setComp1);
        data.setValueTextColor(Color.BLACK);
        data.setValueTextSize(9f);

        return data;
    }
}
