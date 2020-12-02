package FlatsManage.manage;

import FlatsManage.JDBCConnectDB;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DeletePeople extends JFrame {

    private static final long serialVersionUID = 1L;

    public DeletePeople() {
        JLabel accounte = new JLabel("ID：");
        JTextField peopidText = new JTextField("", 20);
        JButton deletePeoBtn = new JButton("删除");
        JPanel jpnumber = new JPanel();
        jpnumber.add(accounte);
        jpnumber.add(peopidText);
        jpnumber.add(deletePeoBtn);
        this.setResizable(false);

        deletePeoBtn.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                String idd = peopidText.getText();
                if (!idd.equals("")) {
                    String sql = "DELETE FROM people WHERE pid = " + idd + "";
                    System.out.println(sql);
                    JDBCConnectDB con = new JDBCConnectDB();
                    int rs = con.cUpd(sql);
                    if (rs <= 0) {
                        JOptionPane.showMessageDialog(null, "请检查ID", "删除失败", JOptionPane.WARNING_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "删除成功", "删除成功", JOptionPane.WARNING_MESSAGE);
                    }
                    con.c_close();
                } else {
                    JOptionPane.showMessageDialog(null, "请检查", "输入错误", JOptionPane.WARNING_MESSAGE);
                }

            }
        });
        this.setTitle("删除人员");
        this.add(jpnumber);
        this.setLocation(400, 300);
        this.setSize(350, 300);
        this.setVisible(true);
    }

}
