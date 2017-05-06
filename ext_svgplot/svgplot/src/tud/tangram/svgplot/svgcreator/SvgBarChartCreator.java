package tud.tangram.svgplot.svgcreator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tud.tangram.svgplot.options.SvgBarChartOptions;
import tud.tangram.svgplot.options.SvgPlotOptions;
import tud.tangram.svgplot.styles.AxisStyle;
import tud.tangram.svgplot.svgpainter.SvgBarPainter;

public class SvgBarChartCreator extends SvgGridCreator {

	static final Logger log = LoggerFactory.getLogger(SvgBarChartCreator.class);
	
	protected final SvgBarChartOptions options;
	
	public static final SvgCreatorInstantiator INSTANTIATOR = new SvgCreatorInstantiator() {
		public SvgCreator instantiateCreator(SvgPlotOptions rawOptions) {
			SvgBarChartOptions bcOptions = new SvgBarChartOptions(rawOptions);
			return new SvgBarChartCreator(bcOptions);
		}
	};
	
	public SvgBarChartCreator(SvgBarChartOptions options) {
		super(options);
		this.options = options;
	}

	/**
	 * Default: show only one edge axis, overridable with options.
	 */
	@Override
	protected AxisStyle getXAxisStyle() {
		return options.showDoubleAxes == null || "off".equals(options.showDoubleAxes) ? AxisStyle.EDGE : AxisStyle.BOX;
	}

	/**
	 * Default: show box axis, overridable with options.
	 */
	@Override
	protected AxisStyle getYAxisStyle() {
		return options.showDoubleAxes == null || "on".equals(options.showDoubleAxes) ? AxisStyle.BOX : AxisStyle.EDGE;
	}

	@Override
	protected void create() {
		super.create();
		
		SvgBarPainter svgBarPainter = new SvgBarPainter(cs, options.barAccumulationStyle, options.points);
		svgBarPainter.paintToSvgDocument(doc, viewbox, options.outputDevice);
	}

}
