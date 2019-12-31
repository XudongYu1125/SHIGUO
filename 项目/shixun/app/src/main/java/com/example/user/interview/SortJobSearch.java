package com.example.user.interview;

import java.util.Comparator;

public class SortJobSearch implements Comparator {

    @Override
    public int compare( Object o1 , Object o2 ) {

        JobSearch j1 = (JobSearch) o1;
        JobSearch j2 = (JobSearch) o2;
        int flag = j2.getDate ().compareTo (j1.getDate ());
        return flag;
    }
}
