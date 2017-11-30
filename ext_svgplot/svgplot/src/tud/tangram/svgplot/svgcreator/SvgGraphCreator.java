package tud.tangram.svgplot.svgcreator;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import tud.tangram.svgplot.data.Point;
import tud.tangram.svgplot.data.PointListList.PointList;
import tud.tangram.svgplot.options.SvgGraphOptions;
import tud.tangram.svgplot.options.SvgPlotOptions;
import tud.tangram.svgplot.plotting.Function;
import tud.tangram.svgplot.plotting.Gnuplot;
import tud.tangram.svgplot.plotting.OverlayList;
import tud.tangram.svgplot.plotting.PlotList;
import tud.tangram.svgplot.plotting.ReferenceLine;
import tud.tangram.svgplot.plotting.ReferenceLine.Direction;
import tud.tangram.svgplot.styles.AxisStyle;
import tud.tangram.svgplot.styles.PointPlotStyle;
import tud.tangram.svgplot.svgpainter.SvgPlotPainter;
import tud.tangram.svgplot.svgpainter.SvgPointsPainter;
import tud.tangram.svgplot.svgpainter.SvgReferenceLinesPainter;
import tud.tangram.svgplot.utils.SvgTools;

public class SvgGraphCreator extends SvgGridCreator {
	protected final SvgGraphOptions options;

	public SvgGraphCreator(SvgGraphOptions options) {
		super(options);
		this.options = options;
	}

	public static SvgCreatorInstantiator INSTANTIATOR = new SvgCreatorInstantiator() {
		public SvgCreator instantiateCreator(SvgPlotOptions rawOptions) {
			SvgGraphOptions options = new SvgGraphOptions(rawOptions);
			SvgGraphCreator creator = new SvgGraphCreator(options);
			return creator;
		}
	};

	/**
	 * Creates the reference lines and graph/integral/scatter plots and puts
	 * according information into the legend.
	 */
	public void create() {
		super.create();

		// Paint reference lines
		if (options.integral != null && options.integral.xRange != null) {
			ArrayList<ReferenceLine> lines = new ArrayList<>();

			/*
			 * If the integral start and/or end is within the axis range, paint
			 * reference lines for them
			 */
			if (options.integral.xRange.getFrom() > cs.xAxis.getRange().getFrom())
				lines.add(new ReferenceLine(Direction.X_LINE, options.integral.xRange.getFrom()));
			if (options.integral.xRange.getTo() < cs.xAxis.getRange().getTo())
				lines.add(new ReferenceLine(Direction.X_LINE, options.integral.xRange.getTo()));

			if (!lines.isEmpty()) {
				SvgReferenceLinesPainter svgReferenceLinesPainter = new SvgReferenceLinesPainter(cs, lines);
				svgReferenceLinesPainter.paintToSvgDocument(doc, viewbox, options.outputDevice);
			}
		}

		try {
			createPlots();
		} catch (Exception e) {
			System.out.println("Could not create the plots.");
			System.out.println(e);
		}
	}

	@Override
	protected AxisStyle getXAxisStyle() {
		return AxisStyle.GRAPH;
	}

	@Override
	protected AxisStyle getYAxisStyle() {
		return AxisStyle.GRAPH;
	}

	/**
	 * Paint the function/integral/scatter plots into the svg file and add
	 * according information into the legend.
	 */
	private void createPlots() {
		/*
		 * Call gnuplot in order to calculate and paint the function graphs and
		 * integrals
		 */
		Gnuplot gnuplot = new Gnuplot(options.gnuplot);
		SvgPlotPainter svgPlotPainter = new SvgPlotPainter(cs, options.functions, gnuplot, options.integral);
		svgPlotPainter.paintToSvgDocument(doc, viewbox, options.outputDevice);

		// Add the legend items for the graph lines and the integral area with a
		// high priority
		svgPlotPainter.prepareLegendRenderer(legendRenderer, options.outputDevice, -100);

		// Get the list of the plots for reference in the description
		PlotList plotList = svgPlotPainter.getPlotList();

		// Overlays for audio tactile output
		OverlayList overlays = plotList.overlays();

		// Paint the scatter plot points to the SVG file
		SvgPointsPainter svgPointsPainter = new SvgPointsPainter(cs, options.points,
				options.dotsBorderless ? PointPlotStyle.MULTI_ROWS_BORDERLESS : PointPlotStyle.MULTI_ROWS,
				options.colors);
		svgPointsPainter.paintToSvgDocument(doc, viewbox, options.outputDevice);
		svgPointsPainter.addOverlaysToList(overlays);

		// Add the legend items for the scatter plot
		svgPointsPainter.prepareLegendRenderer(legendRenderer, options.outputDevice);

		// Add all overlays to the main overlay list
		this.overlays.addAll(overlays, true);

		createDesc(plotList);
	}

	/**
	 * Writes the external HTML description document
	 * 
	 * @param plotList
	 *            | ???
	 */
	private void createDesc(PlotList plotList) {
		

		Node div = desc.appendBodyChild(desc.createDiv("functions"));
		
		// general description
		div.appendChild(desc.createFunctionIntro(cs, options.functions.size()));

		// functions
		if (!options.functions.isEmpty()) {
			div.appendChild(desc.createFunctionList(options.functions));

			// intersections between functions
			if (plotList.size() > 1) {
				desc.appendBodyChild(desc.createFunctionIntersectionList(cs, plotList));
			}
		}

		// extreme points & zero
		desc.appendBodyChild(desc.createFunctionExtremaAndZero(cs, plotList));

		// points
		if (options.points != null && options.points.size() > 0) {
			desc.appendBodyChild(desc.createFunctionPointsDescription(cs, options.points));
		}

		// integral
		if (options.integral != null && options.integral.function1 >= 0) {
			div = desc.appendBodyChild(desc.createDiv("integral-"));
			if (options.integral.function2 >= 0)
				div.appendChild(desc.createP(SvgTools.translate("desc.integral_1",
						Math.max(cs.xAxis.getRange().getFrom(), options.integral.xRange.getFrom()),
						Math.min(cs.xAxis.getRange().getTo(), options.integral.xRange.getTo()),
						SvgTools.getFunctionName(options.integral.function1),
						SvgTools.getFunctionName(options.integral.function2))));
			else
				div.appendChild(desc.createP(SvgTools.translate("desc.integral_0",
						Math.max(cs.xAxis.getRange().getFrom(), options.integral.xRange.getFrom()),
						Math.min(cs.xAxis.getRange().getTo(), options.integral.xRange.getTo()),
						SvgTools.getFunctionName(options.integral.function1))));
		}

		desc.appendBodyChild(desc.createP(SvgTools.translate("desc.note")));
	}

}
