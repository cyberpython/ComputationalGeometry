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
import aueb.geom.algorithms.logging.LogEvent;
import aueb.geom.ui.views.EventLogDrivenView;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author cyberpython
 */
public abstract class ConvexHullView extends EventLogDrivenView implements MouseListener {

    private Color pointColor;
    private Color lineColor;
    private Color highlightLineColor;
    private int pointRadius;
    private int pointDim;
    private Stroke segmentStroke;
    private Stroke highlightSegmentStroke;
    private List<Point2D> points;

    public ConvexHullView() {
        super();

        this.pointColor = new Color(117, 139, 255); //light blue
        this.highlightLineColor = new Color(255, 206, 22, 155);// dark yellow
        this.lineColor = new Color(66, 66, 66);// dark grey



        this.pointRadius = 4;
        this.pointDim = 2 * pointRadius;

        this.segmentStroke = new BasicStroke(4);
        this.highlightSegmentStroke = new BasicStroke(4);


        this.points = new ArrayList<Point2D>();

        this.addMouseListener(this);
    }

    public void setPoints(List<Point2D> points) {
        this.clear();
        this.points = points;
        this.repaint();
    }

    public void setHighlightLineColor(Color c){
        this.highlightLineColor = c;
    }

    public void setHighlightStroke(Stroke s){
        this.highlightSegmentStroke = s;
    }

    public void setSegmentStroke(Stroke s){
        this.segmentStroke = s;
    }

    public int getPointRadius(){
        return this.pointRadius;
    }

    public int getPointDim(){
        return this.pointDim;
    }

    public Color getPointColor(){
        return this.pointColor;
    }

    public Color getLineColor(){
        return this.lineColor;
    }

    public Color getHighlightLineColor(){
        return this.highlightLineColor;
    }

    public List<Point2D> getPoints(){
        return this.points;
    }

    public Stroke getHighlightStroke(){
        return this.highlightSegmentStroke;
    }

    public Stroke getSegmentStroke(){
        return this.segmentStroke;
    }

    public void clear() {
        if (!isBlockingInput()) {
            this.setEventLog(new ArrayList<LogEvent>());
            setEventIndex(0);
            this.getPoints().clear();
            this.repaint();
        }
    }

    public void drawBeforeEvents(Graphics2D g2d) {

        
    }

    public void drawAfterEvents(Graphics2D g2d) {
        drawPoints(g2d);
    }

    private void drawPoints(Graphics2D g2d) {
        for (Iterator<Point2D> it = points.iterator(); it.hasNext();) {
            Point2D p = it.next();
            drawPoint(g2d, p);
        }
    }

    private void drawPoint(Graphics2D g2d, Point2D p0) {
        double x;
        double y;

        g2d.setPaint(pointColor);
        x = translateX(p0.getX()) - pointRadius;
        y = translateY(p0.getY()) - pointRadius;
        g2d.fill(new Ellipse2D.Double(x, y, pointDim, pointDim));
    }

    void drawSegment(Graphics2D g2d, Segment2D segment) {

        g2d.setPaint(lineColor);
        g2d.setStroke(segmentStroke);
        Point2D p1 = segment.getStart();
        Point2D p2 = segment.getEnd();
        double x1 = translateX(p1.getX());
        double y1 = translateY(p1.getY());
        double x2 = translateX(p2.getX());
        double y2 = translateY(p2.getY());
        g2d.draw(new Line2D.Double(x1, y1, x2, y2));
    }

    void highlightSegment(Graphics2D g2d, Segment2D segment) {
        g2d.setPaint(highlightLineColor);
        g2d.setStroke(highlightSegmentStroke);

        Point2D p1 = segment.getStart();
        Point2D p2 = segment.getEnd();
        double x1 = translateX(p1.getX());
        double y1 = translateY(p1.getY());
        double x2 = translateX(p2.getX());
        double y2 = translateY(p2.getY());
        g2d.draw(new Line2D.Double(x1, y1, x2, y2));
    }

    // <editor-fold defaultstate="collapsed" desc=" MouseListener Implementation ">
    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        if (!isBlockingInput()) {
            this.points.add(new Point2D.Double(translateX(e.getX()), translateY(e.getY())));
            this.repaint();
        }
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
    // </editor-fold>
}
