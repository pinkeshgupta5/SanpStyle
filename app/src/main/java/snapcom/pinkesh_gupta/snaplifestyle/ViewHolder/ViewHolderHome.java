package snapcom.pinkesh_gupta.snaplifestyle.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import snapcom.pinkesh_gupta.snaplifestyle.R;

/**
 * Created by pinkesh_gupta on 12/11/2017.
 */

public class ViewHolderHome extends RecyclerView.ViewHolder {
    public ImageView ImageViewTop;

    public ViewHolderHome(View itemView) {

        super(itemView);

        ImageViewTop=(ImageView)itemView.findViewById(R.id.imageViewTop);

    }
}