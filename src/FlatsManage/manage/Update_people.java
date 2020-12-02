package FlatsManage.manage;

import FlatsManage.JDBCConnectDB;
import FlatsManage.tools.GetRoomInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Update_people extends JFrame {


    String roomIdPrevious;

    public Update_people() {

        Update_people jframe = this;
        FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 10, 10);// 设置对其方式以及确定的左右上下间隙

        JLabel idLable = new JLabel(" ID ：");
        JLabel NameLable = new JLabel("姓名：");
        JLabel sexLable = new JLabel("性别：");
        JLabel roomidLable = new JLabel("房号：");

        JTextField idText = new JTextField("", 13);
        JTextField NameText = new JTextField("", 20);
        JTextField sexText = new JTextField("", 20);
        JTextField roomidText = new JTextField("", 20);

        JButton verifyBtn = new JButton("验证");
        JButton updateBtn = new JButton("更新");
        JTextArea shall = new JTextArea(3, 2);
        shall.append("  Tips: 请输入要更改人员的 id 进行验证 " + "\n 当且仅当验证成功后Id输入框会被锁定" + "\n    验证成功后尝试拖动窗口大小   ");
        JPanel jpanel = new JPanel(flowLayout);
        jpanel.add(shall);
        jpanel.add(idLable);
        jpanel.add(idText);
        jpanel.add(verifyBtn);

        verifyBtn.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                String sql = "SELECT\n" + "*\n" + "FROM\n" + " people\n" + "WHERE\n" + "pid=" + idText.getText() + ";";
                JDBCConnectDB con = new JDBCConnectDB();
                if (!idText.getText().equals("")) {
                    ResultSet rs = con.cQue(sql);
                    try {
                        if (rs.next()) {
                            // 人员的id为主键,查询结果亦会只返回一个值
                            jpanel.add(NameLable);
                            jpanel.add(NameText);
                            jpanel.add(sexLable);
                            jpanel.add(sexText);
                            jpanel.add(roomidLable);
                            jpanel.add(roomidText);
                            jpanel.add(updateBtn);
                            jframe.setVisible(true);//消除需要重新拖到窗口才能显示结果的bug
                            idText.setEditable(false);
                            NameText.setText(rs.getString("NAME"));
                            sexText.setText(rs.getString("sex"));
                            sexText.setEditable(false);
                            roomidText.setText(rs.getString("room_id"));
                            roomIdPrevious = rs.getString("room_id");
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
                // 此处注意,插入char要加引号
                String sql = "UPDATE " + " people " + " SET " + " NAME = \"" + NameText.getText() + "\"," + " room_id= "
                        + roomidText.getText() + " WHERE " + " pid = " + idText.getText();
                System.out.println(sql);

                if (!roomidText.getText().equals("") && !NameText.getText().equals("") && !sexText.getText().equals("")) {
                    try {
                        if (roomIdPrevious.equals(roomidText.getText()) || GetRoomInfo.roomVacancy(roomidText.getText())) {
                            // 房间id没有改变 或者   新的房间有空闲
                            if (con.cUpd(sql) >= 1) {
                                JOptionPane.showMessageDialog(null, "更新成功", "更新成功", JOptionPane.WARNING_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "更新失败", "更新失败", JOptionPane.WARNING_MESSAGE);
                            }
                        } else {// 房间已满
                            JOptionPane.showMessageDialog(null, "当前房间已满或不存在,请更换房间,", "出错啦", JOptionPane.WARNING_MESSAGE);
                        }
                    } finally {
                        con.c_close();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "请检查", "输入错误", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        this.setTitle("人员信息更新");
        this.add(jpanel);
        this.setLocation(400, 300);
        this.setSize(274, 300);
        this.setVisible(true);
        this.setResizable(false);


    }

}
