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
import aueb.geom.algorithms.logging.convex_hull.jarvis_march.JarvisAddSegmentEvent;
import aueb.geom.algorithms.logging.convex_hull.jarvis_march.JarvisChainsDetectedEvent;
import aueb.geom.algorithms.logging.convex_hull.jarvis_march.JarvisPointSelectedEvent;
import aueb.geom.algorithms.logging.convex_hull.jarvis_march.JarvisPointsCheckEvent;
import aueb.geom.algorithms.logging.LogEvent;
import aueb.geom.algorithms.logging.PermanentLogEvent;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.Iterator;

/**
 *
 * @author cyberpython
 */
public class JarvisView extends ConvexHullView{

    private Color chainSeparatorColor;
    private Color testAngleColor;
    private Color selectedAngleColor;

    private BasicStroke chainSeparator;
    

    public JarvisView() {
        super();

        this.selectedAngleColor = new Color(28, 53, 130);
        this.testAngleColor = new Color(230, 0, 0, 155); //red with alpha
        this.chainSeparatorColor = new Color(155, 0, 0, 155); //dark red with alpha

        float dash1[] = {10.0f};
        this.chainSeparator = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);

        setHighlightStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f));


    }
    

    public void drawEvents(Graphics2D g2d) {
        int eventIndex = getEventIndex();
        if (eventIndex < getEventLogSize()) {
            int i = 0;
            for (Iterator<LogEvent> it = getEventLogIterator(); it.hasNext();) {
                LogEvent logEvent = it.next();
                if (i <= eventIndex) {
                    if (logEvent instanceof PermanentLogEvent) {
                        if (logEvent instanceof JarvisChainsDetectedEvent) {
                            drawChainSeparator(g2d, ((JarvisChainsDetectedEvent) logEvent).getSegment());
                        } else if (logEvent instanceof JarvisAddSegmentEvent) {
                            drawSegment(g2d, ((JarvisAddSegmentEvent) logEvent).getSegment());
                        }
                    } else {
                        if (i == eventIndex) {
                            if (logEvent instanceof JarvisPointSelectedEvent) {
                                drawAngleCheck(g2d, (JarvisPointSelectedEvent) logEvent, true);
                            } else if (logEvent instanceof JarvisPointsCheckEvent) {
                                drawAngleCheck(g2d, (JarvisPointsCheckEvent) logEvent, false);
                            }
                        }
                    }
                }
                i++;
            }
        }
    }

    private void drawChainSeparator(Graphics2D g2d, Segment2D segment) {
        g2d.setPaint(chainSeparatorColor);
        g2d.setStroke(chainSeparator);
        Point2D p1 = segment.getStart();
        Point2D p2 = segment.getEnd();
        double x1 = translateX(p1.getX());
        double y1 = translateY(p1.getY());
        double x2 = translateX(p2.getX());
        double y2 = translateY(p2.getY());
        g2d.draw(new Line2D.Double(x1, y1, x2, y2));
    }

    private void drawAngleCheck(Graphics2D g2d, JarvisPointsCheckEvent evt, boolean selected) {
        boolean rightChain = evt.isRightChainEvent();
        Point2D refPoint = evt.getReferencePoint();
        Point2D candidatePoint = evt.getPoint();
        Double angle = evt.getPolarAngle();

        if (angle == null) {
            return;
        }

        double start = 0;
        if (!rightChain) {
            start = 180;
        }
        Arc2D arc = new Arc2D.Double(translateX(refPoint.getX()) - 25, translateY(refPoint.getY()) - 25, 50, 50, start, angle, Arc2D.PIE);
        if (selected) {
            g2d.setPaint(selectedAngleColor);
        } else {
            g2d.setPaint(testAngleColor);
        }
        g2d.fill(arc);

        if (rightChain) {
            highlightSegment(g2d, new Segment2D(refPoint, new Point2D.Double(this.getWidth(), refPoint.getY())));
        } else {
            highlightSegment(g2d, new Segment2D(refPoint, new Point2D.Double(0, refPoint.getY())));
        }
        highlightSegment(g2d, new Segment2D(refPoint, candidatePoint));
    }
    
}
