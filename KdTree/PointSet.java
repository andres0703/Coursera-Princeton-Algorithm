import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {
	
	private SET<Point2D> points;
	
	public PointSET() {
		points = new SET<Point2D>();
	}
	
	public boolean isEmpty() {
		return points.size() == 0;
	}
	
	public int size() {
		return points.size();
	}
	
	public void insert(Point2D p) {
		if (contains(p)) return;
		points.add(p);
	}
	
	public boolean contains(Point2D p) {
		return points.contains(p);
	}
	
	public void draw() {
		for (Point2D point : points) {
			point.draw();
		}
	}
	
	public Iterable<Point2D> range(RectHV rect) {
		SET<Point2D> res = new SET<Point2D>();
		for (Point2D point : points) {
			if (rect.contains(point)) res.add(point);
		}
		return res;
	}
	
	public Point2D nearest(Point2D p) {
		if (!inRange(p)) throw new IllegalArgumentException("point out of bound");
		if (points.isEmpty()) return null;
		
		double minDistance = Double.POSITIVE_INFINITY;
		double distance = 0.0;
		Point2D res = null;
		for (Point2D point : points) {
			distance = point.distanceTo(p);
			if (distance < minDistance) {
				minDistance = distance;
				res = point;
			}
		}
		return res;
	}
	
	// return true if both x and y of the point is in range of [0, 1]
	private boolean inRange(Point2D p) {
		return (p.x() >= 0 && p.x() <= 1) && (p.y() >= 0 && p.y() <= 1);
	}
}
