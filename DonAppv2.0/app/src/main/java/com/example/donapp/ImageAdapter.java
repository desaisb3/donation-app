package com.example.donapp;

import android.content.Context;
import android.media.Image;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.imageViewHolder> {

    private Context mContext;
    private List<Upload> mUploads;
    private OnItemClickListener mListener;

    //TO GET THE VALUES FROM OUTSIDE TO OUR ADAPTER WE USE THIS
    public ImageAdapter(Context context, List<Upload> uploads){
        mContext=context;
        mUploads=uploads;
    }

    @NonNull
    @Override
    public imageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item,parent,false);

        return new imageViewHolder(v);
    }

    //TO GET DATA OUT OF UPLOAD ITEMS AND INTO OUR CARD ITEMS
    @Override
    public void onBindViewHolder(@NonNull imageViewHolder holder, int position) {
        Upload uploadCurrent = mUploads.get(position);
        holder.textViewName.setText(uploadCurrent.getName());

        Picasso.with(mContext).load(uploadCurrent.getImageUrl()).placeholder(R.drawable.ic_launcher_background).fit().centerInside().into(holder.imageView);

        holder.itemDesc.setText(uploadCurrent.getItemDesc());

        holder.phoneNum.setText(uploadCurrent.getPhoneNum());

        holder.email.setText(uploadCurrent.getEmail());
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class imageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public TextView textViewName;
        public ImageView imageView;
        public TextView itemDesc;
        public TextView phoneNum;
        public TextView email;

        public imageViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.text_view_name);
            imageView = itemView.findViewById(R.id.image_view_upload);
            itemDesc = itemView.findViewById(R.id.item_desc);
            phoneNum=itemView.findViewById(R.id.phone_num);
            email=itemView.findViewById(R.id.email_add);


            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener!=null){
                int position = getAdapterPosition();

                //THIS IS TO MAKE SURE THAT CLICKED POSITION IS VALID
                if (position!=RecyclerView.NO_POSITION){
                    mListener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            MenuItem addToFavorites = menu.add(Menu.NONE, 1, 1, "Add to Favorites");
            MenuItem delete = menu.add(Menu.NONE,2,2,"Delete this item");
            MenuItem notInterested= menu.add(Menu.NONE,3,3,"Not Interested in this Item");

            addToFavorites.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
            notInterested.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListener!=null){
                int position = getAdapterPosition();

                //THIS IS TO MAKE SURE THAT CLICKED POSITION IS VALID
                if (position!=RecyclerView.NO_POSITION){
                    switch(item.getItemId()){
                        case 1:
                            mListener.addToFavorites(position);
                            return true;
                        case 2:
                            mListener.onDeleteClick(position);
                            return true;
                        case 3:
                            mListener.notInterested(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);

        void addToFavorites(int position);

        void onDeleteClick(int position);

        void notInterested(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }
}
