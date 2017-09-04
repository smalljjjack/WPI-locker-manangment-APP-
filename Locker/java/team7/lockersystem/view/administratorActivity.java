package team7.lockersystem.view;

import team7.lockersystem.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;
import team7.lockersystem.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import team7.lockersystem.constant.*;
/**
 * Created by Small on 4/22/2017.
 */

public class administratorActivity extends Activity implements OnClickListener{
    EditText enterlockernum;
    Button repair_btn, register_btn, logoff_btn;
    private int n;

    public void setn(){
        if (n < 20) n = 20;
    }
    public void onCreate(Bundle savedInstanceState) {
        setn();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.administratorlayout);
        try {
            DBOperator.copyDB(getBaseContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        enterlockernum = (EditText) this.findViewById(R.id.enterlockernum);
        repair_btn =(Button) this.findViewById(R.id.repair_btn);
        repair_btn.setOnClickListener(this);
        register_btn =(Button) this.findViewById(R.id.register_btn);
        register_btn.setOnClickListener(this);
        logoff_btn = (Button)this.findViewById(R.id.logoff_btn);
        logoff_btn.setOnClickListener(this);
    }

    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.repair_btn){
            //try{
               try{
                   DBOperator.getInstance().execSQL(SQLCommand.LOCKER_REPAIR,
                    this.getArgs(true));
                   DBOperator.getInstance().execSQL(SQLCommand.BANLOCKER,
                           this.setLocker(false));
                   Toast.makeText(getBaseContext(), "succeeed! Please to contact maintanence staffs",
                           Toast.LENGTH_SHORT).show();
               }
               catch(Exception e){
                   Toast.makeText(getBaseContext(), "some error within the process, please try again",
                           Toast.LENGTH_SHORT).show();
                   e.printStackTrace();
               }


        }else if(id == R.id.register_btn){
            try {
                DBOperator.getInstance().execSQL(SQLCommand.STARTLOCKER,
                        this.setLocker(true));

                try {
                    DBOperator.getInstance().execSQL(SQLCommand.LOCKER_REGISTER,
                            this.getArgs(false));
                }catch(Exception e){

                }

                Toast.makeText(getBaseContext(), "The locker register successfully",
                        Toast.LENGTH_SHORT).show();
            }catch(Exception e){
                Toast.makeText(getBaseContext(), "register error",
                        Toast.LENGTH_SHORT).show();
            }
        }
        else if (id==R.id.logoff_btn){
            //go back to main screen
            Intent intent = new Intent(this, lockersystemActivity.class);
            this.startActivity(intent);
        }
    }

    public String[] setLocker(boolean isgood){

        String[] setLocker = new String[2];
        if(true) {
            setLocker[0] = "true";
            setLocker[1] = enterlockernum.getText().toString();
        }else{
            setLocker[0] = "false";
            setLocker[1] = enterlockernum.getText().toString();
        }
            return setLocker;
    }

    public String[] getArgs(boolean ismain){
        String[] args = null;
        Date now = new Date();
        //give new maintanence ID

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        if(true){
            try {
                n++;
                // repair a locker
                args = new String[4];
                //occupy_id
                args[0] = Integer.toString(n);
                //locker_id
                args[1] = enterlockernum.getText().toString();
                args[2] = dateFormat.format(now).toString();
                args[3] = "";

            }catch(Exception e){
                Toast.makeText(getBaseContext(), "Time error!!! Plz contact admin staffs to solve it.",
                        Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }else{
            try{
                args = new String[2];
                args[0] = dateFormat.format(now).toString();;
                args[1] = Integer.toString(n-1);
            }
            catch(Exception e){
                Toast.makeText(getBaseContext(), "Register error, plz register by yourself!!! ",
                        Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

        return args;
    }
}
