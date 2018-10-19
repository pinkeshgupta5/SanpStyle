package snapcom.pinkesh_gupta.snaplifestyle.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import snapcom.pinkesh_gupta.snaplifestyle.R;
import snapcom.pinkesh_gupta.snaplifestyle.Utilities.NoScanResultException;
import snapcom.pinkesh_gupta.snaplifestyle.Utilities.ScanResultReceiver;

/**
 * Created by rajinders on 2/12/16.
 */

public class ScanFragment extends Fragment  {
   public static String codeContent;
    //private final String noResultErrorMsg = "No scan data received!";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IntentIntegrator integrator = new IntentIntegrator(this.getActivity()).forSupportFragment(this);
        // use forSupportFragment or forFragment method to use fragments instead of activity
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setPrompt(this.getString(R.string.scan_bar_code));
        integrator.setResultDisplayDuration(0); // milliseconds to display result on screen after scan
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.initiateScan();
    }

    /**
     * function handle scan result
     *
     * @param requestCode scanned code
     * @param resultCode  result of scanned code
     * @param intent      intent
     */
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        //ScanResultReceiver parentActivity = (ScanResultReceiver) this.getActivity();

        if (scanningResult != null) {
            //we have a result
            codeContent = scanningResult.getContents();
            //scanResultReceiver.scanResultData(codeContent);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setCancelable(true);
            builder.setTitle("Scan Result");
            builder.setMessage(codeContent);
            //builder.setInverseBackgroundForced(true);
            builder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog,
                                            int which) {


                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            BrowseFragment browseFragment = new BrowseFragment();
                            if(browseFragment instanceof ScanResultReceiver){
                                browseFragment.scanResultData(codeContent);
                            }
                            /*Bundle b = new Bundle();
                            b.putString("scandata",codeContent);
                            browseFragment.setArguments(b);*/
                            fragmentTransaction.replace(R.id.container, browseFragment, "browse");
                            fragmentTransaction.addToBackStack("browse");
                            fragmentTransaction.commit();


                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();
            //codeFormat = scanningResult.getFormatName();
            // send received data
            //parentActivity.scanResultData(codeContent);

        } else {
            // send exception
            //parentActivity.scanResultData(new NoScanResultException(noResultErrorMsg));
        }
    }



}
