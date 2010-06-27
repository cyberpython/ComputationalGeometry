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
package aueb.geom.ui.views.convex_hull;

import aueb.geom.Segment2D;
import aueb.geom.algorithms.logging.convex_hull.grahams_scan.GrahamsScanSegmentAddEvent;
import aueb.geom.algorithms.logging.convex_hull.grahams_scan.GrahamsScanSegmentCheckEvent;
import aueb.geom.algorithms.logging.convex_hull.grahams_scan.GrahamsScanSegmentRemoveEvent;
import aueb.geom.algorithms.logging.LogEvent;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author cyberpython
 */
public class GrahamsView extends ConvexHullView {


    private Color removeLineColor;
    private Color addLineColor;

    private BasicStroke deleteSegmentStroke;

    public GrahamsView() {
        super();

        this.removeLineColor = new Color(155, 0, 0); //dark red
        this.addLineColor = new Color(0, 155, 0); //dark green

        this.setHighlightLineColor(new Color(255, 206, 22));
        
        float dash1[] = {10.0f};
        this.deleteSegmentStroke = new BasicStroke(4, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
    }


    public void drawEvents(Graphics2D g2d) {
        int eventIndex = this.getEventIndex();
        if (eventIndex < getEventLogSize()) {

            ArrayList<Segment2D> segments = new ArrayList<Segment2D>();

            for (int i = 0; i <= eventIndex; i++) {

                LogEvent logEvent = this.getEventLoItem(i);
                if (i < eventIndex) {
                    if (logEvent instanceof GrahamsScanSegmentAddEvent) {
                        Segment2D s = ((GrahamsScanSegmentAddEvent) logEvent).getSegment();
                        segments.add(s);
                    } else if (logEvent instanceof GrahamsScanSegmentRemoveEvent) {
                        segments.remove(segments.size() - 1);
                    }
                } else {

                    if (logEvent instanceof GrahamsScanSegmentAddEvent) {
                        drawSegments(g2d, segments);
                        Segment2D s = ((GrahamsScanSegmentAddEvent) logEvent).getSegment();
                        drawAddSegment(g2d, s);
                    } else if (logEvent instanceof GrahamsScanSegmentRemoveEvent) {
                        segments.remove(segments.size() - 1);
                        drawSegments(g2d, segments);
                        Segment2D s = ((GrahamsScanSegmentRemoveEvent) logEvent).getSegment();
                        drawRemoveSegment(g2d, s);
                    } else if (logEvent instanceof GrahamsScanSegmentCheckEvent) {
                        drawSegments(g2d, segments);
                        Segment2D s1 = ((GrahamsScanSegmentCheckEvent) logEvent).getSegment1();
                        Segment2D s2 = ((GrahamsScanSegmentCheckEvent) logEvent).getSegment2();
                        drawSegmentsCheck(g2d, s1, s2);
                    }else{
                        drawSegments(g2d, segments);
                    }

                }

            }
        }
    }


    private void drawSegments(Graphics2D g2d, ArrayList<Segment2D> segments) {
        for (Iterator<Segment2D> it = segments.iterator(); it.hasNext();) {
            Segment2D s = it.next();
            drawSegment(g2d, s);
        }
    }

    private void drawAddSegment(Graphics2D g2d, Segment2D segment) {

        g2d.setPaint(addLineColor);
        g2d.setStroke(this.getSegmentStroke());
        Point2D p1 = segment.getStart();
        Point2D p2 = segment.getEnd();
        double x1 = translateX(p1.getX());
        double y1 = translateY(p1.getY());
        double x2 = translateX(p2.getX());
        double y2 = translateY(p2.getY());
        g2d.draw(new Line2D.Double(x1, y1, x2, y2));
    }

    private void drawRemoveSegment(Graphics2D g2d, Segment2D segment) {

        g2d.setPaint(removeLineColor);
        g2d.setStroke(deleteSegmentStroke);
        Point2D p1 = segment.getStart();
        Point2D p2 = segment.getEnd();
        double x1 = translateX(p1.getX());
        double y1 = translateY(p1.getY());
        double x2 = translateX(p2.getX());
        double y2 = translateY(p2.getY());
        g2d.draw(new Line2D.Double(x1, y1, x2, y2));
    }

    private void drawSegmentsCheck(Graphics2D g2d, Segment2D s1, Segment2D s2) {
        highlightSegment(g2d, s1);
        highlightSegment(g2d, s2);
    }

}
