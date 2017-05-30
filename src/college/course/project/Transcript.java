/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package college.course.project;

import java.awt.Color;
import java.awt.print.PrinterException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author mattw
 */
public class Transcript extends javax.swing.JFrame {
    
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form Transcript
     */
    public Transcript() {
        initComponents();
        conn = Database.java_db();
        this.getContentPane().setBackground(Color.blue);
        currentDateAndTime();
        showTable();
    }
    
    private void currentDateAndTime() {
        Calendar calendar = new GregorianCalendar();
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        menu_date.setText((month + 1) + "/" + day + "/" + year);
        
        Date date = new Date();
        SimpleDateFormat s = new SimpleDateFormat("hh:mm:ss a");
        menu_time.setText(s.format(date));
    }
    
    private void showTable() {
        String sql1 = "SELECT course.course_short, course.credits, course.term, course.credits, schedule.gpa FROM course INNER JOIN schedule ON course.course_id = schedule.course_id WHERE schedule.student_id = (?) ORDER BY course.term";
        String sql2 = "SELECT * FROM student WHERE student_id = (?)";
        String sql3 = "SELECT sum(schedule.credits), avg(schedule.gpa) FROM schedule WHERE schedule.student_id = (?)";
        
        try {
            pst = conn.prepareStatement(sql1);
            pst.setInt(1, College.student_id);
            rs = pst.executeQuery();
            table_transcript.setModel(DbUtils.resultSetToTableModel(rs));
            
            pst = conn.prepareStatement(sql2);
            pst.setInt(1, College.student_id);
            rs = pst.executeQuery();

            while (rs.next()) {
                String firstname = rs.getString("first_name");
                String lastname = rs.getString("last_name");
                txt_fullname.setText(firstname + " " + lastname);
                String student_class = rs.getString("class");
                txt_class.setText(student_class);
                String major = rs.getString("major");
                txt_major.setText(major);
            }
            
            pst = conn.prepareStatement(sql3);
            pst.setInt(1, College.student_id);
            rs = pst.executeQuery();
            
            while (rs.next()) {
                int credit = rs.getInt("sum(schedule.credits)");
                txt_credits.setText(String.valueOf(credit));
                double avg = rs.getDouble("avg(schedule.gpa)");
                avg = Math.round(avg * 10.0) / 10.0;
                txt_gpa.setText(String.valueOf(avg));
                
                double sum_credit = ((double)credit / 120) * 100;
                int credit_sum = (int)sum_credit;
                progress_credits.setValue(credit_sum);
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        table_transcript = new javax.swing.JTable();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        txt_fullname = new javax.swing.JTextField();
        txt_class = new javax.swing.JTextField();
        txt_credits = new javax.swing.JTextField();
        txt_gpa = new javax.swing.JTextField();
        txt_major = new javax.swing.JTextField();
        lbl_fullname = new javax.swing.JLabel();
        lbl_class = new javax.swing.JLabel();
        lbl_major = new javax.swing.JLabel();
        lbl_credits = new javax.swing.JLabel();
        lbl_gpa = new javax.swing.JLabel();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        progress_credits = new javax.swing.JProgressBar();
        jMenuBar1 = new javax.swing.JMenuBar();
        menu_students = new javax.swing.JMenu();
        item_registration = new javax.swing.JMenuItem();
        item_schedule = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        item_print = new javax.swing.JMenuItem();
        item_logout = new javax.swing.JMenuItem();
        item_close = new javax.swing.JMenuItem();
        menu_date = new javax.swing.JMenu();
        menu_time = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        table_transcript.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(table_transcript);

        jLayeredPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Unofficial Transcript", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(255, 255, 255))); // NOI18N

        txt_fullname.setEditable(false);

        txt_class.setEditable(false);

        txt_credits.setEditable(false);

        txt_gpa.setEditable(false);

        txt_major.setEditable(false);

        lbl_fullname.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lbl_fullname.setForeground(new java.awt.Color(255, 255, 255));
        lbl_fullname.setText("Full Name");

        lbl_class.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lbl_class.setForeground(new java.awt.Color(255, 255, 255));
        lbl_class.setText("Class");

        lbl_major.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lbl_major.setForeground(new java.awt.Color(255, 255, 255));
        lbl_major.setText("Major");

        lbl_credits.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lbl_credits.setForeground(new java.awt.Color(255, 255, 255));
        lbl_credits.setText("SUM HRS");

        lbl_gpa.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lbl_gpa.setForeground(new java.awt.Color(255, 255, 255));
        lbl_gpa.setText("GPA");

        jLayeredPane1.setLayer(txt_fullname, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(txt_class, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(txt_credits, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(txt_gpa, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(txt_major, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lbl_fullname, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lbl_class, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lbl_major, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lbl_credits, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lbl_gpa, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(lbl_fullname)
                        .addGap(18, 18, 18)
                        .addComponent(txt_fullname))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(lbl_class)
                        .addGap(57, 57, 57)
                        .addComponent(txt_class, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(lbl_major)
                        .addGap(52, 52, 52)
                        .addComponent(txt_major))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(lbl_credits)
                        .addGap(25, 25, 25)
                        .addComponent(txt_credits))
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addComponent(lbl_gpa)
                        .addGap(64, 64, 64)
                        .addComponent(txt_gpa)))
                .addContainerGap())
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_fullname)
                    .addComponent(txt_fullname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_class)
                    .addComponent(txt_class, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_major)
                    .addComponent(txt_major, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_credits)
                    .addComponent(txt_credits, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_gpa)
                    .addComponent(txt_gpa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLayeredPane2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Road to Graduation", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), new java.awt.Color(255, 255, 255))); // NOI18N

        progress_credits.setStringPainted(true);

        jLayeredPane2.setLayer(progress_credits, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane2Layout = new javax.swing.GroupLayout(jLayeredPane2);
        jLayeredPane2.setLayout(jLayeredPane2Layout);
        jLayeredPane2Layout.setHorizontalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(progress_credits, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jLayeredPane2Layout.setVerticalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(progress_credits, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        menu_students.setText("Students");

        item_registration.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        item_registration.setText("Course Registration");
        item_registration.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_registrationActionPerformed(evt);
            }
        });
        menu_students.add(item_registration);

        item_schedule.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        item_schedule.setText("Class Schedule");
        item_schedule.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_scheduleActionPerformed(evt);
            }
        });
        menu_students.add(item_schedule);
        menu_students.add(jSeparator1);

