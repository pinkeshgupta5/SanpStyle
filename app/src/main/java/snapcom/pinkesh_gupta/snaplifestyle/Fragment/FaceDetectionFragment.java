package snapcom.pinkesh_gupta.snaplifestyle.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import snapcom.pinkesh_gupta.snaplifestyle.BuildConfig;

import snapcom.pinkesh_gupta.snaplifestyle.R;

import snapcom.pinkesh_gupta.snaplifestyle.Utilities.ScanResultReceiver;

import static android.app.Activity.RESULT_OK;


import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.Landmark;

/**
 * Created by pinkesh_gupta on 12/12/2017.
 */

public class FaceDetectionFragment extends Fragment {
    private Context ctx;
    private View view;
    private static final String LOG_TAG = "FACE API";
    private static final int PHOTO_REQUEST = 10;
    private TextView scanResults;
    private ImageView imageView;
    private Uri imageUri;
    private com.google.android.gms.vision.face.FaceDetector detector;
    private static final int REQUEST_WRITE_PERMISSION = 20;
    private static final String SAVED_INSTANCE_URI = "uri";
    private static final String SAVED_INSTANCE_BITMAP = "bitmap";
    private static final String SAVED_INSTANCE_RESULT = "result";
    Bitmap editedBitmap;
    Button button;
    Button button_browse;
    SparseArray<Face> faces;
    Face face;
    public static String first;
    public static String second;
    public static String third;
    private Object object;
    int type = 1;


