package com.example.osmeuslugares;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;

public class Sonidos {

	SoundPool soundPool;
	int idSonido1, idSonido2;
	Activity activity;

	/**
	 * 
	 */
	public Sonidos(Activity activity) {
		super();
		// TODO Auto-generated constructor stub
		this.activity = activity;
		// SoundPool(Max repro simult‡neas, tipo de stream, calidad)
		soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		idSonido1 = soundPool.load(activity, R.raw.sonido1, 0);
		idSonido2 = soundPool.load(activity, R.raw.sonido2, 0);

	}

	public void playSonido1() {
		soundPool.play(idSonido1, 1, 1, 0, 0, 1);
	}

	public void playSonido2() {
		soundPool.play(idSonido2, 1, 1, 0, 0, 1);
	}

}