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

package aueb.geom.ui.views;

import aueb.geom.algorithms.logging.LogEvent;
import aueb.geom.ui.auto.ProgressiveView;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author cyberpython
 */
public abstract class EventLogDrivenView extends JPanel implements ProgressiveView{

    private Color borderColor;
    private Color bgColor1;
    private Color bgColor2;

    private BasicStroke borderStroke;

    private List<LogEvent> eventLog;
    private int eventIndex;

    private boolean isInputBlocked;
    private boolean painting;

    public EventLogDrivenView(){
        super();

        this.borderColor = new Color(0, 0, 0);
        this.bgColor1 = new Color(255, 255, 255);
        this.bgColor2 = new Color(230, 230, 230);

        this.borderStroke = new BasicStroke(1);

        this.eventLog = new ArrayList<LogEvent>();
        this.eventIndex = 0;

        this.isInputBlocked = false;
        this.painting = false;
    }

    public void setEventLog(List<LogEvent> eventLog) {
        if (!isInputBlocked) {
            this.eventLog = eventLog;
            this.eventIndex = 0;
            this.repaint();
        }
    }

    public void setEventIndex(int index){
        this.eventIndex = index;
    }

    public int getEventIndex(){
        return this.eventIndex;
    }

    public int getEventLogSize(){
        return this.eventLog.size();
    }

    public LogEvent getEventLoItem(int index){
        return this.eventLog.get(index);
    }

    public Iterator<LogEvent> getEventLogIterator(){
        return this.eventLog.iterator();
    }

    public void goToEnd() {
        this.eventIndex = this.eventLog.size() - 1;
        this.repaint();
    }

    public void next() {
        if (!painting) {
            if (this.eventIndex < this.eventLog.size() - 1) {
                this.eventIndex++;
            }
            this.repaint();
        }
    }

    public boolean hasNext() {
        return eventIndex < eventLog.size() - 1;
    }

    public boolean isBlockingInput() {
        return this.isInputBlocked;
    }

    public boolean canProceed() {
        return !this.painting;
    }

    public void setBlockInput(boolean block) {
        this.isInputBlocked = block;
    }

    @Override
    protected void paintComponent(Graphics arg0) {
        this.painting = true;
        super.paintComponent(arg0);
        this.draw(arg0);
        this.painting = false;
    }

    private void draw(Graphics g){

        int width = this.getWidth();
        int height = this.getHeight();

        BufferedImage bi = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) bi.getGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        fillBgWithGradient(g2d, width, height);

        drawBeforeEvents(g2d);
        drawEvents(g2d);
        drawAfterEvents(g2d);

        drawBorder(g2d, width, height);

        Graphics2D g2d2 = (Graphics2D) g;
        g2d2.drawImage(bi, null, 0, 0);

        g2d.dispose();
        g2d2.dispose();

    }

    private void drawBorder(Graphics2D g2d, int width, int height) {
        g2d.setStroke(borderStroke);
        g2d.setPaint(borderColor);
        g2d.drawRect(0, 0, width - 1, height - 1);
    }

    private void fillBgWithGradient(Graphics2D g2d, int width, int height) {
        g2d.setBackground(bgColor1);
        GradientPaint bgColor = new GradientPaint(0, (float) (height * 0.5), bgColor1, 0, (float) (height * 1.25), bgColor2);
        g2d.setPaint(bgColor);
        g2d.fillRect(0, 0, width, height);
    }

    public double translateX(double x) {
        return x;
    }

    public double translateY(double y) {
        double maxY = this.getHeight();
        return maxY - y;
    }

    public abstract void clear();

    public abstract void drawBeforeEvents(Graphics2D g2d);
    public abstract void drawEvents(Graphics2D g2d);
    public abstract void drawAfterEvents(Graphics2D g2d);



}
