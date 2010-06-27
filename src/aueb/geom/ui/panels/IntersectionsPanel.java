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

package aueb.geom.ui.panels;

import aueb.geom.algorithms.Intersections;
import aueb.geom.algorithms.intersections.Segment2DEx;
import aueb.geom.algorithms.logging.LogEvent;
import aueb.geom.ui.views.intersections.IntersectionsView;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cyberpython
 */
public class IntersectionsPanel extends EventLogDrivenViewPanel {

    public IntersectionsPanel() {
        super();
        setView(new IntersectionsView());
        setRandomDataLabelText("random segments");
    }

    public void initEvents(List<LogEvent> events) {
        IntersectionsView view = (IntersectionsView) getView();
        if (view != null) {
            Intersections.bentleyOttmann(view.getSegments(), events);
        }
    }

    public void generateRandomData(int count) {
        IntersectionsView view = (IntersectionsView) getView();
        if (view != null) {
            view.clear();
            List<Segment2DEx> setA = new ArrayList<Segment2DEx>();
            int w = view.getWidth();
            int h = view.getHeight();
            while (setA.size() < count) {
                int x1 = (int) (Math.random() * w) + 1;
                int x2 = (int) (Math.random() * w) + 1;
                if (Math.abs(x2 - x1) > 40) {
                    int y1 = (int) (Math.random() * h) + 1;
                    int y2 = (int) (Math.random() * h) + 1;
                    if (Math.abs(y2 - y1) > 10) {
                        setA.add(new Segment2DEx(new Point2D.Double(x1, y1), new Point2D.Double(x2, y2)));
                    }
                }
            }

            view.setSegments(setA);
        }
    }
}
