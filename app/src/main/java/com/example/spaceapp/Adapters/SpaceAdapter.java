package com.example.spaceapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spaceapp.Models.Space;
import com.example.spaceapp.R;

import java.util.List;

public class SpaceAdapter extends RecyclerView.Adapter<SpaceAdapter.ViewHolder>{
    private List<Space> spaces;
    private Context mContext;
    private LayoutInflater inflater;
    ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public SpaceAdapter(List<Space> spaces, Context mContext) {
        this.spaces = spaces;
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView =View.inflate(parent.getContext(),R.layout.space_item,null);
        return new SpaceAdapter.ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Space current = spaces.get(position);
        holder.txtMessage.setText(current.getMessage());
        holder.txtTimestamp.setText(current.getTimestamp());
        if (itemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{
                        itemClickListener.OnClick(current);
                    }
                    catch (Exception e){
                        e.getMessage();
                    }
                }
            });
        }
    }

    public interface ItemClickListener{
        void OnClick(Space space);
    }



    @Override
    public int getItemCount() {
        return spaces.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtMessage,txtTimestamp;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMessage = itemView.findViewById(R.id.text_view_itemMessage);
            txtTimestamp = itemView.findViewById(R.id.text_view_itemTimestamp);
        }
    }
}
