package com.hahazql.util.auto.config;

/**
 * Created by zql on 2015/10/20.
 */
public abstract class AutoConfigInterface implements Comparable<AutoConfigInterface>
{
    @FieldConfig(value="专用于排序的值")
    private int sort;

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int compareTo(AutoConfigInterface o)
    {
        return this.getSort() - o.getSort();
    }

}
