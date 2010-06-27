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

import aueb.geom.algorithms.ConvexHull;
import aueb.geom.algorithms.logging.LogEvent;
import aueb.geom.ui.views.convex_hull.ConvexHullView;
import aueb.geom.ui.views.convex_hull.JarvisView;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cyberpython
 */
public class JarvisPanel extends EventLogDrivenViewPanel{
    
    public JarvisPanel(){
        super();
        setView(new JarvisView());
        setRandomDataLabelText("random points");
    }

    public void initEvents(List<LogEvent> events){
        ConvexHullView view = (ConvexHullView) getView();
        if(view!=null){
            ConvexHull.jarvisMarch(view.getPoints(), events);
        }
    }

    public void generateRandomData(int count){
        ConvexHullView view = (ConvexHullView) getView();
        if(view!=null){
            view.clear();
            List<Point2D> setA = new ArrayList<Point2D>();
            int w = view.getWidth()-20;
            int h = view.getHeight()-20;
            while(setA.size()<count){
                int x1 = (int) (Math.random()*w) + 10;
                int y1 = (int) (Math.random()*h) + 10;
                Point2D p = new Point(x1, y1);
                if(!setA.contains(p)){
                    setA.add(p);
                }
            }

            view.setPoints(setA);
        }
    }

}
