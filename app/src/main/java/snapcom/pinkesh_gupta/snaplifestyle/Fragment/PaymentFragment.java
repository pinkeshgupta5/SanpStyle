package snapcom.pinkesh_gupta.snaplifestyle.Fragment;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.IOException;
import java.io.InputStream;
import snapcom.pinkesh_gupta.snaplifestyle.R;

/**
 * Created by pinkesh_gupta on 12/11/2017.
 */

public class PaymentFragment extends Fragment {

    private View view;
    private Button button_biometric;
    private Button button_Facial;
    private LinearLayout linearLayout;
    private LinearLayout linearlayoutsmiley;
    private ImageView imageView_biometric;
    private ImageView imageViewsmiley;
    private Bitmap SmileyBitImage;



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fregment_payment, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        linearLayout = (LinearLayout) view.findViewById(R.id.linearlayout);
        imageView_biometric = (ImageView) view.findViewById(R.id.imageView_biometric);
        linearlayoutsmiley = (LinearLayout) view.findViewById(R.id.linearlayoutsmiley);
        try {
            SmileyBitImage = getBitmapFromAsset("smiley.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageViewsmiley = (ImageView) view.findViewById(R.id.imageViewsmiley);
        imageViewsmiley.setImageBitmap(SmileyBitImage);
        button_biometric = (Button) view.findViewById(R.id.button_biometric);
        button_biometric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayout.setVisibility(view.VISIBLE);
                linearlayoutsmiley.setVisibility(view.GONE);
                imageView_biometric.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        linearLayout.setVisibility(view.GONE);
                        linearlayoutsmiley.setVisibility(view.VISIBLE);

                    }
                });
            }
        });

        button_Facial = (Button) view.findViewById(R.id.button_Facial);
        button_Facial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FaceDetectionFragment faceDetectionFragment = new FaceDetectionFragment();
                faceDetectionFragment.setObject(1);
                fragmentTransaction.replace(R.id.container, faceDetectionFragment, "facedetection");
                fragmentTransaction.addToBackStack("facedetection");
                fragmentTransaction.commit();
                //Intent i = new Intent(getActivity(), CameraActivity.class);
                // getActivity().startActivity(i);
            }
        });

    }

    public Bitmap getBitmapFromAsset(String strName) throws IOException {
        AssetManager assetManager = getActivity().getAssets();
        InputStream istr = assetManager.open(strName);
        Bitmap bitmap = BitmapFactory.decodeStream(istr);
        istr.close();
        return bitmap;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        view = null;

    }
}
