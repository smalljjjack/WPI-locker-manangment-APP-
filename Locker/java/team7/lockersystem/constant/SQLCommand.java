package team7.lockersystem.constant;

/**
 * Created by Small on 4/20/2017.
 */

public abstract class SQLCommand {
    public static String LOCKER_REPAIR = "insert into maintenance(main_id, locker_id, main_startdate, main_enddate) values(?,?,?,?)";
    public static String LOCKER_REGISTER = "update maintenance set main_enddate = ? where main_id = ?";
    public static String CHECKPASSWORD ="select user_password from user where user_id =?";

    public static String CHECK_VERICODE = "select user_verifycode from user where user_id = ?";
    public static String RESETPASSWORD = "update user set user_password = ? where user_id = ?";

    public static String BANUSER = "update user set user_available = ? where user_id = ? ";
    public static String STARTUSER = "update user set user_available = ? where user_id = ? ";
    public static String BANLOCKER= "update locker set locker_available = ? where locker_id = ?";
    public static String STARTLOCKER ="update locker set locker_available = ? where locker_id = ?";
    public static String CHECKIN_LOCKER = "insert into occupy(occupy_id, user_id, locker_id, occupy_startdatetime, occupy_enddatetime) values(?,?,?,?,?)";
    public static String CHECKOUT_LOCKER = "update occupy set occupy_enddatetime = ? where occupy_id = ?";
    public static String CHEKC_LOCKERAVAILABLE = "select locker_available from locker where locker_id = ? ";
    public static String CHECK_USERAVAILABLE = "select user_available from user where user_id like ?";
    public static String GETUSERNAME = "select user_firstname from user where user_id = ?";

    public static String QUERY1 = "select locker_id, locker_gender from locker where locker_id like '%1%' and locker_available like '%true%'";
    public static String QUERY2 = "select locker_id, locker_gender from locker where locker_id like '%2%' and locker_available like '%true%'";
    public static String QUERY3 = "select locker_id, locker_gender from locker where locker_id like '%1%' and locker_available like '%false%'";
    public static String QUERY4 = "select locker_id, locker_gender from locker where locker_id like '%2%' and locker_available like '%false%'";


}
