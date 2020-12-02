package FlatsManage.manage;

import FlatsManage.JDBCConnectDB;
import FlatsManage.tools.GetRoomInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Update_room extends JFrame {

    public Update_room() {
        Update_room jframe = this;
        FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 10, 10);// 设置对其方式以及确定的左右上下间隙

        JLabel idLable = new JLabel(" ID ：");
        JLabel locLable = new JLabel("地点：");
        JLabel volLable = new JLabel("容量：");

        JTextField idText = new JTextField("", 13);
        JTextField locText = new JTextField("", 20);
        JTextField volText = new JTextField("", 20);

        JButton verifyBtn = new JButton("验证");
        JButton updateBtn = new JButton("更新");
        JTextArea shall = new JTextArea(2, 2);
        shall.append("  Tips: 请输入要更改房间的 id 进行验证 " + "\n 当且仅当验证成功后Id输入框会被锁定" + "\n    验证成功后尝试拖动窗口大小   ");

        JPanel jpanel = new JPanel(flowLayout);
        jpanel.add(shall);
        jpanel.add(idLable);
        jpanel.add(idText);
        jpanel.add(verifyBtn);

        verifyBtn.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                jpanel.add(locLable);
                jpanel.add(locText);

                jpanel.add(volLable);
                jpanel.add(volText);

                jpanel.add(updateBtn);
                String sql = "SELECT " + "* " + "FROM " + " room " + "WHERE " + "rid=" + idText.getText() + ";";
                System.out.println(sql);

                if (!idText.getText().equals("")) {
                    JDBCConnectDB con = new JDBCConnectDB();
                    ResultSet rs = con.cQue(sql);
                    try {
                        if (rs.next()) {
                            //房间的id为主键,查询结果亦会返回一个值
                            locText.setText(rs.getString("loc"));
                            volText.setText(rs.getString("vol"));
                            idText.setEditable(false);
                            locText.setEnabled(false);
                            jframe.setVisible(true);//消除需要重新拖到窗口才能显示结果的bug
                            JOptionPane.showMessageDialog(null, "验证成功", "成功", JOptionPane.WARNING_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "请检查ID", "未找到", JOptionPane.WARNING_MESSAGE);
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } finally {
                        con.c_close();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "请检查", "输入错误", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        updateBtn.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                JDBCConnectDB con = new JDBCConnectDB();
                String sql = "UPDATE " + " room " + " SET " +
                        "vol= " + volText.getText() +
                        " WHERE " + " rid = " + idText.getText();
                System.out.println(sql);

                if (!volText.getText().equals("")) {
                    try {
                        if (GetRoomInfo.roomChangeVol(volText.getText(), idText.getText())) {
                            if (con.cUpd(sql) >= 1) {
                                JOptionPane.showMessageDialog(null, "更新成功", "更新成功", JOptionPane.WARNING_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "更新失败", "更新失败", JOptionPane.WARNING_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "新的容量太小了", "更新失败", JOptionPane.WARNING_MESSAGE);
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } finally {
                        con.c_close();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "请检查", "输入错误", JOptionPane.WARNING_MESSAGE);
                }

            }
        });
        this.setTitle("房间信息更新");
        this.add(jpanel);
        this.setLocation(400, 300);
        this.setSize(274, 300);
        this.setVisible(true);
        this.setResizable(false);

    }
}
