/*
 * Copyright (c) 2010 Georgios Migdos <cyberpython@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package aueb.geom.ui.views.intersections;

import aueb.geom.Segment2D;
import aueb.geom.algorithms.intersections.Segment2DEx;
import aueb.geom.algorithms.logging.LogEvent;
import aueb.geom.algorithms.logging.intersections.NewIntersectionEvent;
import aueb.geom.algorithms.logging.PermanentLogEvent;
import aueb.geom.algorithms.logging.intersections.ScanLinePositionChangeEvent;
import aueb.geom.algorithms.logging.intersections.SegmentsIntersectionCheckEvent;
import aueb.geom.ui.views.EventLogDrivenView;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.event.MouseInputListener;

/**
 *
 * @author cyberpython
 */
public class IntersectionsView extends EventLogDrivenView implements MouseInputListener {

   private Color scanLineColor;
    private Color pointColor;
    private Color intersectionPointColor;
    private Color lineColor;
    private Color highlightLineColor;
    private int pointRadius;
    private int pointDim;
    private BasicStroke scanLineStroke;
    private BasicStroke segmentStroke;
    
    private double scanLineX;
    
    private List<Segment2DEx> segments;
    private Point2D tmp;
    private Segment2DEx tmpS;

    public IntersectionsView() {
        super();

        this.scanLineX = 0;


        this.pointColor = new Color(117, 139, 255); //light blue
        this.highlightLineColor = new Color(255, 206, 22); //yellow
        this.lineColor = new Color(66, 66, 66); //dark grey
        this.intersectionPointColor = new Color(230, 0, 0, 155); //red with alpha

        this.scanLineColor = new Color(0, 0, 0);

        this.pointRadius = 4;
        this.pointDim = 2 * pointRadius;

        this.segmentStroke = new BasicStroke(4);
        this.scanLineStroke = new BasicStroke(1);

        this.segments = new ArrayList<Segment2DEx>();

        tmp = null;
        tmpS = null;

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

    }

    public List<Segment2DEx> getSegments() {
        return this.segments;
    }

    public void setSegments(List<Segment2DEx> segments) {
        if (!isBlockingInput()) {
            this.clear();
            this.segments = segments;
            this.repaint();
        }
    }

    @Override
    public void setEventLog(List<LogEvent> eventLog) {
        if (!isBlockingInput()) {
            this.scanLineX = 0;
        }
        super.setEventLog(eventLog);
    }

    public void clear() {
        if (!isBlockingInput()) {
            this.setEventLog(new ArrayList<LogEvent>());
            this.setEventIndex(0);
            this.segments.clear();
            this.tmp = null;
            this.tmpS = null;
            this.repaint();
        }
    }

    public void drawBeforeEvents(Graphics2D g2d){
         drawSegments(g2d);
     }

    public void drawAfterEvents(Graphics2D g2d){
        drawScanLine(g2d);
    }

    public void drawEvents(Graphics2D g2d) {
        int eventIndex = this.getEventIndex();
        if (eventIndex < getEventLogSize()) {
            int i = 0;
            for (Iterator<LogEvent> it = getEventLogIterator(); it.hasNext();) {
                LogEvent logEvent = it.next();
                if (i <= eventIndex) {
                    if (logEvent instanceof PermanentLogEvent) {
                        if (logEvent instanceof NewIntersectionEvent) {
                            drawPoint(g2d, ((NewIntersectionEvent) logEvent).getPoint(), true);
                        }
                    } else {
                        if (i == eventIndex) {
                            if (logEvent instanceof ScanLinePositionChangeEvent) {
                                scanLineX = ((ScanLinePositionChangeEvent) logEvent).getX();
                            } else if (logEvent instanceof SegmentsIntersectionCheckEvent) {
                                Segment2D s = ((SegmentsIntersectionCheckEvent) logEvent).getSegment1();
                                drawSegment(g2d, s, true);
                                drawPoint(g2d, s.getStart(), false);
                                drawPoint(g2d, s.getEnd(), false);


                                s = ((SegmentsIntersectionCheckEvent) logEvent).getSegment2();
                                drawSegment(g2d, s, true);
                                drawPoint(g2d, s.getStart(), false);
                                drawPoint(g2d, s.getEnd(), false);
                            }
                        }
                    }
                }
                i++;
            }
        }
    }

    private void drawPoint(Graphics2D g2d, Point2D p0, boolean highlight) {
        double x;
        double y;
        if (highlight) {
            g2d.setPaint(intersectionPointColor);
            x = translateX(p0.getX()) - 2 * pointRadius;
            y = translateY(p0.getY()) - 2 * pointRadius;
            g2d.fill(new Ellipse2D.Double(x, y, 2 * pointDim, 2 * pointDim));
        } else {
            g2d.setPaint(pointColor);
            x = translateX(p0.getX()) - pointRadius;
            y = translateY(p0.getY()) - pointRadius;
            g2d.fill(new Ellipse2D.Double(x, y, pointDim, pointDim));
        }
    }

    private void drawSegments(Graphics2D g2d) {
        for (Iterator<Segment2DEx> it = segments.iterator(); it.hasNext();) {
            Segment2D s = it.next();
            drawSegment(g2d, s, false);
            drawPoint(g2d, s.getStart(), false);
            drawPoint(g2d, s.getEnd(), false);
        }
    }

    private void drawSegment(Graphics2D g2d, Segment2D segment, boolean highlight) {
        if (highlight) {
            g2d.setPaint(highlightLineColor);
        } else {
            g2d.setPaint(lineColor);
        }
        g2d.setStroke(segmentStroke);
        Point2D p1 = segment.getStart();
        Point2D p2 = segment.getEnd();
        double x1 = translateX(p1.getX());
        double y1 = translateY(p1.getY());
        double x2 = translateX(p2.getX());
        double y2 = translateY(p2.getY());
        g2d.draw(new Line2D.Double(x1, y1, x2, y2));
    }

    private void drawScanLine(Graphics2D g2d) {

        g2d.setPaint(scanLineColor);
        g2d.setStroke(scanLineStroke);

        double x1 = translateX(scanLineX);
        double y1 = translateY(0);
        double x2 = translateX(scanLineX);
        double y2 = translateY(this.getHeight());
        g2d.draw(new Line2D.Double(x1, y1, x2, y2));
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        if (!isBlockingInput()) {
            tmp = new Point2D.Double(translateX(e.getX()), translateY(e.getY()));
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (tmpS != null) {
            segments.remove(tmpS);
        }
        if (!isBlockingInput()) {
            this.segments.add(new Segment2DEx(tmp, new Point2D.Double(translateX(e.getX()), translateY(e.getY()))));
            repaint();
        }
        tmp = null;
        tmpS = null;

    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
        if (tmpS != null) {
            segments.remove(tmpS);
        }
        if (isBlockingInput()) {
            return;
        }
        if (tmp != null) {
            tmpS = new Segment2DEx(tmp, new Point2D.Double(translateX(e.getX()), translateY(e.getY())));
            this.segments.add(tmpS);
            repaint();
        }
    }

    public void mouseMoved(MouseEvent e) {
    }
}
