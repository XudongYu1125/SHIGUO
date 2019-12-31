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
import android.widget.SearchView;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import static com.example.user.interview.InterviewFragment.getCompanyImg;
import static com.example.user.interview.InterviewFragment.getCompanyName;
import static com.example.user.interview.InterviewFragment.getPositionList;

public class SearchResultFragment1 extends Fragment {


    private View view;
    private static List <Position> positions;
    private static List companyName;
    private static List companyImg;
    private static PositionRecyclerAdapter positionRecyclerAdapter;
    private static XRecyclerView recyclerView;
    private SearchView sv;
    protected boolean isCreated = false;

    @Nullable
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater , @Nullable ViewGroup container , @Nullable Bundle savedInstanceState ) {
        view = inflater.inflate (R.layout.result1,null);
        initJobs ();
        initRecyclerView ();
//        initSearchView ();
        Log.e ("sf1","调用了onCreateView");
        return view;
    }

    //对数据初始化
    private void initJobs(){
        positions = getPositionList();
        companyImg = getCompanyImg ();
        companyName = getCompanyName ();
    }

    //对recyclerView初始化
    private void initRecyclerView(){
        recyclerView = view.findViewById (R.id.result_rv1);
        positionRecyclerAdapter = new PositionRecyclerAdapter (getActivity ( ) , (ArrayList <Position>) positions,companyName,companyImg);
        recyclerView.setAdapter (positionRecyclerAdapter);
        recyclerView.getDefaultRefreshHeaderView ();
        recyclerView.setPullRefreshEnabled (false);
        recyclerView.setLayoutManager (new LinearLayoutManager (getActivity (), LinearLayout.VERTICAL,false));
        recyclerView.addItemDecoration (new DividerItemDecoration (getActivity (),DividerItemDecoration.VERTICAL));
        positionRecyclerAdapter.setOnItemClickListener (new PositionRecyclerAdapter.OnItemClickListener ( ) {
            @Override
            public void onItemClick( View view , Position data ) {
                Intent intent = new Intent (getActivity (),PositionInfo.class);
                Gson gson = new Gson ();
                intent.putExtra ("职位信息",gson.toJson (data));
                startActivity (intent);
            }
        });
    }




    public static  XRecyclerView getRecyclerViewPosition(){return recyclerView;}


    public static PositionRecyclerAdapter getPositionAdapter(){

        return positionRecyclerAdapter;

    }





    public static List<Position> filterPosition( List <Position> positionList , String query ) {
        query = query.toLowerCase ( );
        final List<Position> filterPositionModeList = new ArrayList <> ();
        for (Position p : positionList){

            String position = p.getName ().toLowerCase ();
            String place = p.getPlace ().toLowerCase ();
            if (position.contains (query) || place.contains (query)){

                filterPositionModeList.add (p);

            }


        }

        return filterPositionModeList;

    }

}
