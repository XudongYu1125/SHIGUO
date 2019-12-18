package com.example.user.interview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

public class PersonalActivity extends Fragment {
    private LinearLayout llChange;
    private LinearLayout llCertificate;
    private LinearLayout llResume;
    private LinearLayout llSettings;
    private Intent intent;
    private ImageView imageView;
    private TextView textView;
    private User user;
    private SharedPreferences sharedPreferences ;
    private String time;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate( R.layout.fragment_personal,container,false );
        findViews(view);
        sharedPreferences = getActivity().getSharedPreferences("loginInfo", MODE_PRIVATE);
        user = new Gson().fromJson(sharedPreferences.getString("user", null), User.class);
        time = sharedPreferences.getString("time",null);
        if (time == null){
            RequestOptions options = new RequestOptions().error(R.drawable.default_vatar);
            Glide.with(this)
                    .load(Constant.BASE_URL + "avatarimg/" + user.getImageurl())
                    .apply(options)
                    .into(imageView);
        }else {
            RequestOptions options = new RequestOptions().signature(new ObjectKey(time)).error(R.drawable.default_vatar);
            Glide.with(this)
                    .load(Constant.BASE_URL + "avatarimg/" + user.getImageurl())
                    .apply(options)
                    .into(imageView);
        }
        setOnclicked();

        return view;
    }

    private void setOnclicked() {
        llChange.setOnClickListener(new onclicked());
        llCertificate.setOnClickListener(new onclicked());
        llResume.setOnClickListener(new onclicked());
        llSettings.setOnClickListener(new onclicked());
    }

    private void findViews(View view) {
        imageView = view.findViewById(R.id.iv_person);
        textView = view.findViewById(R.id.tv_person_nickname);
        llChange = view.findViewById(R.id.ll_change);
        llCertificate = view.findViewById(R.id.ll_personal_certificate);
        llResume = view.findViewById(R.id.ll_resume_center);
        llSettings = view.findViewById(R.id.ll_settings);
    }
    private class onclicked implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ll_change:
                    intent = new Intent();
                    intent.setClass(getContext(),ChangePersonalActivity.class);
                    startActivityForResult(intent,100);
                    break;
                case R.id.ll_personal_certificate:
                    intent = new Intent();
                    intent.setClass(getContext(),PersonalCertificateActivity.class);
                    startActivity(intent);
                    break;
                case R.id.ll_resume_center:
                    intent = new Intent();
                    intent.setClass(getContext(),ResumeCenterActivity.class);
                    startActivity(intent);
                    break;
                case R.id.ll_settings:
                    intent = new Intent();
                    intent.setClass(getContext(),SettingsActivity.class);
                    startActivity(intent);
                    break;
            }

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String time1 = sharedPreferences.getString("time",null);
        if (time1 == null){
            Log.e("1","2");
            RequestOptions options = new RequestOptions().error(R.drawable.default_vatar);
            Glide.with(this)
                    .load(Constant.BASE_URL + "avatarimg/" + user.getImageurl())
                    .apply(options)
                    .into(imageView);
        }else {
            Log.e("2","1");
            RequestOptions options = new RequestOptions().signature(new ObjectKey(time1)).error(R.drawable.default_vatar);
            Glide.with(this)
                    .load(Constant.BASE_URL + "avatarimg/" + user.getImageurl())
                    .apply(options)
                    .into(imageView);
        }
    }
}
