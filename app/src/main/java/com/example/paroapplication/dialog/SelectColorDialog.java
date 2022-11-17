package com.example.paroapplication.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paroapplication.Action.Action;
import com.example.paroapplication.R;
import com.example.paroapplication.adapter.ImageColorAdapter;
import com.example.paroapplication.bean.ActionShow;
import com.example.paroapplication.bean.Color;

import java.util.ArrayList;

public class SelectColorDialog extends Dialog {
    private Context context;
    private RecyclerView rvColor;
    private Button btnCancel;
    private Button btAdd;
    private ArrayList<Color> mlist=new ArrayList<>();
    private ActionShow  action;
    private int pos;
    private Color mColor;

    public SelectColorDialog(@NonNull Context context, ActionShow action, int pos) {
        super(context, R.style.DialogTheme);
        this.context = context;
        this.action=action;
        this.pos=pos;
    }
    private ChangeColor color;

    public void setColor(ChangeColor color) {
        this.color = color;
    }

    public interface ChangeColor{
        void change(Color color,int pos);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(true);
        Window window = getWindow();
        assert window != null;
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        setContentView(R.layout.select_color_dialog);
        RecyclerView recyclerView = findViewById(R.id.rv_color);
        ImageColorAdapter imageColorAdapter = new ImageColorAdapter(context);
        imageColorAdapter.setSelectColor(new ImageColorAdapter.SelectColor() {
            @Override
            public void SelectColor(Color color,int pos) {
                mColor=color;
                for (int i = 0; i <mlist.size() ; i++) {
                    mlist.get(i).setSelect(false);
                }
                mlist.get(pos).setSelect(true);
                imageColorAdapter.setData(mlist);

            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(context, 8));
        recyclerView.setAdapter(imageColorAdapter);
        initView();
        initData();
        imageColorAdapter.setData(mlist);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null!=color){
                    if (mColor==null){
                        Toast.makeText(context,"Please select a type",Toast.LENGTH_SHORT).show();
                    }else {
                        color.change(mColor,pos);
                        dismiss();
                    }

                }
            }
        });
    }

    private void initData() {
        mlist.add(new Color(context.getResources().getDrawable(R.drawable.ic_baseline_circle_1),1));
        mlist.add(new Color(context.getResources().getDrawable(R.drawable.ic_baseline_circle_2),2));
        mlist.add(new Color(context.getResources().getDrawable(R.drawable.ic_baseline_circle_3),3));
        mlist.add(new Color(context.getResources().getDrawable(R.drawable.ic_baseline_circle_4),4));
        mlist.add(new Color(context.getResources().getDrawable(R.drawable.ic_baseline_circle_5),5));
        mlist.add(new Color(context.getResources().getDrawable(R.drawable.ic_baseline_circle_6),6));
        mlist.add(new Color(context.getResources().getDrawable(R.drawable.ic_baseline_circle_7),7));
        mlist.add(new Color(context.getResources().getDrawable(R.drawable.ic_baseline_circle_8),8));
        mlist.add(new Color(context.getResources().getDrawable(R.drawable.ic_baseline_circle_9),9));
        mlist.add(new Color(context.getResources().getDrawable(R.drawable.ic_baseline_circle_10),10));
        mlist.add(new Color(context.getResources().getDrawable(R.drawable.ic_baseline_circle_11),11));
        mlist.add(new Color(context.getResources().getDrawable(R.drawable.ic_baseline_circle_12),12));
        mlist.add(new Color(context.getResources().getDrawable(R.drawable.ic_baseline_circle_13),13));
        mlist.add(new Color(context.getResources().getDrawable(R.drawable.ic_baseline_circle_14),14));
        mlist.add(new Color(context.getResources().getDrawable(R.drawable.ic_baseline_circle_15),15));
        mlist.add(new Color(context.getResources().getDrawable(R.drawable.ic_baseline_circle_16),16));
        mlist.add(new Color(context.getResources().getDrawable(R.drawable.ic_baseline_circle_17),17));
        mlist.add(new Color(context.getResources().getDrawable(R.drawable.ic_baseline_circle_18),18));
        mlist.add(new Color(context.getResources().getDrawable(R.drawable.ic_baseline_circle_19),19));
        mlist.add(new Color(context.getResources().getDrawable(R.drawable.ic_baseline_circle_20),20));
        mlist.add(new Color(context.getResources().getDrawable(R.drawable.ic_baseline_circle_21),21));
        mlist.add(new Color(context.getResources().getDrawable(R.drawable.ic_baseline_circle_22),22));
        mlist.add(new Color(context.getResources().getDrawable(R.drawable.ic_baseline_circle_23),23));
        mlist.add(new Color(context.getResources().getDrawable(R.drawable.ic_baseline_circle_24),24));

    }


    private void initView() {
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btAdd = (Button) findViewById(R.id.bt_add);
    }
}
