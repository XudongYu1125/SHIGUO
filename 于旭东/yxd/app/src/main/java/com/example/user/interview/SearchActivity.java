package com.example.user.interview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import static com.example.user.interview.InterviewActivity.getJobSearchList;
import static com.example.user.interview.InterviewActivity.getPositionList;
import static com.example.user.interview.SearchResultFragment1.filterPosition;
import static com.example.user.interview.SearchResultFragment1.getPositionAdapter;
import static com.example.user.interview.SearchResultFragment1.getRecyclerViewPosition;
import static com.example.user.interview.SearchResultFragment2.filterJobSearch;
import static com.example.user.interview.SearchResultFragment2.getJobSearchAdapter;
import static com.example.user.interview.SearchResultFragment2.getRecyclerViewJobSearch;


public class SearchActivity extends AppCompatActivity {

    private ImageView cancle;
    private TabLayout tabLayout;
    private SearchView sv;
    private ViewPager viewPager;
    private String[] tabTitles = new String[]{
            "职位信息" ,
            "求职信息"
    };

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.search_layout);
        initTab ( );
        setCancle ( );
        initSearchView ();
    }


    //取消搜索
    public void setCancle() {
        cancle = findViewById (R.id.search_cancle);
        cancle.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick( View v ) {
                finish ( );
            }
        });
    }


    public void initTab() {
        List <Fragment> fragments = new ArrayList <> ( );
        TabLayout tabLayout = findViewById (R.id.search_tab);
        viewPager = findViewById (R.id.search_vp);
        viewPager.setOffscreenPageLimit (0);
        for (String tab : tabTitles) {
            tabLayout.addTab (tabLayout.newTab ( ).setText (tab));
        }

        fragments.add (new SearchResultFragment1 ( ));
        fragments.add (new SearchResultFragment2 ( ));
        FragmentAdapter fragmentAdapter = new FragmentAdapter (getSupportFragmentManager ( ) , fragments , tabTitles);
        viewPager.setAdapter (fragmentAdapter);
        viewPager.setOffscreenPageLimit (0);
        tabLayout.setupWithViewPager (viewPager);
    }

    private void initSearchView(){

        SearchView sv = findViewById (R.id.search_sv);
        sv.setOnQueryTextListener (new SearchView.OnQueryTextListener ( ) {
            @Override
            public boolean onQueryTextSubmit( String query ) {
                return true;
            }

            @Override
            public boolean onQueryTextChange( String newText ) {



                if (viewPager.getCurrentItem () == 0){

                    PositionRecyclerAdapter positionRecyclerAdapter = getPositionAdapter();
                    List<Position> positionList = filterPosition(getPositionList(),newText);
                    XRecyclerView rvPosition = getRecyclerViewPosition ();
                    positionRecyclerAdapter.setFilter (positionList);
                    positionRecyclerAdapter.animateTo (positionList);
                    rvPosition.scrollToPosition (0);

                }
                if (viewPager.getCurrentItem () == 1){

                    JobSearchRecyclerAdapter jobSearchRecyclerAdapter = getJobSearchAdapter();
                    List<JobSearch> jobSearchList = filterJobSearch (getJobSearchList (),newText);
                    XRecyclerView rvJobSearch = getRecyclerViewJobSearch ();

                    jobSearchRecyclerAdapter.setFilter (jobSearchList);
                    jobSearchRecyclerAdapter.animateTo (jobSearchList);
                    rvJobSearch.scrollToPosition (0);
                }

                return true;
            }
        });





    }



}
