/**
 * 工程名:ShaVoiceService
 * 文件名:OnMainChannleListener.java
 * 包   名:com.syu.sha
 * 日   期:2015年11月23日下午7:29:13
 * 作   者:fyt 
 * Copyright (c) 2015, kexuan52@yeah.net All Rights Reserved.
 *
 */
package kun.kt.opencam.ipc;

import android.os.IInterface;

/**
 *类   名:OnMainChannleListener
 *功   能:TODO
 *
 *日  期:2015年11月23日 下午7:29:13
 * @author fyt
 *
 */
public interface OnMainChannleListener extends IInterface {
	/** Local-side IPC implementation stub class. */
	public static abstract class Stub extends android.os.Binder implements kun.kt.opencam.ipc.OnMainChannleListener {
		private static final String DESCRIPTOR = "com.syu.sha.OnMainChannleListener";

		/** Construct the stub at attach it to the interface. */
		public Stub() {
			this.attachInterface(this, DESCRIPTOR);
		}

		/**
		 * Cast an IBinder object into an com.syu.sha.OnMainChannleListener interface,
		 * generating a proxy if needed.
		 */
		public static kun.kt.opencam.ipc.OnMainChannleListener asInterface(android.os.IBinder obj) {
			if ((obj == null)) {
				return null;
			}
			IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
			if (((iin != null) && (iin instanceof kun.kt.opencam.ipc.OnMainChannleListener))) {
				return ((kun.kt.opencam.ipc.OnMainChannleListener) iin);
			}
			return new kun.kt.opencam.ipc.OnMainChannleListener.Stub.Proxy(obj);
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
			case TRANSACTION_onChannleChanged: {
				data.enforceInterface(DESCRIPTOR);
				int _arg0;
				_arg0 = data.readInt();
				this.onChannleChanged(_arg0);
				reply.writeNoException();
				return true;
			}
			}
			return super.onTransact(code, data, reply, flags);
		}

		private static class Proxy implements kun.kt.opencam.ipc.OnMainChannleListener {
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
			public void onChannleChanged(int state) throws android.os.RemoteException {
				android.os.Parcel _data = android.os.Parcel.obtain();
				android.os.Parcel _reply = android.os.Parcel.obtain();
				try {
					_data.writeInterfaceToken(DESCRIPTOR);
					_data.writeInt(state);
					mRemote.transact(Stub.TRANSACTION_onChannleChanged, _data, _reply, 0);
					_reply.readException();
				} finally {
					_reply.recycle();
					_data.recycle();
				}
			}
		}

		static final int TRANSACTION_onChannleChanged = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
	}

	public void onChannleChanged(int state) throws android.os.RemoteException;
}
