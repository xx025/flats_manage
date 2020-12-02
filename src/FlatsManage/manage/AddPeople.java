package FlatsManage.manage;

import FlatsManage.JDBCConnectDB;
import FlatsManage.tools.GetRoomInfo;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

//添加新的记录(房间),首先向数据库查询(只查询id)
// 并以id进行倒序排序,由于id置为主键了,所以可
// 以取排序后表的第一条然后进行算数加一(添加人员亦是)
public class AddPeople extends JFrame {

    private static final long serialVersionUID = 1L;

    public AddPeople() {
        JLabel idLable = new JLabel(" ID ：");
        JLabel NameLable = new JLabel("姓名：");
        JLabel sexLable = new JLabel("性别：");
        JLabel roomidLable = new JLabel("房号：");

        JTextField idText = new JTextField("", 20);
        JTextField NameText = new JTextField("", 20);
        JTextField sexText = new JTextField("", 20);
        JTextField roomidText = new JTextField("", 20);

        JButton addBtn = new JButton("添加");

        JPanel jpanel = new JPanel();

        jpanel.add(idLable);
        jpanel.add(idText);
        jpanel.add(NameLable);
        jpanel.add(NameText);

        jpanel.add(sexLable);
        jpanel.add(sexText);

        jpanel.add(roomidLable);
        jpanel.add(roomidText);

        jpanel.add(addBtn);


        // 添加新的最大ID
        idText.setText(getMaxId());
        idText.setEnabled(false);
        this.setResizable(false);

        addBtn.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {

                JDBCConnectDB con2 = new JDBCConnectDB();
                String sql2 = "INSERT INTO people(pid,NAME, sex, room_id) VALUES  (" + idText.getText() + ",'" + NameText.getText() + "', '"
                        + sexText.getText() + "', " + roomidText.getText() + ")";
                System.out.println(sql2);
                if (!roomidText.getText().equals("") && !NameText.getText().equals("") && !sexText.getText().equals("")) {
                    if (GetRoomInfo.roomVacancy(roomidText.getText())) {
                        try {
                            if (con2.cUpd(sql2) >= 1) {
                                JOptionPane.showMessageDialog(null, "添加成功", "成功", JOptionPane.WARNING_MESSAGE);
                                idText.setText(getMaxId());
                                NameText.setText("");
                                sexText.setText("");
                                roomidText.setText(" ");
                            } else {
                                JOptionPane.showMessageDialog(null, "添加失败", "失败", JOptionPane.WARNING_MESSAGE);
                            }
                        } finally {
                            con2.c_close();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "请更换房间,当前房间已满", "房间人数已满", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "请检查", "输入错误", JOptionPane.WARNING_MESSAGE);
                }


            }
        });
        JTextArea shall = new JTextArea(3, 1);
        shall.append(" Tips:添加成功后,信息框会被清空,\n" +
                "id会自动生成新的,因而可以继续添加");
        jpanel.add(shall);
        this.setTitle("添加人员");
        this.add(jpanel);
        this.setLocation(400, 300);
        this.setSize(270, 300);
        this.setVisible(true);
    }

    public String getMaxId() {
        JDBCConnectDB con = new JDBCConnectDB();
        String maxId = null;
        String sql1 = "SELECT pid FROM people ORDER BY pid DESC LIMIT 0, 1";
        // 注:使用MAX(pid)报错,时间原因暂不使用
        System.out.println(sql1);
        ResultSet rs1 = con.cQue(sql1);
        try {
            if (rs1.next()) {
                maxId = rs1.getString("pid");
                System.out.println("当前最大ID:" + maxId + "将会生成新的ID:" + maxId + "+1");
            } else {
                maxId = "0";
                System.out.println("没有人存在,置maxid为0");
            }
            // String转Int
            // https://blog.csdn.net/a772304419/article/details/79723249
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            // 释放资源
            con.c_close();
        }
        return String.valueOf(Integer.parseInt(maxId) + 1);
    }
}
