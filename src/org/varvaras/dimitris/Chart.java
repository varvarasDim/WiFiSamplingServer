package org.varvaras.dimitris;
import java.awt.BorderLayout;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.Layer;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.Color;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;

class Data {
	private int dbm = 0;
	private int count = 0;

	public Data(int dbm, int count) {
		this.dbm = dbm;
		this.count = count;
	}

	
	
	public int getDbm() {
		return dbm;
	}



	public void setDbm(int dbm) {
		this.dbm = dbm;
	}



	public int getCount() {
		return count;
	}



	public void setCount(int count) {
		this.count = count;
	}



	public boolean equals(Object other) {

		if (!(other instanceof Data))
			return false;

		Data data = (Data) other;

		if ((this.dbm == data.dbm))
			return true;
		else
			return false;
	}
}

class Chart {
	public int counter = 0;
	public List<Data> datalist = new ArrayList<Data>();
	JFreeChart chart;
	JFrame frameC;
	ChartPanel chartPanel;
	XYDataset dataset;
	private XYSeries series = new XYSeries("data");

	static JTextField sample = new JTextField(20);

	public Chart() {

		try {
			dataset = createDataset();
			chart = createChart(dataset);
			chartPanel = new ChartPanel(chart);
			chartPanel.setPreferredSize(new java.awt.Dimension(1500, 800));
			frameC = new JFrame("Wifi signal chart");
			frameC.setContentPane(chartPanel);

		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		frameC.pack();
		frameC.setVisible(true);

	}

	public void refreshChart() {
		chartPanel.removeAll();
		chartPanel.revalidate();
		try {
			dataset = createDataset();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		chart = createChart(dataset);
		chart.removeLegend();
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setLayout(new BorderLayout());
		chartPanel.repaint();
	}

	private JFreeChart createChart(final XYDataset dataset) {

		final JFreeChart chart = ChartFactory.createXYLineChart("samples of "+WiFiServer.WIFI_NETWORK,
				"number of sample", "dbm", dataset, PlotOrientation.VERTICAL, false, true,
				false);

		XYPlot plot = (XYPlot) chart.getPlot();
		final IntervalMarker target = new IntervalMarker(-100, -30);
		final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		target.setPaint(new Color(222, 222, 255, 128));
		plot.addRangeMarker(target, Layer.BACKGROUND);
		plot.setBackgroundPaint(Color.lightGray);
		renderer.setSeriesLinesVisible(0, true);
		plot.setRenderer(renderer);

		return chart;

	}

	private XYDataset createDataset() throws ParseException {
		series.clear();
		for (int x = 0; x < datalist.size(); x++) {
			series.add(datalist.get(x).getCount(), datalist.get(x).getDbm());
		}

		final XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series);

		return dataset;
	}
}
