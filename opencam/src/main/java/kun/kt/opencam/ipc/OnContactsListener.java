/**
 * 工程名:ShaVoiceService
 * 文件名:OnContactsListener.java
 * 包   名:com.syu.sha
 * 日   期:2015年11月23日下午4:54:13
 * 作   者:fyt 
 * Copyright (c) 2015, kexuan52@yeah.net All Rights Reserved.
 *
 */
package kun.kt.opencam.ipc;

import android.os.IInterface;

import java.util.List;

/**
 *类   名:OnContactsListener
 *功   能:TODO
 *
 *日  期:2015年11月23日 下午4:54:13
 * @author fyt
 *
 */
public interface OnContactsListener extends IInterface {
//	public void onBluetoothState(int state);
	/** Local-side IPC implementation stub class. */
	public static abstract class Stub extends android.os.Binder implements kun.kt.opencam.ipc.OnContactsListener {
		private static final String DESCRIPTOR = "com.syu.sha.OnContactsListener";

		/** Construct the stub at attach it to the interface. */
		public Stub() {
			this.attachInterface(this, DESCRIPTOR);
		}

		/**
		 * Cast an IBinder object into an com.syu.sha.OnContactsListener interface,
		 * generating a proxy if needed.
		 */
		public static kun.kt.opencam.ipc.OnContactsListener asInterface(android.os.IBinder obj) {
			if ((obj == null)) {
				return null;
			}
			IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
			if (((iin != null) && (iin instanceof kun.kt.opencam.ipc.OnContactsListener))) {
				return ((kun.kt.opencam.ipc.OnContactsListener) iin);
			}
			return new kun.kt.opencam.ipc.OnContactsListener.Stub.Proxy(obj);
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
			case TRANSACTION_onBluetoothState: {
				data.enforceInterface(DESCRIPTOR);
				List<Contasts> contasts;
				contasts = data.readArrayList(Contasts.class.getClassLoader());
				this.onContacts(contasts);
				reply.writeNoException();
				return true;
			}
			}
			return super.onTransact(code, data, reply, flags);
		}

		private static class Proxy implements kun.kt.opencam.ipc.OnContactsListener {
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
			public void onContacts(List<Contasts> contasts) throws android.os.RemoteException {
				android.os.Parcel _data = android.os.Parcel.obtain();
				android.os.Parcel _reply = android.os.Parcel.obtain();
				try {
					_data.writeInterfaceToken(DESCRIPTOR);
					_data.writeList(contasts);
					mRemote.transact(Stub.TRANSACTION_onBluetoothState, _data, _reply, 0);
					_reply.readException();
				} finally {
					_reply.recycle();
					_data.recycle();
				}
			}
		}

		static final int TRANSACTION_onBluetoothState = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
	}
	/**
	 * 蓝牙下载的联系人
	 */
	public void onContacts(List<Contasts> contasts) throws android.os.RemoteException;
}
