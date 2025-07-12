import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ToJarFrame
  extends JFrame
{
  private JFileChooser jfc;
  private JLabel addrLabel;
  private JTextField address;
  private JButton compile;
  private JButton jButton1;
  private JTextField outputtarget;
  private JProgressBar progress;
  private JButton select;
  private JLabel targetLabel;
  private JTextField title;
  private JLabel titleLabel;
  
  public ToJarFrame() {
    this.jfc = new JFileChooser();
    initComponents();
    this.outputtarget.setText(System.getProperty("user.home") + System.getProperty("file.separator") + "ScratchBugOnAPlate.jar");
  } 
  
  private void selectActionPerformed(ActionEvent evt) { 
    this.jfc.showSaveDialog(this);
    File selected = this.jfc.getSelectedFile();
    if (selected != null) {
      this.outputtarget.setText(selected.getAbsolutePath());
    } 
  }

  private void compileActionPerformed(ActionEvent evt) {
    this.progress.setIndeterminate(true);
    this.outputtarget.setEditable(false);
    try {
      ZipOutputStream out = new ZipOutputStream(new FileOutputStream(this.outputtarget.getText()));
      ZipInputStream in = new ZipInputStream(ToJarFrame.class.getResourceAsStream("ScratchDesktop.dat"));
      
      byte[] buf = new byte[1024];
      ZipEntry entry;
      while ((entry = in.getNextEntry()) != null) {
        out.putNextEntry(new ZipEntry(entry.getName()));
        if (!entry.getName().equals("config.xml") && !entry.getName().equals("project.dat")) {
          int len; 
          while ((len = in.read(buf)) != -1) {
            out.write(buf, 0, len);
          }
        }
        else if (entry.getName().equals("project.dat")) {
          FileInputStream fis = new FileInputStream(this.address.getText()); int len;
          while ((len = fis.read(buf)) != -1) {
            out.write(buf, 0, len);
          }
          fis.close();
        } else {
          Properties p = new Properties();
          p.setProperty("title", this.title.getText());
          p.setProperty("project", "project.dat");
          p.setProperty("autostart", "true");
          p.setProperty("compiler-version", "1.1");
          p.setProperty("compiler-time", System.currentTimeMillis() + "");
          p.storeToXML(out, null);
        } 
        out.closeEntry();
        in.closeEntry();
      } 
      in.close();
      out.close();
      JOptionPane.showMessageDialog(this, "sucessfully created:\n" + this.outputtarget.getText());
      Runtime.getRuntime().exec(new String[] { "java", "-jar", this.outputtarget.getText() });
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(this, "Error: " + ex, "Error", 0);
    } 
    this.outputtarget.setEditable(true);
    this.progress.setIndeterminate(false); 
  }
  
  private void initComponents() { 
      this.addrLabel = new JLabel(); this.titleLabel = new JLabel(); this.address = new JTextField(); this.title = new JTextField(); this.compile = new JButton(); this.targetLabel = new JLabel(); this.outputtarget = new JTextField(); this.select = new JButton(); this.progress = new JProgressBar(); this.jButton1 = new JButton(); setDefaultCloseOperation(3); setTitle("SB to JAR Converter"); setLocationByPlatform(true); setResizable(false); this.addrLabel.setText("Project:"); this.titleLabel.setText("Title:"); this.address.setText("C:/scratch-project.sb"); this.title.setText("Bug on a plate by bernatp"); this.compile.setText("Convert"); this.compile.addActionListener(new ActionListener() 
      { 
          public void actionPerformed(ActionEvent evt) { 
              ToJarFrame.this.compileActionPerformed(evt); 
            } 
        }
      ); 
      this.targetLabel.setText("Target:"); this.outputtarget.setText("C:/"); this.select.setText("Select"); this.select.addActionListener(new ActionListener() 
      { 
          public void actionPerformed(ActionEvent evt) { 
              ToJarFrame.this.selectActionPerformed(evt); 
            } 
        }
      ); 
      this.jButton1.setText("Select"); this.jButton1.addActionListener(new ActionListener() 
      {
          public void actionPerformed(ActionEvent evt) { 
              ToJarFrame.this.jButton1ActionPerformed(evt); 
            }
        }
      ); 
      GroupLayout layout = new GroupLayout(getContentPane()); getContentPane().setLayout(layout); layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.addrLabel).addComponent(this.titleLabel, -1, 38, 32767).addComponent(this.targetLabel)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addComponent(this.progress, -1, 441, 32767).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.compile)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addComponent(this.outputtarget, -1, 449, 32767).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.select)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.title, GroupLayout.Alignment.LEADING, -1, 449, 32767).addComponent(this.address, -1, 449, 32767)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jButton1))).addContainerGap())); layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.addrLabel).addComponent(this.address, -2, -1, -2).addComponent(this.jButton1)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.title, -2, -1, -2).addComponent(this.titleLabel)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.select).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.outputtarget, -2, -1, -2).addComponent(this.targetLabel))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.compile).addComponent(this.progress, -2, -1, -2)).addContainerGap(19, 32767))); 
      pack(); 
    } 
  
  private void jButton1ActionPerformed(ActionEvent evt) { 
      this.jfc.showOpenDialog(this);
      if (this.jfc.getSelectedFile() != null) {
          this.address.setText(this.jfc.getSelectedFile().getAbsolutePath());
        } 
    }

  private boolean isInteger(String str) {
    try {
      Integer.valueOf(str);
      return true;
    } catch (NumberFormatException ex) {
      return false;
    } 
  }

  public static void main(String[] args) {
    try {
      for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          UIManager.setLookAndFeel(info.getClassName());
          break;
        } 
      } 
    } catch (ClassNotFoundException ex) {
      Logger.getLogger(ToJarFrame.class.getName()).log(Level.SEVERE, (String)null, ex);
    } catch (InstantiationException ex) {
      Logger.getLogger(ToJarFrame.class.getName()).log(Level.SEVERE, (String)null, ex);
    } catch (IllegalAccessException ex) {
      Logger.getLogger(ToJarFrame.class.getName()).log(Level.SEVERE, (String)null, ex);
    } catch (UnsupportedLookAndFeelException ex) {
      Logger.getLogger(ToJarFrame.class.getName()).log(Level.SEVERE, (String)null, ex);
    } 
    EventQueue.invokeLater(new Runnable()
    {
        public void run() {
            (new ToJarFrame()).setVisible(true);
        }
    }
    );
  }
}