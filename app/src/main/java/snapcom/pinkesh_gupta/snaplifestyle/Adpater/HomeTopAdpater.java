package snapcom.pinkesh_gupta.snaplifestyle.Adpater;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import snapcom.pinkesh_gupta.snaplifestyle.Fragment.HomeFragment;
import snapcom.pinkesh_gupta.snaplifestyle.R;
import snapcom.pinkesh_gupta.snaplifestyle.ViewHolder.ViewHolderBrowse;
import snapcom.pinkesh_gupta.snaplifestyle.ViewHolder.ViewHolderHome;

/**
 * Created by pinkesh_gupta on 12/11/2017.
 */

public class HomeTopAdpater extends RecyclerView.Adapter<ViewHolderHome>{
    Context ctx;
    private Bitmap[] topBitArray;

    public HomeTopAdpater(Context applicationContext, Bitmap[] topBitArray) {
        this.ctx = applicationContext;
        this.topBitArray= topBitArray;
    }

    @Override
    public ViewHolderHome onCreateViewHolder(ViewGroup parent, int viewType) {

        View item= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.top_image_item, parent, false);
        ViewHolderHome rv = new ViewHolderHome(item);

        return rv;
    }

    @Override
    public void onBindViewHolder(final ViewHolderHome holder,final int position) {

        holder.ImageViewTop.setImageBitmap(topBitArray[position]);


    }

    @Override
    public int getItemCount() {


        return topBitArray.length;




    }
}
