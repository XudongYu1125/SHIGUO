package com.example.user.interview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import com.google.gson.Gson;

/**
 * @author june
 */
public class AddNewPosition extends AppCompatActivity{



    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.add_new_job);
        setBack ();
        setSubmit ();
    }


    private void setBack(){
        Toolbar toolbar = findViewById (R.id.add_job_toolbar);
        toolbar.setNavigationOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick( View v ) {

                Position p = new Position ();
                Intent intent = new Intent ();
                intent.putExtra ("发布新的职位",p);
                intent.putExtra ("状态",false);
                AddNewPosition.this.setResult (0,intent);
                finish ();
            }
        });
    }



    private void setSubmit(){
        ImageView submit = findViewById (R.id.new_job_submit);
        submit.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick( View v ) {

                Position p = getPosition ();
                Intent intent = new Intent ();
                intent.putExtra ("发布新的职位",p);
                intent.putExtra ("状态",true);
                AddNewPosition.this.setResult (0,intent);
                AddNewPosition.this.finish ();
            }
        });
    }

    private Position getPosition(){
        Position position = new Position ();
        EditText edPositionName = findViewById (R.id.new_job_ed_jobName);
        EditText edCompanyName = findViewById (R.id.new_job_ed_companyName);
        EditText edSalary = findViewById (R.id.new_job_ed_salary);
        EditText edRequest = findViewById (R.id.new_job_ed_request);
        String positionName = edPositionName.getText ().toString ();
        String companyName = edCompanyName.getText ().toString ();
        String salary = edSalary.getText ().toString ();
        position.setName (positionName);
        position.setSalary (salary);
        position.setRequest (edRequest.getText ().toString ());
        return position;
    }
}
