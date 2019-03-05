package eu.benayoun.badass.utility.model;

import java.util.ArrayList;

public class BadassList<E>
{
    int currentIndex;
    ArrayList<E> arrayList;

    public BadassList(ArrayList<E> arrayList)
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


   public int totalSize()
   {
        return arrayList.size();
   }

   public int remainingElements()
   {
       return arrayList.size()-1-currentIndex;
   }

   public E getLastElement()
    {
        return BadassUtilsArrayList.getLastElement(arrayList);
    }
}