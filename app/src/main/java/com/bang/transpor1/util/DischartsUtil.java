package com.bang.transpor1.util;

import android.graphics.Color;
import android.util.Log;

import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by 12457 on 2018/10/26.
 */

public class DischartsUtil {
    private XYSeries xySeries;
    private XYSeries xySeries1;
    private int count;
    private List<Double> list1 = new ArrayList<>();
    private double[] x, y = new double[10];
    ;

    public XYMultipleSeriesDataset setDataSet(List<Double> list, String title) {
        list1 = list;
        //创建数据源
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        //定义线，
        xySeries = new XYSeries(title);
        xySeries.clear();
        for (int i = 0; i < list.size(); i++) {
            xySeries.add(i+1, list.get(i));
        }
        dataset.addSeries(xySeries);
        return dataset;
    }

    //定义渲染器
    public XYMultipleSeriesRenderer setRenderer(String xTitle, String yTitle, String charTitle) {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        renderer.setXTitle(xTitle);  //"时间"
        renderer.setYTitle(yTitle);  //"温度"
        renderer.setAxisTitleTextSize(30);//设置轴标题文本大小
        renderer.setChartTitle(charTitle);//设置图表标题
        renderer.setChartTitleTextSize(30);//设置图表标题文字的大小
        renderer.setLabelsTextSize(18);//设置标签的文字大小
        renderer.setLegendTextSize(30);//设置图例文本大小
        renderer.setPointSize(3f);//设置点的大小\

        renderer.setYAxisMin(0);//设置y轴最小值是0
        renderer.setYAxisMax(15);//设置Y轴的最大值为15，   0--15

        renderer.setYLabels(10);//设置Y轴刻度个数（貌似不太准确）
        renderer.setXAxisMax(20);  //设置X轴的屏幕显示长度，
        renderer.setShowGrid(true);//显示网格
        renderer.setApplyBackgroundColor(true);
        renderer.setBackgroundColor(Color.BLACK);

        renderer.setClickEnabled(true); //设置是否可以滑动及放大缩小;
        renderer.setFitLegend(true);// 调整合适的位置
        renderer.setRange(new double[]{-1, 12, 0, 12}); //设置chart的视图范围  (x轴最小范围，x轴最大范围，y轴最小范围，y轴最大范围)
        renderer.setZoomButtonsVisible(false);//设置可以缩放
        renderer.setShowGridX(true);
        renderer.setShowGridY(false);
//        renderer.setFitLegend(true);// 调整合适的位置
        //renderer.setGridColor();//设置网格颜色
        //renderer.setAxesColor();//设置坐标轴颜色
        //线的属性
        XYSeriesRenderer r = new XYSeriesRenderer();//(类似于一条线对象)
        r.setColor(Color.BLUE);//设置颜色
        r.setPointStyle(PointStyle.CIRCLE);//设置点的样式
        r.setFillPoints(true);//填充点（显示的点是空心还是实心）
        r.setDisplayChartValues(true);//将点的值显示出来
        r.setChartValuesSpacing(10);//显示的点的值与图的距离
        r.setChartValuesTextSize(25);//点的值的文字大小

        renderer.addSeriesRenderer(r);
        return renderer;
    }

    //动态赋值，
    public void initLine(XYSeries series) {

        Random r = new Random();
        int xTemp = 0;
        int yTemp = r.nextInt(10);

        int count = series.getItemCount();//返回本系列的条目的数量,只有2条，一开始
        Log.e("ssssss", count + "");
        if (count > 10) {
            count = 10;
        }
        //这里是给第几个点赋值坐标，并不是多个赋值，仅仅是一个，如第九个，x[9]=series.getX(9);
        for (int i = 0; i < count; i++) {
            x[i] = series.getX(i);//这个地方是getX（i）来获取的是再次调用series对象时，取出的数据，存放在x[],y[]数组里
            //为下面赋值做铺垫。
            y[i] = series.getY(i);
            Log.e("x[i]:", "" + x[i]);
            Log.e("Y[i]:", "" + x[i]);
        }
        series.clear();
        series.add(xTemp, yTemp);

        for (int i = 0; i < count; i++) { //取出上一个的值，存放在下一个坐标上
            series.add(x[i] + 1, y[i]);
        }
    }

    public XYSeries getxySeries() {
        return xySeries;
    }

}