        item_print.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        item_print.setText("Print");
        item_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_printActionPerformed(evt);
            }
        });
        menu_students.add(item_print);

        item_logout.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        item_logout.setText("Logout");
        item_logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_logoutActionPerformed(evt);
            }
        });
        menu_students.add(item_logout);

        item_close.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        item_close.setText("Close");
        item_close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_closeActionPerformed(evt);
            }
        });
        menu_students.add(item_close);

        jMenuBar1.add(menu_students);

        menu_date.setText("Date");
        jMenuBar1.add(menu_date);

        menu_time.setText("Time");
        jMenuBar1.add(menu_time);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLayeredPane2)
                    .addComponent(jLayeredPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(49, 49, 49))
        );

        setSize(new java.awt.Dimension(827, 539));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void item_registrationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_registrationActionPerformed
        Courses courses = new Courses();
        courses.setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_item_registrationActionPerformed

    private void item_scheduleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_scheduleActionPerformed
        Schedule schedule = new Schedule();
        schedule.setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_item_scheduleActionPerformed

    private void item_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_printActionPerformed
        MessageFormat header = new MessageFormat("Unofficial Transcript");
        
        MessageFormat footer = new MessageFormat("Page{0, number, integer}");
        
        try {
            table_transcript.print(JTable.PrintMode.NORMAL, header, footer);
        } catch (PrinterException e) {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_item_printActionPerformed

    private void item_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_logoutActionPerformed
        Login login = new Login();
        login.setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_item_logoutActionPerformed

    private void item_closeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_closeActionPerformed
        System.exit(0);
    }//GEN-LAST:event_item_closeActionPerformed

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
            java.util.logging.Logger.getLogger(Transcript.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Transcript.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Transcript.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Transcript.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Transcript().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem item_close;
    private javax.swing.JMenuItem item_logout;
    private javax.swing.JMenuItem item_print;
    private javax.swing.JMenuItem item_registration;
    private javax.swing.JMenuItem item_schedule;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JLabel lbl_class;
    private javax.swing.JLabel lbl_credits;
    private javax.swing.JLabel lbl_fullname;
    private javax.swing.JLabel lbl_gpa;
    private javax.swing.JLabel lbl_major;
    private javax.swing.JMenu menu_date;
    private javax.swing.JMenu menu_students;
    private javax.swing.JMenu menu_time;
    private javax.swing.JProgressBar progress_credits;
    private javax.swing.JTable table_transcript;
    private javax.swing.JTextField txt_class;
    private javax.swing.JTextField txt_credits;
    private javax.swing.JTextField txt_fullname;
    private javax.swing.JTextField txt_gpa;
    private javax.swing.JTextField txt_major;
    // End of variables declaration//GEN-END:variables
}
