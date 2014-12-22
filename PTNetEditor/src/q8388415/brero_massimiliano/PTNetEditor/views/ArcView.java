package q8388415.brero_massimiliano.PTNetEditor.views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

import q8388415.brero_massimiliano.PTNetEditor.controllers.PTNNetController;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNIArcDTO;
import q8388415.brero_massimiliano.PTNetEditor.types.PTNIScaleListener;

/**
 * Visual reprensentation of an arc connecting 2 nodes. This class just knows
 * about starting and ending points. It does not know anything about a source or
 * target node since it's just a view and we do no want to implement any logic
 * in here. So e.g. the adjusting process for start and end point is taking care
 * of by the net controller and its delegates: the arc helper and the node
 * helper classes.
 * 
 * @author Laptop
 *
 */
public class ArcView implements PTNIScaleListener, PTNIArcDTO {

	private Point start;
	private Point end;
	private String id;
	private Boolean selected = false;
	private final Color DEFAULT_COLOR = Color.GRAY;
	private Color color;
	private Graphics desktopGraphics = null;
	private double scale;
	private static double currentScale = 1.0;
	private final double MAX_SIZE = 1.4;
	private final double MIN_SIZE = 1;
	private PTNNetController netController;
	// We need this flag to check if an arc was just drawn (and thus can be
	// deleted safely on the desktop).
	private final int ARROW_SIZE_X = 8;
	private final int ARROW_SIZE_Y = 4;
	/**
	 * How near the arc a user has to click to select it.
	 */
	private final double SENSITIVITY = 4.0;

	public ArcView(String id, Point s, Point e, PTNNetController netController) {

		this.netController = netController;
		scale = currentScale;
		color = DEFAULT_COLOR;
		this.setStart(s);
		this.setEnd(e);
		this.setId(id);

	}

	/**
	 * We compute the slope angle of our line here. We can cat that simply by
	 * using our start and end points for calculating the arc steepness
	 * (interpreted as a function Then this method uses the Math.atan2() method
	 * to get the angle from the steepness. We need that so drawArrowHead can be
	 * rotated and its tip always faces the right direction.
	 * 
	 * @param g
	 *            The desktop's Graphics object to draw on.
	 */
	public void drawArc(Graphics g) {

		double gradient = Math.atan2(this.getEnd().y - this.getStart().y, this.getEnd().x - this.getStart().x);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(color);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.drawLine(this.getStart().x, this.getStart().y, this.getEnd().x, this.getEnd().y);
		this.drawArrowHead(g2, end, gradient);

	}

	/**
	 * Redraws an arc after it was already drawn once and thus object holds a
	 * reference to the desktop's graphics object.
	 */
	private void updateArc() {
		if (desktopGraphics != null)
			this.drawArc(desktopGraphics);
	}

	/**
	 * Draws the arrow head at the end of the lines and rotates it according to
	 * line position. The arrow head will always face the right direction.
	 * 
	 * @param g2
	 * @param end
	 * @param gradient
	 */
	private void drawArrowHead(Graphics2D g2, Point end, double gradient) {
		
		AffineTransform rotateTrans = new AffineTransform();
		Polygon p = this.getArrowHeadPolygon();

		/**
		* This methos allows us to set fixed anchor point, so we don't have
		* to calculate for the "classic" translate method of AffineTransform.
		* */
		rotateTrans.setToIdentity();
		rotateTrans.translate(0, 0);
		rotateTrans.setToRotation(gradient, end.getX(), end.getY());

		Shape shape = rotateTrans.createTransformedShape(p);

		g2.fill(shape);
		g2.draw(shape);

	}

	private Polygon getArrowHeadPolygon() {
		Polygon p = new Polygon();
		p.addPoint(end.x, end.y);
		// Okay, this can also be done with an AffineTransorm. But since we
		// already use it for rotating
		// it would involve quite some translating computations and multiplying
		// those matrices is also
		// a little more complex than this simple but effective approach;
		p.addPoint((int) (end.x - ARROW_SIZE_X * currentScale), (int) (end.y - ARROW_SIZE_Y * currentScale));
		p.addPoint((int) (end.x - ARROW_SIZE_X * currentScale), (int) (end.y + ARROW_SIZE_Y * currentScale));

		return p;
	}

	/**
	 * Checks if the arc representation contains the given Point. A little bit
	 * of starting and ending segment is cut, so the arc won't be selected if
	 * somebody clicks to mark a node an the click is near the node icon's
	 * boundaries.
	 * 
	 * @param p
	 *            {@link Point}
	 * @return {@link Boolean}
	 */
	public Boolean contains(Point p) {
		return Line2D.ptSegDist(this.getStart().x, this.getStart().y, this.getEnd().x, this.getEnd().y, p.getX(), p.getY()) < SENSITIVITY;
	}

	public Point getStart() {
		return start;
	}

	public void setStart(Point start) {
		this.start = start;
	}

	public Point getEnd() {
		return end;
	}

	public void setEnd(Point end) {
		this.end = end;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand() == "+") {
			this.increaseScale();

		} else if (e.getActionCommand() == "-") {
			this.decreaseScale();
		}

	}

	@Override
	public void increaseScale() {

		if (scale <= MAX_SIZE) {
			scale += 0.1;
			currentScale = scale;
			netController.repaintDesktop();
		}
	}

	@Override
	public void decreaseScale() {
		if (scale > MIN_SIZE) {
			scale -= 0.1;
			currentScale = scale;
			netController.repaintDesktop();
		}
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean s) {
		this.selected = s;
		if (selected)
			this.setColor(Color.ORANGE);
		else
			this.setColor(DEFAULT_COLOR);
		this.updateArc();
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
