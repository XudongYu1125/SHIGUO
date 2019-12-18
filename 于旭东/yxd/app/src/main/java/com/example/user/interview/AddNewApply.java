package com.example.user.interview;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * @author june
 */
public class AddNewApply extends AppCompatActivity {


    private Toolbar toolbar;
    private ImageView submit;


    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.add_new_apply);
        setBack ();
        setSubmit ();

    }



/**    取消发布*/
    private void setBack(){
        toolbar = findViewById (R.id.add_apply_toolbar);
        toolbar.setNavigationOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick( View v ) {
                JobSearch jobSearch = new JobSearch ();
                Intent intent = new Intent ();
                intent.putExtra ("发布新的求职",jobSearch);
                intent.putExtra ("状态",false);
                AddNewApply.this.setResult (1,intent);
                finish ();
            }
        });
    }

/**发布求职*/
    private  void setSubmit(){
        submit = findViewById (R.id.new_apply_send);
        submit.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick( View v ) {
                JobSearch jobSearch = getJobSearch ();
                Intent intent = new Intent ();
                intent.putExtra ("发布新的求职",jobSearch);
                intent.putExtra ("状态",true);
                AddNewApply.this.setResult (1,intent);
                AddNewApply.this.finish ();
            }
        });
    }


    private JobSearch getJobSearch(){
        final EditText edPosition = findViewById (R.id.new_apply_ed_job);
        final EditText edPlace = findViewById (R.id.new_apply_ed_place);
        final EditText edSalary = findViewById (R.id.new_apply_ed_salary);
        final EditText edExperience = findViewById (R.id.new_apply_ed_experience);
        String position = edPosition.getText ().toString ();
        String place = edPlace.getText ().toString ();
        String salary = edSalary.getText ().toString ();
        String request = edExperience.getText ().toString ();
        JobSearch jobSearch = new JobSearch ();
        jobSearch.setPosition (position);
        jobSearch.setPlace (place);
        jobSearch.setSalary (salary);
        jobSearch.setRequest (request);
        return jobSearch;
    }


}
