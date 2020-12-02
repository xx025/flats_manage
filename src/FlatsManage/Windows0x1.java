package FlatsManage;

import FlatsManage.manage.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class Windows0x1 extends JFrame {

    private static final long serialVersionUID = 1L;
    JPanel panel = new JPanel();
    JScrollPane jstable;
    ShowAllPeople showsall = new ShowAllPeople();

    public Windows0x1() throws SQLException {

        Windows0x1 jframe = this;
        JLabel PEO = new JLabel("人员：");
        JLabel ROM = new JLabel("房间：");
        JButton updateBtnP = new JButton("更改");
        JButton addBtnP = new JButton("添加");
        JButton deleteBtnP = new JButton("删除");
        JButton updateBtnR = new JButton("更改");
        JButton addBtnR = new JButton("添加");
        JButton deleteBtnR = new JButton("删除");
        JButton showAlLroom = new JButton("查看");
        JButton showAlLpeople = new JButton("查看");
        JButton searchBtn = new JButton("搜索");
        JTextField searchField = new JTextField("荣国府", 44);
        // 定义三个个功能键
        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT, 10, 10);// 设置对其方式以及确定的左右上下间隙
        JPanel panel1 = new JPanel(flowLayout);
        JPanel panel2 = new JPanel(flowLayout);
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        panel1.add(PEO);
        panel1.add(addBtnP);
        panel1.add(updateBtnP);
        panel1.add(deleteBtnP);
        panel1.add(showAlLpeople);

        panel2.add(ROM);
        panel2.add(addBtnR);
        panel2.add(updateBtnR);
        panel2.add(deleteBtnR);
        panel2.add(showAlLroom);

        panel3.add(searchField);
        panel3.add(searchBtn);

        jstable = showsall.getjScrollPane();

        panel4.add(jstable);
        // 人员管理
        this.setLocation(10, 10);
        this.setSize(700, 660);
        this.setTitle("公寓管理");


        panel.add(panel1);
        panel.add(panel2);
        panel.add(panel4);
        panel.add(panel3);
        this.add(panel);

        this.setVisible(true);// 窗口可见
        this.setResizable(false);

        addBtnP.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new AddPeople();// 按钮绑定添加人员
            }
        });

        updateBtnP.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new Update_people();// 按钮绑定更新人员

            }
        });

        deleteBtnP.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new DeletePeople();// 按钮绑定删除人员
            }
        });
        showAlLpeople.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                //按钮绑定,查看所有人
                try {
                    showsall = new ShowAllPeople();
                    panel4.remove(jstable);// 移除上次结果
                    jstable = showsall.getjScrollPane();
                    panel4.add(jstable);
                    jframe.setVisible(true);//消除需要重新拖到窗口才能显示结果的bug
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });

        // 房间管理
        addBtnR.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new AddRoom();// 按钮绑定添加房间
            }
        });

        updateBtnR.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new Update_room();// 按钮绑定更新房间
            }
        });

        deleteBtnR.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new DeleteRoom();// 删除房间
            }
        });
        showAlLroom.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                ShowAllRoom showRe;
                try {
                    showRe = new ShowAllRoom();
                    panel4.remove(jstable);// 移除上次结果
                    jstable = showRe.getjScrollPane();// 收缩结果返回的表格
                    panel4.repaint();// 重新绘制窗口
                    panel4.add(jstable);// 添加新的结果
                    jframe.setVisible(true);//消除需要重新拖到窗口才能显示结果的bug
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        searchBtn.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                String key = searchField.getText();
                try {
                    panel4.remove(jstable);// 移除上次结果
                    Search search_result = new Search(key);// 搜索结果
                    jstable = search_result.getjScrollPane();// 收缩结果返回的表格
                    panel4.repaint();// 重新绘制窗口
                    panel4.add(jstable);// 添加新的结果
                    jframe.setVisible(true);//消除需要重新拖到窗口才能显示结果的bug
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置关闭窗体的后台
    }
}
