import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Stack;

public class KdTree {
	
	private Node root;
	private int size = 0;
	
	private class Node {
		Point2D point;
		Node lb, rt;  // left/bottom or right/top node
		boolean isVertical;
		RectHV rect;
		
		public Node(Point2D point, boolean isVertical, RectHV rect) {
			this.point = point;
			this.isVertical = isVertical;
			this.rect = rect;
		}
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public int size() {
		return this.size;
	}
	
	public boolean contains(Point2D p) {
		if (p == null) throw new IllegalArgumentException("null");
		return contains(root, p);
	}
	
	// recursive method
	private boolean contains(Node node, Point2D point) {
		if (node == null) return false;
		if (point.equals(node.point)) return true;
		
		if (node.isVertical) {
			if (point.x() < node.point.x()) return contains(node.lb, point);
			return contains(node.rt, point);
		} else {
			if (point.y() < node.point.y()) return contains(node.lb, point);
			return contains(node.rt, point);
		}
	}
	
	public void insert(Point2D p) {
		if (p == null) throw new IllegalArgumentException("null");
		if (contains(p)) return;
		RectHV rect = new RectHV(0, 0, 1, 1);
		root = insert(root, p, true, rect);
	}
	
	private Node insert(Node node, Point2D p, boolean isVertical, RectHV rect) {
		
		if (node == null) {
			size++;
			return new Node(p, isVertical, rect);
		}
		
		RectHV thisRect = node.rect;
		if (node.isVertical) {
			double cmp = p.x() - node.point.x();
			if (cmp >= 0) {
				RectHV rightRect = new RectHV(node.point.x(), thisRect.ymin(), thisRect.xmax(), thisRect.ymax());
				node.rt = insert(node.rt, p, false, rightRect);
			}
			else {
				RectHV leftRect = new RectHV(thisRect.xmin(), thisRect.ymin(), node.point.x(), thisRect.ymax());
				node.lb = insert(node.lb, p, false, leftRect);
			}
		} else if (!node.isVertical) {
			double cmp = p.y() - node.point.y();
			if (cmp >= 0) {
				RectHV topRect = new RectHV(thisRect.xmin(), node.point.y(), thisRect.xmax(), thisRect.ymax());
				node.rt = insert(node.rt, p, true, topRect);
			}
			else {
				RectHV bottomRect = new RectHV(thisRect.xmin(), thisRect.ymin(), thisRect.xmax(), node.point.y());
				node.lb = insert(node.lb, p, true, bottomRect);
			}
		}
		return node;
	}
	
	public void draw() {
		
		// draw the frame
		StdDraw.setPenColor(StdDraw.BLACK);
		Point2D lb = new Point2D(0.0, 0.0);
		Point2D rb = new Point2D(1.0, 0.0);
		Point2D lt = new Point2D(0.0, 1.0);
		Point2D rt = new Point2D(1.0, 1.0);
		lb.drawTo(rb);
		lb.drawTo(lt);
		rt.drawTo(lt);
		rt.drawTo(rb);
		
		// draw the nodes and lines
		draw(root);
	}
	
	// recursive method for drawing nodes and lines
	private void draw(Node node) {
		if (node == null) return;
		
		Point2D point = node.point;
	    StdDraw.setPenColor(StdDraw.BLACK);
	    StdDraw.setPenRadius(0.005);
	    point.draw();
	    
	    if (node.isVertical) {
	        StdDraw.setPenColor(StdDraw.RED);
	        StdDraw.setPenRadius();
	        
	        Point2D bottom = new Point2D(point.x(), node.rect.ymin());
	        Point2D top = new Point2D(point.x(), node.rect.ymax());
	        bottom.drawTo(top);
	        
	        draw(node.lb);
	        draw(node.rt);
	    } else {
	        StdDraw.setPenColor(StdDraw.BLUE);
	        StdDraw.setPenRadius();
	        
	        Point2D left = new Point2D(node.rect.xmin(), point.y());
	        Point2D right = new Point2D(node.rect.xmax(), point.y());
	        left.drawTo(right);
	        
	        draw(node.lb);
	        draw(node.rt);
	    }
	}

	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null) throw new IllegalArgumentException("null");
		Stack<Point2D> stack = new Stack<Point2D>();
		range(stack, rect, root);
		return stack;
	}
	
	private void range(Stack<Point2D> stack, RectHV rect, Node node) {
		if (node == null) return;
		if (rect.contains(node.point)) stack.push(node.point);
		if (node.lb != null && rect.intersects(node.lb.rect)) range(stack, rect, node.lb);
		if (node.rt != null && rect.intersects(node.rt.rect)) range(stack, rect, node.rt);
	}
	
	public Point2D nearest(Point2D p) {
		if (p == null) throw new IllegalArgumentException("null");
    if (root == null) return null;
		Point2D res = nearest(root, p, root.point);
		return res;
	}
	
	private Point2D nearest(Node node, Point2D p, Point2D currMinPoint) {
		if (node == null) return null;
		double distanceSqr = node.point.distanceSquaredTo(p);
		double currMinDSqr = currMinPoint.distanceSquaredTo(p);
		Point2D nearest = currMinPoint;
		
		if (distanceSqr < currMinDSqr) {
			currMinDSqr = distanceSqr;
			nearest = node.point;
		}
		
		if (node.isVertical && p.x() >= node.point.x()) {
			if (node.rt != null && node.rt.rect.distanceSquaredTo(p) < currMinDSqr)  nearest = nearest(node.rt, p, nearest);
			if (node.lb != null && node.lb.rect.distanceSquaredTo(p) < currMinDSqr)  nearest = nearest(node.lb, p, nearest);
		}
		
		if (node.isVertical && p.x() < node.point.x()) {
			if (node.lb != null && node.lb.rect.distanceSquaredTo(p) < currMinDSqr)  nearest = nearest(node.lb, p, nearest);			
			if (node.rt != null && node.rt.rect.distanceSquaredTo(p) < currMinDSqr)  nearest = nearest(node.rt, p, nearest);
		}
		
		if (!node.isVertical && p.y() >= node.point.y()) {
			if (node.rt != null && node.rt.rect.distanceSquaredTo(p) < currMinDSqr)  nearest = nearest(node.rt, p, nearest);			
			if (node.lb != null && node.lb.rect.distanceSquaredTo(p) < currMinDSqr)  nearest = nearest(node.lb, p, nearest);
		}
		
		if (!node.isVertical && p.y() < node.point.y()) {
			if (node.lb != null && node.lb.rect.distanceSquaredTo(p) < currMinDSqr)  nearest = nearest(node.lb, p, nearest);			
			if (node.rt != null && node.rt.rect.distanceSquaredTo(p) < currMinDSqr)  nearest = nearest(node.rt, p, nearest);
		}
		return nearest;
	}	
	
	public static void main(String[] args) {

	    // create initial board from file
	    In in = new In(args[0]);
	    
	    double[] points = in.readAllDoubles();
	    int n = points.length;
	    KdTree tree = new KdTree();
	    
	    for (int i = 0; i < n; i += 2) {
	    	Point2D point = new Point2D(points[i], points[i + 1]);
	    	tree.insert(point);
	    }
	    tree.draw();
	}
}
