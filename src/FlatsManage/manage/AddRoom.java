package FlatsManage.manage;

import FlatsManage.JDBCConnectDB;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

//添加新的记录(房间),首先向数据库查询(只查询id)
// 并以id进行倒序排序,由于id置为主键了,所以可
// 以取排序后表的第一条然后进行算数加一(添加人员亦是)
public class AddRoom extends JFrame {

    public AddRoom() {
        JLabel idLable = new JLabel("  ID： ");
        JLabel sexLable = new JLabel("地点：");
        JLabel volLable = new JLabel("容量：");


        JTextField idText = new JTextField("", 20);
        JTextField locText = new JTextField("", 20);
        JTextField volText = new JTextField("", 20);

        JButton addBtn = new JButton("添加");
        JPanel jpanel = new JPanel();
        jpanel.add(idLable);
        jpanel.add(idText);
        jpanel.add(sexLable);
        jpanel.add(locText);
        jpanel.add(volLable);
        jpanel.add(volText);
        jpanel.add(addBtn);
        // 添加新的最大ID
        idText.setText(getMaxId());
        idText.setEnabled(false);
        this.setResizable(false);

        addBtn.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                JDBCConnectDB con2 = new JDBCConnectDB();
                String sql2 = "INSERT INTO room(rid,loc, vol) VALUES  (" + idText.getText() + ",'" + locText.getText() + "', " + volText.getText() + ")";
                System.out.println(sql2);

                if (!locText.getText().equals("") || !volText.getText().equals("")) {
                    try {
                        if (con2.cUpd(sql2) >= 1) {
                            JOptionPane.showMessageDialog(null, "添加成功", "成功", JOptionPane.WARNING_MESSAGE);
                            idText.setText(getMaxId());
                            locText.setText("");
                            volText.setText(" ");
                        } else {
                            JOptionPane.showMessageDialog(null, "添加失败,请检查房间地点", "失败", JOptionPane.WARNING_MESSAGE);
                        }
                    } finally {
                        con2.c_close();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "请检查", "输入错误", JOptionPane.WARNING_MESSAGE);
                }

            }
        });
        this.setTitle("添加房间");
        this.add(jpanel);
        this.setLocation(400, 300);
        this.setSize(270, 300);
        this.setVisible(true);
    }

    public String getMaxId() {
        JDBCConnectDB con = new JDBCConnectDB();
        String maxId = null;
        String sql1 = "SELECT rid FROM room ORDER BY rid DESC LIMIT 0, 1";
        // 注:使用MAX(rid)报错,时间原因暂不使用
        System.out.println(sql1);
        ResultSet rs1 = con.cQue(sql1);
        try {
            if (rs1.next()) {
                maxId = rs1.getString("rid");
                System.out.println("当前最大ID:" + maxId + "将会生成新的ID:" + maxId + "+1");
                // String转Int
                // https://blog.csdn.net/a772304419/article/details/79723249
            } else {
                maxId = "0";
                System.out.println("没有房间存在,置maxid为0");
            }
        } catch (SQLException throwables) {
//            throwables.printStackTrace();
        } finally {
            // 释放资源
            con.c_close();
        }
        return String.valueOf(Integer.parseInt(maxId) + 1);
    }
}
