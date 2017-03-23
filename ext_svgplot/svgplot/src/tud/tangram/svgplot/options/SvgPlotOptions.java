package tud.tangram.svgplot.options;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.IStringConverterFactory;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import tud.tangram.svgplot.coordinatesystem.Range;
import tud.tangram.svgplot.data.CsvParser;
import tud.tangram.svgplot.data.Point;
import tud.tangram.svgplot.data.PointListList;
import tud.tangram.svgplot.plotting.Function;
import tud.tangram.svgplot.plotting.IntegralPlotSettings;

@Parameters(separators = "=", resourceBundle = "Bundle")
public class SvgPlotOptions {

	@Parameter(names = { "--diagramtype", "--dt" }, required = true)
	private DiagramType diagramType;
	
	public DiagramType getDiagramType() {
		return diagramType;
	}

	public void setDiagramType(DiagramType diagramType) {
		this.diagramType = diagramType;
	}

	@Parameter(names = { "--device", "-d" })
	private OutputDevice outputDevice = OutputDevice.Default;

	public OutputDevice getOutputDevice() {
		return outputDevice;
	}

	public void setOutputDevice(OutputDevice outputDevice) {
		this.outputDevice = outputDevice;
	}

	@Parameter(description = "functions")
	private List<Function> functions = new ArrayList<>();

	public List<Function> getFunctions() {
		return functions;
	}

	public void setFunctions(List<Function> functions) {
		this.functions = functions;
	}

	@Parameter(names = { "--size", "-s" }, descriptionKey = "param.size")
	private Point size = new Point(210, 297);

	public Point getSize() {
		return size;
	}

	/**
	 * Page size in mm
	 * 
	 * @param size
	 */
	public void setSize(Point size) {
		this.size = size;
	}

	@Parameter(names = { "--xrange", "-x" }, descriptionKey = "param.xrange")
	private Range xRange = new Range(-8, 8);

	public Range getxRange() {
		return xRange;
	}

	public void setxRange(Range xRange) {
		this.xRange = xRange;
	}

	@Parameter(names = { "--yrange", "-y" }, descriptionKey = "param.yrange")
	private Range yRange = new Range(-8, 8);

	public Range getyRange() {
		return yRange;
	}

	public void setyRange(Range yRange) {
		this.yRange = yRange;
	}

	@Parameter(names = { "--pi", "-p" }, descriptionKey = "param.pi")
	private boolean pi = false;

	public boolean isPi() {
		return pi;
	}

	public void setPi(boolean pi) {
		this.pi = pi;
	}

	@Parameter(names = { "--xlines" }, descriptionKey = "param.xlines")
	private String xLines = null;

	public String getxLines() {
		return xLines;
	}

	public void setxLines(String xLines) {
		this.xLines = xLines;
	}

	@Parameter(names = { "--ylines" }, descriptionKey = "param.ylines")
	private String yLines = null;

	public String getyLines() {
		return yLines;
	}

	public void setyLines(String yLines) {
		this.yLines = yLines;
	}

	@Parameter(names = { "--title", "-t" }, descriptionKey = "param.title")
	private String title = "";

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Parameter(names = { "--gnuplot", "-g" }, descriptionKey = "param.gnuplot")
	private String gnuplot = null;

	public String getGnuplot() {
		return this.gnuplot;
	}

	@Parameter(names = { "--css", "-c" }, descriptionKey = "param.css")
	private String css = null;

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	/** Output path */
	@Parameter(names = { "--output", "-o" }, descriptionKey = "param.output")
	private File output = null;

	public File getOutput() {
		return output;
	}

	public void setOutput(File output) {
		this.output = output;
	}

	@Parameter(names = { "--help", "-h", "-?" }, help = true, descriptionKey = "param.help")
	private boolean help;

	public boolean getHelp() {
		return help;
	}

	@Parameter(names = { "--integral", "-i" }, descriptionKey = "param.integral")
	private IntegralPlotSettings integral;

