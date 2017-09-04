package team7.lockersystem.view;

import team7.lockersystem.R;
import team7.lockersystem.util.DBOperator;
import team7.lockersystem.constant.*;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.database.Cursor;


public class lockersystemActivity extends Activity implements OnClickListener {

    //@Override
    Button login_btn,retrievepassword_btn;
    Spinner usertype_spn;
    EditText enteruserid, enterpassword;
    int n;
    public static String userId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginlayout);
        try{
            DBOperator.copyDB(getBaseContext());
        }catch(Exception e){
            e.printStackTrace();
        }
        enteruserid = (EditText) this.findViewById(R.id.enteruserid);
        enterpassword = (EditText) this.findViewById(R.id.enterpassword);
        login_btn=(Button)this.findViewById(R.id.login_btn);
        login_btn.setOnClickListener(this);
        retrievepassword_btn=(Button)this.findViewById(R.id.retrievepassword_btn);
        retrievepassword_btn.setOnClickListener(this);
        usertype_spn=(Spinner)this.findViewById(R.id.usertype_spn);

    }
    public void onClick(View v) {
        int id = v.getId();
        String a;
        if (id == R.id.login_btn) {
            int pos = usertype_spn.getSelectedItemPosition();
            if (pos == Spinner.INVALID_POSITION) {
                //User doesn't choose any type, show warning
                Toast.makeText(this.getBaseContext(), "Please choose a type!", Toast.LENGTH_SHORT).show();
                return;

                //try {
            }
            else if (pos == 0) {
                try {
                    Cursor cursor = DBOperator.getInstance().execQuery(
                            SQLCommand.CHECKPASSWORD, this.getArgs(true));
                    String veri1 = null;
                    if (cursor.moveToFirst()) {
                        veri1 = cursor.getString(cursor.getColumnIndex("user_password"));
                    }
                    String veri2 = enterpassword.getText().toString();
                    if (veri1.equals(veri2)) {
                        Intent intent = new Intent(this, StudentActivity.class);
                        this.startActivity(intent);
                        userId = enteruserid.getText().toString();
                    } else {
                        Toast.makeText(this, "Wrong Password", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    Toast.makeText(this, "login error", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                //try {
            }else if(pos == 1) {
                try {
                    Cursor cursor = DBOperator.getInstance().execQuery(
                            SQLCommand.CHECKPASSWORD, this.getArgs(true));
                    String veri1 = null;
                    if (cursor.moveToFirst()) {
                        veri1 = cursor.getString(cursor.getColumnIndex("user_password"));
                    }
                    String veri2 = enterpassword.getText().toString();
                    if (veri1.equals(veri2)) {
                        Intent intent = new Intent(this, guestCheckinActivity.class);
                        this.startActivity(intent);
                        userId = enteruserid.getText().toString();
                    }
                }catch(Exception e){
                    Toast.makeText(this, "login error", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }else if(pos == 2) {
                try {

                    Cursor cursor = DBOperator.getInstance().execQuery(
                            SQLCommand.CHECKPASSWORD, this.getArgs(true));
                    String veri1 = null;
                    if (cursor.moveToFirst()) {
                        veri1 = cursor.getString(cursor.getColumnIndex("user_password"));
                    }
                    String veri2 = enterpassword.getText().toString();
                    if (veri1.equals(veri2)) {
                        Intent intent = new Intent(this, adminchoosefunctionActivity.class);
                        this.startActivity(intent);
                        userId = enteruserid.getText().toString();
                    }
                }catch(Exception e){
                    Toast.makeText(this, "login error", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }else if (id == R.id.retrievepassword_btn) {
            Intent intent = new Intent(this, resetActivity.class);
            this.startActivity(intent);
        }
    }

    private String[] getArgs(boolean ischeck){
        String[] args= null;
        if(ischeck){
            args = new String[1];
            args[0] = enteruserid.getText().toString();
        }else{
            args = new String[1];
            args[0] = enteruserid.getText().toString();
        }
        return args;
    }
}