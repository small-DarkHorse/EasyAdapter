package com.hz.android.easyadapter.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hz.android.easyadapter.EasyAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.forward.androids.views.ScrollPickerView;
import cn.forward.androids.views.StringScrollPicker;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<String> list = new ArrayList<>();
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.test_recyclerView);
        final View container_multiOptions = findViewById(R.id.container_multSelectMode_options);
        final View container_singleOption = findViewById(R.id.container_SingleSelectMode_option);
        StringScrollPicker stringScrollPicker = (StringScrollPicker) findViewById(R.id.max_select_count_pv);

        stringScrollPicker.setData(new ArrayList<CharSequence>(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12")));
        stringScrollPicker.setOnSelectedListener(new ScrollPickerView.OnSelectedListener() {
            @Override
            public void onSelected(ScrollPickerView scrollPickerView, int position) {
                Toast.makeText(MainActivity.this, "stringScrollPicker:" + position, Toast.LENGTH_SHORT).show();
                myAdapter.setMaxSelectedCount(position);

            }
        });
        stringScrollPicker.setSelectedPosition(0);
        myAdapter = new MyAdapter();
        list.addAll(Arrays.asList("李子", "樱桃", "葡萄", "菠萝", "椰子", "草莓", "苹果", "西瓜", "香蕉", "柚子", "柠檬", "桔子", "芒果", "枣子", "猕猴桃", "水蜜桃", "荔枝", "杨梅"));

        Spinner select_mode_spinner = (Spinner) findViewById(R.id.spinner_mode_selected);
        select_mode_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "mode position: " + position, Toast.LENGTH_SHORT).show();
                if (position == 0) {
                    myAdapter.setSelectMode(EasyAdapter.SelectMode.CLICK);
                    container_singleOption.setVisibility(View.GONE);
                    container_multiOptions.setVisibility(View.GONE);
                } else if (position == 1) {
                    myAdapter.setSelectMode(EasyAdapter.SelectMode.SINGLE_SELECT);
                    container_singleOption.setVisibility(View.VISIBLE);
                    container_multiOptions.setVisibility(View.GONE);
                } else if (position == 2) {
                    myAdapter.setSelectMode(EasyAdapter.SelectMode.MULTI_SELECT);
                    container_singleOption.setVisibility(View.GONE);
                    container_multiOptions.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //监听单选
        myAdapter.setOnItemSingleSelectListener(new EasyAdapter.OnItemSingleSelectListener() {

            @Override
            public void onSelected(int itemPosition, boolean isSelected) {
                Toast.makeText(MainActivity.this, "selectedPosition:" + itemPosition  +" == "+ myAdapter.getSingleSelectedPosition(), Toast.LENGTH_SHORT).show();

            }
        });

        /*// 监听点击事件
        myAdapter.setOnItemClickListener(new EasyAdapter.OnItemClickListener() {
            @Override
            public void onSelected(int clickPosition) {
                Toast.makeText(MainActivity.this, "clickPosition:" + clickPosition, Toast.LENGTH_SHORT).show();
            }
        });
        //监听单选
        myAdapter.setOnItemSingleSelectListener(new EasyAdapter.OnItemSingleSelectListener() {
            @Override
            public void onSelected(int selectedPosition) {
                Toast.makeText(MainActivity.this, "selectedPosition:" + selectedPosition, Toast.LENGTH_SHORT).show();

            }
        });
        //监听多选
        myAdapter.setOnItemMultiSelectListener(new EasyAdapter.OnItemMultiSelectListener() {
            @Override
            public void onSelected(int multiSelectedPosition) {
                Toast.makeText(MainActivity.this, "multiSelectedPosition:" + multiSelectedPosition, Toast.LENGTH_SHORT).show();
            }
        });
*/
        //select_mode_spinner.setSelection(0); //点击
        //select_mode_spinner.setSelection(1); //单选 此时RecyclerView+Adapter就是一个单选框
        select_mode_spinner.setSelection(2); //多选
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); //把列表设置成水平滚动
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(myAdapter);

        // myAdapter.setSelected(1, 3, 5); // 多选模式下 和单选模式 都一样可以调用

    }

    //监听按钮
    public void btnShowSingleSelected(View view) {
        int singleSelectedPosition = myAdapter.getSingleSelectedPosition();
        Toast.makeText(MainActivity.this, "btnShowSingleSelected:" + singleSelectedPosition, Toast.LENGTH_SHORT).show();

    }

    public void btnSelectedAll(View view) {
        myAdapter.selectAll();
    }

    public void btnReverseSelected(View view) {
        myAdapter.reverseSelected();
    }

    public void btnShowMultiSelected(View view) {
        List<Integer> selectedList = myAdapter.getMultiSelectedPosition();
        Toast.makeText(MainActivity.this, "btnShowMultiSelected:" + selectedList.toString(), Toast.LENGTH_SHORT).show();
    }

    public void btnClearSelected(View view) {
        myAdapter.clearSelected();
    }

    // 继承EasyAdapter
    private class MyAdapter extends EasyAdapter<MyViewHolder> {
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(getApplicationContext(), R.layout.item_show_text, null);
            return new MyViewHolder(view);
        }

        @Override
        public void whenBindViewHolder(MyViewHolder holder, int position) {
            holder.textView.setTag(position);//绑定
            holder.textView.setText(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    private static class MyViewHolder extends ViewHolder {
        private TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.txt_item);
        }
    }
}
