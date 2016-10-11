package com.halo.haloandroidframework.expandLayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.halo.haloandroidframework.R;
import com.halo.uiview.expandableView.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank1 on 2016/10/11.
 */

public class ExpandLayoutActivity extends Activity implements View.OnClickListener{
    private ExpandableLayout expandableLayout0;
    private ListView kkkView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expandable_sample);
        expandableLayout0 = (ExpandableLayout) findViewById(R.id.expandable_layout_0);
        kkkView = (ListView) findViewById(R.id.kkk);
        kkkView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,getData()));
        findViewById(R.id.expand_button).setOnClickListener(this);

    }

    private List<String> getData(){

        List<String> data = new ArrayList<String>();
        data.add("测试数据1");
        data.add("测试数据2");
        data.add("测试数据3");
        data.add("测试数据4");

        return data;
    }

    @Override
    public void onClick(View view) {
        if (expandableLayout0.isExpanded()) {
            expandableLayout0.collapse();
        }
        else {
            expandableLayout0.expand();
        }
    }
}
