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

/*
 * ComputationalGeometryPanel.java
 *
 * Created on Jun 27, 2010, 2:14:21 PM
 */

package aueb.geom.ui;

/**
 *
 * @author cyberpython
 */
public class ComputationalGeometryPanel extends javax.swing.JPanel {

    /** Creates new form ComputationalGeometryPanel */
    public ComputationalGeometryPanel() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        intersectionsPanel1 = new aueb.geom.ui.panels.IntersectionsPanel();
        jarvisPanel1 = new aueb.geom.ui.panels.JarvisPanel();
        grahamsPanel1 = new aueb.geom.ui.panels.GrahamsPanel();

        jTabbedPane1.addTab("Intersections", intersectionsPanel1);
        jTabbedPane1.addTab("Jarvis' March", jarvisPanel1);
        jTabbedPane1.addTab("Graham's Scan", grahamsPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private aueb.geom.ui.panels.GrahamsPanel grahamsPanel1;
    private aueb.geom.ui.panels.IntersectionsPanel intersectionsPanel1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private aueb.geom.ui.panels.JarvisPanel jarvisPanel1;
    // End of variables declaration//GEN-END:variables

}
