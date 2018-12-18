package com.example.cc.simplecharttest;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

//import static com.example.cc.simplecharttest.R.id.fab;
import static com.example.cc.simplecharttest.R.id.lineChart;

public class MainActivity extends AppCompatActivity {
   private List<String> mlist=new ArrayList<>();
   private List<Entry> entries = new ArrayList<>();
   private LineChart mLineChart;
   private LineDataSet lineDataSet;
   private LineData data;
    private int ClickTime;
    private DragFloatActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ClickTime=3;
        //动态加载的问题参考P113
        //FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.fab );
        fab=(DragFloatActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (ClickTime){
                    case 1:
                        ClickTime=ClickTime+1;
                        Toast.makeText(MainActivity.this,"你操死了潘雯雯1号",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        ClickTime=ClickTime-1;
                        Toast.makeText(MainActivity.this,"你操死了潘雯雯2号",Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        ClickTime=ClickTime-1;
                        Toast.makeText(MainActivity.this,"你操死了潘雯雯3号",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }


            }
        });

        //隐藏工具栏
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null)
            actionBar.hide();
        //使状态栏透明
        if (Build.VERSION.SDK_INT>=21){
            View decorView=getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        mlist.add("一月");
        mlist.add("二月");
        mlist.add("三月");
        mlist.add("四月");
        mlist.add("五月");
        mlist.add("六月");
        mlist.add("七月");
        mlist.add("八月");
        mlist.add("九月");
        mlist.add("十月");
        mlist.add("十一月");
        mlist.add("十二月");
        mlist.add("十三月");
        //实例化控件
        mLineChart=(LineChart)findViewById(lineChart);
        //显示边界
        mLineChart.setDrawBorders(true);
        //如果为True则背景不能透明，一般为False
        mLineChart.setDrawGridBackground(false);
        //获取X轴对象
        XAxis xAxis=mLineChart.getXAxis();
        //下标的位置：BOTTOM,BOTH_SIDED,BOTTOM_INSIDE,TOP,TOP_INSIDE
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        //获取Y轴对象
        YAxis leftYAxis=mLineChart.getAxisLeft();
        YAxis rightYAxis=mLineChart.getAxisRight();

        //得到legend图例，也就是标签的意思
        Legend legend=mLineChart.getLegend();
        legend.setTextColor(Color.CYAN);
        //设置legend位置
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //设置标签是否换行
        legend.setWordWrapEnabled(true);
        //隐藏标签
        //legend.setEnabled(false);

        //隐藏描述
        Description description=new Description();
        description.setEnabled(false);
        mLineChart.setDescription(description);
        //设置描述内容
        Description description1=new Description();
        description1.setText("X轴Description");
        description1.setTextColor(Color.RED);
        mLineChart.setDescription(description1);


        //设置Y轴值
        leftYAxis.setAxisMinimum(0f);
        leftYAxis.setAxisMaximum(100f);
        rightYAxis.setAxisMinimum(0f);
        rightYAxis.setAxisMaximum(100f);
        leftYAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return (int)value+"%";
            }
        });
        //Y轴右侧不显示
        rightYAxis.setEnabled(false);

        leftYAxis.setGranularity(1f);
        leftYAxis.setLabelCount(11,false);
        leftYAxis.setTextColor(Color.BLUE);//文字颜色
        leftYAxis.setGridColor(Color.RED);//网格线颜色
        leftYAxis.setAxisLineColor(Color.GREEN);//Y轴颜色

        //限制线LimitLine
        LimitLine limitLine1=new LimitLine(70,"上高限制性");//限制线
        limitLine1.setLineWidth(4f);//宽度
        limitLine1.setTextColor(Color.RED);
        limitLine1.setTextSize(4f);
        limitLine1.setLineColor(Color.BLUE);
        leftYAxis.addLimitLine(limitLine1);//右边Y轴添加限制线
        LimitLine limitLine2=new LimitLine(50,"下高限制性");//限制线
        limitLine2.setLineWidth(4f);//宽度
        limitLine2.setTextColor(Color.RED);
        limitLine2.setTextSize(4f);
        limitLine2.setLineColor(Color.BLUE);
        leftYAxis.addLimitLine(limitLine2);//右边Y轴添加限制线



        /*//设置X轴的值（最小值、最大值、然后会根据设置的刻度数量自动分配刻度显示）
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(13f);*/

        //设置X轴下标最小间隔
        xAxis.setGranularity(1f);
        //设置X轴的刻度数量,true是按比例分配，false是适配X刻度值分配
        xAxis.setLabelCount(13,true);

        //设置X轴值为字符串,一次一月到12月
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mlist.get((int)value);
            }
        });

    //定义数组，设置数据
        //List<Entry> entries = new ArrayList<>();
    for (int i = 0; i < 13; i++)
        //产生10个点并赋值，相当于第一个是横轴坐标值，第二个是竖轴坐标值
        entries.add(new Entry(i, (float) (Math.random()) * 80));
    //曲线数据集
    lineDataSet = new LineDataSet(entries, "温度");

    //取消曲线显示的值为整数与设置自定义X轴类似。设置曲线显示值为整数，可在设置曲线LineDataSet 时,修改值的类型
        /*lineDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                int IValue=(int)value;
                return String.valueOf(IValue);
            }
        });*/

    //LineChart的数据源
    data = new LineData(lineDataSet);
    //chart装入数据
    mLineChart.setData(data);
        //视图更新，没有这一步，曲线不会呈现动态
        //mLineChart.invalidate();
        //点击弹框
    MyMarkerView mv = new MyMarkerView(MainActivity.this);
    mLineChart.setMarker(mv);//有失效方法
    mLineChart.setTouchEnabled(false);

        //启动服务
        /*Intent intent=new Intent(this,MyService.class);
        startService(intent);*/

        /*Entry entry = new Entry(lineDataSet.getEntryCount(), number);
        lineData.addEntry(entry, 0);
        //通知数据已经改变
        lineData.notifyDataChanged();
        mLineChart.notifyDataSetChanged();
        //设置在曲线图中显示的最大数量
        mLineChart.setVisibleXRangeMaximum(10);
        //移到某个位置
        mLineChart.moveViewToX(lineData.getEntryCount() - 5);*/
        new MyThread().start();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        /*重置floatActionBar位置否则容易找不到
        fab.setX(100);
        fab.setY(100);*/
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
            Toast.makeText(this,"当前屏幕为竖屏会影响效果，建议横屏观看",Toast.LENGTH_SHORT).show();
        super.onConfigurationChanged(newConfig);
    }

    class MyThread extends Thread{
        @Override
        public void run() {
            while (true){try {
                Thread.sleep(2000);

            }catch (InterruptedException e){
                e.printStackTrace();
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //逻辑
                    //定义数组，设置数据
                    entries.clear();
                    mLineChart.clear();
                    for (int i = 0; i < 13; i++)
                        //产生10个点并赋值，相当于第一个是横轴坐标值，第二个是竖轴坐标值
                        entries.add(new Entry(i, (float) (Math.random()) * 80));
                    //曲线数据集
                    lineDataSet = new LineDataSet(entries, "温度");
                    data = new LineData(lineDataSet);
                    //通知数据已经改变
                    mLineChart.setData(data);


                }
            });
        }
        }
    }
}
//参考网站https://blog.csdn.net/ww897532167/article/details/74139843
