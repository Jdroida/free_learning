package com.example.myapplication.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.bean.Stuff;
import com.example.myapplication.myview.MyImageView;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 放置闲置物品的RecyclerView的适配器
 */
public class AllStuffAdapter extends RecyclerView.Adapter<AllStuffAdapter.ViewHolder> {

    private final List<Stuff> idleGoodsInfoList;
    private final Context mcontext;


    class ViewHolder extends RecyclerView.ViewHolder {
        MyImageView idlePropertyImgMyImageView;
        LinearLayout idleGoodsItemLinearLayout;
        TextView idlePropertyTitleTextView;
        TextView idlePropertyPersonTextView;
        TextView idlePropertyLocationTextView;
        TextView idlePropertyPriceTextView;
        Button del;

        public ViewHolder(@NonNull View view) {
            super(view);
            idleGoodsItemLinearLayout = view.findViewById(R.id.ll_idleGoodsItem);
            idlePropertyImgMyImageView = view.findViewById(R.id.mv_idleGoodsImg);

            idlePropertyTitleTextView = view.findViewById(R.id.tv_idleGoodsTitle);
            idlePropertyPersonTextView = view.findViewById(R.id.tv_idleGoodsPerson);
            idlePropertyLocationTextView = view.findViewById(R.id.tv_idleGoodsLocation);
            idlePropertyPriceTextView = view.findViewById(R.id.tv_idleGoodsPrice);
            del = view.findViewById(R.id.btn_del);
        }
    }

    public AllStuffAdapter(List<Stuff> idleGoodsInfoList, Context mcontext) {
        this.idleGoodsInfoList = idleGoodsInfoList;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_goods_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Stuff goods = this.idleGoodsInfoList.get(position);
        /*if (!TextUtils.isEmpty(goods.getImg())) {
            Glide.with(mcontext).load(goods.getImg()).into(holder.idlePropertyImgMyImageView);
        }*/
        holder.idlePropertyTitleTextView.setText(goods.getName());
        holder.idlePropertyPersonTextView.setText(goods.getOwnerName());
        holder.idlePropertyLocationTextView.setText(goods.getOwnerAddress());
        holder.idlePropertyPriceTextView.setText(goods.getPrice() + "");
        holder.del.setOnClickListener(v -> {
            Stuff stuff = new Stuff(idleGoodsInfoList.get(position).getName());
            stuff.setObjectId(idleGoodsInfoList.get(position).getObjectId());
            stuff.delete(new UpdateListener() {

                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        Toast.makeText(mcontext, "删除成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mcontext, "删除失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    notifyDataSetChanged();
                }

            });
        });
    }


    @Override
    public int getItemCount() {
        return idleGoodsInfoList.size();
    }
}
