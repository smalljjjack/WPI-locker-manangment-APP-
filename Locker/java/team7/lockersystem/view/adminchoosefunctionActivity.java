package team7.lockersystem.view;

import team7.lockersystem.R;
import team7.lockersystem.util.DBOperator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.*;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Small on 4/22/2017.
 */

public class adminchoosefunctionActivity extends Activity implements OnClickListener {
    private Button logoff_btn, checklockerusage_btn, lockermaintenance_btn;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.administratorfunctionlayout);
        ;
        logoff_btn = (Button) this.findViewById(R.id.logoff_btn);
        logoff_btn.setOnClickListener(this);
        checklockerusage_btn = (Button) this.findViewById(R.id.checklockerusage_btn);
        checklockerusage_btn.setOnClickListener(this);
        lockermaintenance_btn = (Button) this.findViewById(R.id.lockermaintenance_btn);
        lockermaintenance_btn.setOnClickListener(this);
    }

    public void onClick(View v) {
        int id = v.getId();
        try {
            if (id == R.id.logoff_btn) {
                //go back to main screen
                Intent intent = new Intent(this, lockersystemActivity.class);
                this.startActivity(intent);
            } else if (id == R.id.checklockerusage_btn) {
                //go to the checkFunction
                Intent intent = new Intent(this, showListActivity.class);
                this.startActivity(intent);
            } else if (id == R.id.lockermaintenance_btn) {
                //go to the Maintenance function
                Intent intent = new Intent(this, administratorActivity.class);
                this.startActivity(intent);
            }
        } catch (Exception e) {
            Toast.makeText(this, "unknown error, confusing... life ", Toast.LENGTH_SHORT).show();
        }
    }
}
