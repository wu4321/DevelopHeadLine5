package com.cqc.develophead.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cqc.develophead.R;
import com.cqc.develophead.bean.Frag1Bean;

import java.util.List;

/**
 * Created by ${cqc} on 2016/9/14.
 * context可以不用传：parent.getContext()
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADVIEW = 0;
    private static final int TYPE_NORMAL = 1;

    private Context context;
    private List<Frag1Bean> list;
    private View headView;

    public MyRecyclerAdapter(Context context, List<Frag1Bean> list) {
        super();
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (headView != null && viewType == TYPE_HEADVIEW) {
//            return new MyViewHolder(headView);
//        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_frag1, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        MyViewHolder holder = (MyViewHolder) viewHolder;
//        if (getItemViewType(position) == TYPE_HEADVIEW){
//            return;
//        }

        Frag1Bean bean = list.get(position);
        holder.tv_title.setText("" + bean.title);
        holder.tv_like.setText("" + bean.likeNumbers);
        holder.tv_comment.setText("" + bean.commentsNumbers);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //在viewpager添加headView
    public void setHeadView(View headView) {
        this.headView = headView;
        notifyItemInserted(0);//插入到第0个位置
    }

//    @Override
//    public int getItemViewType(int position) {
//        if (headView == null) {
//            return TYPE_NORMAL;
//        }
//        if (position == 0) {
//            return TYPE_HEADVIEW;
//        }
//        return TYPE_NORMAL;
//    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_title;
        public TextView tv_like;
        public TextView tv_comment;

        public MyViewHolder(View itemView) {
            super(itemView);
//            if (itemView == headView) {
//                return;
//            }
            tv_title = (TextView) itemView.findViewById(R.id.tv_item_title);
            tv_like = (TextView) itemView.findViewById(R.id.tv_item_like);
            tv_comment = (TextView) itemView.findViewById(R.id.tv_item_comment);
        }
    }
}
