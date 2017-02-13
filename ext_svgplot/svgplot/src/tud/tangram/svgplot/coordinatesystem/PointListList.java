package tud.tangram.svgplot.coordinatesystem;

import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.IStringConverter;

/**
 * 
 * @author Gregor Harlan, Jens Bornschein
 * Idea and supervising by Jens Bornschein jens.bornschein@tu-dresden.de
 * Copyright by Technische Universität Dresden / MCI 2014
 *
 */
public class PointListList extends ArrayList<PointListList.PointList>{

	private static final long serialVersionUID = 6902232865786868851L;
	public double maxX = 0;
	public double maxY = 0;
	public double minX = 0;
	public double minY = 0;
	public PointListList() {this("");}
	public PointListList(String pointLists) {
		if(pointLists == null || pointLists.isEmpty()) return;
		
		//TODO: load from file
		
		//pointLists = pointLists.replaceAll("[^\\d.,^\\s+,^\\{^\\}^-]", "");
		String[] lists = pointLists.split("\\}");
		for (String l : lists) {
			PointList pl = new PointList(l);
			if(pl.size() > 0){
				this.add(pl);
			}
		}		
	}
	
	@Override
	public boolean add(PointListList.PointList pl) {
		maxX = Math.max(maxX, pl.maxX);
		maxY = Math.max(maxY, pl.maxY);				
		minX = Math.min(minX, pl.minX);
		minY = Math.min(minY, pl.minY);	
		return super.add(pl);
	}
	
	public boolean add(List<Point> points) {
		PointList pl = new PointList(points);		
		maxX = Math.max(maxX, pl.maxX);
		maxY = Math.max(maxY, pl.maxY);				
		minX = Math.min(minX, pl.minX);
		minY = Math.min(minY, pl.minY);	
		return super.add(pl);
	}

	
	public static class Converter implements IStringConverter<PointListList> {
		@Override
		public PointListList convert(String value) {
			return new PointListList(value);
		}
	}
		
	/**
	 * List of Points including max values
	 * @author Jens Bornschein
	 * 
	 */
	public class PointList extends ArrayList<Point>{
		
		private static final long serialVersionUID = -2318768874799315111L;
		public double maxX = 0;
		public double maxY = 0;
		public double minX = 0;
		public double minY = 0;
		public String name = "";
		
		public PointList(List<Point> points){
			if(points != null && points.size() > 0){
				for (Point p : points) {
					this.add(p);
				}
			}
		}
				
		public PointList(String points){
			if(points == null || points.isEmpty()) return;
			
			String[] pl = points.split("::");
			
			if(pl != null && pl.length > 0){
				
				String pts;
				if(pl.length > 1){
					name = pl[0].trim();
					pts = pl[1].replaceAll("[^\\d.,^\\s+,^-]", "");
				}else{
					pts = pl[0].replaceAll("[^\\d.,^\\s+,^-]", "");
				}				
				String[] s = pts.split("\\s+");
				
				for (String string : s) {
					if(string != null && !string.isEmpty()){
						Point p = (new Point.Converter()).convert(string);
						this.add(p);	
					}
				}				
			}					
		}	
		
		public PointList() {this("");}

		@Override
		public boolean add(Point p) {
			maxX = Math.max(maxX, p.x);
			maxY = Math.max(maxY, p.y);				
			minX = Math.min(minX, p.x);
			minY = Math.min(minY, p.y);	
			return super.add(p);
		}
		
		public class Converter implements IStringConverter<PointList> {
			@Override
			public PointList convert(String value) {
				return new PointList(value.trim());
			}
		}
	}
}