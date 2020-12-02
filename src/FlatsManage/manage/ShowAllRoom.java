package FlatsManage.manage;

import FlatsManage.JDBCConnectDB;
import FlatsManage.layout.mutably;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import static FlatsManage.tools.GetRoomInfo.roomVacancyCount;

public class ShowAllRoom extends mutably {
    JScrollPane jScrollPane;

    public ShowAllRoom() throws SQLException {

        JDBCConnectDB conn = new JDBCConnectDB();
        columnNames = new Vector();
        columnNames.add("ID");
        columnNames.add("地点");
        columnNames.add("容量");
        columnNames.add("还可加入");
        ResultSet res = conn.cQue(
                "SELECT * FROM room");
        while (res.next()) {
            Vector hang = new Vector();
            hang.add(res.getString(1));
            hang.add(res.getString(2));
            hang.add(res.getString(3));
            hang.add(roomVacancyCount(res.getString(1)));
            rowData.add(hang);
        }
        jTable = new JTable(rowData, columnNames);
        jScrollPane = new JScrollPane(jTable);
    }

    public JScrollPane getjScrollPane() {
        return jScrollPane;
    }
}
