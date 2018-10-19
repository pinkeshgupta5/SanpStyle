package snapcom.pinkesh_gupta.snaplifestyle.Fragment;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

import snapcom.pinkesh_gupta.snaplifestyle.Adpater.HomeBottomAdapter;
import snapcom.pinkesh_gupta.snaplifestyle.Adpater.HomeTopAdpater;
import snapcom.pinkesh_gupta.snaplifestyle.R;

import static android.widget.LinearLayout.HORIZONTAL;

/**
 * Created by pinkesh_gupta on 12/8/2017.
 */

public class HomeFragment extends Fragment {
    private View view;
    private Button choosetwin;
    private Bitmap[] topBitArray;
    private Bitmap[] bottoBitArray;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        topBitArray = new Bitmap[10];
        try {
            //these images are stored in the root of "assets"
            topBitArray[0] = getBitmapFromAsset("manny1.png");
            topBitArray[1] = getBitmapFromAsset("manny2.png");
            topBitArray[2] = getBitmapFromAsset("manny3.png");
            topBitArray[3] = getBitmapFromAsset("manny4.png");
            topBitArray[4] = getBitmapFromAsset("manny5.png");
            topBitArray[5] = getBitmapFromAsset("manny6.png");
            topBitArray[6] = getBitmapFromAsset("manny7.png");
            topBitArray[7] = getBitmapFromAsset("manny8.png");
            topBitArray[8] = getBitmapFromAsset("manny9.png");
            topBitArray[9] = getBitmapFromAsset("manny10.png");


        } catch (IOException e) {
            e.printStackTrace();
        }

        bottoBitArray = new Bitmap[5];
        try {
            //these images are stored in the root of "assets"
            bottoBitArray[0] = getBitmapFromAsset("smart_manny1.png");
            bottoBitArray[1] = getBitmapFromAsset("smart_manny2.png");
            bottoBitArray[2] = getBitmapFromAsset("smart_manny3.png");
            bottoBitArray[3] = getBitmapFromAsset("smart_manny4.png");
            bottoBitArray[4] = getBitmapFromAsset("smart_manny5.png");


        } catch (IOException e) {
            e.printStackTrace();
        }
        choosetwin =(Button)view.findViewById(R.id.choosetwin);
        choosetwin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager= getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                /*ScanFragment scanFragment = new ScanFragment();
                fragmentTransaction.replace(R.id.container,scanFragment,"scanscreen");
                fragmentTransaction.addToBackStack("scanscreen");*/
                FaceDetectionFragment faceDetectionFragment = new FaceDetectionFragment();
                faceDetectionFragment.setObject(0);
                fragmentTransaction.replace(R.id.container, faceDetectionFragment, "facedetection");
                fragmentTransaction.addToBackStack("facedetection");
                fragmentTransaction.commit();
            }
        });

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.hometop_recycler_view);
        HomeTopAdpater homeTopAdpater = new HomeTopAdpater(getContext(), topBitArray);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), HORIZONTAL, false));
        recyclerView.setAdapter(homeTopAdpater);

        RecyclerView recyclerViewbottom = (RecyclerView) view.findViewById(R.id.homebottom_recycler_view);
        HomeBottomAdapter homeBottomAdapter = new HomeBottomAdapter(getContext(), bottoBitArray);
        recyclerViewbottom.setLayoutManager(new LinearLayoutManager(getContext(), HORIZONTAL, false));
        recyclerViewbottom.setAdapter(homeBottomAdapter);
    }

    public Bitmap getBitmapFromAsset(String strName) throws IOException {
        AssetManager assetManager = getActivity().getAssets();
        InputStream istr = assetManager.open(strName);
        Bitmap bitmap = BitmapFactory.decodeStream(istr);
        istr.close();
        return bitmap;

    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        view=null;

    }

}