	public IntegralPlotSettings getIntegral() {
		return integral;
	}

	public void setIntegral(IntegralPlotSettings integral) {
		this.integral = integral;
	}

	// TODO: add parameter for scatter plot file
	// parameter for marking some points
	@Parameter(names = { "--points", "--pts" }, descriptionKey = "param.points")
	private String pts;

	public String getPts() {
		return pts;
	}

	public void setPts(String pts) {
		this.pts = pts;
	}

	@Parameter(names = { "--csvpath", "--csv" }, descriptionKey = "param.csvpath")
	private String csvPath = null;

	public String getCsvPath() {
		return csvPath;
	}

	public void setCsvPath(String csvPath) {
		this.csvPath = csvPath;
	}

	@Parameter(names = { "--csvorientation", "--csvo" }, descriptionKey = "param.csvorientation")
	private CsvOrientation csvOrientation = CsvOrientation.HORIZONTAL;

	public CsvOrientation getCsvOrientation() {
		return csvOrientation;
	}

	public void setCsvOrientation(CsvOrientation csvOrientation) {
		this.csvOrientation = csvOrientation;
	}

	/**
	 * interpreted List of list of points parsed from the 'pts' property
	 */
	private PointListList points;

	/**
	 * interpreted List of list of points parsed from the 'pts' property
	 */
	public PointListList getPoints() {
		return points;
	}

	/**
	 * interpreted List of list of points parsed from the 'pts' property
	 */
	public void setPoints(PointListList points) {
		this.points = points;
	}

	/**
	 * Returns a converter for the special class-types of this project for
	 * JCommander interpretation.
	 */
	public static class StringConverterFactory implements IStringConverterFactory {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Class<? extends IStringConverter<?>> getConverter(Class forType) {
			if (forType.equals(Point.class))
				return Point.Converter.class;
			else if (forType.equals(Range.class))
				return Range.Converter.class;
			else if (forType.equals(Function.class))
				return Function.Converter.class;
			else if (forType.equals(PointListList.class))
				return PointListList.Converter.class;
			else if (forType.equals(IntegralPlotSettings.class))
				return IntegralPlotSettings.Converter.class;
			else if (forType.equals(CsvOrientation.class))
				return CsvOrientation.CsvOrientationConverter.class;
			else if (forType.equals(DiagramType.class))
				return DiagramType.DiagramTypeConverter.class;
			else
				return null;
		}
	}

	public void finalizeOptions() {
		if (csvPath != null) {
			try {
				CsvParser parser = new CsvParser(new FileReader(csvPath), ',', '"');
				if(csvOrientation == CsvOrientation.VERTICAL)
					points = parser.parseAsScatterDataVerticalRows();
				else
					points = parser.parseAsScatterDataHorizontalRows();

			} catch (IOException e) {
				points = (new PointListList.Converter()).convert(pts);
			}
		} else {
			this.points = (new PointListList.Converter()).convert(pts);
		}
		if (this.points != null && !this.points.isEmpty() && this.points.hasValidMinMaxValues()) {
			double xPointRangeFactor = 0.05 * (points.getMaxX() - points.getMinX());
			double yPointRangeFactor = 0.05 * (points.getMaxY() - points.getMinY());
			if (xRange.getFrom() > points.getMinX())
				xRange.setFrom(points.getMinX() - Math.abs(xPointRangeFactor * points.getMinX()));
			if (xRange.getTo() < points.getMaxX())
				xRange.setTo(points.getMaxX() + Math.abs(xPointRangeFactor * points.getMaxX()));
			if (yRange.getFrom() > points.getMinY())
				yRange.setFrom(points.getMinY() - Math.abs(yPointRangeFactor * points.getMinY()));
			if (yRange.getTo() < points.getMaxY())
				yRange.setTo(points.getMaxY() + Math.abs(yPointRangeFactor * points.getMaxY()));
		}
	}
}
