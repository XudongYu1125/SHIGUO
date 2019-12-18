package com.example.user.interview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author june
 */
public class PositionInfo extends AppCompatActivity {
    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.job_info);
        showItem ();
        onClick ();
    }


    private void showItem(){

        Intent intent = getIntent ();
        Position p = intent.getParcelableExtra ("职位信息");
        TextView tv_name = findViewById (R.id.position_name);
        TextView tv_salary = findViewById (R.id.position_salary);
        TextView tv_request = findViewById (R.id.position_request);
        tv_name.setText (p.getName ());
        tv_salary.setText (p.getSalary ());
        Log.e ("position",p.toString ());
        tv_request.setText (p.getRequest ());

    }

    private void onClick(){

        Toolbar toolbar = findViewById (R.id.add_apply_toolbar);
        toolbar.setNavigationOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick( View v ) {
                finish ();
            }
        });

        Button button = findViewById (R.id.job_info_submit);
        button.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick( View v ) {
                Intent intent = new Intent (PositionInfo.this,SubmitResume.class);
                startActivity (intent);
            }
        });




    }


}
