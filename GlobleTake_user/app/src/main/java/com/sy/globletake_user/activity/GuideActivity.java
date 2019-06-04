package com.sy.globletake_user.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.sy.globletake_user.Other.Const;
import com.sy.globletake_user.R;
import com.sy.globletake_user.utils.SharePreferenceUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private List<View> mViews=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
//        XuniKeyWord.assistActivity(findViewById(android.R.id.content));
        //拿到首页数据
//        Intent intent = getIntent();
//        final Bundle bundleExtra = intent.getExtras();

        mViewPager= (ViewPager) findViewById(R.id.guide_pager);


        Bitmap bitmap1 = readBitMap(this, R.drawable.guid1);
        Bitmap bitmap2 = readBitMap(this, R.drawable.guid2);
        Bitmap bitmap3 = readBitMap(this, R.drawable.guid3);
        View view1=getLayoutInflater().inflate(R.layout.guide_item, null);
        ImageView imageView1= (ImageView) view1.findViewById(R.id.guide_img);
        imageView1.setImageBitmap(bitmap1);

        View view2=getLayoutInflater().inflate(R.layout.guide_item, null);
        ImageView imageView2= (ImageView) view2.findViewById(R.id.guide_img);
        imageView2.setImageBitmap(bitmap2);

        View view3=getLayoutInflater().inflate(R.layout.guide_item, null);
        ImageView imageView3= (ImageView) view3.findViewById(R.id.guide_img);
        imageView3.setImageBitmap(bitmap3);

        mViews.add(view1);
        mViews.add(view2);
        mViews.add(view3);
        GuideAdapter guideAdapter=new GuideAdapter();
        mViewPager.setAdapter(guideAdapter);
        view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                join();
                Intent intent1=new Intent();
//                intent1.putExtras(bundleExtra);
                intent1.setClass(GuideActivity.this, MainActivity.class);
                startActivity(intent1);
                GuideActivity.this.finish();
            }
        });
    }
    public class GuideAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViews.get(position));

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViews.get(position));
            return mViews.get(position);
        }
    }

    /**
     * 以最省内存的方式读取本地资源的图片
     * @param context
     *@param resId
     * @return
     */
    public static Bitmap readBitMap(Context context, int resId){
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
//获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }


    private void join() {
        //记录页已打开过
        SharePreferenceUtils.setParam(getApplicationContext(), Const.IS_FIRST, true);
    }
}
