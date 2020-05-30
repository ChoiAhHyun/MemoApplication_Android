package com.cah.androidmemo;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<MemoData> mList;

    public ListAdapter(List<MemoData> list) {
        this.mList = list;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv_title;
        TextView tv_content;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_content = itemView.findViewById(R.id.tv_content);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) v.getLayoutParams();
        lp.height = parent.getMeasuredWidth() / 2 - 8;
        v.setLayoutParams(lp);
        return new ListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final MemoData item = mList.get(position);
        holder.tv_title.setText(item.getTitle());
        holder.tv_content.setText(item.getContent());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
                intent.putExtra("id", item.getId());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
