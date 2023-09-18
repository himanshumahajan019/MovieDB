
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONObject;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author Pratham Saroch
 */
public class UpcomingMovies extends javax.swing.JFrame {

    public UpcomingMovies() {
        initComponents();
//        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
//        int width = d.width;
//        int height = d.height;
        setSize(950, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        getContentPane().setBackground(Color.BLACK);

        getInfoOfUpcomingMovies();
    }

    void getInfoOfUpcomingMovies() {
        jPanel1.removeAll();
        int x = 10;
        String ans = MyClient.UpcomingMovies();
        System.out.println("Original JSON " + ans);

        JSONObject mainobj = new JSONObject(ans);

        JSONArray arr = mainobj.getJSONArray("results");

        System.out.println("arr " + arr);

        for (int i = 0; i < arr.length(); i++) {
            JSONObject subobj = (JSONObject) (arr.get(i));

            String oname = subobj.get("original_title").toString();
            String photo = subobj.get("poster_path").toString();
            String overviewString = subobj.get("overview").toString();

            String overview = "";

            String[] n = overviewString.split("(?<=\\G.{" + 39 + "})");
            for (String string : n) {
                overview += string + " " + "\n" + " ";
            }

            int id = subobj.getInt("id");
            int vote = (int) subobj.get("vote_count");

            String release_date = subobj.get("release_date").toString();

            UpcomingMovieDesign2 obj = new UpcomingMovieDesign2();
            obj.setSize(100, 350);
            obj.jLabel2.setText(oname);
            obj.jLabel3.setText(release_date);
            obj.jTextArea1.setText(overview);

            String res = MyClient.checkButton(id);
            if (res.equals("success")) {
                obj.jButton1.setText("Added");
            }

            obj.jProgressBar1.setString("Votes : " + vote);
            obj.jProgressBar1.setValue(vote);

            try {

//                ImageIcon ic = new ImageIcon("C:\\Users\\Pratham Saroch\\Documents\\NetBeansProjects\\MovieDB_2023\\src\\myuploads\\PathanPoster.jpg");
//                Image img = ic.getImage().getScaledInstance(jLabel1.getWidth(), jLabel1.getHeight(), Image.SCALE_SMOOTH);
//                obj.jLabel1.setIcon(new ImageIcon(img));
                BufferedImage bi = ImageIO.read(new URL("https://image.tmdb.org/t/p/w200" + photo));

                bi = scale(bi, 160, 130);

                obj.jLabel1.setIcon(new ImageIcon(bi));

            } catch (Exception e) {
                e.toString();
            }

            obj.jButton1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    String ans = MyClient.addUpcomingToFav(id);

                    if (ans.equals("add")) {
                        obj.jButton1.setText("Added");
                    } else {
                        obj.jButton1.setText("Add");
                    }
                }
            });

            obj.setBounds(x, 10, 270, 350);
            x = x + 370;
            jPanel1.add(obj);
            jPanel1.repaint();
            obj.repaint();
        }

        jPanel1.setPreferredSize(new Dimension(370 * arr.length(), 470));
    }

    public static BufferedImage scale(BufferedImage src, int w, int h) {
        BufferedImage img
                = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        int x, y;
        int ww = src.getWidth();
        int hh = src.getHeight();
        int[] ys = new int[h];
        for (y = 0; y < h; y++) {
            ys[y] = y * hh / h;
        }
        for (x = 0; x < w; x++) {
            int newX = x * ww / w;
            for (y = 0; y < h; y++) {
                int col = src.getRGB(newX, ys[y]);
                img.setRGB(x, y, col);
            }
        }
        return img;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Upcoming Movies");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(330, 10, 310, 50);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 908, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 438, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanel1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(30, 80, 870, 400);

        jButton1.setText("Back");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(20, 10, 80, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        Welcome obj = new Welcome();
        obj.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UpcomingMovies.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UpcomingMovies.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UpcomingMovies.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UpcomingMovies.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UpcomingMovies().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
