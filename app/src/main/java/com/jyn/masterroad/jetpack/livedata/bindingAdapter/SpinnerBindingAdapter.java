package com.jyn.masterroad.jetpack.livedata.bindingAdapter;

import android.view.View;
import android.widget.AbsSpinner;
import android.widget.AdapterView;
import android.widget.SpinnerAdapter;

import androidx.annotation.RestrictTo;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;


import java.util.List;

/*
 * databinding之自定义双向绑定(二)
 * https://blog.csdn.net/qinbin2015/article/details/104779002?spm=1001.2014.3001.5501
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public class SpinnerBindingAdapter {

    @BindingAdapter({"android:select"})
    public static <T> void setSelect(AbsSpinner view, T entries) {
        if (null == entries) return;
        if (view.getSelectedItem().equals(entries)) return;
        SpinnerAdapter adapter = view.getAdapter();
        if (adapter.getCount() > 0) view.setSelection(0);
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).equals(entries)){
                view.setSelection(i);
            }
        }
    }

    @InverseBindingAdapter(attribute = "android:select", event = "android:selectAttrChanged")
    public static Object getSelect(AbsSpinner view) {
        if (view == null) return null;
        return view.getSelectedItem();
    }

    @BindingAdapter(value = {"android:onItemSelectedListener", "android:selectAttrChanged"},
            requireAll = false)
    public static void setListener(AbsSpinner view, final AdapterView.OnItemSelectedListener onItemSelectedListener,
                                   final InverseBindingListener selectAttrChanged) {
        if (selectAttrChanged == null) {
            view.setOnItemSelectedListener(onItemSelectedListener);
        } else {
            view.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (onItemSelectedListener != null) {
                        onItemSelectedListener.onItemSelected(parent, view, position, id);
                    }
                    selectAttrChanged.onChange();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    if (onItemSelectedListener != null) {
                        onItemSelectedListener.onNothingSelected(parent);
                    }
                }
            });
        }
    }
}
