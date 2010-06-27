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

package aueb.geom.algorithms.logging.convex_hull.jarvis_march;

import aueb.geom.Segment2D;
import aueb.geom.algorithms.logging.PermanentLogEvent;
import java.awt.geom.Point2D;

/**
 *
 * @author cyberpython
 */
public class JarvisAddSegmentEvent extends PermanentLogEvent{

    Segment2D s;

    public JarvisAddSegmentEvent(Point2D p0, Point2D p1){
        this.s = new Segment2D(p0, p1);
    }

    public JarvisAddSegmentEvent(Segment2D s){
        this.s = s;
    }

    public Segment2D getSegment(){
        return s;
    }

}
