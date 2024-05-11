package com.zzg;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class Demo3 {
    private JButton button1;
    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JLabel label1;
    private JLabel label2;
    private JButton openfileBtn;
    private JLabel filename;


    public Demo3() {
        button1.addActionListener(e -> {
            String text = textField1.getText();
            String text1 = textField2.getText();
            if (text==null || "".equals(text)){
                JOptionPane.showMessageDialog(null,"用户名不能为空","请重试!",JOptionPane.ERROR_MESSAGE);
                return;
            }
            System.out.println(text);
            System.out.println(text1);
            Map<String, Object> authenticatedUser = getAuthenticatedUser(text, text1);
            System.out.println(authenticatedUser);
        });
        openfileBtn.addActionListener(e -> {
            JFileChooser fc=new JFileChooser();
            fc. setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);//可以选择文件和文件夹
            //文件打开对话框
            int i = fc.showOpenDialog(null);
            if (i==0){
                File selectedFile = fc.getSelectedFile();
                System.out.println(selectedFile);
                filename.setText(selectedFile.getPath());
            }else {
                filename.setText("选择错误");
            }

            //showSaveDialog
            //showDialog

        });
    }

    private Map<String,Object> getAuthenticatedUser(String username, String password) {
        Map<String,Object> user = null;

        final String DB_URL = "jdbc:mysql://127.0.0.1:3306/cloudreader?useSSL=false&serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&allowPublicKeyRetrieval=true";
        final String USERNAME = "root";
        final String PASSWORD = "123";

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            //Connected to DB successfully

            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM tb_user WHERE username=? ";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
//            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new HashMap<>();
                user.put("id",resultSet.getString("id")) ;
                user.put("username",resultSet.getString("username")) ;
                user.put("nickname",resultSet.getString("nickname")) ;
                user.put("password",resultSet.getString("password")) ;
            }

            stmt.close();
            conn.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        return user;
    }

    public static void main(String[] args) {
        FlatDarkLaf.setup();
        JFrame frame = new JFrame("Demo3");
        frame.setContentPane(new Demo3().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,400);
        frame.setVisible(true);
    }
}
