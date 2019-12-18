package com.example.user.interview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class SubmitResume extends AppCompatActivity {

    private ArrayList <Resume> resumes = new ArrayList <> ();
    private static ResumeRecyclerAdapter resumeRecyclerAdapter;


    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.submit_resume_layout);
        setBack ( );
        onAddNewResume ( );
        initRecyclerView ( );
    }


//    private void initData(){
//
//    }


    public void setBack() {

        Toolbar toolbar = findViewById (R.id.submit_resume_toolbar);
        toolbar.setNavigationOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick( View v ) {
                finish ( );
            }
        });


    }




    public void onAddNewResume() {

        ImageView imageView = findViewById (R.id.add_new_resume);
        imageView.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick( View v ) {

                




                Intent intent = new Intent (SubmitResume.this , AddNewResume.class);
                startActivityForResult (intent , 0);
            }
        });


    }

    @Override
    protected void onActivityResult( int requestCode , int resultCode , @Nullable Intent data ) {
        switch (resultCode) {
            case 0:
                Resume resume = data.getParcelableExtra ("新的简历");
                boolean status = data.getBooleanExtra ("状态" , false);
                if (status) {
                    addItemResume (resume);
                }
                break;
            default:
                break;
        }

        super.onActivityResult (requestCode , resultCode , data);
    }


    private void initRecyclerView() {

        final RecyclerView recyclerView = findViewById (R.id.rv_submit_resume);

        resumeRecyclerAdapter = new ResumeRecyclerAdapter (resumes);
        recyclerView.setLayoutManager (new LinearLayoutManager (this , LinearLayout.VERTICAL , false));
        recyclerView.addItemDecoration (new DividerItemDecoration (this , DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter (resumeRecyclerAdapter);
        resumeRecyclerAdapter.setOnItemClickListener (new ResumeRecyclerAdapter.OnItemClickListener ( ) {
            @Override
            public void onItemClick( View view , Resume data ) {

                Intent intent = new Intent (SubmitResume.this , ResumeInfo.class);
                intent.putExtra ("简历信息" , data);
                intent.putExtra ("title",resumeRecyclerAdapter.getItemCount ());
                startActivity (intent);


            }
        });


    }

    public static void addItemResume( Resume resume ) {
        resumeRecyclerAdapter.addItem (resume);
    }
}
