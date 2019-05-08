package com.bang.transpor1.util;

import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.Paint;
import android.util.Log;

import com.bang.transpor1.R;

import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Bang on 2018/10/26.
 */
/*
* 最高温度，最低温度折线图
* */

public class TempchartsUtil {
    private XYSeries xySeriesMax;
    private XYSeries xySeriesMin;
    private int count;
    private List<Integer> maxTemplist = new ArrayList<>();
    private List<Integer> minTemplist = new ArrayList<>();
    private List<String> weekList = new ArrayList<>();
    private double[] x, y = new double[10];


    public XYMultipleSeriesDataset setDataSet(List<Integer> maxTemplist, List<Integer> minTemplist, List<String> weekList) {
        this.maxTemplist = maxTemplist;
        this.minTemplist = minTemplist;
        this.weekList = weekList;
        //创建数据源
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        //定义线，
        xySeriesMax = new XYSeries("最高温度");
        xySeriesMin = new XYSeries("最低温度");
        xySeriesMax.clear();
        xySeriesMin.clear();
        for (int i = 0; i < 7; i++) {
            xySeriesMax.add(i+1, maxTemplist.get(i));
            xySeriesMin.add(i+1, minTemplist.get(i));
        }
        dataset.addSeries(xySeriesMax);
        dataset.addSeries(xySeriesMin);
        return dataset;
    }

    //定义渲染器
    public XYMultipleSeriesRenderer getRenderer() {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();

/*        renderer.setApplyBackgroundColor(true);// 设置是否显示背景色
        renderer.setBackgroundColor(R.color.grey_50);// 设置背景色
        renderer.setMarginsColor(Color.parseColor("#FAFAFA"));
        renderer.setAxesColor(Color.parseColor("#FAFAFA"));
        renderer.setXLabelsColor(Color.BLACK);
        renderer.setYLabelsColor(0, Color.BLACK);*/

//        renderer.setXLabelsPadding();
        renderer.setLabelsTextSize(20);
        renderer.setPointSize(3f);//设置点的大小\

        renderer.setClickEnabled(true); //设置是否可以滑动及放大缩小;
        renderer.setFitLegend(true);// 调整合适的位置
        renderer.setRange(new double[]{0, 8, 0, 40}); //设置chart的视图范

        renderer.setMarginsColor(Color.parseColor("#FAFAFA"));
        renderer.setAxesColor(Color.parseColor("#FAFAFA"));
        renderer.setXLabelsColor(Color.BLACK);
        renderer.setShowGrid(true);
        renderer.setShowGridX(true);
        renderer.setShowGridY(false);
        renderer.setGridColor(Color.rgb(225,225,225));//设置网格颜色
        renderer.setApplyBackgroundColor(true);
        renderer.setBackgroundColor(Color.WHITE);

        renderer.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);    //设置图表的X轴的当前方向
//        renderer.setFitLegend(true);// 调整合适的位置
//        renderer.setAxesColor(R.color.touming);//设置坐标轴颜色
//        renderer.setShowAxes(false);

        //线的属性
        XYSeriesRenderer r = new XYSeriesRenderer();//(类似于一条线对象)
        r.setColor(Color.RED);//设置颜色
        r.setPointStyle(PointStyle.CIRCLE);//设置点的样式
        r.setFillPoints(true);//填充点（显示的点是空心还是实心）
        r.setDisplayChartValues(true);//将点的值显示出来
        r.setChartValuesSpacing(10);//显示的点的值与图的距离
        r.setChartValuesTextSize(25);//点的值的文字大小

        XYSeriesRenderer r1 = new XYSeriesRenderer();//(类似于一条线对象)
        r1.setColor(Color.BLUE);//设置颜色
        r1.setPointStyle(PointStyle.CIRCLE);//设置点的样式
        r1.setFillPoints(true);//填充点（显示的点是空心还是实心）
        r1.setDisplayChartValues(true);//将点的值显示出来
        r1.setChartValuesSpacing(20);//显示的点的值与图的距离
        r1.setChartValuesTextSize(25);//点的值的文字大小
//        renderer.setXLabelsAngle(90); //设置刻度线与XY轴之间的相对位置关系
        renderer.setXLabels(0);
        renderer.setYLabels(0);
        for (int i = 0; i < weekList.size() ; i++) {
            renderer.addXTextLabel(i+1,weekList.get(i));
        }
//        r.setLineWidth(3);//设置线宽Y
        renderer.addSeriesRenderer(r);
        renderer.addSeriesRenderer(r1);
        return renderer;
    }


    /**
     * 添加新的数据，多组，更新曲线，只能运行在主线程
     *  @param maxList
     * @param minList
     */
    public void updateChart(List<Integer> maxList, List<Integer> minList, GraphicalView mGraphicalView) {
        xySeriesMax.clear();
        xySeriesMin.clear();
        for (int i = 0; i < maxList.size(); i++) {
            xySeriesMin.add(i+1, minList.get(i));
            xySeriesMax.add(i+1, maxList.get(i));
        }
        mGraphicalView.repaint();//此处也可以调用invalidate()
    }

    public List<Integer> getMaxTemplist() {
        return maxTemplist;
    }

    public List<Integer> getMinTemplist() {
        return minTemplist;
    }
}
