package yunrui.game;

import yunrui.game.been.Getquestion;
import yunrui.game.util.Mediplayer;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.game.R;

public class SingleSelectionDialog extends Dialog {
MainActivity mainActivity=new MainActivity();
	
	public SingleSelectionDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);

	}

	public SingleSelectionDialog(Context context, int theme) {
		super(context, theme);
	}

	public SingleSelectionDialog(Context context) {
		super(context);
	}

	public static class Builder {
		private boolean run=DataUtil.isRun();
		private int j=DataUtil.getJ();
		private int mf=DataUtil.getMf();
		private int core=30;
		private Context context;
		private CharSequence title;
		private String[] mListItem;
		private int mClickedDialogEntryIndex;
		private DialogInterface.OnClickListener mOnClickListener;

		public Builder(Context context) {
			this.context = context;
		}

		/**
		 * Set the Dialog title from resource
		 * 
		 * @param title
		 * @return
		 */
		public Builder setTitle(int title) {
			this.title = (String) context.getText(title);
			return this;
		}

		/**
		 * Set the Dialog title from String
		 * 
		 * @param title
		 * @return
		 */
		public Builder setTitle(CharSequence title) {
			this.title = title;
			return this;
		}

		public CharSequence[] getItems() {
			return mListItem;
		}

		public Builder setItems(String[] mListItem) {
			this.mListItem = mListItem;
			return this;
		}
		
		
		public Builder setSingleChoiceItems(String[] items, int checkedItem, final OnClickListener listener) {
			this.mListItem = items;
            this.mOnClickListener = listener;
            this.mClickedDialogEntryIndex = checkedItem;
            return this;
        } 


	

		public SingleSelectionDialog create() {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final SingleSelectionDialog dialog = new SingleSelectionDialog(
					context, R.style.Dialog);
			View layout = inflater.inflate(R.layout.single_selection_dialog,
					null);
			dialog.addContentView(layout, new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			AlertDialog ad;
			
			if(mListItem == null){
				throw new RuntimeException("Entries should not be empty");
			}
			ListView lvListItem = (ListView) layout.findViewById(R.id.lvListItem);
//			android.R.layout.simple_list_item_single_choice
			//lvListItem.setAdapter(new ArrayAdapter(context, android.R.layout.simple_list_item_single_choice, android.R.id.text1, mListItem));
//			SingleSelectionAdapter mSingleSelectionAdapter = new SingleSelectionAdapter(context, R.layout.single_list_item, R.id.ctvListItem, mListItem);
//			lvListItem.setAdapter(mSingleSelectionAdapter);
			lvListItem.setAdapter(new ArrayAdapter(context,  R.layout.single_selection_list_item, R.id.ctvListItem, mListItem));
			lvListItem.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					mOnClickListener.onClick(dialog, position);
					i=DataUtil.getI();
					j=DataUtil.getJ();
					mf=DataUtil.getMf();
			    	   if (Test.dotest(position)) {
		        		if (i>=0&&i<=5) {
		    				run=true;
		    				 i=0;
		    				 j=j-6;
		    				 mf+=core;
		    				 imf=core;
//		    				 MainActivity.surfaceCreated(MainActivity.surfaceHolder);
		    			}
		    			if (i>5&&i<=10) {
		    				run=true;
		    				 i=0;
		    				j=j-5;
		    				mf+=(core-5);
		    				imf=(core-5);
		    			}
		    			if (i>10&&i<=15) {
		    				run=true;
		    				 i=0;
		    				j=j-4;
		    				mf+=(core-10);
		    				imf=(core-10);
		    			}
		    			if (i>15&&i<=20) {
		    				run=true;
		    				 i=0;
		    				j=j-3;
		    				imf=(core-15);
		    				mf+=(core-15);
		    			}
		    			if (i>20&&i<=25) {
		    				run=true;
		    				 i=0;
		    				j=j-2;
		    				mf+=(core-20);
		    				imf=(core-20);
		    			}
		    			if (i>25&&i<=30) {
		    				run=true;
		    				 i=0;
		    				j=j-1;
		    				mf+=(core-25);
		    				imf=(core-25);
		    			}
		    			if (!Test.music2) {
		    				soundPool=Mediplayer.truesound(context);
							soundPool.start();
						}
		    			
		    			} else {
		    				run=true;
		    				i=0;
		    				j=j-0;
		    				mf+=(0);
		    				imf=(0);
		    				if (!Test.music2) {
		    					soundPool=Mediplayer.falsePlayer(context);
								soundPool.start();
							}
		    				
						}
			    	   if (mf<0) {
						mf=0;
					}
			    	DataUtil.setRun(run);
			    	DataUtil.setJ(j);
			    	DataUtil.setMf(mf);
			    	DataUtil.setClick(true);
			    	Getquestion.mfList.add(imf);
			    	Getquestion.questtimeList.add(i);
			    	
			      }		      
				
			});
			lvListItem.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			lvListItem.setItemChecked(mClickedDialogEntryIndex, true);
			lvListItem.setSelection(mClickedDialogEntryIndex);
//			mSingleSelectionAdapter.setSelection(mClickedDialogEntryIndex);
			
			
			

			TextView tvHeader = (TextView)layout.findViewById(R.id.title);
			tvHeader.setText(title);
			//dialog.setTitle(title);

			
			
			return dialog;
		}
		
		
		private MediaPlayer soundPool;	
	}
	public static int i;
	static int imf=0;
	
	  
	
	

}
