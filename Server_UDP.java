/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dos;

import static dos.UDPServer.data;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author rsrah
 */
public class Server_UDP extends javax.swing.JFrame {

    Scanner sc = new Scanner(System.in); 
    DatagramSocket ds; 
    byte[] receive = new byte[65535]; 
    DatagramPacket DpReceive = null; 
    byte buf[] = null;
    InetAddress ip; 
    
    public Server_UDP() throws SocketException, UnknownHostException {
        this.ip = InetAddress.getLocalHost();
        this.ds = new DatagramSocket(1234);
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        MsgRecieveDisp = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        sendText = new javax.swing.JTextField();
        sendToClientBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Server");

        MsgRecieveDisp.setColumns(20);
        MsgRecieveDisp.setRows(5);
        jScrollPane1.setViewportView(MsgRecieveDisp);

        jLabel1.setText("Server Message: ");

        sendToClientBtn.setText("Send");
        sendToClientBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendToClientBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sendText, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(sendToClientBtn)
                        .addGap(0, 6, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(sendText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sendToClientBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sendToClientBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendToClientBtnActionPerformed
        
        if (evt.getSource() == sendToClientBtn){ 
            try{
                String inp = sendText.getText();  
                sendText.setText("");
                if (inp.equals("bye")){ 
                    MsgRecieveDisp.append("Server sent bye.....EXITING\n");
                    setVisible(false); //you can't see me!
                    dispose(); //Destroy the JFrame object
                }
                //receive = new byte[65535];
                MsgRecieveDisp.append("Server:-" + inp + "\n");
                buf = inp.getBytes(); 
                DatagramPacket DpSend = new DatagramPacket(buf, buf.length, ip, 1233);
                ds.send(DpSend);
            }catch(IOException e){}
        }
        
    }//GEN-LAST:event_sendToClientBtnActionPerformed
    
    public static StringBuilder data(byte[] a) 
    { 
        if (a == null) 
            return null; 
        StringBuilder ret = new StringBuilder(); 
        int i = 0; 
        while (a[i] != 0) 
        { 
            ret.append((char) a[i]); 
            i++; 
        } 
        return ret; 
    } 
    
    public static void main(String args[]) throws IOException {
        
        Server_UDP serv = new Server_UDP();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                serv.setVisible(true);                
            }
        });
        while(true){
            serv.DpReceive = new DatagramPacket(serv.receive, serv.receive.length); 
            try{
                serv.ds.receive(serv.DpReceive); 
                
                if (data(serv.receive).toString().equals("bye")) {
                    serv.MsgRecieveDisp.append("Client sent bye.....EXITING\n");
                    //serv.setVisible(false); //you can't see me!
                    serv.dispose(); //Destroy the JFrame object
                    break;
                } 
                else
                    serv.MsgRecieveDisp.append("Client:-" + data(serv.receive) + "\n");
                serv.receive = new byte[65535];
            }catch(IOException e){}
        }
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea MsgRecieveDisp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField sendText;
    private javax.swing.JButton sendToClientBtn;
    // End of variables declaration//GEN-END:variables
}
