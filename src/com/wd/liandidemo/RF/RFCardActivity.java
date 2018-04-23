/*package com.wd.liandidemo.RF;


import cn.wonders.pos_qdg.BaseActivity;

import com.landicorp.android.eptapi.card.MifareDriver;
import com.landicorp.android.eptapi.card.RFCpuCardDriver;
import com.landicorp.android.eptapi.card.RFDriver;
import com.landicorp.android.eptapi.device.RFCardReader;
import com.landicorp.android.eptapi.exception.RequestException;
import com.landicorp.android.eptapi.utils.BytesUtil;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

*//**
 * There are all rf card operations samples in this Activity.
 * @author chenwei
 *
 *//*
public class RFCardActivity extends BaseActivity {
	public static String QMoney = "000000000000";
	
	
	
	private RFCardReaderSample rfCardSample;

	*//** Called when the activity is first created. *//*
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.rf_card);
	    
		String s = "15.1";
		String d =  (int) ((Double.parseDouble(s))*100) +"";
		   
		QMoney = QMoney.substring(0, QMoney.length()-d.length())+d;
	    
	    rfCardSample = new RFCardReaderSample(this) {
			
	    	public void onDeviceServiceCrash() {
				// Handle in 'RFCardActivity'
				RFCardActivity.this.onDeviceServiceCrash();
			}
			
			@Override
			protected void displayRFCardInfo(String cardInfo) {
				// Handle in 'RFCardActivity'
				RFCardActivity.this.displayInfo(cardInfo);
			}
		};

		// Create all listener to listen user input
		
		findViewById(R.id.button_search_up_card_and_read).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				displayInfo(" ------------ start search  ------------ ");
				rfCardSample.searchCard(onSearchListener);
				
			}
		});
		
		findViewById(R.id.button_stop_search).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				displayInfo(" ### stop search ### \n\n");
				rfCardSample.stopSearch();
			}
		});
		
		
	}
	
	 * 重启消费服务
	 
	private void StartServce(boolean isError){
		if(!isError)
			BuzzerTools.startOKV(RFCardActivity.this,false);
		rfCardSample.stopSearch();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BuzzerTools.startOKV(RFCardActivity.this,true);
		rfCardSample.searchCard(onSearchListener);
	}
	
	*//**
	 * Create a listener to listen the result of search card.
	 *//*
	private String driverName;
	private RFCardReader.OnSearchListener onSearchListener = new RFCardReader.OnSearchListener() {
		@Override
		public void onCrash() {
			//失败
			onDeviceServiceCrash();
//			StartServce();
		}
		
		@Override
		public void onFail(int error) {
			//失败
			StartServce(false);
			Log.e("tag", "SEARCH ERROR - "+getErrorDescription(error));
		}
		
		@Override
		public void onCardPass(int cardType) {
			// Choose the right card driver .
			switch (cardType) {
			case S50_CARD:
				driverName = "S50";
				break;
			case S70_CARD:
				driverName = "S70";
				break;
			case CPU_CARD:
			case PRO_CARD:
			case S50_PRO_CARD: // The card of this type can use 'S50' driver too.
			case S70_PRO_CARD: // The card of this type can use 'S70' driver too.
				driverName = "PRO";
				break;
			default:
				//失败
				Log.e("tag", "Search card fail, unknown card type!");
				return;
			}

			Log.e("tag", "rf card detected, and use "+driverName+" driver to read it!");
			try {
				RFCardReader.getInstance().activate(driverName, onActiveListener);
			} catch (RequestException e) {
				e.printStackTrace();
				onDeviceServiceCrash();
			}
		}
		
		public String getErrorDescription(int code) {
			switch(code){
			case ERROR_CARDNOACT:
				return "Pro card or TypeB card is not activated";
			case ERROR_CARDTIMEOUT:
				return "No response";
			case ERROR_PROTERR : 
				return "The card return data does not meet the requirements of the protocal"; 		
			case ERROR_TRANSERR:
				return "Communication error"; 
			}
			return "unknown error ["+code+"]";
		}
	};
	

	
	
	*//**
	 * Create a listener to listen the result of activate card.
	 *//*
	private RFCardReader.OnActiveListener onActiveListener = new RFCardReader.OnActiveListener() {
		
		@Override
		public void onCrash() {
			onDeviceServiceCrash();			
		}
		
		@Override
		public void onUnsupport(String driverName) {
			//	All driver names using in this example have already support.
		}
		
		@Override
		public void onCardActivate(RFDriver cardDriver) {
			byte[] serial = getLastCardSerialNo();
			
			Log.e("tag", "card activated!"+BytesUtil.bytesToInt(serial));
			if(cardDriver instanceof RFCpuCardDriver) {
				// It is assumed to be UP card
				Log.e("tag", "if里面的");
				UPCardReader reader = new UPCardReader((RFCpuCardDriver) cardDriver) {
					@Override
					protected void showErrorMessage(String msg) {
						Log.e("tag", "showErrorMessage里面的" + msg);
						StartServce(false);
						rfCardSample.displayRFCardInfo(msg);
					}
					
					@Override
					protected void onServiceCrash() {
						Log.e("tag", "onServiceCrash里面的");
						rfCardSample.onDeviceServiceCrash();
					}
					
					@Override
					protected void onDataRead(String pan, String track2, String track3,
							String expiredDate, byte[] serialNo, String readTime) {
						Log.e("tag", "onDataRead里面的");
						StringBuilder infoBuilder = new StringBuilder();
						infoBuilder.append("PAN [");
						infoBuilder.append(pan);
						infoBuilder.append("]\n");

						infoBuilder.append("TRACK2 [");
						infoBuilder.append(track2);
						infoBuilder.append("]\n");

						infoBuilder.append("TRACK3 [");
						infoBuilder.append(track3);
						infoBuilder.append("]\n");

						infoBuilder.append("EXPIRED DATE [");
						infoBuilder.append(expiredDate);
						infoBuilder.append("]\n");
						
						rfCardSample.displayRFCardInfo(infoBuilder.toString());
					}
				};
				
				reader.startRead(new PBOClistener() {
					
					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						StartServce(true);
//						rfCardSample.displayRFCardInfo("SUCCESS");
					}
					
					@Override
					public void onFild() {
						// TODO Auto-generated method stub
						
					}
				});
			}
			else if(cardDriver instanceof MifareDriver) {
				// Use MifareOneCardReader to do some operations.
				Log.e("tag", "else里面的");
				MifareOneCardReader card = new MifareOneCardReader((MifareDriver)cardDriver) {
					
					@Override
					protected void showErrorMessage(String msg) {
						StartServce(false);
						rfCardSample.displayRFCardInfo(msg);
					}
					
					@Override
					protected void onDeviceServiceException() {
						rfCardSample.onDeviceServiceCrash();
					}
					
					@Override
					protected void onDataRead(String info) {
						rfCardSample.displayRFCardInfo(info);
					}
				};
				
				card.startRead();
			}
			else {
				// It's never gonna happen.
				Log.e("tag", "都没有");
			}
		}
		
		@Override
		public void onActivateError(int code) {
			StartServce(false);
			Log.e("tag", "ACTIVATE ERROR - "+getErrorDescription(code));
		}
		
		public String getErrorDescription(int code){		
			switch(code){
			case ERROR_TRANSERR:
				return "Communication error"; 
			case ERROR_PROTERR : 
				return "The card return data does not meet the requirements of the protocal"; 		
			case ERROR_CARDTIMEOUT:
				return "No response";
			}
			return "unknown error ["+code+"]";
		}
	};
	
    
	@Override
	protected void onResume() {
		super.onResume();
		
		bindDeviceService();
	}
	
	// Sometimes you need to release the right of using device before other application 'onStart'.
	@Override
	protected void onPause() {
		super.onPause();
		
		unbindDeviceService();
	}
	
	*//**
	 * If device service crashed, quit application or try to relogin service again.
	 *//*
	public void onDeviceServiceCrash() {
		bindDeviceService();
	}
	
	*//**
	 * All device operation result infomation will be displayed by this method.
	 * 
	 * @param info
	 *//*
	@SuppressLint("NewApi") public void displayInfo(String info) {
		EditText infoEditText = (EditText) findViewById(R.id.info_text);
		String text = infoEditText.getText().toString();
		if (text.isEmpty()) {
			infoEditText.setText(info);
		} else {
			infoEditText.setText(text + "\n" + info);
		}
		infoEditText.setSelection(infoEditText.length());
	}

	
}
*/