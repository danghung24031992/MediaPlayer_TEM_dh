package com.dh.mediaplayer.notification;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.RemoteControlClient;
import android.media.RemoteControlClient.MetadataEditor;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.dh.mediaplayer.MainActivity;
import com.dh.mediaplayer.R;
import com.dh.mediaplayer.bean.SongBean;
import com.dh.mediaplayer.bean.UtilsSong;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SongService extends Service implements AudioManager.OnAudioFocusChangeListener{
	String LOG_CLASS = "SongService";
	private MediaPlayer mp;
	int NOTIFICATION_ID = 1111;
	public static final String NOTIFY_PREVIOUS = "audioplayer.previous";
	public static final String NOTIFY_DELETE = "audioplayer.delete";
	public static final String NOTIFY_PAUSE = "audioplayer.pause";
	public static final String NOTIFY_PLAY = "audioplayer.play";
	public static final String NOTIFY_NEXT = "audioplayer.next";
	
	private ComponentName remoteComponentName;
	private RemoteControlClient remoteControlClient;
	AudioManager audioManager;
	Bitmap mDummyAlbumArt;
	private static Timer timer; 
	private static boolean currentVersionSupportBigNotification = false;
	private static boolean currentVersionSupportLockScreenControls = false;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		mp = new MediaPlayer();
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        
        currentVersionSupportBigNotification = UtilsSong.currentVersionSupportBigNotification();
        currentVersionSupportLockScreenControls = UtilsSong.currentVersionSupportLockScreenControls();
        timer = new Timer();
        mp.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				Controls.nextControl(MainActivity.context.getApplicationContext());
			}
		});
		super.onCreate();
	}

	/**
	 * Send message from timer
	 * @author jonty.ankit
	 */
	private class MainTask extends TimerTask{ 
        public void run(){
            handler.sendEmptyMessage(0);
        }
    } 
	
	 private final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
        	if(mp != null){
        		int progress = (mp.getCurrentPosition()*100) / mp.getDuration();
        		Integer i[] = new Integer[3];
        		i[0] = mp.getCurrentPosition();
        		i[1] = mp.getDuration();
        		i[2] = progress;
        		try{
        			PlayerConstants.PROGRESSBAR_HANDLER.sendMessage(PlayerConstants.PROGRESSBAR_HANDLER.obtainMessage(0, i));
        		}catch(Exception e){}
        	}
    	}
    };


    @SuppressLint("NewApi")
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		try {
			SongBean data = PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER);
			if(currentVersionSupportLockScreenControls){
				RegisterRemoteClient();
			}
			String songPath = data.getLink();
			playSong(songPath, data);
			newNotification();
			
			PlayerConstants.SONG_CHANGE_HANDLER = new Handler(new Callback() {
				@Override
				public boolean handleMessage(Message msg) {
					SongBean data = PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER);
					String songPath = data.getLink();
					newNotification();
					try{
						playSong(songPath, data);
						MainActivity.changeUI();
						AudioPlayerActivity.changeUI();
					}catch(Exception e){
						e.printStackTrace();
					}
					return false;
				}
			});
			
			PlayerConstants.PLAY_PAUSE_HANDLER = new Handler(new Callback() {
				@Override
				public boolean handleMessage(Message msg) {
					String message = (String)msg.obj;
					if(mp == null)
						return false;
					if(message.equalsIgnoreCase(getResources().getString(R.string.play))){
						PlayerConstants.SONG_PAUSED = false;
						if(currentVersionSupportLockScreenControls){
							remoteControlClient.setPlaybackState(RemoteControlClient.PLAYSTATE_PLAYING);
						}
						mp.start();
					}else if(message.equalsIgnoreCase(getResources().getString(R.string.pause))){
						PlayerConstants.SONG_PAUSED = true;
						if(currentVersionSupportLockScreenControls){
							remoteControlClient.setPlaybackState(RemoteControlClient.PLAYSTATE_PAUSED);
						}
						mp.pause();
					}
					newNotification();
					try{
						MainActivity.changeButton();
						AudioPlayerActivity.changeButton();
					}catch(Exception e){}
					Log.d("TAG", "TAG Pressed: " + message);
					return false;
				}
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return START_STICKY;
	}

	/**
	 * Notification
	 * Custom Bignotification is available from API 16
	 */
	@SuppressLint("NewApi")
	private void newNotification() {
		String songName = PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER).getMusicName();
		String albumName = PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER).getAlbumName();
		RemoteViews simpleContentView = new RemoteViews(MainActivity.context.getApplicationContext().getPackageName(), R.layout.custom_notification);
		RemoteViews expandedView = new RemoteViews(MainActivity.context.getApplicationContext().getPackageName(), R.layout.big_notification);
		 
		Notification notification = new NotificationCompat.Builder(MainActivity.context.getApplicationContext())
        .setSmallIcon(R.mipmap.ic_music)
        .setContentTitle(songName).build();

		setListeners(simpleContentView);
		setListeners(expandedView);
		
		notification.contentView = simpleContentView;
		if(currentVersionSupportBigNotification){
			notification.bigContentView = expandedView;
		}
		
		try{
			long albumId = PlayerConstants.SONGS_LIST.get(PlayerConstants.SONG_NUMBER).getAlbum_ID();
			Bitmap albumArt = UtilsSong.getAlbumart(MainActivity.context.getApplicationContext(), albumId);
			if(albumArt != null){
				notification.contentView.setImageViewBitmap(R.id.imageViewAlbumArt, albumArt);
				if(currentVersionSupportBigNotification){
					notification.bigContentView.setImageViewBitmap(R.id.imageViewAlbumArt, albumArt);
				}
			}else{
				notification.contentView.setImageViewResource(R.id.imageViewAlbumArt, R.mipmap.ic_play_bottom_layout);
				if(currentVersionSupportBigNotification){
					notification.bigContentView.setImageViewResource(R.id.imageViewAlbumArt, R.mipmap.ic_play_bottom_layout);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		if(PlayerConstants.SONG_PAUSED){
			notification.contentView.setViewVisibility(R.id.btnPause, View.GONE);
			notification.contentView.setViewVisibility(R.id.btnPlay, View.VISIBLE);

			if(currentVersionSupportBigNotification){
				notification.bigContentView.setViewVisibility(R.id.btnPause, View.GONE);
				notification.bigContentView.setViewVisibility(R.id.btnPlay, View.VISIBLE);
			}
		}else{
			notification.contentView.setViewVisibility(R.id.btnPause, View.VISIBLE);
			notification.contentView.setViewVisibility(R.id.btnPlay, View.GONE);

			if(currentVersionSupportBigNotification){
				notification.bigContentView.setViewVisibility(R.id.btnPause, View.VISIBLE);
				notification.bigContentView.setViewVisibility(R.id.btnPlay, View.GONE);
			}
		}

		notification.contentView.setTextViewText(R.id.textSongName, songName);
		notification.contentView.setTextViewText(R.id.textAlbumName, albumName);
		if(currentVersionSupportBigNotification){
			notification.bigContentView.setTextViewText(R.id.textSongName, songName);
			notification.bigContentView.setTextViewText(R.id.textAlbumName, albumName);
		}
		notification.flags |= Notification.FLAG_ONGOING_EVENT;
		startForeground(NOTIFICATION_ID, notification);
	}
	
	/**
	 * Notification click listeners
	 * @param view
	 */
	public void setListeners(RemoteViews view) {

		Intent previous = new Intent(NOTIFY_PREVIOUS);
		Intent delete = new Intent(NOTIFY_DELETE);
		Intent pause = new Intent(NOTIFY_PAUSE);
		Intent next = new Intent(NOTIFY_NEXT);
		Intent play = new Intent(NOTIFY_PLAY);
		
		PendingIntent pPrevious = PendingIntent.getBroadcast(MainActivity.context.getApplicationContext(), 0, previous, PendingIntent.FLAG_UPDATE_CURRENT);
		view.setOnClickPendingIntent(R.id.btnBack, pPrevious);

		PendingIntent pDelete = PendingIntent.getBroadcast(MainActivity.context.getApplicationContext(), 0, delete, PendingIntent.FLAG_UPDATE_CURRENT);
		view.setOnClickPendingIntent(R.id.btnDelete, pDelete);
		
		PendingIntent pPause = PendingIntent.getBroadcast(MainActivity.context.getApplicationContext(), 0, pause, PendingIntent.FLAG_UPDATE_CURRENT);
		view.setOnClickPendingIntent(R.id.btnPause, pPause);
		
		PendingIntent pNext = PendingIntent.getBroadcast(MainActivity.context.getApplicationContext(), 0, next, PendingIntent.FLAG_UPDATE_CURRENT);
		view.setOnClickPendingIntent(R.id.btnNext, pNext);
		
		PendingIntent pPlay = PendingIntent.getBroadcast(MainActivity.context.getApplicationContext(), 0, play, PendingIntent.FLAG_UPDATE_CURRENT);
		view.setOnClickPendingIntent(R.id.btnPlay, pPlay);

	}
	
	@Override
	public void onDestroy() {
		if(mp != null){
			mp.stop();
			mp = null;
		}
		super.onDestroy();
	}

	/**
	 * Play song, Update Lockscreen fields
	 * @param songPath
	 * @param data
	 */
	@SuppressLint("NewApi")
	private void playSong(String songPath, SongBean data) {
		try {
			if(currentVersionSupportLockScreenControls){
				UpdateMetadata(data);
				remoteControlClient.setPlaybackState(RemoteControlClient.PLAYSTATE_PLAYING);
			}
			mp.reset();
			mp.setDataSource(songPath);
			mp.prepare();
			mp.start();
			timer.scheduleAtFixedRate(new MainTask(), 0, 100);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@SuppressLint("NewApi")
	private void RegisterRemoteClient(){
		remoteComponentName = new ComponentName(MainActivity.context.getApplicationContext(), new NotificationBroadcast().ComponentName());
		 try {
		   if(remoteControlClient == null) {
			   audioManager.registerMediaButtonEventReceiver(remoteComponentName);
			   Intent mediaButtonIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
			   mediaButtonIntent.setComponent(remoteComponentName);
			   PendingIntent mediaPendingIntent = PendingIntent.getBroadcast(this, 0, mediaButtonIntent, 0);
			   remoteControlClient = new RemoteControlClient(mediaPendingIntent);
			   audioManager.registerRemoteControlClient(remoteControlClient);
		   }
		   remoteControlClient.setTransportControlFlags(
				   RemoteControlClient.FLAG_KEY_MEDIA_PLAY |
				   RemoteControlClient.FLAG_KEY_MEDIA_PAUSE |
				   RemoteControlClient.FLAG_KEY_MEDIA_PLAY_PAUSE |
				   RemoteControlClient.FLAG_KEY_MEDIA_STOP |
				   RemoteControlClient.FLAG_KEY_MEDIA_PREVIOUS |
				   RemoteControlClient.FLAG_KEY_MEDIA_NEXT);
	  }catch(Exception ex) {
	  }
	}
	
	@SuppressLint("NewApi")
	private void UpdateMetadata(SongBean data){
		if (remoteControlClient == null)
			return;
		MetadataEditor metadataEditor = remoteControlClient.editMetadata(true);
		metadataEditor.putString(MediaMetadataRetriever.METADATA_KEY_ALBUM, data.getAlbumName());
		metadataEditor.putString(MediaMetadataRetriever.METADATA_KEY_ARTIST, data.getArtist());
		metadataEditor.putString(MediaMetadataRetriever.METADATA_KEY_TITLE, data.getAlbumName());
		mDummyAlbumArt = UtilsSong.getAlbumart(MainActivity.context.getApplicationContext(), data.getAlbum_ID());
		if(mDummyAlbumArt == null){
			mDummyAlbumArt = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_play_bottom_layout);
		}
		metadataEditor.putBitmap(MetadataEditor.BITMAP_KEY_ARTWORK, mDummyAlbumArt);
		metadataEditor.apply();
		audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
	}

	@Override
	public void onAudioFocusChange(int focusChange) {

	}
}