package eu.benayoun.badass.utility.model;

import java.util.ArrayList;

public class SmartArrayListCtrl<E>
{
    int currentIndex;
    ArrayList<E> arrayList;

    public SmartArrayListCtrl(ArrayList<E> arrayList)
    {
        this.arrayList = arrayList;
        currentIndex =0;
    }

    // Info on list state
    public boolean isOutOfBound()
    {
        return currentIndex>arrayList.size()-1;
    }

    public int getCurrentIndex()
    {
        return currentIndex;
    }

    // get Object
    public E get()
    {
        if (isOutOfBound()) return null;
        else return arrayList.get(currentIndex);
    }

    public void advance()
    {
        if (isOutOfBound()==false) currentIndex++;
    }

    public E getLastElement()
    {
        return ArrayListUtils.getLastElement(arrayList);
    }
}