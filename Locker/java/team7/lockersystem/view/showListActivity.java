package team7.lockersystem.view;

import team7.lockersystem.R;
        import team7.lockersystem.util.DBOperator;
        import android.annotation.SuppressLint;
        import android.app.Activity;
        import android.content.Intent;
        import android.database.Cursor;
        import android.os.Bundle;
        import android.view.View;
        import android.view.View.*;
        import android.widget.AdapterView;
        import android.widget.ListView;
        import android.widget.ScrollView;
        import android.widget.SimpleCursorAdapter;
        import android.widget.Spinner;
        import android.widget.Toast;
        import android.widget.Button;
        import android.widget.AdapterView.OnItemClickListener;
        import  team7.lockersystem.constant.*;

/**
 * Created by Small on 4/20/2017.
 */

public class showListActivity extends Activity implements OnClickListener{
    private Button available_btn, unavailable_btn,logoff_btn;
    private ScrollView scrollview;
    private Spinner choosefloor;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lockerusinglayout);
        //copy database file
        try{
            DBOperator.copyDB(getBaseContext());
        }catch(Exception e){
            e.printStackTrace();
        }
        available_btn=(Button)this.findViewById(R.id. available_btn);
        available_btn.setOnClickListener(this);
        unavailable_btn=(Button)this.findViewById(R.id.unavailable_btn);
        unavailable_btn.setOnClickListener(this);
        logoff_btn = (Button)this.findViewById(R.id. logoff_btn);
        logoff_btn.setOnClickListener(this);
        choosefloor=(Spinner)this.findViewById(R.id.choosefloor);
        try{
        scrollview=(ScrollView)this.findViewById(R.id.scrollview);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void onClick(View v) {
        int id = v.getId();
        String sql = null;
        if (id == R.id.available_btn) {
            //show query result
            int pos = choosefloor.getSelectedItemPosition();
            if (pos == Spinner.INVALID_POSITION) {
                //User doesn't choose any query, show warning
                Toast.makeText(this.getBaseContext(), "Please choose a query!", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                scrollview.removeAllViews();
            } catch (Exception e) {
                Toast.makeText(this.getBaseContext(), "error 1!", Toast.LENGTH_SHORT).show();
            }
            try {
                if (pos == 0) {
                    //show all books
                    sql = SQLCommand.QUERY1;
                } else if (pos == 1) {
                    //list the call numbers of books with the title ‘Database Management’
                    sql = SQLCommand.QUERY2;
                }
                Cursor cursor = DBOperator.getInstance().execQuery(sql);
                scrollview.addView(new TableView(this.getBaseContext(), cursor));
            } catch (Exception e) {
                Toast.makeText(this.getBaseContext(), "choose floor error", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        } else if (id == R.id.unavailable_btn) {
            int pos = choosefloor.getSelectedItemPosition();
            if (pos == 0) {
                if (pos == Spinner.INVALID_POSITION) {
                    //User doesn't choose any query, show warning
                    Toast.makeText(this.getBaseContext(), "Please choose a floor!", Toast.LENGTH_SHORT).show();
                    return;
                }
                scrollview.removeAllViews();

                try {
                    if (pos == 0) {
                        sql = SQLCommand.QUERY3;
                    } else if (pos == 1) {
                        sql = SQLCommand.QUERY4;
                    }

                } catch (Exception e) {
                    Toast.makeText(this.getBaseContext(), "error2", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                Cursor cursor = DBOperator.getInstance().execQuery(sql);
                scrollview.addView(new TableView(this.getBaseContext(), cursor));
            }
        }else if (id == R.id.logoff_btn) {
                //go back to main screen
                Intent intent = new Intent(this, lockersystemActivity.class);
                this.startActivity(intent);
            }
        }
    }

    /*public String[] getArgs(boolean isfirst){
        String[] args = new String[2];
        if(true){
            args[0] = "true";
            args[1] = "1";
        }else{
            args[0] = "true";
            args[1] = "2";
        }
        return args;
    }

    public String[] getBrgs(boolean issecond){
        String[] brgs = new String[2];
        if(true){
            brgs[0] = "false";
            brgs[1] = "1";
        }else{
            brgs[0] = "false";
            brgs[1] = "2";
        }
        return brgs;
    }*/

