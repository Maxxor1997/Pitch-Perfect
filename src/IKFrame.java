import com.phidgets.*;
import com.phidgets.event.*;
import com.phidgets.InterfaceKitPhidget;
import com.phidgets.PhidgetException;
import com.phidgets.event.SensorChangeEvent;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JOptionPane;


public class IKFrame extends javax.swing.JFrame implements  SensorChangeListener{

    private InterfaceKitPhidget ik;
    private SimulateIKFrame sik;
    private boolean simulateMode=false;
    Note randomNote;
    int pointCounter=0;
    int guessCounter=0;
    int playernum = 0;
    ArrayList<Player> players = new ArrayList<>();
    
    public IKFrame() {
        initComponents();
        setUpIK();
        //askAboutSimulateMode();
        if (simulateMode==true) {
             sik = new SimulateIKFrame(this);
             sik.setVisible(true);
        }
        Tone t = new Tone();
        askPlayers();
        //ayyyyy
        System.out.println("ayyyyyyyyy");
    }

    
    public void setUpIK() {
        try {
            ik = new InterfaceKitPhidget();
            ik.openAny();
        } catch(Exception e) { System.out.println("Couldn't connect to IK"); }
        
        ik.addSensorChangeListener(this);
    }
    
    public void askAboutSimulateMode() {
         int dialogButton = JOptionPane.YES_NO_OPTION;
         int result=JOptionPane.showConfirmDialog (null, "Run in Simulator Mode?","Pop Quiz!",dialogButton);
         System.out.println(result);
         if(result == JOptionPane.YES_OPTION) 
             simulateMode=true;
         else
             simulateMode=false;
    }
    /*this method called by phidget when sensor is changed AND
    called by SimulateIKFrame when slider is changed.
    */
    public void askPlayers(){
        //asks how many players
        while(true) {
            int ayyy = playernum + 1;
            String swag = JOptionPane.showInputDialog("Player" + ayyy + "'s name is?");
            if (swag == null){
                if(playernum ==0){
                    //press cancel with no players = exit
                    System.exit(0);
                }
                break;
            }
            else if (swag.equals("")){
                //if they enter nothing
                System.out.println("You dun goofed");
            }
            else {
                Player x = new Player(swag);
                playernum++;
                players.add(x);
                if (playernum == 4) {
                    break;
                    //ayyylmao
                }
            }
        }
        if (playernum ==0){
            //if there are no players when exiting the loop
            askPlayers();
        }
        
        
    }
            
            
    public void sensorChanged(SensorChangeEvent sensorChangeEvent)
    {
        int index=sensorChangeEvent.getIndex();
        int val=sensorChangeEvent.getValue();
        System.out.println("Sensor " + index + " changed to: " + val);
        //TEST
        inttoNote(val);
        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Guess");
        jButton1.setEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton2.setText("Tone");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setText("Score: 0");

        jLabel2.setText("Guesses:0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addComponent(jButton1)
                        .addGap(34, 34, 34)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(45, 45, 45)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(179, 179, 179)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(145, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(31, 31, 31)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(248, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public Note inttoNote(int val) {
        final AudioFormat af =
            new AudioFormat(Note.SAMPLE_RATE, 8, 1, true, true);
        SourceDataLine line = null;
        try {
            line = AudioSystem.getSourceDataLine(af);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(IKFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            line.open(af, Note.SAMPLE_RATE);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(IKFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        line.start();
        Tone t = new Tone();
        Note matchingNote = null;
        if (val<83){
            t.play(line, Note.A4,1000);
            matchingNote=Note.A4;
        }
        else  if (val<166){
            t.play(line, Note.A4$,1000);
              matchingNote=Note.A4$;
        }
        else  if (val<249){
            t.play(line, Note.B4,1000);
              matchingNote=Note.B4;
        }
        else  if (val<332){
            t.play(line, Note.C4,1000);
              matchingNote=Note.C4;
        }
        else  if (val<415){
            t.play(line, Note.C4$,1000);
              matchingNote=Note.C4$;
        }
        else  if (val<498){
            t.play(line, Note.D4,1000);
              matchingNote=Note.D4;
        }
        else  if (val<581){
            t.play(line, Note.D4$,1000);
              matchingNote=Note.D4$;
        }
        else  if (val<664){
            t.play(line, Note.E4,1000);
              matchingNote=Note.E4;
        }
        else  if (val<747){
            t.play(line, Note.F4,1000);
              matchingNote=Note.F4;
        }
        else  if (val<830){
            t.play(line, Note.F4$,1000);
              matchingNote=Note.F4$;
        }
        else  if (val<913){
            t.play(line, Note.G4,1000);
              matchingNote=Note.G4;
        }
        else  if (val<1000){
            t.play(line, Note.G4$,1000);
              matchingNote=Note.G4$;
        }
        line.drain();
        line.close();
        return matchingNote;
        
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
     
      
      
        int val = readSensor(0);
        guessCounter++;
     
        
        Note matchingNote=inttoNote(val);
        jTextField1.setText(matchingNote.toString());
        
        if(matchingNote.equals(randomNote)){
            pointCounter++;
            jLabel1.setText("Score: " + pointCounter);
            guessCounter=0;
            jLabel2.setText("Guesses: "+Integer.toString(guessCounter));
            jButton2.setEnabled(true);
            jButton1.setEnabled(false);
        }

        if (guessCounter==3){
            System.out.println("YOU LOSE!");
            System.out.println("YOUR SCORE IS:"+pointCounter);

            System.exit(0);
        }
          
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        jButton1.setEnabled(true);
        jButton2.setEnabled(false);
        Random r = new Random();
        Tone t = new Tone();
       final AudioFormat af =
            new AudioFormat(Note.SAMPLE_RATE, 8, 1, true, true);
       
       SourceDataLine line = null;
        try {
            line = AudioSystem.getSourceDataLine(af);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(IKFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            line.open(af, Note.SAMPLE_RATE);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(IKFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        line.start();
        Note[] notes = {Note.A4, Note.A4$, Note.B4, Note.C4, Note.C4$, Note.D4, Note.D4$, Note.E4, Note.F4, Note.F4$, Note.G4, Note.G4$,};
                randomNote = notes[r.nextInt(12)];
                
         t.play(line,randomNote,1000);     
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    public int readSensor(int index) {
        if (simulateMode==true) {
            return(sik.sensorValue[index]);
        }
        
        try {
           return( ik.getSensorValue(index));
        }
        catch(Exception e) { 
           System.out.println("Error reading sensor " + index);
           return(-1); 
        }
        
    }
    
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
            java.util.logging.Logger.getLogger(IKFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IKFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IKFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IKFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IKFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