    private static final int REQUEST_IMAGE_CAPTURE = 1;
    public final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 1;
    private String CAMERA_PERMISSION = android.Manifest.permission.CAMERA;
    private String READ_EXTERNAL_STORAGE_PERMISSION = android.Manifest.permission.READ_EXTERNAL_STORAGE;
    private String WRITE_EXTERNAL_STORAGE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE;

//requests for runtime time permissions


    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }


    // for security permissions
    @DialogType
    private int mDialogType;
    private String mRequestPermissions = "We are requesting the camera and Gallery permission as it is absolutely necessary for the app to perform it\'s functionality.\nPlease select \"Grant Permission\" to try again and \"Cancel \" to exit the application.";
    private String mRequsetSettings = "You have rejected the camera and Gallery permission for the application. As it is absolutely necessary for the app to perform you need to enable it in the settings of your device.\nPlease select \"Go to settings\" to go to application settings in your device and \"Cancel \" to exit the application.";
    private String mGrantPermissions = "Grant Permissions";
    private String mCancel = "Cancel";
    private String mGoToSettings = "Go To Settings";
    private String mPermissionRejectWarning = "Cannot Proceed Without Permissions</string>";


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_face_detection, container, false);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (object != null) {
            type = (Integer) object;
        }
        button = (Button) view.findViewById(R.id.button);
        button.setVisibility(View.VISIBLE);
        button_browse = (Button) view.findViewById(R.id.button_browse);
        scanResults = (TextView) view.findViewById(R.id.results);
        imageView = (ImageView) view.findViewById(R.id.scannedResults);
        if (savedInstanceState != null) {
            editedBitmap = savedInstanceState.getParcelable(SAVED_INSTANCE_BITMAP);
            if (savedInstanceState.getString(SAVED_INSTANCE_URI) != null) {
                imageUri = Uri.parse(savedInstanceState.getString(SAVED_INSTANCE_URI));
            }
            imageView.setImageBitmap(editedBitmap);
            scanResults.setText(savedInstanceState.getString(SAVED_INSTANCE_RESULT));
        }
        detector = new FaceDetector.Builder(getActivity())
                .setTrackingEnabled(false)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .build();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    checkMultiplePermissions(REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS, getActivity());
                    ActivityCompat.requestPermissions(getActivity(), new
                            String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
//                    takePicture();
                } else {
                    ActivityCompat.requestPermissions(getActivity(), new
                            String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
                    takePicture();
                }
            }
        });

    }

    // @Override
    /*public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_WRITE_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePicture();
                } else {
                    Toast.makeText(getActivity(), "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
        }
    }*/

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                    takePicture();
                } else {
                    boolean showRationale1 = shouldShowRequestPermissionRationale(CAMERA_PERMISSION);
                    boolean showRationale2 = shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE_PERMISSION);
                    boolean showRationale3 = shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE_PERMISSION);
                    if (showRationale1 && showRationale2 && showRationale3) {
                        //explain to user why we need the permissions
                        Toast.makeText(getActivity(), mRequestPermissions + mGoToSettings + mCancel, Toast.LENGTH_SHORT).show();
                        // mDialogType = DialogType.DIALOG_DENY;
                        // Show dialog with
                        //openAlertDialog(mRequestPermissions, mGrantPermissions, mCancel, (OnDialogButtonClickListener) this, getContext());
                    } else {
                        //explain to user why we need the permissions and ask him to go to settings to enable it
                        Toast.makeText(getActivity(), mRequestPermissions + mGoToSettings + mCancel, Toast.LENGTH_SHORT).show();
                        //mDialogType = DialogType.DIALOG_NEVER_ASK;
                        //openAlertDialog(mRequsetSettings, mGoToSettings, mCancel, (OnDialogButtonClickListener) this, getContext());
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_REQUEST && resultCode == RESULT_OK) {
            launchMediaScanIntent();
            try {
                scanFaces();
            } catch (Exception e) {
                Toast.makeText(getActivity(), "Failed to load Image", Toast.LENGTH_SHORT).show();
                Log.e(LOG_TAG, e.toString());
            }
        }
    }


    private void scanFaces() throws Exception {
        Bitmap bitmap = decodeBitmapUri(getActivity(), imageUri);
        if (detector.isOperational() && bitmap != null) {
            Log.v("bitmap", ">>>>>>" + bitmap);
            Log.v("detector", ">>>>>>" + detector);
            editedBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                    .getHeight(), bitmap.getConfig());
            float scale = getResources().getDisplayMetrics().density;
            Log.v("scale", ">>>>>>" + scale);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.rgb(255, 61, 61));
            paint.setTextSize((int) (14 * scale));
            paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(3f);
            Canvas canvas = new Canvas(editedBitmap);
            Log.v("canvas", ">>>>>>" + canvas);
            canvas.drawBitmap(bitmap, 0, 0, paint);
            Frame frame = new Frame.Builder().setBitmap(editedBitmap).build();
            Log.v("frame", ">>>>>>" + frame);
            faces = detector.detect(frame);
            Log.v("faces", ">>>>>>" + faces);
            scanResults.setText(null);
            for (int index = 0; index < faces.size(); ++index) {
                face = faces.valueAt(index);
                first = String.valueOf(face.getIsSmilingProbability());
                second = String.valueOf(face.getIsLeftEyeOpenProbability());
                third = String.valueOf(face.getIsRightEyeOpenProbability());
                canvas.drawRect(
                        face.getPosition().x,
                        face.getPosition().y,
                        face.getPosition().x + face.getWidth(),
                        face.getPosition().y + face.getHeight(), paint);
                scanResults.setText(scanResults.getText() + "Face " + (index + 1) + "\n");
                scanResults.setText(scanResults.getText() + "Smile probability:" + "\n");
                scanResults.setText(scanResults.getText() + first + "\n");
                scanResults.setText(scanResults.getText() + "Left Eye Open Probability: " + "\n");
                scanResults.setText(scanResults.getText() + second + "\n");
                scanResults.setText(scanResults.getText() + "Right Eye Open Probability: " + "\n");
                scanResults.setText(scanResults.getText() + third + "\n");
                scanResults.setText(scanResults.getText() + "---------" + "\n");

                for (Landmark landmark : face.getLandmarks()) {
                    int cx = (int) (landmark.getPosition().x);
                    int cy = (int) (landmark.getPosition().y);
                    canvas.drawCircle(cx, cy, 5, paint);
                }
            }

            if (faces.size() == 0) {
                scanResults.setText("Scan Failed: Found nothing to scan");
                //   if(editedBitmap!=null)
                //  imageView.setImageBitmap(editedBitmap);
            } else {
                imageView.setImageBitmap(editedBitmap);
                scanResults.setText(scanResults.getText() + "No of Faces Detected: " + "\n");
                scanResults.setText(scanResults.getText() + String.valueOf(faces.size()) + "\n");
                scanResults.setText(scanResults.getText() + "---------" + "\n");
                button_browse.setVisibility(View.VISIBLE);
                if (type == 1) {
                    button.setVisibility(View.GONE);
                    button_browse.setBackground(null);
                    button_browse.setText("Payment is Successful");
                    button_browse.setClickable(false);
                } else {
                    button.setVisibility(View.GONE);
                }
                button_browse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        BrowseFragment browseFragment = new BrowseFragment();
                        if (browseFragment instanceof ScanResultReceiver) {
                            browseFragment.facedetectionResultData(first, second, third);
                        }
                        fragmentTransaction.add(R.id.container, browseFragment, "facedetection");
                        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        fragmentTransaction.addToBackStack("facedetection");
                        fragmentTransaction.commit();
                    }
                });
            }
        } else {
            scanResults.setText("Could not set up the detector!");
        }
    }

    private void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(), "picture.jpg");
        imageUri = FileProvider.getUriForFile(getActivity(),
                BuildConfig.APPLICATION_ID + ".provider", photo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, PHOTO_REQUEST);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (imageUri != null) {
            outState.putParcelable(SAVED_INSTANCE_BITMAP, editedBitmap);
            outState.putString(SAVED_INSTANCE_URI, imageUri.toString());
            outState.putString(SAVED_INSTANCE_RESULT, scanResults.getText().toString());
        }
        super.onSaveInstanceState(outState);
    }


    private void launchMediaScanIntent() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(imageUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }

    private Bitmap decodeBitmapUri(Context ctx, Uri uri) throws FileNotFoundException {
        int targetW = 600;
        int targetH = 600;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(ctx.getContentResolver().openInputStream(uri), null, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        return BitmapFactory.decodeStream(ctx.getContentResolver()
                .openInputStream(uri), null, bmOptions);
    }

    @IntDef({DialogType.DIALOG_DENY, DialogType.DIALOG_NEVER_ASK})
    @Retention(RetentionPolicy.SOURCE)
    @interface DialogType {
        int DIALOG_DENY = 0, DIALOG_NEVER_ASK = 1;
    }


    //check for camera and storage access permissions
    @TargetApi(Build.VERSION_CODES.M)
    private void checkMultiplePermissions(int permissionCode, Context context) {

        String[] PERMISSIONS = {CAMERA_PERMISSION, READ_EXTERNAL_STORAGE_PERMISSION, WRITE_EXTERNAL_STORAGE_PERMISSION};
        if (!hasPermissions(context, PERMISSIONS)) {
            ActivityCompat.requestPermissions((Activity) context, PERMISSIONS, permissionCode);
        } else {

            takePicture();
        }
    }

    private boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

   /* public static void openAlertDialog(String message, String positiveBtnText, String negativeBtnText,
                                       final OnDialogButtonClickListener listener, Context mContext) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setPositiveButton(positiveBtnText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                listener.onPositiveButtonClicked();
            }
        });
        builder.setPositiveButton(positiveBtnText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                listener.onNegativeButtonClicked();
            }
        });

        builder.setTitle(mContext.getResources().getString(R.string.app_name));
        builder.setMessage(message);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setCancelable(false);
        builder.create().show();
    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();
        view = null;
        detector.release();

    }

    public void setObject(Object object) {
        this.object = object;
    }


}
