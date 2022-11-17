package com.example.paroapplication.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.paroapplication.GlideEngine;
import com.example.paroapplication.ImageDao.ImageBean;
import com.example.paroapplication.ImageDao.ImageViewModel;
import com.example.paroapplication.LoginActivity;
import com.example.paroapplication.MainActivity;
import com.example.paroapplication.R;
import com.example.paroapplication.RecordDatabase.RecordViewModel;
import com.example.paroapplication.RegisterActivity;
import com.example.paroapplication.StaffDatabase.Staff;
import com.example.paroapplication.adapter.ImageAdapter;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.luck.picture.lib.language.LanguageConfig;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class AccountFragment extends Fragment {
    private Staff staff;
    private Button addPhoto;
    private Button loginOut;
    private ImageViewModel imageViewModel;
    private ArrayList<ImageBean> mlist=new ArrayList<>();
    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
                if (null != mlist && mlist.size() > 0) {
                 imageAdapter.setData(mlist);
                }

            }
        }
    };
    private RecyclerView rvImg;

    public AccountFragment(Staff staff) {
        this.staff=staff;
    }

//    private RecordViewModel recordViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.account_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addPhoto = requireView().findViewById(R.id.bt_add_photo);
        loginOut = requireView().findViewById(R.id.bt_login_out);
        rvImg = requireView().findViewById(R.id.rv_img);
        rvImg.setLayoutManager(new GridLayoutManager(getActivity(),2));
        imageAdapter=new ImageAdapter(getActivity());
        rvImg.setAdapter(imageAdapter);
        imageViewModel = new ViewModelProvider(requireActivity(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(ImageViewModel.class);
        getImageData();

        loginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        addPhoto.setOnClickListener(v -> PictureSelector.create(AccountFragment.this)
                .openGallery(SelectMimeType.ofImage())
                .setMaxSelectNum(9)
                .setLanguage(LanguageConfig.ENGLISH)
                .setImageEngine(GlideEngine.createGlideEngine())
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(ArrayList<LocalMedia> result) {
                        if (null != result && result.size() > 0) {
                            ArrayList<LocalMedia> result1 = result;
                            for (int i = 0; i <result1.size() ; i++) {
                                String path = result1.get(i).getRealPath();
                                ImageBean imageBean=new ImageBean(staff.getStaffName(),path,System.currentTimeMillis());
                                imageViewModel.insertStaff(imageBean);
                            }
                            getImageData();
                        }

                    }

                    @Override
                    public void onCancel() {

                    }
                }));
            }

    private void getImageData() {
        new Thread(() -> {
            mlist.clear();
            List<ImageBean> staffByNameAndPsw = imageViewModel.getStaffByNameAndPsw(staff.getStaffName());
            mlist.addAll(staffByNameAndPsw);
           handler.sendEmptyMessage(100);
        }).start();

    }

}
