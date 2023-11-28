package com.example.cognitive_diagnosis_app;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class VFPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> myFragmentList;
    public VFPagerAdapter(@NonNull FragmentManager fm, List<Fragment> fragmentList){
        super(fm);//调用父类构造函数，传递FragmentManager 参数
        //用于确保适配器类内部具有有效的 FragmentManager 实例，从而顺利完成片段管理和展示的任务
        this.myFragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return  myFragmentList == null ? null: myFragmentList.get(position);
    }
    // myFragmentList == null ? null 判断myFragmentList是否为空，如果不为null，执行：后面的代码。
    //myFragmentList.get(position)获得第position个的fragment对象返回

    @Override
    public int getCount() {
        return  myFragmentList == null ? null: myFragmentList.size();
        // 返回片段对象的数量，即myFragmentList列表中的元素个数
    }

}
