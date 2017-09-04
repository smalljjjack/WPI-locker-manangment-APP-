package team7.lockersystem.view;
import java.lang.*;
import team7.lockersystem.R;
import team7.lockersystem.util.DBOperator;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.*;

import android.widget.Toast;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import  team7.lockersystem.constant.*;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.EditText;

import team7.lockersystem.util.DBOperator;


public class resetActivity extends Activity implements OnClickListener{

    private Button reset_btn, logoff_btn;
    private EditText useridET, vericode, resetpasswordET;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resetlayout);
        //copy database file
        try {
            DBOperator.copyDB(getBaseContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        reset_btn=(Button)this.findViewById(R.id.reset_btn);
        reset_btn.setOnClickListener(this);
        logoff_btn=(Button)this.findViewById(R.id.logoff_btn);
        logoff_btn.setOnClickListener(this);
        useridET = (EditText)this.findViewById(R.id.useridET);
        vericode = (EditText)this.findViewById(R.id.vericode);
        resetpasswordET = (EditText)this.findViewById(R.id.resetpasswordET);
    }

    public void onClick(View v) {
        int id = v.getId();
        String a;
        if (id == R.id.reset_btn) {

            Cursor cursor = DBOperator.getInstance().execQuery(
                       SQLCommand.CHECK_VERICODE, this.getArgs(true));
            String veri1 = null;
                if(cursor.moveToFirst()){
                    veri1 = cursor.getString(cursor.getColumnIndex("user_verifycode"));
                }
            String veri2 = vericode.getText().toString();
            if(veri1.equals(veri2)){
                 DBOperator.getInstance().execSQL(SQLCommand.RESETPASSWORD, this.getArgs(false));
                Toast.makeText(this, "reset successfully", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, veri1, Toast.LENGTH_SHORT).show();
            }
        }
        else if (id == R.id.logoff_btn) {
            //go back to main screen
            Intent intent = new Intent(this, lockersystemActivity.class);
            this.startActivity(intent);
        }
    }

    public String[] getArgs(boolean isReset){
        String[] args = null;
        if(isReset){
            args = new String[1];
            args[0] = useridET.getText().toString();
        } else{
            args = new String[2];
            args[0] = resetpasswordET.getText().toString();
            args[1] = useridET.getText().toString();
        }
        return args;
    }
}

