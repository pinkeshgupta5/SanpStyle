package snapcom.pinkesh_gupta.snaplifestyle.Adpater;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import snapcom.pinkesh_gupta.snaplifestyle.R;
import snapcom.pinkesh_gupta.snaplifestyle.ViewHolder.ViewHolderHome;

/**
 * Created by pinkesh_gupta on 12/11/2017.
 */

public class HomeBottomAdapter extends RecyclerView.Adapter<ViewHolderHome>{
    Context ctx;
    private Bitmap[] bottoBitArray;

    public HomeBottomAdapter(Context applicationContext, Bitmap[] bottoBitArray) {
        this.ctx = applicationContext;
        this.bottoBitArray = bottoBitArray;
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

        holder.ImageViewTop.setImageBitmap(bottoBitArray[position]);


    }

    @Override
    public int getItemCount() {


        return bottoBitArray.length;




    }
}
