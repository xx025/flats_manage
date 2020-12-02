package FlatsManage.manage;

import FlatsManage.JDBCConnectDB;
import FlatsManage.layout.mutably;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class Search extends mutably {

    JScrollPane jScrollPane;

    public Search(String key) throws SQLException {
        JDBCConnectDB con = new JDBCConnectDB();
        String sql;
        columnNames = new Vector();
        columnNames.add("身份ID");
        columnNames.add("姓名");
        columnNames.add("性别");
        columnNames.add("房间号");
        columnNames.add("房间地址");
        sql = "SELECT p.pid,p.NAME,p.sex,p.room_id,ro.loc FROM people AS p,room AS ro WHERE p.room_id =ro.rid AND (p.NAME LIKE '%"
                + key + "%' OR p.sex LIKE '%" + key + "%' OR p.room_id LIKE '%" + key + "%' OR ro.rid LIKE '%" + key + "%' OR ro.loc LIKE '%" + key + "%') Order by ro.rid,p.pid";
        System.out.println(sql);
        ResultSet res = con.cQue(sql);
        while (res.next()) {
            Vector hang = new Vector();
            hang.add(res.getString(1));
            hang.add(res.getString(2));
            hang.add(res.getString(3));
            hang.add(res.getString(4));
            hang.add(res.getString(5));
            rowData.add(hang);
        }
        jTable = new JTable(rowData, columnNames);
        jScrollPane = new JScrollPane(jTable);
        con.c_close();
    }

    public JScrollPane getjScrollPane() {
        return jScrollPane;
    }
}
