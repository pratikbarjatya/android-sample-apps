package com.ooyala.sample.players;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.ooyala.android.OoyalaPlayer;
import com.ooyala.android.OoyalaNotification;
import com.ooyala.android.OoyalaPlayerLayout;
import com.ooyala.android.PlayerDomain;
import com.ooyala.android.configuration.Options;
import com.ooyala.android.freewheelsdk.OoyalaFreewheelManager;
import com.ooyala.android.skin.OoyalaSkinLayout;
import com.ooyala.android.skin.OoyalaSkinLayoutController;
import com.ooyala.android.skin.configuration.SkinOptions;
import com.ooyala.android.ui.OptimizedOoyalaPlayerLayoutController;
import com.ooyala.sample.R;
import com.ooyala.android.util.SDCardLogcatOoyalaEventsLogger;

/**
 * This activity illustrates how to use Freewheel while manually configuring Freewheel settings
 *
 * Supported parameters for Freewheel Configuration:
 * - fw_android_mrm_network_id
 * - fw_android_ad_server
 * - fw_android_player_profile
 * - FRMSegment
 * - fw_android_site_section_id
 * - fw_android_video_asset_id
 */
public class CustomConfiguredFreewheelPlayerActivity extends AbstractHookActivity {
	public final static String getName() {
		return "Preconfigured Freewheel Player";
	}

	protected OptimizedOoyalaPlayerLayoutController playerLayoutController;
	protected OoyalaSkinLayoutController controller;


	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.player_simple_frame_layout);
		completePlayerSetup(asked);
	}

	@Override
	void completePlayerSetup(boolean asked) {

		//Initialize the player
		OoyalaSkinLayout skinLayout = (OoyalaSkinLayout) findViewById(R.id.ooyalaPlayer);

		// Create the OoyalaPlayer, with some built-in UI disabled
		PlayerDomain domain1 = new PlayerDomain(domain);
		Options options = new Options.Builder().setShowPromoImage(false).setUseExoPlayer(true).build();
		player = new OoyalaPlayer(pcode, domain1, options);

		//Create the SkinOptions, and setup React
		SkinOptions skinOptions = new SkinOptions.Builder().build();
		controller = new OoyalaSkinLayoutController(getApplication(), skinLayout, player, skinOptions);
		//Add observer to listen to fullscreen open and close events
		controller.addObserver(this);

		player.addObserver(this);

		/** DITA_START:<ph id="freewheel_custom"> **/
		OoyalaFreewheelManager fwManager = new OoyalaFreewheelManager(this, skinLayout, player);

		Map<String, String> freewheelParameters = new HashMap<String, String>();
		freewheelParameters.put("fw_android_mrm_network_id", "380912");
		freewheelParameters.put("fw_android_ad_server", "http://g1.v.fwmrm.net/");
		freewheelParameters.put("fw_android_player_profile", "90750:ooyala_android");
		freewheelParameters.put("FRMSegment", "channel=TEST;subchannel=TEST;section=TEST;mode=online;player=ooyala;beta=n");
		freewheelParameters.put("fw_android_site_section_id", "ooyala_android_internalapp");
		freewheelParameters.put("fw_android_video_asset_id", "NqcGg4bzoOmMiV35ZttQDtBX1oNQBnT-");

		fwManager.overrideFreewheelParameters(freewheelParameters);
		/** DITA_END:</ph> **/

		if (player.setEmbedCode(embedCode)) {
//      player.play();
		}
	}

}