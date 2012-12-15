package edu.jhu.ep.butlerdidit.service;

import java.util.LinkedList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

abstract class GSBroadcastReceiver<T> extends BroadcastReceiver {
	
	protected LinkedList<T> listeners = new LinkedList<T>();
	
	public void registerListener(T listener) {
		listeners.add(listener);
	}
	
	public void unregisterListener(T listener) {
		listeners.remove(listener);
	}

	@Override
	public abstract void onReceive(Context context, Intent intent);
}
