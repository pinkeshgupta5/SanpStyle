package snapcom.pinkesh_gupta.snaplifestyle.Adpater;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import snapcom.pinkesh_gupta.snaplifestyle.Fragment.BrowseFragment;
import snapcom.pinkesh_gupta.snaplifestyle.R;
import snapcom.pinkesh_gupta.snaplifestyle.Utilities.BrowseResponseReceiver;
import snapcom.pinkesh_gupta.snaplifestyle.ViewHolder.ViewHolderBrowse;

/**
 * Created by pinkesh_gupta on 12/8/2017.
 */

public class BrowseAdapter extends RecyclerView.Adapter<ViewHolderBrowse> {
            Context ctx;
            private Bitmap[] mBitArray;
            private Object object;

            public BrowseAdapter(Context applicationContext, Bitmap[] mBitArray) {
                this.ctx = applicationContext;
                this.mBitArray = mBitArray;
            }
            public BrowseAdapter(Context applicationContext, Bitmap[] mBitArray,Object object) {
                this.ctx = applicationContext;
                this.object = object;
                this.mBitArray = mBitArray;
            }

            @Override
            public ViewHolderBrowse onCreateViewHolder(ViewGroup parent, int viewType) {

                View item= LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.browse_item, parent, false);
                ViewHolderBrowse rv = new ViewHolderBrowse(item);

                return rv;
            }

            @Override
            public void onBindViewHolder(final ViewHolderBrowse holder,final int position) {

                //Glide.with(ctx).load(mBitArray[position]).into(holder.imageView);
                holder.imageView.setImageBitmap(mBitArray[position]);
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(object instanceof BrowseFragment){
                            ((BrowseResponseReceiver)object).imageResultResponse(mBitArray[position]);
                        }


                    }
                });


    }

    @Override
    public int getItemCount() {


        return mBitArray.length;




    }
}
