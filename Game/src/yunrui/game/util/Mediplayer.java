package yunrui.game.util;

import java.io.IOException;

import com.example.game.R;

import android.content.Context;
import android.media.MediaPlayer;


public class Mediplayer {

	public static MediaPlayer truesound(Context context){
		MediaPlayer mp = MediaPlayer.create(context, R.raw.f);
		try {
			mp.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mp;
	}
	public static MediaPlayer falsePlayer(Context context){
		MediaPlayer mp=MediaPlayer.create(context, R.raw.t);
		try {
			mp.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mp;
	}
}