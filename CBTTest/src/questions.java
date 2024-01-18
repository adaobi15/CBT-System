
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Statement;
import javax.swing.JLabel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.Timer;
import java.util.concurrent.TimeUnit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.sql.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author adaobi
 */
public class questions extends javax.swing.JFrame {

    private String email;
    private String matno;

    List<List<String>> questionsList = new ArrayList<>();

    //list decalration for questions,optons and answer
    List<String> questions = new ArrayList<>();

    List<String> optionA = new ArrayList<>();

    List<String> optionB = new ArrayList<>();

    List<String> optionC = new ArrayList<>();

    List<String> optionD = new ArrayList<>();

    List<String> answer = new ArrayList<>();

    List<String> studentAnswer = new ArrayList<>(); //this should be filled up with empty strings

    String yourOption = "";

    int i = 0;
    int score = 0;
    int m = 1; //for question number
    //   String matno;
//    String email;
    int duration;
    int numberOfQuestions;

    //int time = Integer.parseInt(new settings().jComboBox1.getSelectedItem().toString());

    /**
     * Creates new form questions
     */
    public class time implements Runnable {

//        int hours = 0;
//int minutes = duration;
//       int seconds = 0;

       public void run() {

            int totalSeconds = 300;
            while (totalSeconds > 0) {
                int remainingHours = totalSeconds / 3600;
                int remainingMinutes = (totalSeconds % 3600) / 60;
                int remainingSeconds = totalSeconds % 60;
              String formattedTime = String.format("Time Remaining: %02d:%02d:%02d:%n", remainingHours, remainingMinutes, remainingSeconds);
                jLabel1.setText(formattedTime);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                totalSeconds--;
            }
            String formattedTime = String.format("Time Remaining: %02d:%02d:%02d%n", 0, 0, 0);
            jLabel1.setText(formattedTime);
            JOptionPane.showMessageDialog(rootPane, "Time Up!! ", "Time Up!!", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public questions(String email, String matno) {
        // Existing constructor code
        initComponents();
//      System.out.println(time);
        time t = new time();
        Thread t1 = new Thread(t);
        t1.start();

        this.email = email;
        this.matno = matno;

        jLabel3.setText(email);
        
           try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/cbt";
            String username = "root";
            String password = "Nkoli1510";

            // Using try-with-resources to handle connection, statement, and result set
            try (Connection con = DriverManager.getConnection(url, username, password);
                 PreparedStatement st = con.prepareStatement("SELECT * FROM duration");
                 ResultSet rs = st.executeQuery()) {

                // Assuming your query returns a single value
                if (rs.next()) {
                    String sduration = rs.getString(1); // Assuming it's an integer; adjust accordingly
                    // If it's a String, use duration = rs.getString(1);
                    duration = Integer.parseInt(sduration);
                }

                // Do something with the retrieved duration value
                System.out.println("Duration: " + duration);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle exceptions appropriately in your application
        }
           
           
            try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/cbt";
            String username = "root";
            String password = "Nkoli1510";

            // Using try-with-resources to handle connection, statement, and result set
            try (Connection con = DriverManager.getConnection(url, username, password);
                 PreparedStatement st = con.prepareStatement("SELECT * FROM questions ");
                 ResultSet rs = st.executeQuery()) {

                // Assuming your query returns a single value
                if (rs.next()) {
                   // String sduration = rs.getString(1); // Assuming it's an integer; adjust accordingly
                    // If it's a String, use duration = rs.getString(1);
                   numberOfQuestions = Integer.parseInt(rs.getString(1));
                }

                // Do something with the retrieved duration value
                System.out.println("no of questions: " + numberOfQuestions);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle exceptions appropriately in your application
        }
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cbt", "root", "Nkoli1510");
//
//            String fetchNextQuery = "SELECT Question, A, B, C, D, answer FROM cbtquestions LIMIT 1 OFFSET ?";
//            try (PreparedStatement fetchNextStatement = con.prepareStatement(fetchNextQuery)) {
//                fetchNextStatement.setInt(1, currentQuestionIndex);
//
//                resultSet = fetchNextStatement.executeQuery();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        } catch (ClassNotFoundException | SQLException ex) {
//            ex.printStackTrace();
//        }
        //part that gets the questions and all and stores in an array
        try {

            
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cbt", "root", "Nkoli1510");

            // Create a statement with a query to select a given amount of questions randomly
            String query = "SELECT Question, A, B, C, D, answer FROM    cbtquestions ORDER BY RAND() LIMIT ?";

            // Adjust the number in setInt(1, numberOfQuestions) to the desired number of questions
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, numberOfQuestions); //"numberofquestions" executes the LIMIT stuff. basically limits/regulates the amount of questions

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Process the results and store them in the array
            while (resultSet.next()) {
                String value = resultSet.getString("Question");
                String value1 = resultSet.getString("A");
                String value2 = resultSet.getString("B");
                String value3 = resultSet.getString("C");
                String value4 = resultSet.getString("D");
                String value5 = resultSet.getString("answer");

                questions.add(value);
                optionA.add(value1);
                optionB.add(value2);
                optionC.add(value3);
                optionD.add(value4);
                answer.add(value5);
            }

            // Close resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //sets first set of questions
        jTextArea1.setText(questions.get(i));
        jRadioButton1.setText(optionA.get(i));
        jRadioButton2.setText(optionB.get(i));
        jRadioButton3.setText(optionC.get(i));
        jRadioButton4.setText(optionD.get(i));

        //dependent on whatever the question size is
        //the iterator here which was i before was conflicting with the initial i, so it was changed to j
        for (int j = 0; j < questions.size(); j++) {
            studentAnswer.add("n/a"); // Add an empty string for each question
        }
    }

//    public void fetchNextQuestionsFromDatabase() {
//
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cbt", "root", "Nkoli1510");
//
//            String fetchNextQuery = "SELECT Question, A, B, C, D, answer FROM cbtquestions LIMIT 1 OFFSET ?";
//            try (PreparedStatement fetchNextStatement = con.prepareStatement(fetchNextQuery)) {
//                // Set the parameter for the OFFSET in the SQL query
//                fetchNextStatement.setInt(1, currentQuestionIndex);
//
//                ResultSet resultSet = fetchNextStatement.executeQuery();
//
//                if (resultSet.next()) {
//                    String Question = resultSet.getString("Question");
//                    String A = resultSet.getString("A");
//                    String B = resultSet.getString("B");
//                    String C = resultSet.getString("C");
//                    String D = resultSet.getString("D");
//                    correctAnswer = resultSet.getString("answer"); // Fetch correct answer
//                    updateUI(Question, A, B, C, D);
//
//                    // Enable the "Previous" button after loading the second question
//                    jButton1.setEnabled(true);
//                } else {
//                    // No more questions, you can handle this case (e.g., display a message)
//                    jButton1.setEnabled(false);  // Disable "Previous" button when there are no more questions
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        } catch (ClassNotFoundException | SQLException ex) {
//            ex.printStackTrace();
//        }
//    }
//    public questions(String userEmail) {
//        this.userEmail = userEmail;
//        // ... Existing code ...
//    }
//
//    private void updateUI(String Question, String A, String B, String C, String D) {
//        jTextArea1.setText(Question);
//        jRadioButton1.setText(A);
//        jRadioButton2.setText(B);
//        jRadioButton3.setText(C);
//        jRadioButton4.setText(D);
//    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Questions");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton3);

        buttonGroup1.add(jRadioButton4);
        jRadioButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton4ActionPerformed(evt);
            }
        });

