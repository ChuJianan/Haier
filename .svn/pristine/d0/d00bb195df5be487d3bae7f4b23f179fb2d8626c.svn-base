package com.haier.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StatFs;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.RejectedExecutionException;


/**
 * ImageView extended class allowing easy downloading
 * of remote images
 */
public class RemoteImageView extends ImageView{
    
    public static String TAG = "RemoteImageView";
	
	/**
	 * Maximum number of unsuccesful tries of downloading an image
	 */
	private static int MAX_FAILURES = 3;
	
	private String mUrl;
	
	/**
	 * Currently successfully grabbed url
	 */
	private String mCurrentlyGrabbedUrl;
	
	/**
	 * Remote image download failure counter
	 */
	private int mFailure;

	/**
	 * Default image shown while loading or on url not found
	 */
	private Integer mDefaultImage;

	private ImageCache mImageCache = new ImageCache();
	
	public RemoteImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public RemoteImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RemoteImageView(Context context) {
        super(context);
    }

	/**
	 * Loads image from remote location
	 * 
	 * @param url eg. http://random.com/abz.jpgS
	 */
	public void setImageUrl(String url){
		if (mUrl != null && mUrl.equals(url) && (mCurrentlyGrabbedUrl == null ||
				(mCurrentlyGrabbedUrl != null && !mCurrentlyGrabbedUrl.equals(url)))) {
			mFailure++;			
			if(mFailure > MAX_FAILURES){
				loadDefaultImage();
				return;
			}
		} else {
			mUrl = url;
			mFailure = 0;
		}
		
		if (mImageCache.isCached(url)) {				
		    this.setImageBitmap(mImageCache.get(url));
		} else {
		    startDownload(url);
		}
	}
	
	private void startDownload(String url){
	    
	    try {
	        new DownloadTask().execute(url);
        } catch (RejectedExecutionException e) {
            // do nothing, just don't crash
        }
        
	}
	
	/**
	 * Sets default local image shown when remote one is unavailable
	 * 
	 * @param resid
	 */
	public void setDefaultImage(Integer resid){
		mDefaultImage = resid;
	}
	
	/**
	 * Loads default image
	 */
	private void loadDefaultImage(){
		if(mDefaultImage != null)
			setImageResource(mDefaultImage);
	}
	
	/**
	 * Asynchronous image download task
	 */
	class DownloadTask extends AsyncTask<String, Void, String>{
		
		private String mTaskUrl;
		private Bitmap mBmp = null;

		@Override
		public void onPreExecute() {
			loadDefaultImage();
			super.onPreExecute();
		}

		@Override
		public String doInBackground(String... params) {

		    mTaskUrl = params[0];
		    
			InputStream stream = null;
			URL imageUrl;
			Bitmap bmp = null;

			try {
				imageUrl = new URL(mTaskUrl);
				try {
					stream = imageUrl.openStream();
					bmp = BitmapFactory.decodeStream(stream);
					try {
						if(bmp != null){
								mBmp = bmp;
								mImageCache.put(mTaskUrl, bmp);
						}
					} catch (NullPointerException e) {
						Log.w(TAG, "Failed to cache "+mTaskUrl);
					}
				} catch (IOException e) {
					Log.w(TAG, "Couldn't load bitmap from url: " + mTaskUrl);
				} finally {
					closeStream(stream);
				}

			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			return mTaskUrl;
		}
		
		private void closeStream(InputStream is){
		    if(is != null){
		        try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
		    }
		}
		
		private void closeStream(OutputStream os){
		    if(os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
		}

		@Override
		public void onPostExecute(String url) {
			super.onPostExecute(url);
			
			Bitmap bmp = mImageCache.get(url);
			if(bmp == null){
				Log.w(TAG, "Trying again to download " + url);
				RemoteImageView.this.setImageUrl(url);
			} else {
				RemoteImageView.this.setImageBitmap(bmp);				
				mCurrentlyGrabbedUrl = url;				
			}
		}

	};
	
}


class FileLastModifSort implements Comparator<File>{

	public int compare(File arg0, File arg1) {
		if (arg0.lastModified() > arg1.lastModified()) {
			return 1;
		} else if (arg0.lastModified() == arg1.lastModified()) {
			return 0;
		} else {
			return -1;
		}

	}

}
