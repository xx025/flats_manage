package FlatsManage.manage;

import FlatsManage.JDBCConnectDB;
import FlatsManage.tools.GetRoomInfo;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class DeleteRoom extends JFrame {

    public DeleteRoom() {
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
                    JDBCConnectDB con = new JDBCConnectDB();
                    //查询房间内人数
                    try {
                        if (GetRoomInfo.peopleNumber(idd) > 0) {
                            // 房间内还有人员,不可删除
                            JOptionPane.showMessageDialog(null, "房间内有人", "错误", JOptionPane.WARNING_MESSAGE);
                        } else {
                            // 房间内没人了可以删除||或房间不存在
                            String sql = "DELETE FROM room WHERE rid = " + idd + "";
                            System.out.println(sql);
                            if (con.cUpd(sql) > 0) {
                                //删除成功
                                JOptionPane.showMessageDialog(null, "删除成功", "删除成功", JOptionPane.WARNING_MESSAGE);
                            } else {
                                //删除失败,房间不存在
                                JOptionPane.showMessageDialog(null, "请检查ID", "删除失败", JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    } finally {
//                    关闭资源
                        con.c_close();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "请检查", "输入错误", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        this.setTitle("删除房间");
        this.add(jpnumber);
        this.setLocation(400, 300);
        this.setSize(350, 300);
        this.setVisible(true);

    }

}
