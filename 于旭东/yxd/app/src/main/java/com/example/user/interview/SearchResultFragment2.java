package com.example.user.interview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import static com.example.user.interview.InterviewActivity.getJobSearchList;


public class SearchResultFragment2 extends Fragment {

    private View view;
    private List <JobSearch> jobSearchList;
    private static XRecyclerView recyclerView;
    private static JobSearchRecyclerAdapter jobSearchRecyclerAdapter;


    @Nullable
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState ) {
        view = inflater.inflate (R.layout.result2,null);
        initJobs ();
        initRecyclerView ();
        Log.e ("sf2","调用了onCreateView");
        return view;
    }




    //对数据初始化
    private void initJobs(){
        jobSearchList = getJobSearchList();
    }


    //对recyclerView初始化
    private void initRecyclerView(){
        recyclerView = view.findViewById (R.id.result_rv2);
        jobSearchRecyclerAdapter = new JobSearchRecyclerAdapter (getActivity ( ) , (ArrayList <JobSearch>) jobSearchList);
        recyclerView.setAdapter (jobSearchRecyclerAdapter);
        recyclerView.getDefaultRefreshHeaderView ();
        recyclerView.setPullRefreshEnabled (false);
        recyclerView.setLayoutManager (new LinearLayoutManager (getActivity (), LinearLayout.VERTICAL,false));
        recyclerView.addItemDecoration (new DividerItemDecoration (getActivity (),DividerItemDecoration.VERTICAL));
        jobSearchRecyclerAdapter.setOnItemClickListener (new JobSearchRecyclerAdapter.OnItemClickListener ( ) {
            @Override
            public void OnItemClick( View view , JobSearch data ) {
                Intent intent = new Intent (getActivity (), ApplyPositionUserInfo.class);
                intent.putExtra ("求职信息",data);
                startActivity (intent);

            }
        });
    }




    public static JobSearchRecyclerAdapter getJobSearchAdapter(){

        return jobSearchRecyclerAdapter;

    }







    public static List<JobSearch> filterJobSearch( List <JobSearch> jobSearchList , String query ) {
        query = query.toLowerCase ( );
        final List<JobSearch> filterPositionModeList = new ArrayList <> ();
        for (JobSearch jb : jobSearchList){

            String position = jb.getPosition ().toLowerCase ();
            if (position.contains (query)){

                filterPositionModeList.add (jb);

            }


        }

        return filterPositionModeList;

    }



    public static XRecyclerView getRecyclerViewJobSearch(){return recyclerView;}


}