        jButton1.setText("PREVIOUS");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("NEXT");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("SUBMIT");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("CALCULATOR");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioButton2)
                            .addComponent(jRadioButton3)
                            .addComponent(jRadioButton4))
                        .addGap(0, 635, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(198, 198, 198)
                                .addComponent(jButton2))
                            .addComponent(jRadioButton1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(67, 67, 67))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 534, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(134, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(114, 114, 114)
                        .addComponent(jLabel3)
                        .addGap(31, 31, 31))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(154, 154, 154))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton4)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (i == 0) {
            JOptionPane.showMessageDialog(null, "This is the first question");

            if (jRadioButton1.isSelected()) {
                yourOption = jRadioButton1.getText();
                studentAnswer.set(i, yourOption);
            } else if (jRadioButton2.isSelected()) {
                yourOption = jRadioButton2.getText();
                studentAnswer.set(i, yourOption);
            } else if (jRadioButton3.isSelected()) {
                yourOption = jRadioButton3.getText();
                studentAnswer.set(i, yourOption);
            } else if (jRadioButton4.isSelected()) {
                yourOption = jRadioButton4.getText();
                studentAnswer.set(i, yourOption);
            }

        } else {
            if (jRadioButton1.isSelected()) {
                yourOption = jRadioButton1.getText();
                studentAnswer.set(i, yourOption);
            } else if (jRadioButton2.isSelected()) {
                yourOption = jRadioButton2.getText();
                studentAnswer.set(i, yourOption);
            } else if (jRadioButton3.isSelected()) {
                yourOption = jRadioButton3.getText();
                studentAnswer.set(i, yourOption);
            } else if (jRadioButton4.isSelected()) {
                yourOption = jRadioButton4.getText();
                studentAnswer.set(i, yourOption);
            }
            i = i - 1;

            jTextArea1.setText(questions.get(i));
            jRadioButton1.setText(optionA.get(i));
            jRadioButton2.setText(optionB.get(i));
            jRadioButton3.setText(optionC.get(i));
            jRadioButton4.setText(optionD.get(i));
            buttonGroup1.clearSelection();

            if (studentAnswer.get(i) == jRadioButton1.getText()) {
                jRadioButton1.setSelected(true);
            } else if (studentAnswer.get(i) == jRadioButton2.getText()) {
                jRadioButton2.setSelected(true);
            } else if (studentAnswer.get(i) == jRadioButton3.getText()) {
                jRadioButton3.setSelected(true);
            } else if (studentAnswer.get(i) == jRadioButton4.getText()) {
                jRadioButton4.setSelected(true);
            }
            // TODO add your handling code here:
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (i == (questions.size() - 1)) {
            JOptionPane.showMessageDialog(null, "This is the last question");
        } else {
            if (jRadioButton1.isSelected()) {
                yourOption = jRadioButton1.getText();
                studentAnswer.set(i, yourOption);
            } else if (jRadioButton2.isSelected()) {
                yourOption = jRadioButton2.getText();
                studentAnswer.set(i, yourOption);
            } else if (jRadioButton3.isSelected()) {
                yourOption = jRadioButton3.getText();
                studentAnswer.set(i, yourOption);
            } else if (jRadioButton4.isSelected()) {
                yourOption = jRadioButton4.getText();
                studentAnswer.set(i, yourOption);
            }

            //changes question
            i = i + 1;

            jTextArea1.setText(questions.get(i));
            jRadioButton1.setText(optionA.get(i));
            jRadioButton2.setText(optionB.get(i));
            jRadioButton3.setText(optionC.get(i));
            jRadioButton4.setText(optionD.get(i));
            buttonGroup1.clearSelection();

            if (studentAnswer.get(i) == jRadioButton1.getText()) {
                jRadioButton1.setSelected(true);
            } else if (studentAnswer.get(i) == jRadioButton2.getText()) {
                jRadioButton2.setSelected(true);
            } else if (studentAnswer.get(i) == jRadioButton3.getText()) {
                jRadioButton3.setSelected(true);
            } else if (studentAnswer.get(i) == jRadioButton4.getText()) {
                jRadioButton4.setSelected(true);
            }

        }
    }//GEN-LAST:event_jButton2ActionPerformed
    private void clearRadioButtons() {
        buttonGroup1.clearSelection();
        jRadioButton1.setSelected(false);
        jRadioButton2.setSelected(false);
        jRadioButton3.setSelected(false);
        jRadioButton4.setSelected(false);
        System.out.println("Radio buttons cleared."); // Debug statement
        // This clears the selection of all radio buttons in the button group
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int a = JOptionPane.showConfirmDialog(null, "Do you really want to submit?", "Select", JOptionPane.YES_NO_OPTION);

        if (a == 0) {
            //time.stop();

            if (jRadioButton1.isSelected()) {
                yourOption = jRadioButton1.getText(); //text of jradiobutton would be stored in yourOption, 
                //then would be stored in the array Useranswer
                studentAnswer.set(i, yourOption);
            } else if (jRadioButton2.isSelected()) {
                yourOption = jRadioButton2.getText();
                studentAnswer.set(i, yourOption);
            } else if (jRadioButton3.isSelected()) {
                yourOption = jRadioButton3.getText();
                studentAnswer.set(i, yourOption);
            } else if (jRadioButton4.isSelected()) {
                yourOption = jRadioButton4.getText();
                studentAnswer.set(i, yourOption);
            }

            if (studentAnswer.get(i) == jRadioButton1.getText()) {
                jRadioButton1.setSelected(true);
            } else if (studentAnswer.get(i) == jRadioButton2.getText()) {
                jRadioButton2.setSelected(true);
            } else if (studentAnswer.get(i) == jRadioButton3.getText()) {
                jRadioButton3.setSelected(true);
            } else if (studentAnswer.get(i) == jRadioButton4.getText()) {
                jRadioButton4.setSelected(true);
            }

            String ans; //actual answer
            String sanswer; //student answer
            int k;
            for (k = 0; k < questions.size(); k++) {
                ans = answer.get(k);
                sanswer = studentAnswer.get(k);

                //use .equals() when comparing strings
                if (sanswer.equals(ans)) {
                    score = score + 5;
                } else {
                    score = score + 0;
                }

            }// TODO add your handling code here:
            System.out.println(score);

            int totalScore = 5 * numberOfQuestions;

            //sets status to done
            //sending email part
            String subject = "Your score for the quiz!\n";        // TODO add your handling code here:
            String receiver = email;
            String body = "Hi! Find within this email, your score for the quiz .\n"
                    + "Score " + score + "/" + totalScore + "\n"
                    + "Have a good day!";
            String senderEmail = "arinzechiadaobiada@gmail.com";
            String senderPassword = "reug vplu ikqf mpbg";
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(senderEmail, senderPassword);
                }
            });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(senderEmail));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
                message.setSubject(subject);
                message.setText(body);
                Transport.send(message);
                JOptionPane.showMessageDialog(rootPane, "Email Sent");

            } catch (MessagingException e) {
                JOptionPane.showMessageDialog(rootPane, e);
            }

//            testResult(); //sets the questions, answers and useranswer for the given user
//            jNext.setEnabled(false);
//            jPrevious.setEnabled(false);
//            jSubmit.setEnabled(false);
//            jViewresult.setEnabled(true);
            String updateQuery = "UPDATE student_db SET score = ? WHERE Matno = ?";

            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cbt", "root", "Nkoli1510"); PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

                String studentMarks = String.valueOf(score);

                // Set the parameters for the update statement
                preparedStatement.setString(1, studentMarks);

                //update where matno equals matno
                preparedStatement.setString(2, matno);

                // Execute the update statement
                preparedStatement.executeUpdate();

                // Optionally commit the changes (depending on your transaction requirements)
                //connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        questionsList.add(questions);
        questionsList.add(optionA);
        questionsList.add(optionB);
        questionsList.add(optionC);
        questionsList.add(optionD);
        questionsList.add(answer);
       
        
         for (List<String> questionSet : questionsList) {
            System.out.println(questionSet);
        }
         
         new script(score, questionsList).setVisible(true);

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton4ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            Runtime.getRuntime().exec("calc");
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jButton4ActionPerformed

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
            java.util.logging.Logger.getLogger(questions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(questions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(questions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(questions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new questions("", "").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    public static javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
