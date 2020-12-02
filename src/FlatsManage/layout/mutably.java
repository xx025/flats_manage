package FlatsManage.layout;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.util.Vector;

//设置表格格式
public class mutably {

    protected Vector columnNames = null;
    // Vector 类实现了一个动态数组。和 ArrayList 很相似，但是两者是不同的：
    // https://www.runoob.com/java/java-vector-class.html
    protected Vector rowData = new Vector();
    protected JTable jTable = new JTable();
    /*
   表格数据居中显式:http://blog.sina.com.cn/s/blog_6cba238f0101bmj1.html
   */
    DefaultTableCellRenderer cr = new DefaultTableCellRenderer();

    public mutably() {
        cr.setHorizontalAlignment(JLabel.CENTER);

        jTable.setDefaultRenderer(Object.class, cr);
    }

}
