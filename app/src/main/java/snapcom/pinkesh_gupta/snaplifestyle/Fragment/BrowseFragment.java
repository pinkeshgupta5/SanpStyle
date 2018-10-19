package snapcom.pinkesh_gupta.snaplifestyle.Fragment;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

import snapcom.pinkesh_gupta.snaplifestyle.Adpater.BrowseAdapter;
import snapcom.pinkesh_gupta.snaplifestyle.R;
import snapcom.pinkesh_gupta.snaplifestyle.Utilities.BrowseResponseReceiver;
import snapcom.pinkesh_gupta.snaplifestyle.Utilities.NoScanResultException;
import snapcom.pinkesh_gupta.snaplifestyle.Utilities.ScanResultReceiver;

import static android.widget.LinearLayout.HORIZONTAL;

/**
 * Created by pinkesh_gupta on 12/8/2017.
 */

@SuppressLint("ValidFragment")
public class BrowseFragment extends Fragment  implements ScanResultReceiver,BrowseResponseReceiver {
    private View view;
    private ImageView imageView_id;
    private TextView textView_smiley;
    private TextView textView_size;
    private TextView textView_lefteye;
    private String data;
    ScanResultReceiver scanResultReceiver;
    private Bitmap[] mBitArray;
    private String scannedData;
    private String smiley;
    private String facedetectiondata;
    private String lefteye;

    public BrowseFragment() {
    }
    public BrowseFragment(ScanResultReceiver scanResultReceiver) {
        this.scanResultReceiver=scanResultReceiver;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_browse, container, false);
       /* Bundle b = getArguments();
        if(b!=null)
        {
            data = b.getString("scandata");

        }*/
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mBitArray = new Bitmap[7];
        try {
            //these images are stored in the root of "assets"
            mBitArray[0] = getBitmapFromAsset("style1.png");
            mBitArray[1] = getBitmapFromAsset("style2.png");
            mBitArray[2] = getBitmapFromAsset("Style3.png");
            mBitArray[3] = getBitmapFromAsset("style4.png");
            mBitArray[4] = getBitmapFromAsset("style5.png");
            mBitArray[5] = getBitmapFromAsset("style6.png");
            mBitArray[6] = getBitmapFromAsset("style7.png");


        } catch (IOException e) {
            e.printStackTrace();
        }
        imageView_id = (ImageView) view.findViewById(R.id.imageView_id);
        textView_size = (TextView) view.findViewById(R.id.textView_size);
        textView_smiley=(TextView)view.findViewById(R.id.textView_smiley);
        textView_lefteye=(TextView)view.findViewById(R.id.textView_lefteye);
        //textView_size.setText(scannedData);
        textView_smiley.setText(smiley);
        textView_size.setText(facedetectiondata);
        textView_lefteye.setText(lefteye);



        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.browse_recycler_view);
        BrowseAdapter browseAdapter = new BrowseAdapter(getContext(), mBitArray,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), HORIZONTAL, false));
        recyclerView.setAdapter(browseAdapter);
        recyclerView.hasFixedSize();
    }

    public Bitmap getBitmapFromAsset(String strName) throws IOException {
        AssetManager assetManager = getActivity().getAssets();
        InputStream istr = assetManager.open(strName);
        Bitmap bitmap = BitmapFactory.decodeStream(istr);
        istr.close();
        return bitmap;

    }

    private String getJsonFormatFromAssert(String strName) {
        try {
            InputStream is = getActivity().getAssets().open("orderdetail.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return strName;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        view = null;

    }


    @Override
    public void scanResultData(String codeContent) {
        scannedData=codeContent;
//        scanResultReceiver.scanResultData(codeContent);
    }

    @Override
    public void facedetectionResultData(String smileyData, String lefteyeData, String righteyeData) {
        smiley=smileyData;
        facedetectiondata=lefteyeData;
        lefteye=righteyeData;
    }

    @Override
    public void scanResultData(NoScanResultException noScanData) {

    }

    @Override
    public void imageResultResponse(Object img_bitmap) {
        imageView_id.setImageBitmap((Bitmap) img_bitmap);
    }
}
