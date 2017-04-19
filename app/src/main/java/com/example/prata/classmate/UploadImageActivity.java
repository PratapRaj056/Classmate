package com.example.prata.classmate;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.File;
import java.io.IOException;

public class UploadImageActivity extends AppCompatActivity {
	
	
	private static final String TAG = "UploadImageActivity";
	private static final int GALLERY_PERMISSIONS_REQUEST = 0;
	private static final int GALLERY_IMAGE_REQUEST = 1;
	public static final int CAMERA_PERMISSIONS_REQUEST = 2;
	public static final int CAMERA_IMAGE_REQUEST = 3;
    TextView data;
	ImageView imageView;
    public static final String FILE_NAME = "temp.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

	    getSupportActionBar().setTitle("Select Image");
	    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	    
        data = (TextView)findViewById(R.id.data);
	    imageView = (ImageView)findViewById(R.id.image);
        AlertDialog.Builder builder = new AlertDialog.Builder(UploadImageActivity.this);
        builder
                .setMessage(R.string.dialog_select_prompt)
                .setPositiveButton(R.string.dialog_select_gallery, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startGalleryChooser();
                    }
                })
                .setNegativeButton(R.string.dialog_select_camera, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startCamera();
                    }
                });
        builder.create().show();

    }
	
	public void startGalleryChooser() {
		if (PermissionUtils.requestPermission(this, GALLERY_PERMISSIONS_REQUEST, Manifest.permission.READ_EXTERNAL_STORAGE)) {
			Intent intent = new Intent();
			intent.setType("image/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(Intent.createChooser(intent, "Select a photo"),
					GALLERY_IMAGE_REQUEST);
		}
	}
	
	public void startCamera() {
		if (PermissionUtils.requestPermission(
				this,
				CAMERA_PERMISSIONS_REQUEST,
				Manifest.permission.READ_EXTERNAL_STORAGE,
				Manifest.permission.CAMERA)) {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			Uri photoUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", getCameraFile());
			intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			startActivityForResult(intent, CAMERA_IMAGE_REQUEST);
		}
	}
	
	public File getCameraFile() {
		File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		return new File(dir, FILE_NAME);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == GALLERY_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
			uploadImage(data.getData());
			Toast.makeText(UploadImageActivity.this,data.getDataString(),Toast.LENGTH_SHORT).show();
		} else if (requestCode == CAMERA_IMAGE_REQUEST && resultCode == RESULT_OK) {
			Uri photoUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", getCameraFile());
			uploadImage(photoUri);
			Toast.makeText(UploadImageActivity.this,photoUri.toString(),Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	public void onRequestPermissionsResult(
			int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		switch (requestCode) {
			case CAMERA_PERMISSIONS_REQUEST:
				if (PermissionUtils.permissionGranted(requestCode, CAMERA_PERMISSIONS_REQUEST, grantResults)) {
					startCamera();
				}
				break;
			case GALLERY_PERMISSIONS_REQUEST:
				if (PermissionUtils.permissionGranted(requestCode, GALLERY_PERMISSIONS_REQUEST, grantResults)) {
					startGalleryChooser();
				}
				break;
		}
	}
	
	public void uploadImage(Uri uri) {
		if (uri != null) {
			try {
				// scale the image to save on bandwidth
				Bitmap bitmap =
						scaleBitmapDown(
								MediaStore.Images.Media.getBitmap(getContentResolver(), uri),
								1200);
				
				readText(bitmap);
				imageView.setImageBitmap(bitmap);
				
			} catch (IOException e) {
				Log.d(TAG, "Image picking failed because " + e.getMessage());
				Toast.makeText(this, "R.string.image_picker_error", Toast.LENGTH_LONG).show();
			}
		} else {
			Log.d(TAG, "Image picker gave us a null image.");
			Toast.makeText(this, "R.string.image_picker_error", Toast.LENGTH_LONG).show();
		}
	}
	
	void readText(final Bitmap imageBitmap){
		
		// imageBitmap is the Bitmap image you're trying to process for text
		if(imageBitmap != null) {
			
			TextRecognizer textRecognizer = new TextRecognizer.Builder(this).build();
			
			if(!textRecognizer.isOperational()) {
				// Note: The first time that an app using a Vision API is installed on a
				// device, GMS will download a native libraries to the device in order to do detection.
				// Usually this completes before the app is run for the first time.  But if that
				// download has not yet completed, then the above call will not detect any text,
				// barcodes, or faces.
				// isOperational() can be used to check if the required native libraries are currently
				// available.  The detectors will automatically become operational once the library
				// downloads complete on device.
				Log.w(TAG, "Detector dependencies are not yet available.");
				
				// Check for low storage.  If there is low storage, the native library will not be
				// downloaded, so detection will not become operational.
				IntentFilter lowstorageFilter = new IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW);
				boolean hasLowStorage = registerReceiver(null, lowstorageFilter) != null;
				
				if (hasLowStorage) {
					Toast.makeText(this,"Low Storage", Toast.LENGTH_LONG).show();
					Log.w(TAG, "Low Storage");
				}
			}
			
			
			Frame imageFrame = new Frame.Builder()
					.setBitmap(imageBitmap)
					.build();
			
			SparseArray<TextBlock> textBlocks = textRecognizer.detect(imageFrame);
			data.setText("");
			for (int i = 0; i < textBlocks.size(); i++) {
				TextBlock textBlock = textBlocks.get(textBlocks.keyAt(i));
				Point[] points = textBlock.getCornerPoints();
				Log.i(TAG, textBlock.getValue());
				String corner = "";
				for(Point point : points){
					corner += point.toString();
				}
				//data.setText(data.getText() + "\n" + corner);
				data.setText(data.getText()+ "\n "+ textBlock.getValue() + " \n" );
				// Do something with value
                /*List<? extends Text> textComponents = textBlock.getComponents();
                for(Text currentText : textComponents) {
                    // Do your thing here }
                    mImageDetails.setText(mImageDetails.getText() + "\n" + currentText);
                }*/
			}
		}
	}
	
	public Bitmap scaleBitmapDown(Bitmap bitmap, int maxDimension) {
		
		int originalWidth = bitmap.getWidth();
		int originalHeight = bitmap.getHeight();
		int resizedWidth = maxDimension;
		int resizedHeight = maxDimension;
		
		if (originalHeight > originalWidth) {
			resizedHeight = maxDimension;
			resizedWidth = (int) (resizedHeight * (float) originalWidth / (float) originalHeight);
		} else if (originalWidth > originalHeight) {
			resizedWidth = maxDimension;
			resizedHeight = (int) (resizedWidth * (float) originalHeight / (float) originalWidth);
		} else if (originalHeight == originalWidth) {
			resizedHeight = maxDimension;
			resizedWidth = maxDimension;
		}
		return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
}
