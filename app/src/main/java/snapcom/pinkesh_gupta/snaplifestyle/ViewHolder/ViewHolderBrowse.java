package snapcom.pinkesh_gupta.snaplifestyle.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import snapcom.pinkesh_gupta.snaplifestyle.R;

/**
 * Created by pinkesh_gupta on 12/8/2017.
 */

public class ViewHolderBrowse extends RecyclerView.ViewHolder {
    public ImageView imageView;

    public ViewHolderBrowse(View itemView) {

        super(itemView);

        imageView=(ImageView)itemView.findViewById(R.id.imageView);
    }
}
