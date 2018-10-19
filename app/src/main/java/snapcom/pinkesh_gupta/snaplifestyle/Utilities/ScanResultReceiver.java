package snapcom.pinkesh_gupta.snaplifestyle.Utilities;

/**
 * Created by Rajinder on 12/10/2016.
 */
public interface ScanResultReceiver {
    /**
     * function to receive scanresult

     * @param codeContent data of the barcode scanned
     */
    public void scanResultData(String codeContent);
    public void facedetectionResultData(String smileyData,String lefteyeData,String righteyeData);

    public void scanResultData(NoScanResultException noScanData);
}
