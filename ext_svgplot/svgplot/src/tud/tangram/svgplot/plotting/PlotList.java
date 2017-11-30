package tud.tangram.svgplot.plotting;

import java.util.ArrayList;

import tud.tangram.svgplot.coordinatesystem.MetricAxis;
import tud.tangram.svgplot.coordinatesystem.CoordinateSystem;
/**
 * 
 * @author Gregor Harlan
 * Idea and supervising by Jens Bornschein jens.bornschein@tu-dresden.de
 * Copyright by Technische Universität Dresden / MCI 2014
 *
 */
public class PlotList extends ArrayList<Plot> {

	private static final long serialVersionUID = 2449399739807644999L;
	private final CoordinateSystem cs;

	public PlotList(CoordinateSystem cs) {
		super();
		this.cs = cs;
	}

	public OverlayList overlays() {
		OverlayList overlays = new OverlayList(cs);

		// Intersections
		for (int i = 0; i < size() - 1; i++) {
			Plot plot1 = get(i);
			for (int k = i + 1; k < size(); k++) {
				overlays.addAll(plot1.getIntersections(get(k)), plot1.getFunction().toString(), null);
			}
		}

		// Extrema
		for (Plot plot : this) {
			overlays.addAll(plot.getExtrema(), plot.getFunction().toString(), null);
		}

		// Roots
		for (Plot plot : this) {
			overlays.addAll(plot.getRoots(), plot.getFunction().toString(), null);
		}

		// Other points
		for (double interval : ((MetricAxis)cs.xAxis).intervalSteps) {
			for (Plot plot : this) {
				overlays.addAll(plot.getPoints(interval, cs.xAxis.getRange().getFrom()), plot.getFunction().toString(), null);
			}
		}

		return overlays;
	}

}
