/**
 * 工程名:ShaVoiceService
 * 文件名:MusicDataListener.java
 * 包   名:com.syu.sha.ipc
 * 日   期:2015年12月15日下午5:34:42
 * 作   者:fyt 
 * Copyright (c) 2015, kexuan52@yeah.net All Rights Reserved.
 *
 */
package kun.kt.opencam.ipc;

import android.os.IInterface;

import java.util.List;

/**
 *类   名:MusicDataListener
 *功   能:TODO
 *
 *日  期:2015年12月15日 下午5:34:42
 * @author fyt
 *
 */
public interface OnMusicDataListener extends IInterface {
	
	/** Local-side IPC implementation stub class. */
	public static abstract class Stub extends android.os.Binder implements kun.kt.opencam.ipc.OnMusicDataListener {
		private static final String DESCRIPTOR = "com.syu.sha.OnMusicDataListener";

		/** Construct the stub at attach it to the interface. */
		public Stub() {
			this.attachInterface(this, DESCRIPTOR);
		}

		/**
		 * Cast an IBinder object into an com.syu.sha.OnMusicDataListener interface,
		 * generating a proxy if needed.
		 */
		public static kun.kt.opencam.ipc.OnMusicDataListener asInterface(android.os.IBinder obj) {
			if ((obj == null)) {
				return null;
			}
			IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
			if (((iin != null) && (iin instanceof kun.kt.opencam.ipc.OnMusicDataListener))) {
				return ((kun.kt.opencam.ipc.OnMusicDataListener) iin);
			}
			return new kun.kt.opencam.ipc.OnMusicDataListener.Stub.Proxy(obj);
		}

		@Override
		public android.os.IBinder asBinder() {
			return this;
		}

		@Override
		public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags)
				throws android.os.RemoteException {
			switch (code) {
			case INTERFACE_TRANSACTION: {
				reply.writeString(DESCRIPTOR);
				return true;
			}
			case TRANSACTION_onMusicDatas: {
				data.enforceInterface(DESCRIPTOR);
				List<Song> songs;
				songs = data.readArrayList(Song.class.getClassLoader());
				this.onMusicDatas(songs);
				reply.writeNoException();
				return true;
			}
			}
			return super.onTransact(code, data, reply, flags);
		}

		private static class Proxy implements kun.kt.opencam.ipc.OnMusicDataListener {
			private android.os.IBinder mRemote;

			Proxy(android.os.IBinder remote) {
				mRemote = remote;
			}

			@Override
			public android.os.IBinder asBinder() {
				return mRemote;
			}

			public String getInterfaceDescriptor() {
				return DESCRIPTOR;
			}

			@Override
			public void onMusicDatas(List<Song> songs) throws android.os.RemoteException {
				android.os.Parcel _data = android.os.Parcel.obtain();
				android.os.Parcel _reply = android.os.Parcel.obtain();
				try {
					_data.writeInterfaceToken(DESCRIPTOR);
					_data.writeList(songs);
					mRemote.transact(Stub.TRANSACTION_onMusicDatas, _data, _reply, 0);
					_reply.readException();
				} finally {
					_reply.recycle();
					_data.recycle();
				}
			}
		}

		static final int TRANSACTION_onMusicDatas = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
	}
	/**
	 * 音乐数据
	 */
	public void onMusicDatas(List<Song> songs) throws android.os.RemoteException;
}
