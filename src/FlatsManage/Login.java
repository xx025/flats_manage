package FlatsManage;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame {

    private static final long serialVersionUID = 1L;
    static Boolean loginSUCCESS = false;// 标记登陆成功

    public Login() {
        JLabel accounte = new JLabel("账户：");
        JLabel password = new JLabel("密码：");

        JTextField account_textfiled = new JTextField("admin", 20);
        JTextField password_textfiled = new JPasswordField("admin", 20);
        JButton loginbtn = new JButton("登录");

        JPanel jpnumber = new JPanel();

        jpnumber.add(accounte);
        jpnumber.add(account_textfiled);
        jpnumber.add(password);

        jpnumber.add(password_textfiled);
        jpnumber.add(loginbtn);

        this.add(jpnumber);
        loginbtn.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                String USER = account_textfiled.getText();
                String PASS = password_textfiled.getText();
                JDBCConnectDB con = new JDBCConnectDB();
                String sql = "SELECT password as pass FROM steward where user=" + "\"" + USER + "\"";
                System.out.println(sql);
                ResultSet rs = con.cQue(sql);
                try {
                    if (rs.next()) {
                        String dbPASS = rs.getString("pass");
                        if (PASS.equals(dbPASS)) {
                            System.out.println("登陆成功");
                            loginSUCCESS = true;
                        } else {
                            JOptionPane.showMessageDialog(null, "账户或密码错误", "请检查账户", JOptionPane.WARNING_MESSAGE);
                            /*
                             * 参考:Java几种消息对话框的弹出
                             * https://blog.csdn.net/qs17809259715/article/details/88062414
                             */
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "账户或密码错误", "请检查账户", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (SQLException rr) {
                    rr.printStackTrace();
                } finally {
                    con.c_close();
                }
                if (loginSUCCESS == true) {
                    dispose();//关闭窗口
                    /*关于:dispose()和setVisible(false);
                     *https://blog.csdn.net/three_know/article/details/78847059
                     */
                    try {
                        new Windows0x1();// 创建窗口类打开窗口
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });
        this.setTitle("登录");
        this.setLocation(400, 300);
        this.setSize(285, 122);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置关闭窗体的后台


    }

    public static Boolean getLoginSUCCESS() {
        return loginSUCCESS;
    }

}
