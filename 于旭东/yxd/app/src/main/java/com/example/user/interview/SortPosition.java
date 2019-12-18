package com.example.user.interview;

import java.util.Comparator;

public class SortPosition implements Comparator {

    @Override
    public int compare( Object o1 , Object o2 ) {
        Position p1 = (Position) o1;
        Position p2 = (Position) o2;
        int flag = p2.getDate ().compareTo (p1.getDate ());
        return flag;
    }
}
