package team7.lockersystem.view;

import java.lang.*;
import java.lang.String;
import java.text.SimpleDateFormat;
import java.util.Date;

import team7.lockersystem.constant.*;
import team7.lockersystem.util.*;
import team7.lockersystem.R;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;
import android.widget.TextView;



/**
 * Created by Small on 4/20/2017.
 */

public class StudentActivity extends Activity implements OnClickListener {

    TextView studentname;
    EditText locknum;
    Button checkin_btn, logoff_btn, checkout_btn;
    public int n;
    private String userId = lockersystemActivity.userId;
    private String username = "student";
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    Date now = new Date();

    public void setn(){
        if (n < 100) n = 100;
    }

    public void onCreate(Bundle savedInstanceState) {
        setn();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studentlayout);
        try {
            DBOperator.copyDB(getBaseContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] name = new String[1];
        name[0] = userId;
        try {
            Cursor cursor = DBOperator.getInstance().execQuery(
                    SQLCommand.GETUSERNAME, name);
            if (cursor.moveToFirst()) {
                username = cursor.getString(cursor.getColumnIndex("user_firstname"));
            }
        }catch(Exception e){
            Toast.makeText(getBaseContext(), "Sorry Can't get user name",
                    Toast.LENGTH_SHORT).show();
        }

        studentname = (TextView)this.findViewById(R.id.studentname);
        studentname.setText("Dear:"+username);
        locknum = (EditText) this.findViewById(R.id.enterlockernum);
        checkin_btn = (Button) this.findViewById(R.id.checkin_btn);
        checkin_btn.setOnClickListener(this);
        logoff_btn = (Button) this.findViewById(R.id.logoff_btn);
        logoff_btn.setOnClickListener(this);
        checkout_btn = (Button) this.findViewById(R.id.checkout_btn);
        checkout_btn .setOnClickListener(this);

        Toast.makeText(getBaseContext(), "Welcome come back Dear"+username,
                Toast.LENGTH_SHORT).show();
    }
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.checkin_btn) {
            String lockerA = null;
            String userA = null;
            //CHECK LOCKER IS AVI OR NOT?
            Cursor cursor = DBOperator.getInstance().execQuery(SQLCommand.CHEKC_LOCKERAVAILABLE,
                    this.setLocker());
            if (cursor.moveToFirst()) {
                lockerA = cursor.getString(cursor.getColumnIndex("locker_available"));
            }
            //System.out.println(lockerA);
            //CHECK USER IS AVI OR NOT?
            Cursor c = DBOperator.getInstance().execQuery(
                    SQLCommand.CHECK_USERAVAILABLE, this.setUser());

            if (c.moveToFirst()) {
                userA = c.getString(c.getColumnIndex("user_available"));
            }
            String a = "true";


            try{
                if (lockerA.equals(a) && userA.equals(a)) {
                    DBOperator.getInstance().execSQL(SQLCommand.CHECKIN_LOCKER,
                            this.getArgs(true));
                    DBOperator.getInstance().execSQL(SQLCommand.BANLOCKER,
                            this.getArgs(false));
                    DBOperator.getInstance().execSQL(SQLCommand.BANUSER,
                            this.getUser(false));
                    Toast.makeText(getBaseContext(), "Check in successfully",
                            Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getBaseContext(), "The locker is unavaliable or you need to checkout your locker first",
                            Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                Toast.makeText(getBaseContext(), "some error occure in the checkin process, plz try again or contact our admin staffs",
                        Toast.LENGTH_SHORT).show();
            }
        }
        // check in locker
        else if (id == R.id.checkout_btn) {
            try {
                if (locknum.getText().toString() == null) {
                    Toast.makeText(getBaseContext(), "Please enter the locaker number",
                            Toast.LENGTH_SHORT).show();
                } else {
                    DBOperator.getInstance().execSQL(SQLCommand.STARTLOCKER,
                            this.startLocker());
                    DBOperator.getInstance().execSQL(SQLCommand.STARTUSER,
                            this.getUser(true));
                    DBOperator.getInstance().execSQL(SQLCommand.CHECKOUT_LOCKER,
                            this.out());
                    Toast.makeText(getBaseContext(), "Check out successfully",
                            Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(getBaseContext(), "some error happen in Check out process, plz try it again",
                        Toast.LENGTH_SHORT).show();
            }
        }
        else if (id == R.id.logoff_btn) {
            //go back to login
            Intent intent = new Intent(this, lockersystemActivity.class);
            this.startActivity(intent);
        }

    }
    public String[] setUser(){
        String[] setUser = new String[1];
        setUser[0] = userId;
        return setUser;
    }

    public String[] setLocker(){
        String[] setLocker = new String[1];
        setLocker[0] = locknum.getText().toString();
        return setLocker;
    }

    public String[] startLocker(){
        String[] startLocker = new String[2];
        startLocker[0] = "true";
        startLocker[1] = locknum.getText().toString();
        return startLocker;
    }

    public String[] getUser(boolean istrue){
        String[] getUser = new String[2];
        if(true) {
            getUser[0] = "true";
            getUser[1] = userId;
        }else{
            getUser[0] = "false";
            getUser[1] = userId;
        }
        return getUser;
    }


    private String[] getArgs(boolean ischeck) {
        String[] args = null;
        if (ischeck) {
            n++;
            args = new String[5];
            String s = null;
            s = Integer.toString(n);
            args[0] = s;
            args[1] = userId;
            args[2] = locknum.getText().toString();
            args[3] = dateFormat.format(now).toString();
            args[4] = "";

        } else {
            args = new String[2];
            args[0] = "false";
            args[1] = locknum.getText().toString();
        }
        return args;
    }

    private String[] out(){
        String[] out = new String[2];
        out[0] = dateFormat.format(now).toString();
        out[1] = Integer.toString(n);
        return out;
    }
}

