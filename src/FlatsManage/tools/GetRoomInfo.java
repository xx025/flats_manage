package FlatsManage.tools;

import FlatsManage.JDBCConnectDB;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GetRoomInfo {

    // 查看当前已有人数
    public static int peopleNumber(String idd) {
        JDBCConnectDB con0x3 = new JDBCConnectDB();
        int counts = 0;
        String verifySql = "SELECT  COUNT(*) as counts FROM    people AS p, room AS ro WHERE    p.room_id = ro.rid    AND ro.rid = "
                + idd;
        System.out.println(verifySql);
        ResultSet rsVerify = con0x3.cQue(verifySql);
        try {
            if (rsVerify.next()) {
                counts = rsVerify.getInt(1);
            } else {
                System.out.println("房间不存咋");
            }
        } catch (SQLException throwables) {
            System.out.println("房间不存咋");
        } finally {
            con0x3.c_close();
        }
        return counts;
    }

    // 获取房间容量
    public static int roomVol(String idd) {
        int vol = 0;
        JDBCConnectDB con0x2 = new JDBCConnectDB();
        String Sql = "SELECT vol FROM room WHERE rid = " + idd;
        System.out.println(Sql);
        ResultSet rs = con0x2.cQue(Sql);
        try {
            rs.next();
            vol = rs.getInt(1);
            if (rs.next()) {
                vol = rs.getInt(1);
                System.out.println(vol);
            }
        } catch (SQLException throwables) {
//            throwables.printStackTrace();
            System.out.println("房间不存咋");
        }
        con0x2.c_close();// 释放资源
        return vol;
    }

    // 房间剩余容量
    public static int roomVacancyCount(String idd) throws SQLException {
        return roomVol(idd) - peopleNumber(idd);
    }

    // 房间空闲(容量>已有人数),用于添加人员可行性判断
    public static boolean roomVacancy(String idd) {
        // 房间有空闲
        return roomVol(idd) > peopleNumber(idd);
    }

    // 改变房间容量是否合法(已有人数<=新容量)
    public static boolean roomChangeVol(String vol, String idd) throws SQLException {
        return peopleNumber(idd) <= Integer.valueOf(vol).intValue();
    }

}
