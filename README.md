# 公寓管理系统


Java+JDBC+Mysql


## 程序逻辑

1. 打开运行

2. 登录:输入账户和密码,将输入的账户与数据库steward(管家表)做对比
    1. 密码比对成功  
        1. 登录成功,关闭登录窗口
        2. 显式操作窗口以及各种功能按钮
        3. 显示所有人员信息
            1. 首先依据`rid`排序
            2. 再依据`pid`排序
    2. 密码比对失败或用户检索失败
        1. 弹窗报错

3. 查找和管理  
    1. 搜索,不指定表向所有列people和room进行模糊搜索       
    2. 管理
        1.  删除
            1. 删除人员不做任何限制,可以删除任意一个人员的记录,删除以人员`pid`为标准进行删除  

                删除失败,提示错误  
                删除成功,提示删除成功

            2. 删除以房间`rid`为标准进行删除          
                1. 删除房间需要确定房间内没有人,否则不能删除 

                删除失败,提示错误    
                删除成功,提示删除成功

        2. 更新
            1. 人员更新,id不可更改,性别可更改,房间可更改,名字可更改
                1. 先进行id检查,检查成功向数据库调取信息显示(文本框预先不存在)在文本框,检查不存在弹窗报错
                2. 锁定id框
                3. 更新后满足房间不可满员
            2. 房间信息只能更新容量
                1. 先进性id检查,检查成功向数据库调取信息显示(文本框预先不存在)在文本框,检查不存在弹窗报错
                2. 锁定id框和地址不可更改
                3. 更新后的容量和当前已有人数不能冲突
        3. 添加
            1. 添加人员,id自动生成
                1. 生成原则向数据库中表检索pid取最大值并+1
                2. 新添加的人满足房间不满员
            2. 新添加房间需要满足`room.loc`不重复,id同人员

#### 项目地址: https://github.com/xx025/flats_manage


## 创建数据库并插入数据

```sql
-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.5.27 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 b1dx0 的数据库结构
CREATE DATABASE IF NOT EXISTS `b1dx0` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `b1dx0`;

-- 导出  表 b1dx0.people 结构
CREATE TABLE IF NOT EXISTS `people` (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(32) NOT NULL,
  `sex` varchar(2) NOT NULL,
  `room_id` int(11) NOT NULL,
  PRIMARY KEY (`pid`),
  KEY `rom_id` (`room_id`),
  CONSTRAINT `rom_id` FOREIGN KEY (`room_id`) REFERENCES `room` (`rid`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;

-- 正在导出表  b1dx0.people 的数据：~25 rows (大约)
DELETE FROM `people`;
/*!40000 ALTER TABLE `people` DISABLE KEYS */;
INSERT INTO `people` (`pid`, `NAME`, `sex`, `room_id`) VALUES
	(14, '林黛玉', '女', 11),
	(16, '妙玉', '女', 13),
	(17, '李纨', '女', 18),
	(18, '迎春', '女', 14),
	(19, '探春', '女', 15),
	(20, '惜春', '女', 16),
	(21, '妙玉', '女', 16),
	(22, '雪雁', '女', 11),
	(26, '紫娟', '女', 11),
	(27, '莺儿', '女', 12),
	(28, '王熙凤', '女', 19),
	(29, '平儿', '女', 19),
	(30, '贾琏', '男', 19),
	(31, '贾母', '女', 19),
	(33, '莺哥', '女', 19),
	(34, '贾敬', '男', 19),
	(35, '王夫人', '女', 19),
	(36, '湘云', '女', 12),
	(37, '秦可卿', '女', 21),
	(38, '尤氏', '女', 21),
	(40, '林如海', '男', 22),
	(41, '贾敏', '女', 22),
	(44, '宝玉', '男', 10),
	(45, '风丫头', '女', 19),
	(46, '风', '女', 11);
/*!40000 ALTER TABLE `people` ENABLE KEYS */;

-- 导出  表 b1dx0.room 结构
CREATE TABLE IF NOT EXISTS `room` (
  `rid` int(11) NOT NULL AUTO_INCREMENT,
  `loc` varchar(32) NOT NULL,
  `vol` int(11) DEFAULT '6',
  PRIMARY KEY (`rid`),
  UNIQUE KEY `loc` (`loc`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

-- 正在导出表  b1dx0.room 的数据：~12 rows (大约)
DELETE FROM `room`;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` (`rid`, `loc`, `vol`) VALUES
	(10, '怡红院', 5),
	(11, '潇湘馆', 5),
	(12, '蘅芜苑', 5),
	(13, '栊翠庵', 5),
	(14, '紫菱洲', 5),
	(15, '秋爽斋', 6),
	(16, '藕香榭', 5),
	(18, '稻香村', 5),
	(19, '荣国府', 16),
	(21, '宁国府', 16),
	(22, '林府', 10),
	(23, '馒头庵', 4);
/*!40000 ALTER TABLE `room` ENABLE KEYS */;

-- 导出  表 b1dx0.steward 结构
CREATE TABLE IF NOT EXISTS `steward` (
  `USER` varchar(32) DEFAULT NULL,
  `PASSWORD` varchar(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  b1dx0.steward 的数据：~1 rows (大约)
DELETE FROM `steward`;
/*!40000 ALTER TABLE `steward` DISABLE KEYS */;
INSERT INTO `steward` (`USER`, `PASSWORD`) VALUES
	('admin', 'admin');
/*!40000 ALTER TABLE `steward` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
```